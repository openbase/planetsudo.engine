/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package planetsudo.main;

import planetsudo.game.GameManager;
import logging.Logger;

/**
 *
 * @author divine
 */
public class Main {

	public final static boolean DEBUG = false;
	private final GameManager gameManager;
	private final GUIController guiController;

	public Main() {
		Logger.info(this, "Starting Game...");
		this.gameManager = new GameManager();
		this.guiController = new GUIController();
		this.guiController.startGUI();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		new Logger(DEBUG);
		new Main();
	}

}