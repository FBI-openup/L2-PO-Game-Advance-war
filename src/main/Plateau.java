package main;

import main.point.PointAffichage;
import main.point.PointPlateau;
import main.terrain.*;
import main.unite.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Grid of the game
 */
public class Plateau {

	/**
	 * Plateau est formé d’un array à 2D d’object Case, qui contient Terrain et Unite (Nullable).
	 */
	static class Case {
		Terrain terrain;
		Unite unite;
		Fleche fleche;

		@Override
		public String toString() {
			return terrain.toString() +
					(unite != null ? " " + unite : "") +
					(fleche != null ? " " + fleche : "");
		}
	}


	private Terrain getTerrain(String nomTerrain) {
		return switch (nomTerrain) {
			case "Montagne" -> new Montagne();
			case "Foret" -> new Foret();
			case "Plaine" -> new Plaine();
			case "Eau" -> new Eau();
			default -> null;
		};
	}

	private Function<Integer, Propriete> getPropriete(String nomPropriete) {
		return switch (nomPropriete) {
			case "Ville" -> Ville::new;
			case "Usine" -> Usine::new;
			case "QG" -> QG::new;
			default -> null;
		};
	}

	private Function<Integer, Unite> getUnite(String nomUnite) {
		return switch (nomUnite) {
			case "Artillerie" -> Artillerie::new;
			case "Bombardier" -> Bombardier::new;
			case "Bazooka" -> Bazooka::new;
			case "Convoit" -> Convoi::new;
			case "DCA" -> DCA::new;
			case "Helico" -> Helicoptere::new;
			case "Infanterie" -> Infanterie::new;
			case "Tank" -> Tank::new;
			default -> null;
		};
	}

	private final Case[][] plateau;

	private final int length;
	private final int width;


	/**
	 * Créer un object plateau
	 *
	 * @param str une carte provenant de `ParseurCartes.parseCarte`
	 */
	public Plateau(String[][] str) {
		length = str.length;
		width = str[0].length;
		plateau = new Case[length][width];

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				Case caze = new Case();
				final String[] terrainUnite = str[i][j].split(";");

				final String[] terrainJoueurNum = terrainUnite[0].split(":");
				if (terrainJoueurNum.length == 1) {
					caze.terrain = getTerrain(terrainJoueurNum[0]);
				} else {
					caze.terrain = getPropriete(terrainJoueurNum[0]).apply(Integer.valueOf(terrainJoueurNum[1]));
				}

				if (terrainUnite.length != 1) {
					final String[] uniteJoueurNum = terrainUnite[1].split(":");
					caze.unite = getUnite(uniteJoueurNum[0]).apply(Integer.valueOf(uniteJoueurNum[1]));
				}
				plateau[i][j] = caze;
			}
		}


	}

	public int getLength() {
		return length;
	}

	public int getWidth() {
		return width;
	}

	private Case getCase(PointPlateau pp) throws IndexOutOfBoundsException {
		if (0 > pp.getX() || pp.getX() > length) {
			throw new IndexOutOfBoundsException("Out of bound vertically");
		} else if (0 > pp.getY() || pp.getY() > width) {
			throw new IndexOutOfBoundsException("Out of bound horizontally");
		}
		return plateau[pp.getX()][pp.getY()];
	}

	private Case getCase(PointAffichage pa) throws IndexOutOfBoundsException {
		return getCase(PointPlateau.fromPointAffichage(pa));
	}


	public Terrain getTerrain(PointPlateau pp) {
		return getCase(pp).terrain;
	}

	public Terrain getTerrain(PointAffichage pa) {
		return getCase(pa).terrain;
	}

	public Optional<Unite> getUnite(PointPlateau pp) {
		return Optional.ofNullable(getCase(pp).unite);
	}

	//TODO guard get methods with valid
	public Optional<Unite> getUnite(PointAffichage pa) {
		return Optional.ofNullable(getCase(pa).unite);
	}

	public Optional<Fleche> getFleche(PointPlateau pp) {
		return Optional.ofNullable(getCase(pp).fleche);
	}

	public Optional<Fleche> getFleche(PointAffichage pa) {
		return Optional.ofNullable(getCase(pa).fleche);
	}

//	TODO: reset all disponibilité à la fin d'une tour

	public boolean valid(PointPlateau pp) {
		return (0 <= pp.getX() && pp.getX() < length) &&
				(0 <= pp.getY() && pp.getY() < width);

	}

	public boolean valid(PointAffichage pa) {
		return valid(PointPlateau.fromPointAffichage(pa));
	}

	public void moveUnite(PointPlateau from, PointPlateau to) {
		plateau[to.getX()][to.getY()].unite = plateau[from.getX()][from.getY()].unite;
		plateau[from.getX()][from.getY()].unite = null;
	}

	public void clearFleche() {
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				plateau[i][j].fleche = null;
			}
		}
	}

	public void resetDisponibilite() {
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				if (plateau[i][j].unite != null) {
					plateau[i][j].unite.setDisponible(true);
				}
			}
		}
	}

	public void setFleches(List<PointAffichage> path) {
		final PointAffichage from = path.get(0);
		final List<Direction> dirs = Direction.getDirections(path);
		if (dirs != null) {
			PointAffichage walker = from.clone();
			for (int i = 0; i < dirs.size() - 1; i++) {
				getCase(walker).fleche = new Fleche(dirs.get(i), dirs.get(i + 1));
				walker.move(dirs.get(i + 1));
				dirs.set(i + 1, dirs.get(i + 1).opposite());
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				sb.append(plateau[i][j]);
				if (j != width - 1) {
					sb.append(" | ");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
