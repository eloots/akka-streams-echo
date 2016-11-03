package com.lightbend.demo.akkastreams

import akka.NotUsed
import akka.stream.scaladsl.Flow

object FilterElements {

  def buildFIR(stages: Seq[FilterStage], initialStage: Flow[Double, (Double, Double), NotUsed] = FirInitial()) = {
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
