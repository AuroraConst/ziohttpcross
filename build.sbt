// give the user a nice default project!
val sharedSettings = Seq(
  scalaVersion := DependencyVersions.scalaVersion,
  organization := "org.aurora",
  scalacOptions ++=  Seq("-Yretain-trees") //necessary in zio-json if any case classes have default parameters
)

lazy val root = project.in(file(".")).
  aggregate(ziohttpcross.js, ziohttpcross.jvm).
  settings(sharedSettings, 
    publish := {},
    publishLocal := {}

  )

lazy val ziohttpcross = crossProject(JSPlatform, JVMPlatform).crossType(CrossType.Full).in(file("."))
  .settings(
    name := "ziohttpcross",
    version := "0.1-SNAPSHOT",
    sharedSettings,
    libraryDependencies ++= Dependencies.zioJson.value,
    libraryDependencies ++= Dependencies.scalatest.value

  ).
  jvmSettings(
    // Add JVM-specific settings here
    ThisBuild / fork              := true,   
    ThisBuild / semanticdbEnabled := true,
    ThisBuild / semanticdbVersion := scalafixSemanticdb.revision,
    ThisBuild / scalafixDependencies ++= List("com.github.liancheng" %% "organize-imports" % "0.6.0"),


    libraryDependencies ++= Seq(
      Dependencies.zioHttp, 
      Dependencies.zioTest,
      Dependencies.zioTestSBT, 
      Dependencies.zioTestMagnolia
  
    )


  ).
  jsSettings(
    // Add JS-specific settings here
    libraryDependencies ++= Dependencies.sttp.value,
    scalaJSUseMainModuleInitializer := true,
    
  )