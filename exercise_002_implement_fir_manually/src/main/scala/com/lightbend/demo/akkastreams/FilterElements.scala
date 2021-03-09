package org.applied.akkastreams

import akka.NotUsed
import akka.stream.scaladsl.Flow

object FilterElements {

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
    def apply(delay: Int, scaleFactor: Double): Flow[(Double, Double), (Double, Double), NotUsed] = {
      Flow[(Double, Double)].statefulMapConcat { () =>
        // mutable state needs to be kept inside the stage
        val eq = Array.fill(delay)(0.0d)
        var idx = 0

      {
        case (sample, ff) =>
          val delayedSample = eq(idx)
          eq(idx) = sample
          idx = (idx + 1) % delay
          Iterable((delayedSample, ff + delayedSample * scaleFactor))
      }
      }
    }
  }

  object DelayLineFlowAlt {
    def apply(delay: Int, scaleFactor: Double): Flow[(Double, Double), (Double, Double), NotUsed] = {
      Flow[(Double, Double)].statefulMapConcat { () =>
        // mutable state needs to be kept inside the stage
        val eq = MQueue(List.fill(delay)(0.0d): _*)

      {
        case (sample, ff) =>
          eq.enqueue(sample)
          val delayedSample = eq.dequeue()
          Iterable((delayedSample, ff + delayedSample * scaleFactor))
      }
      }
    }
  }
}
