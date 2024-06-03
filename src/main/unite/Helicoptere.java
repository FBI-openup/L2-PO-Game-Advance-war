package main.unite;

import main.arme.Missile;
import main.deplacement.Aerien;
import ressources.Chemins;

public class Helicoptere extends Unite {
	public Helicoptere(int joueurNum) {
		super(
				"Hélicoptère",
				joueurNum,
				new Missile(),
				new Aerien(),
				6,
				12000,
				Chemins.FICHIER_HELICOPTERE
		);
	}
}
