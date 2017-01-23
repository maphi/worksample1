package com.example.http

import akka.http.scaladsl.server.Directives._
import com.example.http.routes.SessionsRoute
import com.example.http.util.{JsonErrorHandling, UpStatus}

trait HTTPService extends JsonErrorHandling with UpStatus with SessionsRoute{
  val routes =
    upStatusRoute ~
      pathPrefix("v1") {
        sessionsRoute
      }
}
