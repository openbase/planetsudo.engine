/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.main.command;

import de.unibi.agai.clparser.command.AbstractCLInteger;
import org.apache.log4j.Logger;

/**
 *
 * @author Divine <DivineThreepwood@gmail.com>
 */
public class SetServerPort extends AbstractCLInteger {

	private final Logger LOGGER = Logger.getLogger(getClass());

	public final static String[] COMMAND_IDENTIFIERS = {"--port"};
	public final static String[] ARGUMENT_IDENTIFIERS = {"HOSTPORT"};

	public SetServerPort() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS);
	}
	
	@Override
	protected Integer getCommandDefaultValue() {
		return 8253;
	}

	@Override
	public String getDescription() {
		return "Setup the default server port.";
	}
}
