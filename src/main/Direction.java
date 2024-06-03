package main;

import main.point.PointAffichage;

import java.util.ArrayList;
import java.util.List;

/**
 * Directions that represent the relative position between two points
 */
public enum Direction {
	Begin, End,
	Right, Left, Down, Up;

	public Direction opposite() {
		return switch (this) {
			case Right -> Left;
			case Left -> Right;
			case Down -> Up;
			case Up -> Down;
			default -> this;
		};
	}

	/**
	 * @param from un point
	 * @param to   un point
	 * @return "to" est à ___ de "from"
	 */
	public static Direction getDirection(PointAffichage from, PointAffichage to) throws RuntimeException {
		if (from.getX() < to.getX()) {
			return Right;
		} else if (from.getX() > to.getX()) {
			return Left;
		}
		if (from.getY() < to.getY()) {
			return Up;
		} else if (from.getY() > to.getY()) {
			return Down;
		}
		throw new RuntimeException("Impossible de calculer la direction");
	}

	/**
	 * Translate a path to a sequence (List) of Directions.
	 *
	 * @param ps a path as a List of points
	 * @return a List of Directions
	 */
	public static List<Direction> getDirections(List<PointAffichage> ps) {
		final List<Direction> output = new ArrayList<>();
		if (ps.size() <= 1) {
			return null;
		}
		output.add(Begin);
		for (int i = 0; i < ps.size() - 1; i++) {
			output.add(getDirection(ps.get(i), ps.get(i + 1)));
		}
		output.add(End);
		return output;

	}
}
