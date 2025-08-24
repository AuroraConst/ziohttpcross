ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / organization := "org.aurora"
ThisBuild / scalaVersion := DependencyVersions.scalaVersion

ThisBuild / scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-feature",
  "-Yretain-trees",
  "-Wunused:all" //necessary in zio-json if any case classes have default parameters
)

// Simple command alias to start the ZIO http server
addCommandAlias("dev", "project ziohttpcrossJVM; ~reStart")
addCommandAlias("devServer", "project ziohttpcrossJVM; ~reStart")




lazy val root = project.in(file(".")).
  aggregate(ziohttpcross.js, ziohttpcross.jvm).
  settings(
    publish := {},
    publishLocal := {}
  )

lazy val ziohttpcross = crossProject(JSPlatform, JVMPlatform).crossType(CrossType.Full).in(file("."))
  .settings(
    name := "ziohttpcross",
    version := "0.1-SNAPSHOT",
    libraryDependencies ++= Dependencies.zioJson.value,
    libraryDependencies ++= Dependencies.scalatest.value

  ).
  jvmSettings(
    // Add JVM-specific settings here
    ThisBuild / fork              := false,   //of rourse javascripts cannot fork as they are single threaded
    ThisBuild / semanticdbEnabled := true,
    ThisBuild / semanticdbVersion := scalafixSemanticdb.revision,
    ThisBuild / scalafixDependencies ++= List("com.github.liancheng" %% "organize-imports" % "0.6.0"),

    // Set working directory to app folder
    Compile / run / baseDirectory := (ThisBuild / baseDirectory).value / "app",

    libraryDependencies ++= Seq(
      Dependencies.zioHttp, 
      Dependencies.zioTest,
      Dependencies.zioTestSBT, 
      Dependencies.zioTestMagnolia,
      Dependencies.nlites_dataimportcsv3s,
      Dependencies.toolkit
  
    )


  ).
  jsSettings(
    // Add JS-specific settings here
    libraryDependencies ++= Dependencies.jsclientlibraries.value,


    // scalaJSUseMainModuleInitializer := true
    
  )



lazy val app = (project in file("app"))
  .settings(
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) },
    semanticdbEnabled               := true,
    autoAPIMappings                 := true,
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Dependencies.jsclientlibraries.value,
    Compile / mainClass             := Some(("org.aurora.app.MainApp"))
  )
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(ziohttpcross.js)