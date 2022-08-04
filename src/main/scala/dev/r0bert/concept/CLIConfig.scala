package dev.r0bert.concept

import java.io.File

/** The configuration for the command-line interface
  *
  * @param copyright
  *   Print the copyright information.
  * @param behavioursTOML
  *   The file containing the behaviours
  */
final case class CLIConfig(
    copyright: Boolean = false,
    behavioursTOML: File = null
)
