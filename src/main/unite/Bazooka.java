package main.unite;

import main.arme.Canon;
import main.deplacement.Pied;
import ressources.Chemins;

public class Bazooka extends Unite {
	public Bazooka(int joueurNum) {
		super("Bazooka",
				joueurNum,
				new Canon(),
				new Pied(),
				2,
				3500,
				Chemins.FICHIER_BAZOOKA
		);
	}
}
