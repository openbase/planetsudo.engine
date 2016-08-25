/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.jp;

import org.openbase.jps.preset.AbstractJPInteger;


/**
 *
 * @author Divine Threepwood
 */
public class JPServerPort extends AbstractJPInteger {

	public final static String[] COMMAND_IDENTIFIERS = {"--port"};

	public JPServerPort() {
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
