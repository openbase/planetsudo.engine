/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.main;

import planetmesserlost.game.GameManager;
import logging.Logger;
import planetmesserlost.main.Main;

/**
 *
 * @author divine
 */
public class Main {

	public final static boolean DEBUG = false;

	private final GameManager gameManager;
	private final GUIController guiController;

	public Main() {
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
