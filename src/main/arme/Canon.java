package main.arme;

import main.unite.*;

import java.util.Map;

public class Canon extends Arme {
	public Canon() {
		super("Canon",
				Map.of(
						Infanterie.class, .45,
						Bazooka.class, .45,
						Tank.class, .55,
						DCA.class, .6,
						Helicoptere.class, .3)
		);
	}
}
