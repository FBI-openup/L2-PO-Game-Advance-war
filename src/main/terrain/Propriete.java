package main.terrain;

import ressources.Chemins;

/**
 * Un type de terrain particulier
 */
public class Propriete extends Terrain {
	private int joueurNum;
	private boolean captured;

	public Propriete(String fichier, int joueurNum) {
		super(fichier);
		this.joueurNum = joueurNum;
		this.captured = false;
	}

	@Override
	public String getCheminImage() {
		return Chemins.getCheminPropriete(fichier, joueurNum);
	}

	@Override
	public String toString() {
		return super.toString() + ":" + joueurNum;
	}

	public int getJoueurNum() {
		return joueurNum;
	}

	public boolean isCaptured() {
		return captured;
	}

	public void setJoueurNum(int joueurNum) {
		this.joueurNum = joueurNum;
	}

	public void setCaptured(boolean captured) {
		this.captured = captured;
	}
}
