package dev.r0bert.concept

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
}
