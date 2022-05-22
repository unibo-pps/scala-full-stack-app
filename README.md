# Todo App

A simple cross-platform application created using Scala

## Project structure
- todo: contains the shared code between the backend and the frontend
- frontend: the js based UI for handling tods
- backend: the service logic implementation + an akka http endpoint

## How to Run
You have to run it.unibo.WebServer (you can use IDEA or sbt shell -> `project backend`; `runMain it.unibo.WebServer`).
The service will be avaiable at [localhost:8080](localhost:8080)

## How to compile js code
In a sbt shell, type: 
```
project frontend
```
Then, you have to transpile the source code with:
```
fastOptJS
```
This will produce a js budle in: `frontend/target/scala-3.1.2/frontend-fastopt/main.js`
Copy this file in `backend/src/main/resources`

**NB** In your project, automate this process with sbt!
