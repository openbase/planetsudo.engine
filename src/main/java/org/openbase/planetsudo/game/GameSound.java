/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2019 openbase.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import org.slf4j.Logger;
import java.io.File;
import org.openbase.jul.audio.AudioDataImpl;
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
    

	private final AudioDataImpl audioData;
	private final boolean disabled;

	private static final AudioPlayer AUDIO_SERVER = new AudioPlayer(20);

	private GameSound(final String uri) {
		AudioDataImpl tmpData = null;
		try {
			tmpData = new AudioDataImpl(new File(ClassLoader.getSystemResource(uri).getFile()));
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
