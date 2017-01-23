package com.example.http.util

import com.example.http.BaseServiceTest
import akka.http.scaladsl.model.StatusCodes._

class UpStatusTest extends BaseServiceTest {

  "UpStatus" should {

    "return 204/No Content" in {
      Get("/_status") ~> upStatusRoute ~> check {
        response.status should be(NoContent)
      }
    }

  }

}