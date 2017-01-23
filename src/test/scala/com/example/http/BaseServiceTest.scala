package com.example.http

import akka.event.{LoggingAdapter, NoLogging}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest._

trait BaseServiceTest extends WordSpec with Matchers with ScalatestRouteTest with HTTPService {

  val log: LoggingAdapter = NoLogging

}
