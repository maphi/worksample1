package com.example.models

import com.example.util.Validation.checkMail

final case class Session(id: String, userName: String, newsletterSubscribed: Option[Boolean],
                   email: String, address: Address, basket: List[ShopProduct], gender: Gender) {

  require(userName.length >= 3, "product name must have 3 or more characters")
  require(checkMail(email), "email must be valid")

}
