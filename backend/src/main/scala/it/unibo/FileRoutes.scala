package it.unibo

import akka.http.scaladsl.server.Directives.{get, getFromResource, path}
import akka.http.scaladsl.server.Route

/** akka routes for file managing */
object FileRoutes:
  def routes: Seq[Route] = Seq(
    path("") {
      get {
        getFromResource("index.html")
      }
    },
    path("main.js") {
      get {
        getFromResource("main.js")
      }
    }
  )
