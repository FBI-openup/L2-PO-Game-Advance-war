package main.point;

import main.Direction;
import ressources.Config;

/**
 * Class of coordinates showed.
 */
public class PointAffichage extends Point implements Cloneable {

	public PointAffichage(int x, int y) {
		super(x, y);
	}

	public PointAffichage(PointAffichage p) {
		super(p.x, p.y);
	}

	public static PointAffichage fromPointPlateau(PointPlateau p) {
		return new PointAffichage(p.y, Config.longueurCarteYCases - p.x - 1);
	}

	@Override
	public void move(Direction direction) {
		switch (direction) {
			case Right -> x += 1;
			case Left -> x -= 1;
			case Up -> y += 1;
			case Down -> y -= 1;
		}
	}

	public void moveTo(PointAffichage p) {
		x = p.x;
		y = p.y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof final PointAffichage that) {
			return this.x == that.x && this.y == that.y;
		} else {
			return false;
		}
	}

	@Override
	public PointAffichage clone() {
		try {
			return (PointAffichage) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
}
