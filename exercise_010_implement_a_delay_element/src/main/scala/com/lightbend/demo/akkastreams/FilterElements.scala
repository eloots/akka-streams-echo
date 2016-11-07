package com.lightbend.demo.akkastreams

import akka.stream.scaladsl.Flow

object FilterElements {

  object DelayLineFlow {
    def apply(delay: Int, scaleFactor: Double) = {
      val eq = Array.fill(delay)(0.0d)
      var idx = 0
      Flow.fromFunction[(Double, Double), (Double, Double)] { case (sample, ff) =>
        val delayedSample = eq(idx)
        eq(idx) = sample
        idx = (idx + 1) % delay
        (delayedSample, ff + delayedSample * scaleFactor)
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
