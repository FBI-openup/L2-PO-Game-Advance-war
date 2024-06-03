package main.unite;

import main.arme.Canon;
import main.deplacement.Chenille;
import ressources.Chemins;

public class Tank extends Unite {
	public Tank(int joueurNum) {
		super(
				"Tank",
				joueurNum,
				new Canon(),
				new Chenille(),
				6,
				7000,
				Chemins.FICHIER_TANK
		);
	}
}
