package dev.r0bert.concept

import scopt.OParser

@main def main(args: String*): Unit =
  val builder = OParser.builder[CLIConfig]
  val parser =
    import builder._
    OParser.sequence(
      programName("concept"),
      head("concept", "v0.0.0-UNRELEASED"),
      version('V', "version")
        .text("Print the version information"),
      help('h', "help")
        .text("Print this help message"),
      opt[Unit]('L', "copyright")
        .action((_, c) => c.copy(copyright = true))
        .text("Print the copyright information and exit")
    )

  OParser.parse(parser, args, CLIConfig()) match {
    case Some(config) if config.copyright =>
      println(license)
    case Some(config) =>
      println("Valid")
    case None =>
      println("Not valid")
  }

def license = """Copyright (c) 2022, Robert Greener

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE."""
