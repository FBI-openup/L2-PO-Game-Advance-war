package main.terrain;

import ressources.Chemins;

/**
 * Terrain du jeu
 */
public class Terrain {
	protected final String fichier;

	/**
	 * @param fichier nom du fichier image
	 */
	public Terrain(String fichier) {
		this.fichier = fichier;
	}

	public String getCheminImage() {
		return Chemins.getCheminTerrain(fichier);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
