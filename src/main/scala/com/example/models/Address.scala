package com.example.models

case class Address(street: String, postalCode: Int, city: String) {
  require(postalCode >= 1 && postalCode <= 99999, "postal code must be between 1 and 99999")
}