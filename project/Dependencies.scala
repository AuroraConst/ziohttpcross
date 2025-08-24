import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import sbt._

object Dependencies {
  val zioVersion = "2.1.1"
  val zioJsonVersion = "0.6.2"
  val zioHttpVersion = "3.0.0-RC8"
  val nlites_dataimportcsv3sVersion = "0.0.1-SNAPSHOT"
  val frontrouteVersion = "0.19.0"
  val toolkitVersion = "0.7.0"

  val zioHttp     = "dev.zio" %% "zio-http"     % zioHttpVersion
  val zioTest     = "dev.zio" %% "zio-test"     % zioVersion % Test
  val zioTestSBT = "dev.zio" %% "zio-test-sbt" % zioVersion % Test
  
  val zioTestMagnolia = "dev.zio" %% "zio-test-magnolia" % zioVersion % Test  


  val nlites_dataimportcsv3s = "com.axiom" %% "nlites_dataimportcsv3s" % nlites_dataimportcsv3sVersion
  val toolkit = "org.scala-lang" %% "toolkit" % toolkitVersion


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


  val jsclientlibraries: Def.Initialize[Seq[ModuleID]] = Def.setting {
    Seq(
      nlites_dataimportcsv3s,
      "io.frontroute" %%% "frontroute" % frontrouteVersion,
      "com.raquo" %%% "laminar" % DependencyVersions.laminar,
      "dev.zio"                       %%% "zio-json"          % "0.4.2",

    )
  }

}
