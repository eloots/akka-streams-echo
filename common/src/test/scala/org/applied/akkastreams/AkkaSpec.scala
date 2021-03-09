package org.applied.akkastreams

import akka.actor.{ActorRefFactory, ActorSystem}
import org.scalatest.{BeforeAndAfterAll, Suite}
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.duration._

trait AkkaSpec extends ScalaFutures with BeforeAndAfterAll { this: Suite =>
  implicit protected val system = ActorSystem()
  implicit protected val actorRefFactory: ActorRefFactory = system

  override implicit def patienceConfig = super.patienceConfig.copy(timeout = 5.seconds)

  override protected def afterAll(): Unit = {
    super.afterAll()
    system.terminate()
  }
}
