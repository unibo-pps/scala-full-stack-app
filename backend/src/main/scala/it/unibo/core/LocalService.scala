package it.unibo.core

import scala.concurrent.{ExecutionContext, Future, blocking}

/** Application service implementation.t */
class LocalService(using ExecutionContext) extends TodoService:
  private var elements: Map[String, Todo] = Map.empty
  def add(what: String): Future[Unit] = Future {
    this.elements = elements + (what -> Todo(what, Open))
  }
  def complete(what: String): Future[Unit] = Future {
    val selected = elements.get(what)
    selected match {
      case Some(_) =>
        elements = elements + (what -> Todo(what, Closed))
      case _ =>
    }
  }
  def all: Future[Seq[Todo]] = Future(elements.values.toSeq)
