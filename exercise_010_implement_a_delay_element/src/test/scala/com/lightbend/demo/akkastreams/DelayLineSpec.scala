package com.lightbend.demo.akkastreams

import akka.stream.scaladsl.{Sink, Source}
import org.scalatest.FreeSpec

trait DelayLineTestData {
  val unitPulse = Source(1.0d +: List.fill[Double](10)(0.0d))
}

class DelayLineSpec extends FreeSpec with AkkaSpec with DelayLineTestData {

  "DelayLine" - {
    "should delay and scale a unit pulse by the specified number of samples and scale factor" in {
      val delayLineOut =
        unitPulse
          .map(sample => (sample, 0.0d))
          .via(FilterElements.DelayLineFlow(4, 0.5))
          .runWith(Sink.seq)
          .futureValue
      val delayedData = delayLineOut.map( _._1)
      val delayedAndScalaData = delayLineOut.map(_._2)
      assert(delayedData == Vector(0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0))
    }
  }
}
