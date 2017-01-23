package com.example

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.example.http.HTTPService

import scala.concurrent.ExecutionContext
import scala.util.Failure

object Main extends App with HTTPService {
  implicit val system = ActorSystem()

  implicit val executionContext: ExecutionContext = system.dispatcher

  val log: LoggingAdapter = Logging(system, getClass)
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  Http().bindAndHandle(routes, "0.0.0.0", 8080).onComplete {
    case Failure(cause) =>
      println(s"Can't bind to localhost:8080: $cause")
      system.terminate()
    case _ =>
      println(s"Server online at http://localhost:8080")
  }
}
