package main;

import main.point.PointAffichage;
import org.junit.Test;

import java.util.List;

public class TestDirection {

	@Test
	public void testGetDirections1() {
		final List<PointAffichage> path = List.of(
				new PointAffichage(1, 1),
				new PointAffichage(1, 2),
				new PointAffichage(2, 2)
		);

		System.out.println(Direction.getDirections(path));
	}

	@Test
	public void testGetDirections2() {
		final List<PointAffichage> path = List.of(
				new PointAffichage(1, 1)
		);

		System.out.println(Direction.getDirections(path));
	}


	@Test
	public void testGetDirections3() {
		final List<PointAffichage> path = List.of(
				new PointAffichage(1, 2),
				new PointAffichage(2, 2)
		);

		System.out.println(Direction.getDirections(path));
	}

}
