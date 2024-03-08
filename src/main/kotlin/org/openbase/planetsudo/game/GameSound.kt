/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game

import org.openbase.jul.audio.AudioDataImpl
import org.openbase.jul.audio.AudioPlayer
import org.openbase.jul.exception.CouldNotPerformException
import org.openbase.jul.exception.printer.ExceptionPrinter
import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException
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
    AgentExplosion("sound/large-underwater-explosion-190270.wav"),
    MothershipExplosion("sound/nuclear-explosion-63470.wav"),
    MothershipUnderAttack("sound/sirene.wav"),
    EarnNormalResource("sound/button-124476.wav"),
    EarnDoubleResource("sound/deliver_resource.wav"),
    EarnExtremResource("sound/interface-124464.wav"),
    EarnAgentFuel("sound/cartoon-jump-6462.wav"),
    EarnMothershipFuel("sound/sci-fi-charge-up-37395.wav"),
    ConstructTower("sound/sci-fi-charge-up-37395.wav"),
    Laser("sound/lasershot.wav"),
    Shift("sound/woosh_low_long01-98755.wav"),
    End("sound/end.wav"),
    EndSoon("sound/end_soon.wav"),
    AgentDisabled("sound/agent_empty_fuel.wav"),
    ;

    private val audioData: AudioDataImpl?
    private val disabled: Boolean

    init {
        val tmpData: AudioDataImpl? = try {
            AudioDataImpl(File(ClassLoader.getSystemResource(uri).file))
        } catch (ex: IOException) {
            ExceptionPrinter.printHistory(
                CouldNotPerformException("Could not load Soundfile[$uri] of $name", ex),
                LoggerFactory.getLogger(GameSound::class.java),
            )
            null
        } catch (ex: CouldNotPerformException) {
            ExceptionPrinter.printHistory(
                CouldNotPerformException("Could not load Soundfile[$uri] of $name", ex),
                LoggerFactory.getLogger(GameSound::class.java),
            )
            null
        } catch (ex: NullPointerException) {
            ExceptionPrinter.printHistory(
                CouldNotPerformException("Could not load Soundfile[$uri] of $name", ex),
                LoggerFactory.getLogger(GameSound::class.java),
            )
            null
        }
        this.audioData = tmpData
        this.disabled = (audioData == null)
    }

    @Synchronized
    fun play() {
        if (disabled || audioData == null) {
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
