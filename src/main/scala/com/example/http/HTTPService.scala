package com.example.http

import com.example.http.util.{JsonErrorHandling, UpStatus}

trait HTTPService extends JsonErrorHandling with UpStatus {
  val routes =
    upStatusRoute
}
