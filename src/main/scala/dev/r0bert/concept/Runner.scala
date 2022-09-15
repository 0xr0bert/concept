package dev.r0bert.concept

import dev.r0bert.beliefspread.core.Agent

import scala.util.Random
import com.typesafe.scalalogging.Logger
import play.api.libs.json.Json
import dev.r0bert.concept.json.AgentSpec
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.File

/** This runs the simulation.
  *
  * @param config
  *   The [[CLIConfig]] of the simulation
  * @param logger
  *   The logger.
  * @author
  *   Robert Greener
  * @since v0.0.1
  */
class Runner(
    config: CLIConfig,
    logger: Logger = Logger("concept")
) {

  /** Run the simulation between time start and end.
    *
    * @param start
    *   The start time.
    * @param end
    *   The end time.
<<<<<<< HEAD
=======
    * @author
    *   Robert Greener
    * @since v0.0.1
>>>>>>> 2c69ba8 (Runner.run: Add @author and @since scaladoc)
    */
  def run(start: Int, end: Int): Unit =
    logger.info("Starting concept")
    logger.info(s"n beliefs: ${config.beliefs.size}")
    logger.info(s"n behaviours: ${config.behaviours.size}")
    logger.info(s"n agents: ${config.agents.size}")
    logger.info(s"Start time: ${start}")
    logger.info(s"End time: ${end}")
    tickBetween(start, end)
    logger.info(s"Ending concept")
    serializeAgents()

  /** Serialize the agents to a json file.
    *
    * Writes to [[CLIConfig.outputFile]]
    *
    * @author
    *   Robert Greener
    * @since v0.0.1
    */
  def serializeAgents(): Unit =
    logger.info("Converting agents to JSON")
    val specs = config.agents.map(AgentSpec.fromAgent(_)).toArray
    val output =
      Json.toJson(specs).toString()
    logger.info("Creating output file")
    val bw = BufferedWriter(FileWriter(config.outputFile))
    logger.info(s"Writing output to ${config.outputFile.getPath()}")
    bw.write(output)
    bw.close()
    logger.info("Writing agents complete")

  /** Tick starting from time 1.
    *
    * @param end
    *   The end time (inclusive).
    * @see
    *   [[tickBetween]]
    * @author
    *   Robert Greener
    * @since v0.0.1
    */
  def tickTo(end: Int): Unit = tickBetween(1, end)

  /** Tick between the start and end (inclusive).
    *
    * @param start
    *   The start time (inclusive).
    * @param end
    *   The end time (inclusive).
    * @author
    *   Robert Greener
    * @since 0.0.1
    */
  def tickBetween(start: Int, end: Int): Unit =
    (start to end).foreach(tick(_))

  /** Tick this time of the simulation (i.e., do everything expected).
    *
    * @param time
    *   The current simulation time.
    * @author
    *   Robert Greener
    * @since v0.0.1
    */
  def tick(time: Int): Unit =
    logger.info(s"Day ${time} - perceiving beliefs")
    perceiveBeliefs(time)
    logger.info(s"Day ${time} - performing actions")
    performActions(time)

  /** Perceive the beliefs for every agent.
    *
    * @param time
    *   The current simulation time.
    * @author
    *   Robert Greener
    * @since v0.0.1
    */
  def perceiveBeliefs(time: Int): Unit =
    config.agents.foreach(a =>
      config.beliefs.foreach(b => a.updateActivation(time, b, config.beliefs))
    )

  /** Choose the action of an agent.
    *
    * @param agent
    *   The [[Agent]].
    * @param time
    *   The current simulation time.
    * @author
    *   Robert Greener
    * @since v0.0.1
    */
  def agentPerformAction(agent: Agent, time: Int): Unit =
    val unnormalizedProbs = config.behaviours
      .map(behaviour =>
        (
          behaviour,
          config.beliefs
            .map(belief =>
              config.performanceRelationships
                .getOrElse((belief, behaviour), 0.0)
                * agent.getActivation(time, belief).getOrElse(0.0)
            )
            .sum
        )
      )
      .toMap

    val normalizingFactor = unnormalizedProbs.values.sum
    val normalizedProbs =
      unnormalizedProbs.view.mapValues(_ / normalizingFactor).toArray

    val r = Random()
    var rv = r.nextDouble()
    var chosenBehaviour = normalizedProbs.last._1
    for ((behaviour, v) <- normalizedProbs) {
      rv = rv - v
      if (rv <= 0) chosenBehaviour = behaviour
    }

    agent.setAction(time, Some(chosenBehaviour))

  /** Perform the actions of all agents
    *
    * @param time
    *   The current simulation time.
    * @see
    *   [[agentPerformAction]]
    * @author
    *   Robert Greener
    * @since v0.0.1
    */
  def performActions(time: Int): Unit =
    config.agents.foreach(agentPerformAction(_, time))
}
