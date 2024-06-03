package main.unite;

import ressources.Chemins;

public class Bombardier extends Unite {
	public Bombardier(int joueurNum) {
//		FIXME
		super(
				"Bombardier",
				joueurNum,
				null,
				null,
				100,
				100,
				Chemins.FICHIER_BOMBARDIER
		);
	}
}
