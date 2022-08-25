package dev.r0bert.concept.performancerelationships

import dev.r0bert.beliefspread.core.Belief
import dev.r0bert.beliefspread.core.Behaviour
import dev.r0bert.concept.json.PerformanceRelationshipSpec
import java.util.UUID

/** A relationship that describes how the holder of the [[Belief]] wants to
  * perform the [[Behaviour]].
  */
type PerformanceRelationships = Map[(Belief, Behaviour), Double]
