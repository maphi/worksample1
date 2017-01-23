package com.example.models

sealed trait Gender

case object Male extends Gender
case object Female extends Gender

object Gender {
  def apply(s: String) = s.toLowerCase match {
    case "male" => Male
    case "female" => Female
    case _ => throw new IllegalArgumentException(s"Unknown gender: $s")
  }
}