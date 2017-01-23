package com.example.models

import org.scalatest._

class ShopProductTest extends WordSpec with Matchers {

  "ShopProduct" should {

    "reject invalid names" in {
      intercept[IllegalArgumentException] {
        ShopProduct("fakeId", "S6")
      }
    }

  }
}