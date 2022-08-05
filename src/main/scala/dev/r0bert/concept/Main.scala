package dev.r0bert.concept

import scopt.OParser
import java.io.File
import scala.io.Source

@main def main(args: String*): Unit =
  val builder = OParser.builder[CLIConfig]
  val parser =
    import builder._
    OParser.sequence(
      programName("concept"),
      head("concept", "v0.0.0-UNRELEASED"),
      version('V', "version")
        .text("Print the version information"),
      help('h', "help")
        .text("Print this help message"),
      opt[Unit]('L', "copyright")
        .action((_, c) =>
          val resource = Source.fromResource("dev/r0bert/concept/LICENSE")
          print(resource.mkString)
          sys.exit(0)
        )
        .text("Print the copyright information and exit"),
      opt[File]('b', "behaviours")
        .required()
        .valueName("<file>")
        .action((f, c) => c.copy(behavioursJSON = f))
        .text("The behaviours config JSON file, see behaviours.json(5)")
        .validate(file =>
          if (file.exists) success else failure(s"$file does not exist")
        )
    )

  OParser.parse(parser, args, CLIConfig()) match {
    case Some(config) =>
      println("Valid")
    case None =>
  }
