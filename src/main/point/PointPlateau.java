package main.point;

import main.Direction;
import ressources.Config;

/**
 * Class of coordinates read from file
 */
public class PointPlateau extends Point implements Cloneable {
	public PointPlateau(int x, int y) {
		super(x, y);
	}

	public static PointPlateau fromPointAffichage(PointAffichage p) {
		return new PointPlateau(Config.longueurCarteYCases - p.y - 1, p.x);
	}

	@Override
	public void move(Direction direction) {
		switch (direction) {
			case Right -> y += 1;
			case Left -> y -= 1;
			case Up -> x -= 1;
			case Down -> x += 1;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof final PointPlateau that) {
			return this.x == that.x && this.y == that.y;
		} else {
			return false;
		}
	}

	@Override
	public PointPlateau clone() {
		try {
			return (PointPlateau) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
}
