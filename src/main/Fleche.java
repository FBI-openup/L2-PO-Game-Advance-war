package main;

import ressources.Chemins;

public class Fleche {
	private final String cheminImage;

	private final Direction from;
	private final Direction to;

	public Fleche(Direction from, Direction to) {
		this.from = from;
		this.to = to;
		cheminImage = Chemins.getCheminFleche(from.toString(), to.toString());
	}


	public String getCheminImage() {
		return cheminImage;
	}

	@Override
	public String toString() {
		return "Fleche{" +
				"from=" + from +
				", to=" + to +
				'}';
	}
}
