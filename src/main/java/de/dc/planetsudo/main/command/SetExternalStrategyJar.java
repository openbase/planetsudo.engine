/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.main.command;

import de.citec.jps.core.JPService;
import de.citec.jps.preset.AbstractJPFile;
import de.citec.jps.preset.JPPrefix;
import de.citec.jps.tools.FileHandler;
import java.io.File;


/**
 *
 * @author divine
 */
public class SetExternalStrategyJar extends AbstractJPFile {
	public final static String[] COMMAND_IDENTIFIERS = {"--buildTarget"};
	public final static String[] ARGUMENT_IDENTIFIERS = {"PATH"};

	public SetExternalStrategyJar() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS, FileHandler.ExistenceHandling.CanExist, FileHandler.AutoMode.Off);
	}

	@Override
	public String getDescription() {
		return "Set the external strategy jar.";
	}

	@Override
	protected File getPropertyDefaultValue() {
		return new File(JPService.getProperty(JPPrefix.class).getValue().getAbsolutePath()+"/ext/Strategy.jar");
	}
}
