package dev.r0bert.concept

import dev.r0bert.beliefspread.core.Agent

import scala.util.Random

/** This runs the simulation.
  *
  * @param config
  *   The [[CLIConfig]] of the simulation
  * @author
  *   Robert Greener
  * @since v0.0.1
  */
class Runner(
    config: CLIConfig
) {

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
    perceiveBeliefs(time)
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
