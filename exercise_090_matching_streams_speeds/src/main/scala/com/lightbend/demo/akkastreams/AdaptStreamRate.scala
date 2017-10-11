package com.lightbend.demo.akkastreams

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.Source
import akka.stream.{ActorMaterializer, ThrottleMode}
import com.lightbend.demo.akkastreams.FilterElements.{VCO, buildIIR}

import scala.concurrent.duration._

object AdaptStreamRate extends App {

  implicit val actorSystem = ActorSystem()
  import actorSystem.dispatcher
  implicit val flowMaterializer = ActorMaterializer()

  class RandomNumberIterator extends Iterator[Double] {
    import scala.util.Random

    Random.setSeed(123456789L)
    override def hasNext: Boolean = true
    override def next(): Double = 0.1d + (Random.nextDouble() + 1.0d) / 6.1d
  }

  val baseStream: Source[Double, NotUsed] =
    Source.fromIterator(() => new RandomNumberIterator)
      .throttle(1, 500.millis, 0, ThrottleMode.Shaping)
      .expand(i => Iterator.continually(i))

  val fastStream = Source.repeat(0.0d).throttle(1, 100.millis, 1, akka.stream.ThrottleMode.Shaping)

  val combinedStream = fastStream.zip(baseStream)

  val runFlow =
    combinedStream.map { case (slow, fast) => slow + fast}
      .take(100)
      .runForeach(d => println(f"$d%5.3f"))

  runFlow flatMap { _ => actorSystem.terminate() }

}