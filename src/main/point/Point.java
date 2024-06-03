package main.point;

import main.Direction;

/**
 * A class of 2D coordinates
 */
public abstract class Point {
	protected int x;
	protected int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "(" +
				"x=" + x +
				", y=" + y +
				')';
	}

	@Override
	public abstract boolean equals(Object obj);

	public abstract void move(Direction direction);

}

