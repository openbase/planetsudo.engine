/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.main.command;

import de.citec.jps.core.JPService;
import de.citec.jps.preset.AbstractJPDirectory;
import de.citec.jps.preset.JPPrefix;
import de.citec.jps.tools.FileHandler;
import java.io.File;

/**
 *
 * @author divine
 */
public class SetStrategyPathCommand extends AbstractJPDirectory {
	public final static String[] COMMAND_IDENTIFIERS = {"-s", "--strategy"};

	public SetStrategyPathCommand() {
		super(COMMAND_IDENTIFIERS, FileHandler.ExistenceHandling.CanExist, FileHandler.AutoMode.On);
	}

	@Override
	public String getDescription() {
		return "Set the StrategyPath.";
	}

	@Override
	protected File getPropertyDefaultValue() {
		return new File(JPService.getProperty(JPPrefix.class).getValue().getAbsolutePath()+"/target/classes/de/dc/planetsudo/game/strategy");
	}
}
