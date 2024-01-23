/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game

import org.openbase.jul.audio.AudioDataImpl
import org.openbase.jul.audio.AudioPlayer
import org.openbase.jul.exception.CouldNotPerformException
import org.openbase.jul.exception.printer.ExceptionPrinter
import java.io.File
import java.time.Duration
import java.time.Instant
import java.util.*

/**
 *
 * @author Divine Threepwood
 */
enum class GameSound(uri: String) {
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
    EarnAgentFuel("sound/cartoon-jump-6462.wav"),
    EarnMothershipFuel("sound/sci-fi-charge-up-37395.wav"),
    ErectTower("sound/sci-fi-charge-up-37395.wav"),
    Laser("sound/lasershot.wav"),

    End("sound/end.wav"),
    EndSoon("sound/end_soon.wav"),
    AgentDisabled("sound/agent_empty_fuel.wav");

    private val audioData: AudioDataImpl?
    private val disabled: Boolean

    init {
        var tmpData: AudioDataImpl? = null
        try {
            tmpData = AudioDataImpl(File(ClassLoader.getSystemResource(uri).file))
        } catch (ex: Exception) {
            ExceptionPrinter.printHistory(
                CouldNotPerformException("Could not load Soundfile[$uri] of $name", ex),
                System.err
            )
        }
        this.audioData = tmpData
        this.disabled = (audioData == null)
    }

    @Synchronized
    fun play() {
        if (disabled) {
            return
        }

        val now = Instant.now()

        // low pass filter
        if (Duration.between(soundHistory[this], now).minus(Duration.ofMillis(250)).isNegative) {
            return
        }

        AUDIO_SERVER.playAudio(audioData)
        soundHistory[this] = Instant.now()
    }

    companion object {
        private val AUDIO_SERVER = AudioPlayer(20)

        val soundHistory: TreeMap<GameSound, Instant> = TreeMap()

        init {
            for (value in entries) {
                soundHistory[value] = Instant.MIN
            }
        }
    }
}
