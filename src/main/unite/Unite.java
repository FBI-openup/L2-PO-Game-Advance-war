package main.unite;

import main.arme.Arme;
import main.deplacement.Deplacement;
import ressources.Chemins;

public class Unite {
	private final String nom;
	private final Arme arme;
	private final Deplacement deplacement;
	private final int limiteDeplacement;
	private final int prix;
	private final String fichier;
	private int joueurNum;
	private double pointDeVie = 10.0;
	private boolean disponible = true;

	/**
	 * Créer un objet Unité
	 *
	 * @param nom               nom du type de l’unité
	 * @param joueurNum         numéro de joueur
	 * @param arme              type d’arme
	 * @param deplacement       mode de déplacement
	 * @param limiteDeplacement nombre maximal de cases dont l’unité peut se déplacer dans un tour.
	 * @param prix              prix de l’unité
	 * @param fichier           nom du fichier image
	 */
	public Unite(String nom, int joueurNum, Arme arme, Deplacement deplacement, int limiteDeplacement, int prix, String fichier) {
		this.nom = nom;
		this.joueurNum = joueurNum;
		this.arme = arme;
		this.deplacement = deplacement;
		this.limiteDeplacement = limiteDeplacement;
		this.prix = prix;
		this.fichier = fichier;
	}

	public Arme getArme() {
		return arme;
	}

	public Deplacement getDeplacement() {
		return deplacement;
	}

	public int getLimiteDeplacement() {
		return limiteDeplacement;
	}

	public int getPrix() {
		return prix;
	}

	public double getPointDeVie() {
		return pointDeVie;
	}

	public String getCheminImage() {
		return Chemins.getCheminUnite(joueurNum, disponible, fichier);
	}

	public int getJoueurNum() {
		return joueurNum;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setPointDeVie(double pointDeVie) {
		this.pointDeVie = pointDeVie;
	}

	public void setJoueurNum(int joueurNum) {
		this.joueurNum = joueurNum;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}


	@Override
	public String toString() {
		return nom;
	}
}