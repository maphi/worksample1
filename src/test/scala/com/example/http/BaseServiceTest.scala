package com.example.http

import akka.actor.ActorSystem
import akka.event.{LoggingAdapter, NoLogging}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.example.models.repo.SessionRepoAkka
import org.scalatest._

trait BaseServiceTest extends WordSpec with Matchers with ScalatestRouteTest with HTTPService {

  val log: LoggingAdapter = NoLogging

  val executionContext = system.dispatcher

  val mySystem = system

  val sessionRepo = new SessionRepoAkka {
    lazy val system: ActorSystem = mySystem
  }

}
