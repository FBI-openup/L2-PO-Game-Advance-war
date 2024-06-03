/**
 * package principal
 */
package main;

import librairies.AssociationTouches;
import librairies.StdDraw;
import main.point.PointAffichage;
import main.point.PointPlateau;
import main.terrain.Propriete;
import main.terrain.Usine;
import main.unite.Infanterie;
import main.unite.Unite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ressources.Affichage;
import ressources.Config;
import ressources.ParseurCartes;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * La classe qui gère la logique du jeu
 */
public class Jeu {

	/**
	 * Les états du jeu. Indique ce qui est "en cours".
	 */
	private static enum Etat {
		Initial,
		Displacement,
		CaptureOrAttack,
		Fabrication,
	}

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Plateau plateau;
	private final PointAffichage curseur;
	private final PointAffichage attaquantPosition;
	private final PointAffichage defenseurPosition;


	private Etat etat;
	private List<PointAffichage> path;
	/**
	 * L'indice 0 est reserve au neutre, qui ne joue pas, mais peut posséder des propriétés
	 * L'indice du joueur actif : 1 = rouge, 2 = bleu
	 */
	private int indexJoueurActif;


	public Jeu(String fileName) throws IOException {
		String[][] carteString = ParseurCartes.parseCarte(fileName);
		plateau = new Plateau(carteString);
		curseur = new PointAffichage(0, 0);
		attaquantPosition = new PointAffichage(0, 0);
		defenseurPosition = new PointAffichage(0, 0);

		etat = Etat.Initial;
		path = new ArrayList<>();
		indexJoueurActif = 1;

		Config.setDimension(carteString[0].length, carteString.length);

		logger.debug("Plateau\n{}", plateau);
	}

	/**
	 * @return true if the game's over
	 */
	public boolean isOver() {
		return false;
	}

	public void afficheStatutJeu() {
		Affichage.videZoneTexte();
		Affichage.afficheTexteDescriptif("Status du jeu");
	}

	public void initialDisplay() {
		// Rend l'affichage plus fluide.
		// Tout draw est mis en buffer et ne s'affiche qu'au prochain StdDraw.show();
		StdDraw.enableDoubleBuffering();
		display();
	}

	/**
	 * Loop through the grid and show stuff.
	 */
	public void display() {
		StdDraw.clear();
		afficheStatutJeu();
		Affichage.dessineGrille();

		for (int i = 0; i < plateau.getLength(); i++) {
			for (int j = 0; j < plateau.getWidth(); j++) {
				final PointPlateau pPlateau = new PointPlateau(i, j);
				final PointAffichage pAffichage = PointAffichage.fromPointPlateau(pPlateau);

				Affichage.dessineImageDansCase(
						pAffichage.getX(),
						pAffichage.getY(),
						plateau.getTerrain(pPlateau).getCheminImage()
				);
				if (plateau.getUnite(pPlateau).isPresent()) {
					final Unite unite = plateau.getUnite(pPlateau).get();
					Affichage.dessineImageDansCase(
							pAffichage.getX(),
							pAffichage.getY(),
							unite.getCheminImage()
					);
					if (unite.getPointDeVie() < 10) {
						Affichage.afficheTexteDansCase(
								pAffichage.getX(),
								pAffichage.getY(),
								Integer.toString((int) Math.ceil(unite.getPointDeVie())),
								Color.WHITE,
								0.7, 0.3,
								new Font("Monospaced", Font.BOLD, 15)
						);
					}
				}
				if (plateau.getFleche(pPlateau).isPresent()) {
					Affichage.dessineImageDansCase(
							pAffichage.getX(),
							pAffichage.getY(),
							plateau.getFleche(pPlateau).get().getCheminImage()
					);
				}


			}
		}

		logger.debug("Curseur à {}", curseur);
		Affichage.dessineCurseur(curseur.getX(), curseur.getY());

		// Montre à l'écran les changements demandés
		StdDraw.show();
	}

	/**
	 * @param path a list of coordinates
	 * @param next a subsequent point to path
	 * @return path in its simplest form
	 */
	public List<PointAffichage> simplifyPath(List<PointAffichage> path, PointAffichage next) {
		final ArrayList<PointAffichage> walked = new ArrayList<>(path);
		if (walked.contains(next)) {
			return walked.subList(0, walked.indexOf(next));
		}
		return walked;
	}

