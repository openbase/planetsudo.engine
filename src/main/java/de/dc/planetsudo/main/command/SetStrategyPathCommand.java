/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.main.command;

import de.unibi.agai.clparser.command.AbstractCLDirectory;
import de.unibi.agai.tools.FileHandler;
import java.io.File;

/**
 *
 * @author divine
 */
public class SetStrategyPathCommand extends AbstractCLDirectory {
	public final static String[] COMMAND_IDENTIFIERS = {"-s", "--strategy"};
	public final static String[] ARGUMENT_IDENTIFIERS = {"LevelPath"};

	public SetStrategyPathCommand() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS, FileHandler.ExistenceHandling.CanExist, FileHandler.AutoMode.On);
	}

	@Override
	public String getDescription() {
		return "Set the StrategyPath.";
	}

	@Override
	protected File getCommandDefaultValue() {
		return new File("/home/divine/workspace/PlanetSudo/target/classes/de/dc/planetsudo/game/strategy");
	}
}
