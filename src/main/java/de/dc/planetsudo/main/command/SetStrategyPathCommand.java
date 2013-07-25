/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.main.command;

import de.unibi.agai.clparser.command.AbstractSetDirectory;
import de.unibi.agai.tools.FileHandler;
import java.io.File;

/**
 *
 * @author divine
 */
public class SetStrategyPathCommand extends AbstractSetDirectory {
	public final static String[] COMMAND_IDENTIFIERS = {"-s", "--strategy"};
	public final static String[] ARGUMENT_IDENTIFIERS = {"LevelPath"};
	public final static File[] DEFAULT_VALUES = {new File("/home/divine/workspace/PlanetSudo/target/classes/de/dc/planetsudo/game/strategy")};

	public SetStrategyPathCommand() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS, DEFAULT_VALUES, FileHandler.ExistenceHandling.CanExist, FileHandler.AutoMode.On);
	}

	@Override
	public String getDescription() {
		return "Set the StrategyPath.";
	}
}
