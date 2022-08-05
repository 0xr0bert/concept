val scala3Version = "3.1.3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "concept",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0-M6" % Test,
      "dev.r0bert" % "beliefspread_3" % "0.15.0",
      "com.github.scopt" %% "scopt" % "4.1.0"
    )
  )
