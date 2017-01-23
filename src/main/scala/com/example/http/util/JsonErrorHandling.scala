package com.example.http.util

import akka.http.scaladsl.model._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server._

trait JsonErrorHandling {
  //respond with JSON in case of rejections
  implicit def rejectionHandler =
    RejectionHandler.default
      .mapRejectionResponse {
        case res @ HttpResponse(status, _, ent: HttpEntity.Strict, _) =>
          val message = ent.data.utf8String.replaceAll("\"", """\"""")

          res.copy(entity = HttpEntity(ContentTypes.`application/json`, s"""{"status":${status.intValue},"error": "$message"}"""))

        case x => {println(x); x}
      }

  //respond with JSON in case of errors
  implicit def exceptionHandler: ExceptionHandler =
    ExceptionHandler {
      case e: Throwable => ctx => {
        ctx.log.warning("Error during processing of request: '{}'. Completing with {} response.", e.getMessage, InternalServerError)
        ctx.complete(HttpResponse(InternalServerError, entity = HttpEntity(ContentTypes.`application/json`, s"""{"status":500,"error": "Internal Server Error"}""")))
      }
    }
}
