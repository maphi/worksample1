package com.example.models

import akka.actor.ActorSystem
import com.example.models.repo.SessionRepoAkka
import org.scalatest._

import scala.concurrent.Future

class SessionRepoAkkaTest extends AsyncWordSpec with Matchers {

  val sessionRepo = new SessionRepoAkka {
    lazy val system = ActorSystem("sessionRepoTest")
  }

  val testSession =
    Session(java.util.UUID.randomUUID.toString, "User Name", None, "user@example.org",
      Address("Musterstrasse 1", 40000, "Musterstadt"), List(), Male)

  val updatedSession = testSession.copy(newsletterSubscribed = Some(true))

  "SessionRepoAkka" should {

    "create sessions" in {
      sessionRepo.add(testSession).map(_ => assert(true))
    }

    "get a session" in {
      sessionRepo.get(testSession.id).map(r => assert(r == Some(testSession)))
    }

    "update a session" in {
      sessionRepo.update(updatedSession).map(assert(_))
    }

    "get the updated session" in {
      sessionRepo.get(updatedSession.id).map(r => assert(r == Some(updatedSession)))
    }

    "delete a session" in {
      sessionRepo.delete(updatedSession.id).map(assert(_))
    }

    "reject getting non existing session" in {
      sessionRepo.get("doesNotExist").map(r => assert(r == None))
    }

    "reject updating non existing session" in {
      sessionRepo.update(updatedSession).map(r => assert(!r))
    }

    "reject deleting non existing session" in {
      sessionRepo.delete(updatedSession.id).map(r => assert(!r))
    }

  }
}