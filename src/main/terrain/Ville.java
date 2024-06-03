package main.terrain;

import ressources.Chemins;

public class Ville extends Propriete {
	public Ville(int joueurNum) {
		super(
				Chemins.FICHIER_VILLE,
				joueurNum
		);
	}
}
