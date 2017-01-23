package com.example.models

import org.scalatest._

class GenderTest extends WordSpec with Matchers {

  "Gender" should {

    "ignore case on construction" in {
      assert(Gender("femALE") == Female)
      assert(Gender("MAle") == Male)
    }

    "reject invalid strings" in {
      intercept[IllegalArgumentException] {
        Gender("elephant")
      }
    }

  }

}
