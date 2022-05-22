package it.unibo

import scala.concurrent.ExecutionContext
import scala.io.StdIn
import it.unibo.core.{Todo, TodoService, TodoServiceClient}
import org.scalajs.dom.html.{Button, Div, Input}
import org.scalajs.dom.document
import org.scalajs.dom
import it.unibo.core.Closed
import scala.util.Success

@main def main(): Unit =
  given ExecutionContext = scala.concurrent.ExecutionContext.global
  val client = TodoServiceClient(dom.document.location.href)
  val mainPane = document.createElement("div").asInstanceOf[Div]
  document.body.appendChild(mainPane)
  inputTodos(client, mainPane)
  client.all.foreach(elements => renderTodos(mainPane, elements, client))

def inputTodos(service: TodoService, todoDiv: Div)(using executionContext: ExecutionContext): Unit =
  val input = document.createElement("input").asInstanceOf[Input]
  val send = document.createElement("button").asInstanceOf[Button]
  send.textContent = "Add todo!"
  document.body.append(input, send)
  send.onclick = (e: dom.MouseEvent) => {
    val todo = input.value
    if (todo.nonEmpty) {
      service
        .add(todo)
        .flatMap(_ => service.all)
        .andThen { case Success(data) => renderTodos(todoDiv, data, service) }
    }
  }

def renderTodos(div: Div, todos: Seq[Todo], service: TodoService)(using executionContext: ExecutionContext): Unit =
  while div.hasChildNodes() do div.removeChild(div.firstChild) // clean all the tods
  for (elem <- todos) do addNewTodo(div, elem, service)

private def addNewTodo(parent: Div, todo: Todo, service: TodoService)(using executionContext: ExecutionContext): Unit =
  val todoDiv = document.createElement("div")
  val text = document.createElement("input").asInstanceOf[Input]
  text.disabled = true
  text.value = todo.what
  val button = document.createElement("button").asInstanceOf[Button]
  button.textContent = "Done!"
  if (todo.status == Closed)
    button.disabled = true
  todoDiv.append(text, button)
  parent.append(todoDiv)
  button.onclick = (e: dom.MouseEvent) => {
    service
      .complete(todo.what)
      .flatMap(_ => service.all)
      .andThen { case Success(data) => renderTodos(parent, data, service) }
  }
