package org.applied.akkastreams

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.Source
import akka.stream.ThrottleMode

import scala.concurrent.duration._

object AdaptStreamRate extends App {

  implicit val actorSystem = ActorSystem()
  import actorSystem.dispatcher

  class RandomNumberIterator extends Iterator[Double] {
    import scala.util.Random

    Random.setSeed(123456789L)
    override def hasNext: Boolean = true
    override def next(): Double = 0.1d + (Random.nextDouble() + 1.0d) / 6.1d
  }

  val baseStream: Source[Double, NotUsed] =
    Source.fromIterator(() => new RandomNumberIterator)
      .throttle(1, 2000.millis, 0, ThrottleMode.Shaping)
      .expand(i => Iterator.continually(i))

  val fastStream =
    Source.cycle(() => List(0.0, 0.1, 0.2, 0.30, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0).iterator)
      .throttle(1, 100.millis, 1, akka.stream.ThrottleMode.Shaping)

  val combinedStream = fastStream.zip(baseStream)

  val runFlow =
    combinedStream.map { case (slow, fast) => (slow, fast, slow + fast)}
      .take(100)
      .runForeach { case (n1, n2, nc) => println(f"$n1%5.3f $n2%5.3f $nc%5.3f")}

  runFlow flatMap { _ => actorSystem.terminate() }

}
