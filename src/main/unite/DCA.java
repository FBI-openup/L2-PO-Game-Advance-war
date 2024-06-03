package main.unite;

import main.arme.MitrailleuseLourde;
import main.deplacement.Chenille;
import ressources.Chemins;

public class DCA extends Unite {
	public DCA(int joueurNum) {
		super("DCA",
				joueurNum,
				new MitrailleuseLourde(),
				new Chenille(),
				6,
				6000,
				Chemins.FICHIER_ANTIAIR
		);
	}
}
