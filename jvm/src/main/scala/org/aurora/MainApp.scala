package org.aurora

import zio._
import zio.http._
import zio.http.endpoint._
object MainApp extends ZIOAppDefault {

  // def makeWebApp:App[Any] =
  //   Http.collect[Request] {
  //     case Method.GET -> !! =>   handler(Response.text("Greetings at your service"))

  //   }

  val routes =
    Routes(
      Method.GET / Root -> handler(Response.text("Greetings at your sxxxxxxxervice")),
      Method.GET / "greet" -> handler { (req: Request) =>
        val name = req.queryParamToOrElse("name", "World")
        Response.text(s"Hello $name!")
      }
    )
  override val run = {
    Console.printLine("please visit http://localhost:8080") 
    Server.serve(routes).provide(Server.default)
  }
}