	/**
	 * @param direction a direction
	 * @return whether the cursor can move towards that direction
	 */
	public boolean canMoveCurseur(Direction direction) {

		final PointAffichage next;
		if (etat != Etat.CaptureOrAttack) {
			next = curseur.clone();
		} else {
			next = attaquantPosition.clone();
		}

		next.move(direction);
		logger.debug(plateau.valid(next) ? "Valid coordinates" : "Invalid coordinates");

		return plateau.valid(next) && switch (etat) {
			case Initial ->
					plateau.getUnite(next).isEmpty() || plateau.getUnite(next).get().getJoueurNum() == indexJoueurActif;
			case Displacement ->
				// Path length guard
					(plateau.getUnite(path.get(0)).isPresent() && simplifyPath(path, next).size() <= plateau.getUnite(path.get(0)).get().getLimiteDeplacement())
							// Enemy guard
							&& (plateau.getUnite(next).isEmpty() || plateau.getUnite(next).get().getJoueurNum() == indexJoueurActif);
			case CaptureOrAttack -> unitesEnnemiesAdjacentes().contains(next)
					|| (plateau.getUnite(attaquantPosition).isPresent()
					&& plateau.getUnite(attaquantPosition).get().getClass() == Infanterie.class
					&& plateau.getTerrain(next).getClass().getSuperclass() == Propriete.class);
			case Fabrication -> plateau.getUnite(curseur).isEmpty();
		};
	}

	/**
	 * Move cursor towards a direction.
	 *
	 * @param direction direction
	 */
	public void moveCurseur(Direction direction) {
		if (!canMoveCurseur(direction)) {
			logger.debug("Can't move cursor");
			return;
		}

		// Move back to attaquant's position
		if (etat == Etat.CaptureOrAttack) {
			curseur.moveTo(attaquantPosition);
		}

		curseur.move(direction);
		if (etat == Etat.Displacement) {
			path = simplifyPath(path, curseur);
			path.add(curseur.clone());
			plateau.clearFleche();
			plateau.setFleches(path);
		} else if (etat == Etat.CaptureOrAttack) {
			defenseurPosition.moveTo(curseur);
		}
		display();
	}

	private void nextJoueur() {
		if (indexJoueurActif == 1) {
			indexJoueurActif = 2;
		} else {
			indexJoueurActif = 1;
		}
	}

	/**
	 * @return next state
	 */
	public Etat sauvegardeDepart() {
		if (plateau.getUnite(curseur).isPresent()
				&& plateau.getUnite(curseur).get().isDisponible()) {
			logger.debug("Déplacement : sauvegarde de départ");
			path.add(curseur.clone());
			return Etat.Displacement;
		}

		return etat;
	}

	/**
	 * @return next state
	 */
	public Etat deplacer() {
		if (plateau.getUnite(curseur).isEmpty()) {
			logger.debug("Déplacement : de {} à {}", path.get(0), curseur);
			// Only test if current spot is empty
			plateau.moveUnite(
					PointPlateau.fromPointAffichage(path.get(0)),
					PointPlateau.fromPointAffichage(curseur)
			);
			plateau.getUnite(curseur).get().setDisponible(false);
			attaquantPosition.moveTo(curseur);
			path.clear();
			plateau.clearFleche();

			// Skip attack if there's no enemy around.
			if (unitesEnnemiesAdjacentes().size() != 0
					|| (plateau.getUnite(attaquantPosition).isPresent()
					&& plateau.getUnite(attaquantPosition).get().getClass() == Infanterie.class)) {
				return Etat.CaptureOrAttack;
			} else {
				return Etat.Initial;
			}
		}

		return etat;
	}

	/**
	 * @return next state
	 */
	public Etat attaque() {
		if (plateau.getUnite(attaquantPosition).isPresent()
				&& plateau.getUnite(defenseurPosition).isPresent()) {
			final Unite attaquant = plateau.getUnite(attaquantPosition).get();
			final Unite defenseur = plateau.getUnite(defenseurPosition).get();
			// Attaque
			defenseur.setPointDeVie(defenseur.getPointDeVie()
					- Math.ceil(attaquant.getPointDeVie())
					*attaquant.getArme().getAttaqueEfficacite(defenseur.getClass())
			);
			// Riposte
			attaquant.setPointDeVie(attaquant.getPointDeVie()
					- Math.ceil(defenseur.getPointDeVie())
					*defenseur.getArme().getAttaqueEfficacite(attaquant.getClass())
			);

			return Etat.Initial;
		}

		return etat;
	}

