package com.axiom

import zio._
import zio.http._
import zio.http.endpoint._
import zio.http.Middleware.{CorsConfig, cors}
import zio.http.Header.{AccessControlAllowOrigin, Origin}
object MainApp extends ZIOAppDefault :

  import com.axiom._, dataimport.dataimportapi._
  //TODO MAYBE NOT NEEDED
  val config: CorsConfig =
  CorsConfig(
    allowedOrigin = {  //allows origin from vite server to access routes on server
      case origin if origin == Origin.parse("http://localhost:5173").toOption.get =>
        Some(AccessControlAllowOrigin.Specific(origin))
      case _  => None
    },
  )


  val routes =
    Routes(
      Method.GET / Root -> handler(Response.text("Greetings at your service!!! Arnoldorferdorf!!!xxxxx")),
      Method.GET / "greet" -> handler { (req: Request) =>
        val name = req.queryParamToOrElse("name", "World")
        Response.text(s"Hello $name!")
        },
      Method.GET / "patients"   -> handler {
        import com.axiom._, dataimport._
        Response.text(s"$importpatients")
      },
      Method.GET / "patientsjson"   -> handler {
        import com.axiom._, dataimport._
        import com.axiom.model.shared._, dto._
        import zio.json._
        Response.text(s"${importpatients.toJson}")
      }
    )  @@ cors(config) //cors configuration

  val staticroutes =  Routes.empty @@ Middleware.serveResources(Path.empty / "static") //accesses resources within directories and subdirectories
  override val run = {
    Console.printLine("please visit http://localhost:8080") 
    Server.serve(routes++staticroutes).provide(Server.default)
}

