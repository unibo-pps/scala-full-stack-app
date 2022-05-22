package it.unibo
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.*
import akka.http.scaladsl.server.Directives.*
import it.unibo.core.*
import upickle.default.*
import scala.util.Success
import scala.concurrent.ExecutionContext
import scala.io.StdIn
import scala.io.StdIn.*
import it.unibo.core.Todo.{*, given}
object WebServer:
  def main(args: Array[String]): Unit =
    given system: ActorSystem[_] = ActorSystem(Behaviors.empty, "todo-system")
    // needed for the future flatMap/onComplete in the end
    given ExecutionContext = system.executionContext
    val model = LocalService() // an entire todo service for the application
    val routes =
      concat((ApplicationLogicRoutes.routes(model) ++ FileRoutes.routes): _*)

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(routes)

    println(s"Server now online. Please navigate to http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
