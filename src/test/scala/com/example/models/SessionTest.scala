package com.example.models

import org.scalatest._

class SessionTest extends WordSpec with Matchers {

  "Session" should {

    "reject invalid names" in {
      intercept[IllegalArgumentException] {
        Session("fakeId", "Jo", None, "jo@example.com", Address("Musterstrasse 1", 40000, "Musterstadt"), List(), Male)
      }
    }

  }
}