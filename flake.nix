{
  description = "COLT Unemployment epigenetic analysis";

  inputs.flake-utils.url = "github:numtide/flake-utils";
  inputs.nixpkgs.url = "github:nixos/nixpkgs/nixpkgs-unstable";

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem(system:
      let pkgs = nixpkgs.legacyPackages.${system}; in
      rec {
        devShell = pkgs.mkShell {
          buildInputs = [
	    pkgs.coursier
	    pkgs.jdk
	    pkgs.sbt
          ];
          shellHook = ''
            export PS1="\n\[\033[1;32m\][colt-unemployment-epi:\\w]\$\[\033[0m\] "
          '';
        };
      }
    );
}
