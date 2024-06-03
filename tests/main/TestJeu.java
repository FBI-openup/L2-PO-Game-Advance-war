package main;

import main.point.PointAffichage;
import org.junit.Test;
import ressources.Chemins;

import java.util.List;

public class TestJeu {
	@Test
	public void testSimplify() throws Exception {
		Jeu jeu = new Jeu(Chemins.getCheminCarte("carte attaques.awdcmap"));

		final List<PointAffichage> walked = List.of(
				new PointAffichage(1, 1),
				new PointAffichage(2, 1),
				new PointAffichage(2, 2),
				new PointAffichage(1, 2)
		);

		final List<PointAffichage> simplified = jeu.simplifyPath(walked, new PointAffichage(2, 2));
		System.out.println(simplified);
	}
}
