package dev.r0bert.concept

import dev.r0bert.beliefspread.core.Belief
import dev.r0bert.beliefspread.core.Behaviour

/** A relationship that describes how the holder of the [[Belief]] wants to
  * perform the [[Behaviour]].
  */
type PerformanceRelationships = Map[(Belief, Behaviour), Double]
