package org.applied.akkastreams

import akka.stream.scaladsl.{Sink, Source}
import org.scalatest.freespec._

trait FIRFIRTestData {
  import FilterElements._

  val firFilterStages1: List[FilterStage] =
    List((2, -0.3)).map(_.toFilterStage)
  val testFilter1 = buildFIR(firFilterStages1)

  val firFilterStages2: List[FilterStage] =
    List((3, 0.5)).map(_.toFilterStage)
  val testFilter2 = buildFIR(firFilterStages2)

  val unitPulseData = 1.0d +: Vector.fill[Double](6)(0.0d)
  val unitPulse = Source(unitPulseData)
}

class FIRFIRSpec extends AnyFreeSpec with AkkaSpec with FIRFIRTestData {
  "Two chained FIR filters when fed a unit pulse" -  {
    "should preduce delayed pulses at the expected delays" in {
      val firfirResponse = unitPulse
        .via(testFilter1)
        .via(testFilter2)
        .runWith(Sink.seq)
        .futureValue
      assert(firfirResponse == Vector(1.0, 0.0, -0.3, 0.5, 0.0, -0.15, 0.0))
    }
  }
}
