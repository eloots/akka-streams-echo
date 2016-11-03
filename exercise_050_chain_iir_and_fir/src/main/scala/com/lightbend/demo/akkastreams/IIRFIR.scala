package com.lightbend.demo.akkastreams

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object IIRFIR extends App {
  import FilterElements._

  // Make the Blueprint of an (FIR based) echo generator Flow
  val firFilterStages: List[FilterStage] =
    List((1000, -0.3), (1500, -0.3), (4500, -0.2)).map(_.toFilterStage)
  val firBasedEcho = buildFIR(firFilterStages)

  // Make the Blueprint of another (IIR based) echo generator Flow
  val iirFilterStages =
    List((1005, 0.3), (1490, 0.3), (4505, 0.2)).map(_.toFilterStage)
  val iirBasedEcho = buildIIR(iirFilterStages)

  // Get some sample audio data as a Source
  val waveFileName = "welcome.wav"
  val WaveSource(soundSource, waveSettings) = WaveSourceFromFile(waveFileName)

  // Create an output wave file with the same settings and the sample Audio
  val waveOutputFileName = "welcome-out.wav"
  val wavOutputFile = WaveOutputFile(waveOutputFileName, waveSettings)

  implicit val actorSystem = ActorSystem()
  import actorSystem.dispatcher
  implicit val flowMaterializer = ActorMaterializer()

  // Run the flow and sink it to a wav file
  val runFlow =
  soundSource
    .via(iirBasedEcho)
    .via(firBasedEcho)
    .grouped(1000)
    .runForeach(d => wavOutputFile.writeFrames(d.map(_ / 2.0).toArray, d.length))

  runFlow flatMap { _ => actorSystem.terminate() } onComplete { _ => wavOutputFile.close() }
}
