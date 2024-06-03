package main;

import librairies.SelecteurDeFichier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ressources.Chemins;

import java.io.IOException;
import java.io.InterruptedIOException;


public class Main {
	public static void main(String[] args) throws IOException {
		//	Define verbosity
		System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");

		final Logger logger = LoggerFactory.getLogger(Main.class);
		try {
			String cheminCarte = selectionCarte();
			Jeu jeu = new Jeu(cheminCarte);
			jeu.initialDisplay();
			while (!jeu.isOver()) {
				jeu.update();
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

	private static String selectionCarte() throws IOException {
		try {
			SelecteurDeFichier selecteur = new SelecteurDeFichier(Chemins.getDossierCartes());
			return selecteur.selectFile();
		} catch (InterruptedIOException excep) {
			throw new IOException("Selection annulée, jeu non lancé.");
		}
	}
}
