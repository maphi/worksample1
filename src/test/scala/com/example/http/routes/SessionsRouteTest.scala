package com.example.http.routes

import akka.http.scaladsl.model.HttpHeader
import akka.http.scaladsl.model.StatusCodes._
import com.example.http.BaseServiceTest
import com.example.models._
import org.scalatest.BeforeAndAfterAll

import scala.concurrent.duration._
import scala.concurrent.Await
import scala.concurrent.duration._

class SessionsRouteTest extends BaseServiceTest with BeforeAndAfterAll{

  import SessionsJsonProtocol._

  def checkHeaderVal(h: Option[HttpHeader], f: String => Boolean): Boolean = {
    h.isDefined && f(h.get.value)
  }

  val testSession =  Session("fakeId", "MaxMustermann", None, "max@example.com", Address("Musterstrasse 1", 40000, "Musterstadt"), List(), Male)
  val updatedSession = testSession.copy(newsletterSubscribed = Some(true))

  override def beforeAll(): Unit = {
    Await.result(sessionRepo.add(testSession), 5 seconds)
  }

  "SessionsRoute" should {

    var sessionId = "fakeId"

    "add a session" in {
      Post("/sessions", testSession) ~> sessionsRoute ~> check {
        val res = responseAs[Session]
        response.status should be(Created)
        checkHeaderVal(header("Location"), s => s.endsWith("/sessions/" + res.id)) should be(true)
        res should be(testSession.copy(id=res.id))
      }
    }

    "update a session" in {
      Put("/sessions/" + updatedSession.id, updatedSession) ~> sessionsRoute ~> check {
        response.status should be(NoContent)
      }
    }

    "fail updating non existing session" in {
      Put("/sessions/doesNotExist", updatedSession) ~> sessionsRoute ~> check {
        response.status should be(NotFound)
      }
    }

    "get the updated session" in {
      Get("/sessions/" + updatedSession.id) ~> sessionsRoute ~> check {
        responseAs[Session] should be(updatedSession)
      }
    }

    "respond with 404 for non existing session" in {
      Get("/sessions/doesNotExist") ~> sessionsRoute ~> check {
        response.status should be(NotFound)
      }
    }

    "delete a session" in {
      Delete("/sessions/" + updatedSession.id) ~> sessionsRoute ~> check {
        response.status should be(NoContent)
      }
    }

    "fail deleting a non existing session" in {
      Delete("/sessions/" + updatedSession.id) ~> sessionsRoute ~> check {
        response.status should be(NotFound)
      }
    }

  }
}