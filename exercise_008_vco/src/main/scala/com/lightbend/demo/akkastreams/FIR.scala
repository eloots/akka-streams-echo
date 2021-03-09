package org.applied.akkastreams

import akka.actor.ActorSystem

object FIR extends App {
  import FilterElements._

  // Make the Blueprint of the (FIR based) echo generator Flow
  val firFilterStages: List[FilterStage] =
  List((3000, -0.3), (1500, -0.2), (4500, -0.35)).map(_.toFilterStage)

  val firBasedEcho = buildFIR(firFilterStages)

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
    .via(firBasedEcho)
    .grouped(1000)
    .runForeach(d => wavOutputFile.writeFrames(d.map(_ / 2.0).toArray, d.length))

  runFlow flatMap { _ => actorSystem.terminate() } onComplete { _ => wavOutputFile.close() }
}
