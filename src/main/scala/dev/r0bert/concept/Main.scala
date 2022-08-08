package dev.r0bert.concept

import scopt.OParser
import java.io.File
import scala.io.Source
import play.api.libs.json.Json
import dev.r0bert.concept.json.BehaviourSpec
import dev.r0bert.concept.json.BeliefSpec
import scala.collection.parallel.CollectionConverters._

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
        .validate(file =>
          if (file.exists) success
          else failure(s"$file does not exist")
        )
        .action((f, c) =>
          val json = Json.parse(Source.fromFile(f).getLines().mkString)
          val bs =
            json
              .as[Array[BehaviourSpec]]
              .par
              .map(_.toBasicBehaviour)
              .toArray
          c.copy(behaviours = bs)
        )
        .text("The behaviours config JSON file, see behaviours.json(5)"),
      opt[File]('c', "beliefs")
        .required()
        .valueName("<file>")
        .validate(file =>
          if (file.exists) success
          else failure(s"$file does not exist")
        )
        .action((f, c) =>
          val bs = Json
            .parse(
              Source
                .fromFile(f)
                .getLines()
                .mkString
            )
            .as[Array[BeliefSpec]]
            .par
            .map(_.toBasicBelief)
            .toArray
          c.copy(beliefs = bs)
        )
        .text("The beliefs config JSON file, see beliefs.json(5)")
    )

  OParser.parse(parser, args, CLIConfig()) match {
    case Some(config) =>
      println("Valid")
    case None =>
  }
