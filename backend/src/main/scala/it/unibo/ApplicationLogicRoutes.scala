package it.unibo

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.model.*
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives.*
import it.unibo.core.*
import upickle.default.*
import it.unibo.core.TodoService
import upickle.default.write
import it.unibo.core.Todo.given

import scala.concurrent.ExecutionContext
import scala.util.Success

/** Akka routes for application logic management */
object ApplicationLogicRoutes {
  def routes(model: TodoService)(using ExecutionContext): Seq[Route] = Seq(
    path("todo") {
      get {
        val element = model.all.map(write(_))
        onComplete(element) {
          case Success(elem) => complete(HttpEntity(ContentTypes.`application/json`, elem))
          case _ => complete(StatusCodes.InternalServerError)
        }
      }
    },
    path("todo" / Segment) { (todo: String) =>
      post {
        val add = model.add(todo)
        onComplete(add) {
          case Success(value) => complete(StatusCodes.Created)
          case _ => complete(StatusCodes.InternalServerError)
        }
      }
    },
    path("todo" / Segment / "complete") { (todo: String) =>
      post {
        val add = model.complete(todo)
        onComplete(add) {
          case Success(_) => complete(StatusCodes.Created)
          case _ => complete(StatusCodes.InternalServerError)
        }
      }
    }
  )
}
