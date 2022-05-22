package it.unibo.core

import upickle.default.read
import sttp.client3.*
import scala.concurrent.{ExecutionContext, Future}

class TodoServiceClient(where: String)(using ExecutionContext) extends TodoService:
  private val backend = FetchBackend()
  def add(what: String): Future[Unit] = basicRequest
    .post(uri"$where/todo/$what")
    .send(backend)
    .map(_ => ())
  def complete(what: String): Future[Unit] =
    basicRequest
      .post(uri"$where/todo/$what/complete")
      .send(backend)
      .map(_ => ())
  def all: Future[Seq[Todo]] =
    quickRequest
      .get(uri"$where/todo")
      .send(backend)
      .map((data: Response[String]) => read[Array[Todo]](data.body))
