package org.applied.akkastreams

import akka.stream.scaladsl.{Sink, Source}
import org.scalatest.freespec._

trait FIRTestData {
  import FilterElements._

  val firFilterStages: List[FilterStage] =
    List((2, -0.3), (3, -0.3), (5, -0.2)).map(_.toFilterStage)

  val testFilter = buildFIR(firFilterStages)

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
