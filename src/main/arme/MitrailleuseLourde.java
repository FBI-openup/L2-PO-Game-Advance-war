package main.arme;

import main.unite.*;

import java.util.Map;

public class MitrailleuseLourde extends Arme {
	public MitrailleuseLourde() {
		super(
				"Mitrailleuse Lourde",
				Map.of(
						Infanterie.class, 1.0,
						Bazooka.class, .8,
						Tank.class, .3,
						DCA.class, .3,
						Helicoptere.class, 1.1
				)
		);
	}
}
