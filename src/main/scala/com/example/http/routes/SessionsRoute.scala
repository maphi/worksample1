package com.example.http.routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.example.models._
import spray.json.{RootJsonFormat, _}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.headers.Location
import com.example.models.repo.SessionRepo

import scala.concurrent.{ExecutionContext, Future}

object SessionsJsonProtocol extends DefaultJsonProtocol with SprayJsonSupport {

  implicit object GenderFormat extends JsonFormat[Gender] {

    def write(g: Gender) = JsString(g.toString)

    def read (v: JsValue): Gender = v match {
      case JsString(s) => Gender(s)
      case _ => deserializationError("Unknown gender")
    }
  }

  implicit val addressFormat = jsonFormat3(Address)
  implicit val shopProductFormat = jsonFormat2(ShopProduct)
  implicit val sessionFormat = jsonFormat7(Session)
  implicit val newSessionFormat = jsonFormat6(NewSession)
  implicit val mergeSessionFormat = jsonFormat6(MergeSession)

}

trait SessionsRoute {
  import SessionsJsonProtocol._

  val sessionRepo: SessionRepo
  implicit def executionContext: ExecutionContext

  val sessionsRoute = pathPrefix("sessions") {
    pathEndOrSingleSlash {
      //Create new Session
      (post & extractUri) {uri =>
        entity(as[NewSession]) {newS =>
          val s = newS.create
          onSuccess(sessionRepo.add(s)) {
            respondWithHeader(Location(uri.withPath(uri.path / s.id))) {
              complete(Created -> s)
            }
          }
        }
      }
    } ~
    pathPrefix(Segment) {id =>
      //get session
      get {
        onSuccess(sessionRepo.get(id)) {
          case Some(u) => complete(u)
          case None => complete(NotFound -> "")
        }
      } ~
        //update session
        put {
          entity(as[MergeSession]) {ms =>
            onSuccess(sessionRepo.get(id).flatMap{
              case Some(u) => sessionRepo.update(ms.merge(u))
              case None => Future.successful(false)
            }) {
              case true => complete(NoContent)
              case false => complete(NotFound -> "")
            }
          }
        } ~
        //delete session
        delete {
          onSuccess(sessionRepo.delete(id)) {
            case true => complete(NoContent)
            case false => complete(NotFound -> "")
          }
        }
    }
  }
}
