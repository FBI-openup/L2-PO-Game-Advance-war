package main.deplacement;

import main.terrain.Terrain;

import java.util.Map;

/**
 * Mode de déplacement d’une unité.
 */
public class Deplacement {
	private final String nom;
	private final Map<Class<? extends Terrain>, Integer> coutDeDeplacement;

	/**
	 * @param nom               nom du type de déplacement
	 * @param coutDeDeplacement cout de déplacement en fonction de Terrain
	 */
	public Deplacement(String nom, Map<Class<? extends Terrain>, Integer> coutDeDeplacement) {
		this.nom = nom;
		this.coutDeDeplacement = coutDeDeplacement;
	}
}
