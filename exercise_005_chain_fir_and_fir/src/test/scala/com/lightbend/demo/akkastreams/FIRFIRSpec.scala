package com.lightbend.demo.akkastreams

import akka.stream.scaladsl.{Sink, Source}
import org.scalatest.FreeSpec

trait FIRFIRTestData {
  import FilterElements._

  val firFilterStages1: List[FilterStage] =
    List((2, -0.3)).map(_.toFilterStage)
  val testFilter1 = buildFIR(firFilterStages1)

  val firFilterStages2: List[FilterStage] =
    List((2, 0.3)).map(_.toFilterStage)
  val testFilter2 = buildFIR(firFilterStages2)

  val unitPulseData = 1.0d +: Vector.fill[Double](5)(0.0d)
  val unitPulse = Source(unitPulseData)
}

class FIRFIRSpec extends FreeSpec with AkkaSpec with FIRFIRTestData {
  "An FIR filter when fed a unit pulse" -  {
    "should preduce delayed pulses at the expected delays" in {
      val firfirResponse = unitPulse
        .via(testFilter1)
        .via(testFilter2)
        .runWith(Sink.seq)
        .futureValue
      //assert(firfirResponse == unitPulseData)
    }
  }
}
