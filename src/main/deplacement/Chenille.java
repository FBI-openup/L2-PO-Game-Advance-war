package main.deplacement;

import main.terrain.*;

import java.util.Map;

public class Chenille extends Deplacement {
	public Chenille() {
		super(
				"Chenille",
				Map.of(
						Propriete.class, 1,
						Plaine.class, 1,
						Foret.class, 2,
						Montagne.class, Integer.MAX_VALUE,
						Eau.class, Integer.MAX_VALUE
				)
		);
	}
}
