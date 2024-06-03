package main.arme;

import main.unite.*;

import java.util.Map;

public class MitrailleuseLegere extends Arme {
	public MitrailleuseLegere() {
		super(
				"Mitrailleuse Légère",
				Map.of(
						Infanterie.class, .6,
						Bazooka.class, .55,
						Tank.class, .15,
						DCA.class, .10,
						Helicoptere.class, .30
				)
		);
	}
}
