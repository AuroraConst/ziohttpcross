import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import sbt._

object Dependencies {
  val zioVersion = "2.1.1"
  val zioJsonVersion = "0.6.2"
  val zioHttpVersion = "3.0.0-RC8"

  val zioHttp     = "dev.zio" %% "zio-http"     % zioHttpVersion
  val zioTest     = "dev.zio" %% "zio-test"     % zioVersion % Test
  val zioTestSBT = "dev.zio" %% "zio-test-sbt" % zioVersion % Test
  val zioTestMagnolia = "dev.zio" %% "zio-test-magnolia" % zioVersion % Test  

  val sttp = Def.setting{
    Seq(
      "com.softwaremill.sttp.client4" %%% "core" % "4.0.0-M8"
      // "com.softwaremill.sttp.client4" %%% "json-common" % "4.0.0-M8"
    )
  }

  val scalajsdom  = Def.setting {
    Seq("org.scala-js" %%% "scalajs-dom" % "2.4.0")
  }

  val zioJson = Def.setting {
    Seq("dev.zio" %%% "zio-json" % zioJsonVersion)
  }

  val scalatest   :     Def.Initialize[Seq[ModuleID]] = Def.setting {
    Seq(
      "org.scalactic" %%% "scalactic"  % DependencyVersions.scalatest,
      "org.scalatest" %%% "scalatest" % DependencyVersions.scalatest % "test"
    )
  }


  val laminar: Def.Initialize[Seq[ModuleID]] = Def.setting {
    Seq(
      "com.raquo" %%% "laminar" % DependencyVersions.laminar
    )
  }

}
