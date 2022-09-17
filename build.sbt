val scala3Version = "3.2.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "concept",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0-M6" % Test,
      "dev.r0bert" %% "beliefspread" % "0.16.0",
      "com.github.scopt" %% "scopt" % "4.1.0",
      "com.typesafe.play" %% "play-json" % "2.10.0-RC6",
      "ch.qos.logback" % "logback-classic" % "1.4.0",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5"
    )
  )
scalacOptions := Seq("-unchecked", "-deprecation")

ThisBuild / assemblyMergeStrategy := {
  case "module-info.class" => MergeStrategy.discard
  case x                   => assemblyMergeStrategy.value(x)
}
