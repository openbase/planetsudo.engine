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
public class SetStrategySourceDirectory extends AbstractJPDirectory {
	public final static String[] COMMAND_IDENTIFIERS = {"--stategySource"};
	public final static String[] ARGUMENT_IDENTIFIERS = {"PATH"};

	public SetStrategySourceDirectory() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS, FileHandler.ExistenceHandling.Must, FileHandler.AutoMode.Off);
	}

	@Override
	public String getDescription() {
		return "Set the strategy source directory.";
	}

	@Override
	protected File getPropertyDefaultValue() {
		return new File(JPService.getProperty(JPPrefix.class).getValue().getAbsolutePath()+"/src/main/java/de/dc/planetsudo/game/strategy");
	}
}
