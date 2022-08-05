package dev.r0bert.concept

import java.io.File
import dev.r0bert.beliefspread.core.BasicBehaviour

/** The configuration for the command-line interface
  *
  * @param behaviours
  *   The [[BasicBehaviour]]s
  */
final case class CLIConfig(
    behaviours: Array[BasicBehaviour] = Array()
)
