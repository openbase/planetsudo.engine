/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.jp;

import org.openbase.jps.preset.AbstractJPString;


/**
 *
 * @author Divine Threepwood
 */
public class JPServerHostname extends AbstractJPString {

	public final static String[] COMMAND_IDENTIFIERS = {"--server"};
	public final static String[] ARGUMENT_IDENTIFIER = {"HOSTNAME"};

	public JPServerHostname() {
		super(COMMAND_IDENTIFIERS);
	}
    
    @Override
    protected String[] generateArgumentIdentifiers() {
        return ARGUMENT_IDENTIFIER;
    }
	
	@Override
	protected String getPropertyDefaultValue() {
		return "localhost";
	}

	@Override
	public String getDescription() {
		return "Setup the default server.";
	}
}
