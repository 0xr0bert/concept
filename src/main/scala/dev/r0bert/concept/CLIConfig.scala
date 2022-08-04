package dev.r0bert.concept

import java.io.File

/** The configuration for the command-line interface
  *
  * @param behavioursTOML
  *   The file containing the behaviours
  */
final case class CLIConfig(
    behavioursTOML: File = null
)
