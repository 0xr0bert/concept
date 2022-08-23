package dev.r0bert.concept

import java.io.File
import dev.r0bert.beliefspread.core.BasicBehaviour
import dev.r0bert.beliefspread.core.BasicBelief
import dev.r0bert.beliefspread.core.BasicAgent

/** The configuration for the command-line interface
  *
  * @param agents
  *   The [[BasicAgent]]s
  * @param behaviours
  *   The [[BasicBehaviour]]s
  * @param beliefs
  *   The [[BasicBelief]]s
  * @author
  *   Robert Greener
  * @since v0.0.1
  */
final case class CLIConfig(
    agents: Array[BasicAgent] = Array(),
    behaviours: Array[BasicBehaviour] = Array(),
    beliefs: Array[BasicBelief] = Array()
)
