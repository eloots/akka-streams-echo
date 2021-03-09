package org.applied.akkastreams

import akka.stream.scaladsl.{Sink, Source}
import org.scalatest.freespec._

trait FIRFIRTestData {
  import FilterElements._

  val firFilterStages: List[FilterStage] =
    List((2, -0.3), (3, 0.25)).map(_.toFilterStage)
  val firFilter = buildFIR(firFilterStages)

  val iirFilterStages: List[FilterStage] = firFilterStages map invertFilterCoefficients
  val iirFilter = buildIIR(iirFilterStages)

  val unitPulseData = 1.0d +: Vector.fill[Double](12)(0.0d)
  val unitPulse = Source(unitPulseData)

  def checkEqualWithinMargin(v1: Seq[Double], v2: Vector[Double], margin: Double): Boolean = {
    assert(v1.size == v2.size)
    v1.zip(v2).forall{ case (n1, n2) => math.abs(n1 - n2) <= margin}
  }
}

class FIRIIRSpec extends AnyFreeSpec with AkkaSpec with FIRFIRTestData {
  "An IIR filter chained to an FIR filter" -  {
    "should produce the input unit pulse sent to the input of the FIR filter with appropriately configured filters" in {
      val firiirResponse = unitPulse
        .via(iirFilter)
        .via(firFilter)
        .runWith(Sink.seq)
        .futureValue
      if (checkEqualWithinMargin(firiirResponse, unitPulseData, 1.0e-17d))
        assert(true)
      else
        assert(firiirResponse == unitPulseData)
    }
  }
}
