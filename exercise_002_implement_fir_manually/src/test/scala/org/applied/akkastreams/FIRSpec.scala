package org.applied.akkastreams

import akka.stream.scaladsl.{Sink, Source}
import org.scalatest.freespec._

trait FIRTestData {
  import FilterElements._

  // Create an FIR filter with delays/scala factors = (2, -0.3), (3, -0.3) and (5, -0.2)
  val testFilter = FirInitial()
    .via(DelayLineFlow(2, -0.3))
    .via(DelayLineFlow(3, -0.3))
    .via(DelayLineFlow(5, -0.2))
    .via(FirSelectOut())

  val unitPulse = Source(1.0d +: List.fill[Double](20)(0.0d))
}

class FIRSpec extends AnyFreeSpec with AkkaSpec with FIRTestData {
  "An FIR filter when fed a unit pulse" -  {
    "should preduce delayed pulses at the expected delays" in {
      val firResponse = unitPulse
        .via(testFilter)
        .runWith(Sink.seq)
        .futureValue
      assert(firResponse == Vector(1.0d, 0.0d, -0.3d, 0.0d, 0.0d, -0.3d, 0.0d, 0.0d, 0.0d, 0.0d, -0.2d) ++ Vector.fill(10)(0.0d))
    }
  }
}
