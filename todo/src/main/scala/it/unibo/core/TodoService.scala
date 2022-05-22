package it.unibo.core
import scala.concurrent.Future
import scala.util.Try

/** Common interface for a todo service handling. It enables:
  *   - add a new todo
  *   - complete a todo
  *   - get all todos
  */
trait TodoService:
  def add(what: String): Future[Unit]
  def complete(what: String): Future[Unit]
  def all: Future[Seq[Todo]]
