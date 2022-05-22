ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.2"

val todo = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .settings(libraryDependencies += "com.lihaoyi" %%% "upickle" % "1.6.0")


val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.9"

val backend = project.dependsOn(todo.jvm)
  .settings(
    libraryDependencies ++= Seq(
      ("com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion).cross(CrossVersion.for3Use2_13),
      ("com.typesafe.akka" %% "akka-stream" % AkkaVersion).cross(CrossVersion.for3Use2_13),
      ("com.typesafe.akka" %% "akka-http" % AkkaHttpVersion).cross(CrossVersion.for3Use2_13),
      "com.lihaoyi" %% "requests" % "0.7.0"
    )
  )

val frontend = project.dependsOn(todo.js)
  .enablePlugins(ScalaJSPlugin)
  .settings(
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client3" %%% "core" % "3.6.2",
      "org.scala-js" %%% "scalajs-dom" % "2.2.0"
    ),
  )
