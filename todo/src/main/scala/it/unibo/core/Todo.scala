package it.unibo.core
import upickle.default.*

// In shared source there are common interface, data & serializers
// Model: todos with status (i.e. completed or not) and a message
case class Todo(what: String, status: TodoStatus)
object Todo:
  // For marshalling & unmarshalling concerns
  given ReadWriter[Todo] = macroRW
  given ReadWriter[TodoStatus] = macroRW

sealed trait TodoStatus
case object Open extends TodoStatus
case object Closed extends TodoStatus
