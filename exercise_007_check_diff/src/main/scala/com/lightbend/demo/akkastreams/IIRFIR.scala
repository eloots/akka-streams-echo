package com.lightbend.demo.akkastreams

import akka.actor.ActorSystem

object IIRFIR extends App {
  import FilterElements._

  def invertFilterCoefficients: PartialFunction[FilterStage, FilterStage] = {
    case FilterStage(delay, coefficient) => FilterStage(delay, -coefficient)
  }

  // Make the Blueprint of an (FIR based) echo generator Flow
  val firFilterStages: List[FilterStage] =
    List((2000, -0.3), (1500, -0.3), (4500, -0.2)).map(_.toFilterStage)
  val firBasedEcho = buildFIR(firFilterStages)

  // Make the Blueprint of an (IIR based) echo generator Flow that cancels out the effect of the FIR filter above
  val iirFilterStages: List[FilterStage] = firFilterStages map invertFilterCoefficients
  val iirBasedEcho = buildIIR(iirFilterStages)

  // Get some sample audio data as a Source
  val waveFileName = "welcome.wav"
  val WaveSource(soundSource, waveSettings) = WaveSourceFromFile(waveFileName)

  // Create an output wave file with the same settings and the sample Audio
  val waveOutputFileName = "welcome-out.wav"
  val wavOutputFile = WaveOutputFile(waveOutputFileName, waveSettings)

  implicit val actorSystem = ActorSystem()
  import actorSystem.dispatcher

  // Run the flow and sink it to a wav file
  val runFlow =
  soundSource
    .via(iirBasedEcho)
    .via(firBasedEcho)
    .grouped(1000)
    .runForeach(d => wavOutputFile.writeFrames(d.map(_ / 2.0).toArray, d.length))

  runFlow flatMap { _ => actorSystem.terminate() } onComplete { _ => wavOutputFile.close() }
}
