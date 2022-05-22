package it.unibo

import it.unibo.core.{Todo, TodoService, TodoStatus}
import upickle.default.*
import Todo.{*, given}

import scala.concurrent.{ExecutionContext, Future}
import scala.io.StdIn

class ServiceClient(where: String)(using ExecutionContext) extends TodoService:
  def add(what: String): Future[Unit] = Future(requests.post(s"$where/todo/$what"))
  def complete(what: String): Future[Unit] = Future(requests.post(s"$where/todo/$what/complete"))
  def all: Future[Seq[Todo]] =
    Future {
      val result = requests.get(s"$where/todo")
      val data: Array[Todo] = read(result)
      data
    }

// Mini client used to test the server API
@main def main(): Unit =
  given ExecutionContext = scala.concurrent.ExecutionContext.global
  val client = ServiceClient("http://localhost:8080")
  client.all.foreach(a => println(s"All= $a"))
  println("End with: ...")
  StdIn.readLine()
