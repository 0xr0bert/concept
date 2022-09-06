{ jdk ? "jdk11" }:

let
  pkgs = import <nixpkgs> { inherit jdk; };
in
  pkgs.mkShell {
    buildInputs = [
      pkgs.coursier
      pkgs.${jdk}
      pkgs.sbt
    ];
  }
