/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2024 openbase.org
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

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.TreeMap;

import org.openbase.jul.audio.AudioDataImpl;
import org.openbase.jul.audio.AudioPlayer;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.printer.ExceptionPrinter;

/**
 *
 * @author Divine Threepwood
 */
public enum GameSound {
	DeployMine("sound/bombpl.wav"),
	DeployMarker("sound/set_marker.wav"),
	RechargeFuel("sound/the-notification-email-143029.wav"),
	CallForSupport("sound/need_backup.wav"),
	SpendFuel("sound/spend_fuel.wav"),
	AgentExplosion("sound/c4_explode1.wav"),
	MothershipExplosion("sound/nuclear-explosion-63470.wav"),
	MothershipUnderAttack("sound/sirene.wav"),
	EarnNormalResource("sound/button-124476.wav"),
	EarnDoubleResource("sound/deliver_resource.wav"),
	EarnExtremResource("sound/interface-124464.wav"),
	EarnAgentFuel("sound/cartoon-jump-6462.wav.wav"),
	EarnMothershipFuel("sound/sci-fi-charge-up-37395.wav"),
	ErectTower("sound/sci-fi-charge-up-37395.wav"),
	Laser("sound/lasershot.wav"),

	End("sound/end.wav"),
	EndSoon("sound/end_soon.wav"),
	AgentDisabled("sound/agent_empty_fuel.wav");

	private final AudioDataImpl audioData;
	private final boolean disabled;

	private static final AudioPlayer AUDIO_SERVER = new AudioPlayer(20);

	public static final TreeMap<GameSound, Instant> soundHistory = new TreeMap<>();

	static {
		for (GameSound value : GameSound.values()) {
			soundHistory.put(value, Instant.MIN);
		}
	}

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

	public synchronized void play() {
		if(disabled) {
			return;
		}

		Instant now = Instant.now();

		// low pass filter
		if(Duration.between(soundHistory.get(this), now).minus(Duration.ofMillis(250)).isNegative()) {
			return;
		}

		AUDIO_SERVER.playAudio(audioData);
		soundHistory.put(this, Instant.now());
	}
}
