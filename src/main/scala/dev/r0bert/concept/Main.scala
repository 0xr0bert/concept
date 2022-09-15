package dev.r0bert.concept

import scopt.OParser
import java.io.File
import scala.io.Source
import play.api.libs.json.Json
import dev.r0bert.concept.json.AgentSpec
import dev.r0bert.concept.json.BehaviourSpec
import dev.r0bert.concept.json.BeliefSpec
import dev.r0bert.concept.json.PerformanceRelationshipSpec
import dev.r0bert.concept.performancerelationships.PerformanceRelationshipUtils.PerformanceRelationshipSpecTools

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
          val bsBeliefs = bs
            .map(_.toBasicBelief(c.behaviours))
            .toArray
          bs
            .foreach(_.linkBeliefRelationships(bsBeliefs))
          c.copy(beliefs = bsBeliefs)
        )
        .text("The beliefs config JSON file, see beliefs.json(5)"),
      opt[File]('a', "agents")
        .required()
        .valueName("<file>")
        .validate(file =>
          if (file.exists) success
          else failure(s"$file does not exist")
        )
        .action((f, c) =>
          val as = Json
            .parse(
              Source
                .fromFile(f)
                .getLines()
                .mkString
            )
            .as[Array[AgentSpec]]
          val asAgents = as
            .map(_.toBasicAgent(c.behaviours, c.beliefs))
            .map(a => (a.uuid, a))
            .toMap
          as
            .foreach(_.linkFriends(asAgents))
          c.copy(agents = asAgents.values.toArray)
        )
        .text("The agents config JSON file, see agents.json(5)"),
      opt[File]('p', "performance-relationships")
        .required()
        .valueName("<file>")
        .validate(file =>
          if (file.exists) success
          else failure(s"$file does not exist")
        )
        .action((f, c) =>
          val prs = Json
            .parse(
              Source
                .fromFile(f)
                .getLines()
                .mkString
            )
            .as[Array[PerformanceRelationshipSpec]]
            .toPerformanceRelationships(
              c.beliefs.map(b => (b.uuid, b)).toMap,
              c.behaviours.map(b => (b.uuid, b)).toMap
            )
          c.copy(performanceRelationships = prs)
        )
        .text(
          "The performance relationships config JSON file, see prs.json(5)"
        ),
      opt[File]('o', "output")
        .required()
        .valueName("<file>")
        .validate(file =>
          if (!file.exists) success
          else failure(s"$file already exists!")
        )
        .action((f, c) => c.copy(outputFile = f))
        .text("The output file is a mutated agents.json(5)")
    )

  OParser.parse(parser, args, CLIConfig()) match {
    case Some(config) =>
      val runner = Runner(config)
      runner.run(1, 2)
    case None => throw RuntimeException("The supplied data is incorrect")
  }
