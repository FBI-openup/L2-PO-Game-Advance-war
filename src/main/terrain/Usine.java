package main.terrain;

import ressources.Chemins;

public class Usine extends Propriete {
	public Usine(int joueurNum) {
		super(
				Chemins.FICHIER_USINE,
				joueurNum
		);
	}
}
