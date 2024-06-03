package main.arme;

import main.unite.*;

import java.util.Map;

public class Bomb extends Arme {
	public Bomb() {
		super(
				"Bomb",
				Map.of(
						Infanterie.class, 1.0,
						Bazooka.class, 1.0,
						Tank.class, 1.0,
						DCA.class, .7,
						Helicoptere.class, 0.0
				)
		);
	}
}
