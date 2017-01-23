package com.example.models

final case class ShopProduct(id: String, name: String) {
  require(name.length >= 3, "product name must have 3 or more characters")
}
