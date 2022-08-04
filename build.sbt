val scala3Version = "3.1.3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "concept",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "0.7.29" % Test,
      "dev.r0bert" % "beliefspread_3" % "0.15.0"
    )
  )
