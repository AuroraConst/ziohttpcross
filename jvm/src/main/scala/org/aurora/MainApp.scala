package org.aurora

import zio._
import zio.http._
import zio.http.endpoint._
object MainApp extends ZIOAppDefault {

  def importedPatients =
    import org.aurora._, dataimport._
    importpatients

  val routes =
    Routes(
      Method.GET / Root -> handler(Response.text("Greetings at your sxxxxxxxervice")),
      Method.GET / "greet" -> handler { (req: Request) =>
        val name = req.queryParamToOrElse("name", "World")
        Response.text(s"Hello $name!")
      },
      Method.GET / "patients"   -> handler {Response.text(s"$importedPatients")
      }
    )
  override val run = {
    Console.printLine("please visit http://localhost:8080") 
    Server.serve(routes).provide(Server.default)
  }
}
