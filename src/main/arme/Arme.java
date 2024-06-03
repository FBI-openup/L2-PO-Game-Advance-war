package main.arme;

import main.unite.Unite;

import java.util.Map;

/**
 * L’arme d’une unité
 */
public class Arme {
	private final String nom;
	private final Map<Class<? extends Unite>, Double> attaqueEfficacite;

	/**
	 * @param nom               nom du type d’arme
	 * @param attaqueEfficacite efficacité d’attaque en fonction d'Unité ennemie.
	 */
	public Arme(String nom, Map<Class<? extends Unite>, Double> attaqueEfficacite) {
		this.nom = nom;
		this.attaqueEfficacite = attaqueEfficacite;
	}

	public double getAttaqueEfficacite(Class<? extends Unite> defenseur) {
		return attaqueEfficacite.get(defenseur);
	}
}
