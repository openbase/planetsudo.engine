/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.main;

import de.citec.jps.core.JPService;
import de.citec.jps.preset.JPDebugMode;
import de.citec.jps.preset.JPPrefix;
import de.dc.util.logging.Logger;
import de.dc.planetsudo.game.GameManager;
import de.dc.planetsudo.main.command.SetExternalStrategyJar;
import de.dc.planetsudo.main.command.SetLevelPathCommand;
import de.dc.planetsudo.main.command.SetServer;
import de.dc.planetsudo.main.command.SetServerPort;
import de.dc.planetsudo.main.command.SetStrategyPathCommand;
import de.dc.planetsudo.main.command.SetStrategySourceDirectory;
import de.dc.planetsudo.main.command.SetTeamPathCommand;


/**
 *
 * @author divine
 */
public class Main {

	public final static boolean DEBUG = false;
	private final GUIController guiController;

	public Main() {
		Logger.info(this, "Install strategy...");
//		String command = "InstallStrategy.bat";
//		try {
//			Process child = Runtime.getRuntime().exec(command);
//		} catch (Exception ex) {
//			Logger.warn(this, "Could not sync strategy!");
//		}
		Logger.info(this, "Starting Game...");
		new GameManager();
		this.guiController = new GUIController();
		this.guiController.startGUI();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		JPService.setApplicationName("PlanetSudo");
		JPService.registerProperty(JPPrefix.class);
		JPService.registerProperty(SetTeamPathCommand.class);
		JPService.registerProperty(SetStrategyPathCommand.class);
		JPService.registerProperty(JPDebugMode.class);
		JPService.registerProperty(SetLevelPathCommand.class);
		JPService.registerProperty(SetServer.class);
		JPService.registerProperty(SetServerPort.class);
		JPService.registerProperty(SetStrategySourceDirectory.class);
		JPService.registerProperty(SetExternalStrategyJar.class);

		Logger.setDebugMode(DEBUG);
		JPService.parseAndExitOnError(args);
		
		new Main();
	}

}