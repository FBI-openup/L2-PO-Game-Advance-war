package main.unite;

import main.arme.MitrailleuseLegere;
import main.deplacement.Pied;
import ressources.Chemins;

public class Infanterie extends Unite {
	public Infanterie(int joueurNum) {
		super(
				"Infanterie",
				joueurNum,
				new MitrailleuseLegere(),
				new Pied(),
				3,
				1500,
				Chemins.FICHIER_INFANTERIE
		);
	}
}
