package com.example.models.repo

import com.example.models._

import scala.concurrent.Future

trait SessionRepo {

  def get(id: String): Future[Option[Session]]

  def add(s: Session): Future[Unit]

  def update(s: Session): Future[Boolean]

  def delete(id: String): Future[Boolean]

}