	/**
	 * @return next state
	 */
	public Etat fabricate() {
		//TODO
		if (plateau.getTerrain(curseur).getClass() == Usine.class) {
			String[] options = {"Oui", "Non"};
			if (Affichage.popup("Test popup" + indexJoueurActif + " ?", options, true, 1) == 0) {

			}

			etat = Etat.Fabrication;
		}

		return etat;
	}

	/**
	 * @return next state
	 */
	public Etat choisirEmplacement() {
//		TODO
		return etat;
	}

	/**
	 * @return next state
	 */
	public Etat capture() {
		logger.debug("capture called");
		if (plateau.getUnite(attaquantPosition).isPresent()
				&& plateau.getUnite(attaquantPosition).get().getClass() == Infanterie.class
				&& plateau.getTerrain(curseur).getClass().getSuperclass() == Propriete.class
				&& ((Propriete) plateau.getTerrain(curseur)).getJoueurNum() != indexJoueurActif) {
			((Propriete) plateau.getTerrain(curseur)).setJoueurNum(indexJoueurActif);
			((Propriete) plateau.getTerrain(curseur)).setCaptured(true);

			return Etat.Initial;
		}
		return etat;
	}

	/**
	 * @return Les unités à la portée de l’unité attaquante
	 */
	public List<PointAffichage> unitesEnnemiesAdjacentes() {
		final List<PointAffichage> adjacents = Stream.of(
				new PointAffichage(attaquantPosition.getX() - 1, attaquantPosition.getY()),
				new PointAffichage(attaquantPosition.getX() + 1, attaquantPosition.getY()),
				new PointAffichage(attaquantPosition.getX(), attaquantPosition.getY() - 1),
				new PointAffichage(attaquantPosition.getX(), attaquantPosition.getY() + 1)
		).filter(
				(p) -> plateau.valid(p)
						&& plateau.getUnite(p).isPresent()
						&& (plateau.getUnite(p).get().getJoueurNum() != indexJoueurActif)
		).toList();

		logger.debug("Possible points {}", adjacents);
		if (adjacents.size() != 0) {
			return adjacents;
		}
		return List.of();
	}

	public void update() {
		AssociationTouches toucheSuivante = AssociationTouches.trouveProchaineEntree(); //cette fonction boucle jusqu'à la prochaine entree de l'utilisateur
		logger.debug("Path: {}", path);
		if (toucheSuivante.isHaut()) {
			moveCurseur(Direction.Up);
		}
		if (toucheSuivante.isBas()) {
			moveCurseur(Direction.Down);
		}
		if (toucheSuivante.isGauche()) {
			moveCurseur(Direction.Left);
		}
		if (toucheSuivante.isDroite()) {
			moveCurseur(Direction.Right);
		}

		if (toucheSuivante.isEntree()) {
			logger.debug("Current state: {}", etat);

			etat = switch (etat) {
				case Initial -> {
					if (plateau.getUnite(curseur).isPresent()) {
						yield sauvegardeDepart();
					} else {
						yield fabricate();
					}
				}

				case Displacement -> deplacer();

				case CaptureOrAttack -> {
					if (plateau.getUnite(curseur).isPresent()) {
						yield attaque();
					} else {
						yield capture();
					}
				}

				case Fabrication -> choisirEmplacement();
			};

			logger.debug("Next state: {}", etat);

			display();
		}


		//  ATTENTION ! si vous voulez détecter d'autres touches que 't',
		//  vous devez les ajouter au tableau Config.TOUCHES_PERTINENTES_CARACTERES
		if (toucheSuivante.isCaractere('t')) {
			logger.debug("hit t");
			String[] options = {"Oui", "Non"};
			if (Affichage.popup("Finir le tour de joueur " + indexJoueurActif + " ?", options, true, 1) == 0) {
				// Le choix 0, "Oui", a été sélectionné.
				logger.debug("FIN DE TOUR");
				nextJoueur();
				plateau.resetDisponibilite();
				etat = Etat.Initial;
				logger.debug("Joueur {}", indexJoueurActif);
			}

			display();
		}
	}
}

