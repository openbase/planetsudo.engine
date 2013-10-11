/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.game;

import de.dc.util.exceptions.CouldNotPerformException;
import de.dc.util.logging.Logger;
import de.dc.util.sound.AudioData;
import de.dc.util.sound.AudioServer;
import java.io.File;

/**
 *
 * @author Divine <DivineThreepwood@gmail.com>
 */
public enum GameSound {

	/*
	 * - Agent explodier
	 * - Muttaschiff explodiert
	 * - Laser
	 * - Mine Legen
	 * - Marker setzen
	 * - Hilferuf
	 * - (tanken)
	 * - Unterstützen
	 * Resource abliefern	 */
	DeployMine("sound/deplay_mine.wav"),
	DeployMarker("sound/set_marker.wav"),
	RechargeFuel("sound/recharge_fuel.wav"),
	CallForSupport("sound/lasershot.wav"),
	SpendFuel("sound/spend_fuel.wav"),
	AgentExplosion("sound/explosion_agent.wav"),
	MothershipExplosion("sound/explosion_mothership.wav"),
	MothershipUnderAttack("sound/attack_mothership.wav"),
	DeliverResource("sound/deliver_resource.wav"),
	Laser("sound/lasershot.wav"),
	AgentDisabled("sound/agent_empty_fuel.wav");

	private final AudioData audioData;
	private final boolean disabled;

	private static final AudioServer AUDIO_SERVER = new AudioServer(20);

	private GameSound(final String uri) {
		AudioData tmpData = null;
		try {
			tmpData = new AudioData(new File(ClassLoader.getSystemResource(uri).getFile()));
		} catch (Exception ex) {
			Logger.warn(this, "Could not load Soundfile["+uri+"] of "+name() ,ex);
		}
		this.audioData = tmpData;
		this.disabled = (audioData == null);
	}



	public void play() {
		if(disabled) {
			return;
		}
		AUDIO_SERVER.playAudio(audioData);
	}
}
