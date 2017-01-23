package com.example.models

import com.example.util.Validation.checkMail

final case class Session(id: String, userName: String, newsletterSubscribed: Option[Boolean],
                   email: String, address: Address, basket: List[ShopProduct], gender: Gender) {

  require(userName.length >= 3, "product name must have 3 or more characters")
  require(checkMail(email), "email must be valid")

}

final case class NewSession(userName: String, newsletterSubscribed: Option[Boolean],
                            email: String, address: Address, basket: List[ShopProduct], gender: Gender) {
  require(userName.length >= 3, "product name must have 3 or more characters")
  require(checkMail(email), "email must be valid")

  def create() = new Session(java.util.UUID.randomUUID.toString, userName,
    newsletterSubscribed, email, address, basket, gender)
}

final case class MergeSession(userName: Option[String], newsletterSubscribed: Option[Boolean],
                            email: Option[String], address: Option[Address], basket: Option[List[ShopProduct]], gender: Option[Gender]) {

  require(userName.map(_.length >= 3).getOrElse(true), "product name must have 3 or more characters")
  require(email.map(checkMail).getOrElse(true), "email must be valid")

  def merge(s: Session) = new Session(
    s.id,
    userName.getOrElse(s.userName),
    newsletterSubscribed.orElse(s.newsletterSubscribed),
    email.getOrElse(s.email),
    address.getOrElse(s.address),
    basket.getOrElse(s.basket),
    gender.getOrElse(s.gender)
  )
}