package org.applied.akkastreams

import akka.stream.scaladsl.{Sink, Source}
import org.scalatest.freespec._

trait IIRTestData {
  import FilterElements._

  val firFilterStages: List[FilterStage] =
    List((3, -0.3), (7, -0.2)).map(_.toFilterStage)

  val testFilter = buildIIR(firFilterStages)

  val unitPulse = Source(1.0d +: List.fill[Double](21)(0.0d))
}

class IIRSpec extends AnyFreeSpec with AkkaSpec with IIRTestData {
  "An FIR filter when fed a unit pulse" - {
    "should preduce delayed pulses at the expected delays" in {
      val firResponse = unitPulse
        .via(testFilter)
        .runWith(Sink.seq)
        .futureValue
      assert(firResponse ==
        Vector(
          1.0d, // 0
          0.0d, // 1
          0.0d, // 2
          -0.3d, // 3
          0.0d, // 4
          0.0d, // 5
          -0.3d * -0.3d, // 6
          -0.0d, // 7
          0.0d, //8
          -0.3d * -0.3d * -0.3d, // 9
          -0.2d, // 10
          0.0d, // 11
          -0.3d * -0.3d * -0.3d * -0.3d, // 12
          -0.2d * -0.3d + -0.2 * -0.3d, // 13
          0.0d, //14
          -0.3d * -0.3d * -0.3d * -0.3d * -0.3d, // 15
          (-0.2d * -0.3d + -0.2 * -0.3d) * -0.3d + -0.3d * -0.3d * -0.2d, // 16
          0.0d, // 17
          -0.3d * -0.3d * -0.3d * -0.3d * -0.3d * -0.3d, // 18
          ((-0.2d * -0.3d + -0.2 * -0.3d) * -0.3d + -0.3d * -0.3d * -0.2d) * -0.3d + -0.3d * -0.3d * -0.3d * -0.2d, // 19
          -0.2d * -0.2d, //20
          -0.3d * -0.3d * -0.3d * -0.3d * -0.3d * -0.3d * -0.3d // 21
        )
      )
    }
  }
}
