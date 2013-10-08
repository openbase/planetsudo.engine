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

	/*
	 Minenexplosion
	 * - Agent explodier
	 * - Muttaschiff explodiert
	 * - Laser
	 * - Mine Legen
	 * - Marker setzen
	 * - Hilferuf
	 * - (tanken)
	 * - Unterstützen
	 * Resource abliefern	 */
	DeployMine("sound/sound.wav"),
	DeployMarker("sound/sound.wav"),
	RechargeFuel("sound/sound.wav"),
	CallForSupport("sound/sound.wav"),
	SpendFuel("sound/sound.wav"),
	AgentExplosion("sound/sound.wav"),
	MothershipExplosion("sound/sound.wav"),
	DeliverResource("sound/sound.wav"),
	Laser("sound/sound.wav");

	private final File soundFile;

	public File getFile() {
		return soundFile;
	}

	private GameSound(String uri) {
		this.soundFile = new File(ClassLoader.getSystemResource(uri).getFile());
	}
}

