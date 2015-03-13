/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.main.command;

import de.citec.jps.preset.AbstractJPInteger;


/**
 *
 * @author Divine Threepwood
 */
public class SetServerPort extends AbstractJPInteger {

	public final static String[] COMMAND_IDENTIFIERS = {"--port"};

	public SetServerPort() {
		super(COMMAND_IDENTIFIERS);
	}
	
	@Override
	protected Integer getPropertyDefaultValue() {
		return 8253;
	}

	@Override
	public String getDescription() {
		return "Setup the default server port.";
	}
}
