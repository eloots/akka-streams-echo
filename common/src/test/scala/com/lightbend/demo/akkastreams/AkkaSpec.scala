package com.lightbend.demo.akkastreams

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.scalatest.{Suite, BeforeAndAfterAll}
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.duration._

trait AkkaSpec extends ScalaFutures with BeforeAndAfterAll { this: Suite =>
  implicit protected val system = ActorSystem()
  implicit protected val materializer = ActorMaterializer()
  implicit protected val executionContext = system.dispatcher

  override implicit def patienceConfig = super.patienceConfig.copy(timeout = 5.seconds)

  override protected def afterAll(): Unit = {
    super.afterAll()
    system.terminate()
  }
}
