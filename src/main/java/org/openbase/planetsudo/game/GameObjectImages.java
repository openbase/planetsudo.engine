/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game;

/**
 *
 * @author divine
 */
public enum GameObjectImages {

	Default("mothership.png"),
	Mothership("mothership2.png"),
	Agent("agent-HD.png"),
	AgentCommander("commander-HD.png"),
	ResourceNormal("resource1.png"),
	ResourceDoublePoints("resource3.png"),
	ResourceExtremPoint("resource6.png"),
	ResourceExtraAgentFuel("resource5.png"),
	ResourceExtraMothershipFuel("resource7.png"),
	ResourceMine("mine.png");

	public static final String IMAGE_DIRECTORY = "img";
	public final String imagesURL;

	private GameObjectImages(final String imagesURL) {
		this.imagesURL = IMAGE_DIRECTORY + "/" + imagesURL;
	}
}
