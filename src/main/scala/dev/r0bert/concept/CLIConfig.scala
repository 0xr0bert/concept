package dev.r0bert.concept

import java.io.File
import dev.r0bert.beliefspread.core.BasicBehaviour
import dev.r0bert.beliefspread.core.BasicBelief
import dev.r0bert.beliefspread.core.BasicAgent
import dev.r0bert.concept.performancerelationships.PerformanceRelationships

/** The configuration for the command-line interface
  *
  * @param outputFile
  *   The output [[File]].
  * @param agents
  *   The [[BasicAgent]]s
  * @param behaviours
  *   The [[BasicBehaviour]]s
  * @param beliefs
  *   The [[BasicBelief]]s
  * @param performanceRelationships
  *   The [[PerformanceRelationships]]
  * @author
  *   Robert Greener
  * @since v0.0.1
  */
final case class CLIConfig(
    outputFile: File = File("output.json"),
    agents: Array[BasicAgent] = Array(),
    behaviours: Array[BasicBehaviour] = Array(),
    beliefs: Array[BasicBelief] = Array(),
    performanceRelationships: PerformanceRelationships = Map.empty
)
