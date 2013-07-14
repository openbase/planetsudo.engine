/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package planetsudo.main;

import configuration.parameter.CommandParameterParser;
import configuration.parameter.PrintHelpCommand;
import configuration.parameter.SetDebugModeCommand;
import de.dc.util.logging.Logger;
import planetsudo.game.GameManager;
import planetsudo.main.clc.SetLevelPathCommand;
import planetsudo.main.clc.SetStrategyPathCommand;
import planetsudo.main.clc.SetTeamPathCommand;


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
		CommandParameterParser.setProgramName("PlanetSudo");
		CommandParameterParser.addCommand(new PrintHelpCommand());
		CommandParameterParser.addCommand(new SetTeamPathCommand());
		CommandParameterParser.addCommand(new SetStrategyPathCommand());
		CommandParameterParser.addCommand(new SetDebugModeCommand());
		CommandParameterParser.addCommand(new SetLevelPathCommand());

		if (!CommandParameterParser.analyse(args)) {
			CommandParameterParser.printHelp();
			System.out.println("Exit...");
			System.exit(255);
		}

		new Logger(DEBUG);
		Logger.setPrintExceptionStackTrace(true);
		Logger.setDisplayExceptionDialog(false);
		new Main();
	}

}