package com.example.http.util

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes._

trait UpStatus {
  val upStatusRoute = pathPrefix("_status") {
    pathEndOrSingleSlash {
      get {
        complete(NoContent)
      }
    }
  }
}
