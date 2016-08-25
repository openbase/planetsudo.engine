/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game;

import org.slf4j.Logger;
import java.io.File;
import org.openbase.jul.audio.AudioData;
import org.openbase.jul.audio.AudioPlayer;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Divine Threepwood
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
	DeployMine("sound/bombpl.wav"),
	DeployMarker("sound/set_marker.wav"),
	RechargeFuel("sound/recharge_fuel.wav"),
	CallForSupport("sound/1_need_backup.wav"),
	SpendFuel("sound/spend_fuel.wav"),
	AgentExplosion("sound/c4_explode1.wav"),
	MothershipExplosion("sound/explosion_mothership.wav"),
	MothershipUnderAttack("sound/attack_mothership.wav"),
	DeliverResource("sound/deliver_resource.wav"),
	Laser("sound/lasershot.wav"),
	End("sound/end.wav"),
	EndSoon("sound/end_soon.wav"),
	AgentDisabled("sound/agent_empty_fuel.wav");
    

	private final AudioData audioData;
	private final boolean disabled;

	private static final AudioPlayer AUDIO_SERVER = new AudioPlayer(20);

	private GameSound(final String uri) {
		AudioData tmpData = null;
		try {
			tmpData = new AudioData(new File(ClassLoader.getSystemResource(uri).getFile()));
		} catch (Exception ex) {
			ExceptionPrinter.printHistory(new CouldNotPerformException("Could not load Soundfile["+uri+"] of "+name() ,ex), System.err);
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
