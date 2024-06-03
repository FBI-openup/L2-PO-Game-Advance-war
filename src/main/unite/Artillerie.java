package main.unite;

import ressources.Chemins;

public class Artillerie extends Unite {

	public Artillerie(int joueurNum) {
//		FIXME
		super(
				"Artillerie",
				joueurNum,
				null,
				null,
				100,
				100,
				Chemins.FICHIER_ARTILLERIE
		);
	}
}
