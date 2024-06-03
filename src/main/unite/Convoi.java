package main.unite;

import ressources.Chemins;

public class Convoi extends Unite {
	public Convoi(int joueurNum) {
//		FIXME
		super(
				"Convoi",
				joueurNum,
				null,
				null,
				100,
				100,
//				FIXME: can't find image convoi
				Chemins.FICHIER_ARTILLERIE
		);
	}
}
