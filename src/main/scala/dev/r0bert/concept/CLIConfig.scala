package dev.r0bert.concept

import java.io.File

/** The configuration for the command-line interface
  *
  * @param behavioursJSON
  *   The file containing the behaviours
  */
final case class CLIConfig(
    behavioursJSON: File = null
)
