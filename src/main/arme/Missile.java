package main.arme;

import main.unite.*;

import java.util.Map;

public class Missile extends Arme {
	public Missile() {
		super(
				"Missile",
				Map.of(
						Infanterie.class, .5,
						Bazooka.class, .5,
						Tank.class, .7,
						DCA.class, .4,
						Helicoptere.class, .7
				)
		);
	}
}
