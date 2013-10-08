/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.game;

import de.dc.util.sound.FileHolder;
import java.io.File;

/**
 *
 * @author Divine <DivineThreepwood@gmail.com>
 */
public enum GameSound implements FileHolder {

	DeployMine("sound/sound.wav"),
	AgentExplosion(""),
	MothershipExplosion(""),
	Laser("sound/sound.wav");

	private final File soundFile;

	public File getFile() {
		return soundFile;
	}

	private GameSound(String uri) {
		this.soundFile = new File(ClassLoader.getSystemResource(uri).getFile());
	}
}
