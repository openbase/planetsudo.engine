/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.main.command;

import de.unibi.agai.clparser.command.AbstractCLString;
import org.apache.log4j.Logger;

/**
 *
 * @author Divine <DivineThreepwood@gmail.com>
 */
public class SetServer extends AbstractCLString {

	private final Logger LOGGER = Logger.getLogger(getClass());

	public final static String[] COMMAND_IDENTIFIERS = {"--server"};
	public final static String[] ARGUMENT_IDENTIFIERS = {"HOSTNAME"};

	public SetServer() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS);
	}
	
	@Override
	protected String getCommandDefaultValue() {
		return "localhost";
	}

	@Override
	public String getDescription() {
		return "Setup the default server.";
	}
}
