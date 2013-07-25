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
public class SetTeamPathCommand extends AbstractCLDirectory {

	public final static String[] COMMAND_IDENTIFIERS = {"-t", "--teamfolder"};
	public final static String[] ARGUMENT_IDENTIFIERS = {"LevelPath"};

	public SetTeamPathCommand() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS, FileHandler.ExistenceHandling.CanExist, FileHandler.AutoMode.On);
	}

	@Override
	public String getDescription() {
		return "Set the TeamPath.";
	}

	@Override
	protected File getCommandDefaultValue() {
		return new File("/home/divine/workspace/PlanetSudo/teams");
	}
}
