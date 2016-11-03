package com.lightbend.demo.akkastreams

import akka.NotUsed
import akka.stream.FlowShape
import akka.stream.scaladsl.{Broadcast, Concat, Flow, GraphDSL, Source, Zip}

object FilterElements {

  object IIRFlow {

    def apply(internalFir: Flow[Double, Double, NotUsed]) = {
      Flow.fromGraph(GraphDSL.create() { implicit b =>
        import GraphDSL.Implicits._

        val start = Source.single(0.0d)
        val concat = b.add(Concat[Double]())

        val zip = b.add(Zip[Double, Double]())
        val bcast = b.add(Broadcast[Double](2))
        // By experimentation:
        // ... bcast ~> internalFir ~> concat doesn't work. Using an extra broadcast
        // with a single output solves the problem.
        // TODO: investigate why this solves the problem... There must be a fundamental cause at play...
        val addBcast = b.add(Broadcast[Double](1))

        zip.out.map { case (in, feedback) => in + feedback} ~> bcast ~> internalFir ~> addBcast

        zip.in1 <~ concat <~ start
        concat <~ addBcast

        FlowShape[Double, Double](zip.in0, bcast.out(1))
      })
    }
  }

  def buildIIR(stages: Seq[FilterStage]): Flow[Double, Double, NotUsed] = {
    require(stages.nonEmpty, "There should be at least one stage in an IIR filter")
    require(stages.forall { case FilterStage(n, _) => n >= 1}, "A filter stage should have at least a delay of one")
    require(stages.head.delay >=1, "The first stage in an IIR filter should have at least a delay of two")

    val firstStage = stages.head
    val adaptedStages = FilterStage(firstStage.delay - 1, firstStage.coefficient) +: stages.tail
    IIRFlow(buildFIR(adaptedStages, FirInitialZero()))
  }

  def buildFIR(stages: Seq[FilterStage],
               initialStage: Flow[Double, (Double, Double), NotUsed] = FirInitial()
              ): Flow[Double, Double, NotUsed] = {
    require(stages.nonEmpty, "There should be at least one stage in an FIR filter")
    require(stages.forall { case FilterStage(n, _) => n >= 1}, "A filter stage should have at least a delay of one")

    val FilterStage(firstStageDelay, firstStageCoef) = stages.head
    val firstStage = DelayLineFlow(firstStageDelay, firstStageCoef)
    val firCoreFLow = (stages.tail foldLeft firstStage) {
      case (flow, FilterStage(delay, coef)) => flow.via(DelayLineFlow(delay, coef))
    }
    initialStage.via(firCoreFLow).via(FirSelectOut())
  }

  object FirInitial {
    def apply() = {
      Flow.fromFunction[Double, (Double, Double)] { sample =>
        (sample, sample)
      }
    }
  }

  object FirInitialZero {
    def apply() = {
      Flow.fromFunction[Double, (Double, Double)] { sample =>
        (sample, 0.0d)
      }
    }
  }

  object FirSelectOut {
    def apply() = {
      Flow.fromFunction[(Double, Double), Double] { case (_, out) => out}
    }
  }

  object DelayLineFlow {
    def apply(delay: Int, scalaFactor: Double) = {
      val eq = Array.fill(delay)(0.0d)
      var idx = 0
      Flow.fromFunction[(Double, Double), (Double, Double)] { case (sample, ff) =>
        val delayedSample = eq(idx)
        eq(idx) = sample
        idx = (idx + 1) % delay
        (delayedSample, ff + delayedSample * scalaFactor)
      }
    }
  }

  object DelayLineFlowAlt {
    def apply(delay: Int, scaleFactor: Double) = {
      val eq = MQueue(List.fill(delay)(0.0d): _*)
      Flow.fromFunction[(Double, Double), (Double, Double)] { case (sample, ff) =>
        eq.enqueue(sample)
        val delayedSample = eq.dequeue()
        (delayedSample, ff + delayedSample * scaleFactor)
      }
    }
  }
}
