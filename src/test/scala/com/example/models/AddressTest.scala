package com.example.models

import org.scalatest._

class AddressTest extends WordSpec with Matchers {

  "Address" should {

    "reject invalid postal codes" in {
      intercept[IllegalArgumentException] {
        Address("Musterstrasse 1", 123456, "Musterstadt")
      }
    }

  }
}