/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.jp;

import org.openbase.jps.core.JPService;
import org.openbase.jps.preset.AbstractJPDirectory;
import org.openbase.jps.preset.JPPrefix;
import org.openbase.jps.tools.FileHandler;
import java.io.File;
import org.openbase.jps.exception.JPNotAvailableException;

/**
 *
 * @author divine
 */
public class JPStrategyPath extends AbstractJPDirectory {
	public final static String[] COMMAND_IDENTIFIERS = {"-s", "--strategy"};

	public JPStrategyPath() {
		super(COMMAND_IDENTIFIERS, FileHandler.ExistenceHandling.CanExist, FileHandler.AutoMode.On);
	}

	@Override
	public String getDescription() {
		return "Set the StrategyPath.";
	}

	@Override
	protected File getPropertyDefaultValue() throws JPNotAvailableException {
		return new File(JPService.getProperty(JPPrefix.class).getValue().getAbsolutePath()+"/target/classes/de/dc/planetsudo/game/strategy");
	}
}
