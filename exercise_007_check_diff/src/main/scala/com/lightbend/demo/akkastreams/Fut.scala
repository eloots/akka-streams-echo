package org.applied.akkastreams

import scala.concurrent.{ Future, blocking }

object Fut {

  import scala.concurrent.ExecutionContext.Implicits.global
  def main(args: Array[String]): Unit = {
    println("Starting")
    val r1 = for {
      _ <- Future.unit
      m1f = Future(blocking{Thread.sleep(2000);5})
      m2f = Future(blocking{Thread.sleep(2000);Some(6)})
      m1 <- m1f
      m2 <- m2f
      m0 = for {m2v <- m2} yield m2v * m1
    } yield m0

    r1.foreach(println)

    Thread.sleep(5000)
  }

}
