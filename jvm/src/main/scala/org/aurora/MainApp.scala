package org.aurora

import zio._
import zio.http._
import zio.http.endpoint._
object MainApp extends ZIOAppDefault :

  import org.aurora._, dataimport._
  importpatients

  val routes =
    Routes(
      Method.GET / Root -> handler(Response.text("Greetings at your service!!! Arnoldorferdorf!!!")),
      Method.GET / "greet" -> handler { (req: Request) =>
        val name = req.queryParamToOrElse("name", "World")
        Response.text(s"Hello $name!")
        },
        Method.GET / "patients"   -> handler {
          import org.aurora._, dataimport._
          Response.text(s"$importpatients")
        },
        Method.GET / "patientsjson"   -> handler {
          import org.aurora._, dataimport._
          import org.aurora.shared._, dto._
          import zio.json._
          Response.text(s"${importpatients.toJson}")
        }

    )
  override val run = {
    Console.printLine("please visit http://localhost:8080") 
    Server.serve(routes).provide(Server.default)
}

