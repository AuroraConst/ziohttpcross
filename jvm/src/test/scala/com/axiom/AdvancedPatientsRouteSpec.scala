package com.axiom



import zio._
import zio.test._
import zio.test.Assertion._
import zio.http._
import zio.json._

object AdvancedPatientsRouteSpec extends ZIOSpecDefault {

  def spec = suite("Advanced Patients Route Tests")(
    
    // Test using the full MainApp routes
    test("GET /patients with full app routes") {
      val app = MainApp.routes
      for {
        request  <- ZIO.succeed(Request.get(URL.decode("/patients").toOption.get))
        response <- app.runZIO(request)
        body     <- response.body.asString
      } yield assertTrue(
        response.status == Status.Ok,
        body.nonEmpty
      )
    },
    
    // Test JSON parsing
    test("GET /patients returns valid JSON") {
      val app = MainApp.routes
      for {
        request  <- ZIO.succeed(Request.get(URL.decode("/patients").toOption.get))
        response <- app.runZIO(request)
        body     <- response.body.asString
        parsed   <- ZIO.fromEither(body.fromJson[List[com.axiom.model.shared.dto.Patient]]).orElseFail("Invalid JSON")
      } yield assertTrue(
        response.status == Status.Ok,
        parsed.isInstanceOf[List[_]]
      )
    },
    
    // Test with query parameters
    test("GET /greet with query params") {
      val app = MainApp.routes
      for {
        request  <- ZIO.succeed(Request.get(URL.decode("/greet?name=TestUser").toOption.get))
        response <- app.runZIO(request)
        body     <- response.body.asString
      } yield assertTrue(
        response.status == Status.Ok,
        body.contains("TestUser")
      )
    },
    
    // Test 404 for non-existent route
    test("GET /nonexistent should return 404") {
      val app = MainApp.routes
      for {
        request  <- ZIO.succeed(Request.get(URL.decode("/nonexistent").toOption.get))
        response <- app.runZIO(request)
      } yield assertTrue(
        response.status == Status.NotFound
      )
    }
  )
}
