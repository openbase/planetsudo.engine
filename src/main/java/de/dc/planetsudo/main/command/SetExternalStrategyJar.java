/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.main.command;

import de.unibi.agai.clparser.CLParser;
import de.unibi.agai.clparser.command.AbstractCLFile;
import de.unibi.agai.clparser.command.SetPrefix;
import de.unibi.agai.tools.FileHandler;
import java.io.File;


/**
 *
 * @author divine
 */
public class SetExternalStrategyJar extends AbstractCLFile {
	public final static String[] COMMAND_IDENTIFIERS = {"--buildTarget"};
	public final static String[] ARGUMENT_IDENTIFIERS = {"PATH"};

	public SetExternalStrategyJar() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS, FileHandler.ExistenceHandling.CanExist, FileHandler.AutoMode.Off);
	}

	@Override
	public String getDescription() {
		return "Set the external strategy jar.";
	}

	@Override
	protected File getCommandDefaultValue() {
		return new File(CLParser.getAttribute(SetPrefix.class).getValue().getAbsolutePath()+"/ext/Strategy.jar");
	}
}
