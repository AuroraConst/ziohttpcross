package com.axiom

import zio._
import zio.http._
import zio.http.endpoint._
import zio.http.Middleware.{CorsConfig, cors}
import zio.http.Header.{AccessControlAllowOrigin, Origin}
import zio.schema.codec.JsonCodec.zioJsonBinaryCodec
// import zio.schema.codec.JsonCodec.schemaBasedBinaryCodec                                                                                                                              
// import zio.schema.codec.JsonCodec.zioJsonBinaryCodec                                                                                                                                  
import os.*
import zio.stream.ZStream
object MainApp extends ZIOAppDefault :

  
  //TODO MAYBE NOT NEEDED
  val validSites = List("http://localhost:5173", "http://localhost:8080", "vscode-webview://03js7hav97p76rvju1vgp6e739m8h196h6ic3dcg5268kb1h5e8e").map(Origin.parse(_).toOption.get)
  val config: CorsConfig =
  CorsConfig(
    allowedOrigin = {  //allows origin from vite server to access routes on server
      // case origin if validSites.filter(_ == origin).size > 0 =>
      //   Some(AccessControlAllowOrigin.Specific(origin)) 
      case origin if validSites.filter(_ == origin).size >= 0 =>  //because it allows size = 0, it allows everything
        Some(AccessControlAllowOrigin.Specific(origin)) 

      case _  => None
    },
  )


  val routes =
    Routes(
      Method.GET / Root -> handler(Response.text("Greetings at your service!!! Arnoldorferdorf!!!xxxxx" +
        "http://localhost:8080/assets/static/sse-test.html")),
      Method.GET / "greet" -> handler { (req: Request) =>
        val name = req.queryParamToOrElse("name", "World")
        Response.text(s"Hello $name!")
        },
      Method.GET / "patients"   -> handler {PatientsHandlers.patients},
      
      Method.GET / "patientsjson"   -> handler {PatientsHandlers.patients},
      // Simple Server-Sent Events endpoint
      Method.GET / "events" -> handler { handlers.eventstreamhandlers.events},
      // SSE with real-time data (example: system time)
      Method.GET / "time-events" -> handler {handlers.eventstreamhandlers.timeevents},

      //directory browsing
      Method.GET / "dev" / "browse" -> handler { directorybrowserhandlers.root},
      Method.GET / "dev" / "browse" / string("subpath") -> handler { directorybrowserhandlers.browse } 
    
    )  @@ cors(config) //cors configuration. 

  
  /**
    * eg. http://localhost:8080/assets/static/sse-test.html
    */
  val staticroutes =  Routes.empty @@ Middleware.serveResources(zio.http.Path.empty / "assets") //accesses resources within directories and subdirectories
  override val run = {
    Console.printLine("please visit http://localhost:8083")  
    Server.serve(routes++staticroutes).provide(Server.defaultWithPort(8083))
}

