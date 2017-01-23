package com.example.models.repo

import akka.actor._
import akka.routing.ConsistentHashingRouter._
import akka.routing._
import com.example.models._
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import SessionStoreActor._
import scala.concurrent._

trait SessionRepoAkka extends SessionRepo {

  implicit def system: ActorSystem
  implicit def executionContext: ExecutionContext = system.dispatcher
  implicit val timeout = Timeout(5 seconds)

  val sessionStore = system.actorOf(ConsistentHashingPool(1024).props(Props[SessionStoreActor]), name = "sessionStore")

  def get(id: String): Future[Option[Session]] = (sessionStore ? GetSession(id)).mapTo[Option[Session]]

  def add(s: Session): Future[Unit] = (sessionStore ? CreateSession(s)).mapTo[Unit]

  def update(s: Session): Future[Boolean] = (sessionStore ? UpdateSession(s)).mapTo[Boolean]

  def delete(id: String): Future[Boolean] = (sessionStore ? DeleteSession(id)).mapTo[Boolean]
}

class SessionStoreActor extends Actor {
  var sessions = Map.empty[String, Session]

  def receive = {
    case CreateSession(s) => sender ! (sessions += (s.id -> s))
    case GetSession(id) => sender ! (sessions get id)
    case UpdateSession(s) => {
      sender ! (if (sessions contains s.id) {
        sessions += (s.id -> s)
        true
      } else {
        false
      })
    }
    case DeleteSession(id) =>
      sender ! (if (sessions contains id) {
        sessions -= id
        true
      } else {
        false
      })
  }
}

object SessionStoreActor {
  sealed trait SessionActorMessages

  final case class CreateSession(s: Session) extends SessionActorMessages with ConsistentHashable {
    override def consistentHashKey = s.id
  }

  final case class GetSession(id: String) extends SessionActorMessages with ConsistentHashable {
    override def consistentHashKey = id
  }

  final case class UpdateSession(s: Session) extends SessionActorMessages with ConsistentHashable {
    override def consistentHashKey = s.id
  }

  final case class DeleteSession(id: String) extends SessionActorMessages with ConsistentHashable {
    override def consistentHashKey = id
  }
}