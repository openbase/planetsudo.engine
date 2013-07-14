/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.main.clc;

import configuration.parameter.AbstractRunCommand;

/**
 *
 * @author divine
 */
public class SetStrategyPathCommand extends AbstractRunCommand<String, Void> {
	public final static String[] COMMAND_IDENTIFIERS = {"-s", "--strategy"};
	public final static String[] ARGUMENT_IDENTIFIERS = {"LevelPath"};
	public final static String[] DEFAULT_VALUES = {"build/classes/planetsudo/game/strategy/"};

	public SetStrategyPathCommand() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS, DEFAULT_VALUES, null);
	}

	@Override
	protected void action() throws Exception {
	}

	@Override
	protected String parse(String arg) throws Exception {
		return arg;
	}

	@Override
	public String getDiscription() {
		return "Set the StrategyPath.";
	}
}
