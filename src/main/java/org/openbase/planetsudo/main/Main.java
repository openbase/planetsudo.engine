/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.main;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2023 openbase.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

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
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
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
		
//        System.setProperty("-Dsun.java2d.opengl", "true");
		new Main();
	}

}
