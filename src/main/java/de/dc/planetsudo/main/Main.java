/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.main;

import de.dc.util.logging.Logger;
import de.unibi.agai.clparser.CLParser;
import de.unibi.agai.clparser.command.DebugModeFlag;
import de.dc.planetsudo.game.GameManager;
import de.dc.planetsudo.main.command.SetLevelPathCommand;
import de.dc.planetsudo.main.command.SetStrategyPathCommand;
import de.dc.planetsudo.main.command.SetTeamPathCommand;
import de.unibi.agai.clparser.command.SetPrefix;


/**
 *
 * @author divine
 */
public class Main {

	public final static boolean DEBUG = false;
	private final GUIController guiController;

	public Main() {
		Logger.info(this, "Install strategy...");
		String command = "InstallStrategy.bat";
		try {
			Process child = Runtime.getRuntime().exec(command);
		} catch (Exception ex) {
			Logger.warn(this, "Could not sync strategy!");
		}
		Logger.info(this, "Starting Game...");
		new GameManager();
		this.guiController = new GUIController();
		this.guiController.startGUI();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		CLParser.setProgramName("PlanetSudo");
		CLParser.registerCommand(SetPrefix.class);
		CLParser.registerCommand(SetTeamPathCommand.class);
		CLParser.registerCommand(SetStrategyPathCommand.class);
		CLParser.registerCommand(DebugModeFlag.class);
		CLParser.registerCommand(SetLevelPathCommand.class);

		Logger.setDebugMode(DEBUG);
		CLParser.analyseAndExitOnError(args);
		
		new Main();
	}

}