/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.main;

import org.openbase.jps.core.JPService;
import org.openbase.jps.preset.JPDebugMode;
import org.openbase.jps.preset.JPPrefix;
import org.slf4j.Logger;
import org.openbase.planetsudo.game.GameManager;
import org.openbase.planetsudo.jp.JPExternalStrategyJar;
import org.openbase.planetsudo.jp.JPLevelPath;
import org.openbase.planetsudo.jp.JPServerHostname;
import org.openbase.planetsudo.jp.JPServerPort;
import org.openbase.planetsudo.jp.JPStrategyPath;
import org.openbase.planetsudo.jp.JPStrategySourceDirectory;
import org.openbase.planetsudo.jp.JPTeamPath;
import org.slf4j.LoggerFactory;


/**
 *
 * @author divine
 */
public class Main {

    private final Logger logger = LoggerFactory.getLogger(getClass());
	private final GUIController guiController;

	public Main() {
		logger.info("Starting Game...");
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
		JPService.registerProperty(JPTeamPath.class);
		JPService.registerProperty(JPStrategyPath.class);
		JPService.registerProperty(JPDebugMode.class);
		JPService.registerProperty(JPLevelPath.class);
		JPService.registerProperty(JPServerHostname.class);
		JPService.registerProperty(JPServerPort.class);
		JPService.registerProperty(JPStrategySourceDirectory.class);
		JPService.registerProperty(JPExternalStrategyJar.class);
		JPService.parseAndExitOnError(args);
		
		new Main();
	}

}