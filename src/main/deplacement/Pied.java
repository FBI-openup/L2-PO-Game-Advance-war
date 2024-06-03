package main.deplacement;

import main.terrain.*;

import java.util.Map;

public class Pied extends Deplacement {
	public Pied() {
		super(
				"Pied",
				Map.of(
						Propriete.class, 1,
						Plaine.class, 1,
						Foret.class, 1,
						Montagne.class, 2,
						Eau.class, Integer.MAX_VALUE
				)
		);
	}
}
