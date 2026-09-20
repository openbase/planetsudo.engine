package org.openbase.planetsudo.game

import org.openbase.planetsudo.level.AbstractLevel
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.Timer

// TODO this currently de-syncs slightly from the timer in LevelMenuPanel
class GameTimeout(level: AbstractLevel, duration: Long, val function: () -> Unit) : ActionListener {
    private val timer: Timer = Timer((1000.0 * (1 / level.getGameSpeedFactor())).toInt(), this)
    private var secondsRemaining = duration / 1000
    private var fired: Boolean = false

    init { //This is a blatant copy of LevelMenuPanel, thus knowingly violating single source of truth
        level.addPropertyChangeListener {
            if (it.propertyName == AbstractLevel.GAME_SPEED_FACTOR_CHANGED) {
                timer.isRunning.let { running ->
                    timer.delay = (1000.0 * (1 / (it.newValue as Double))).toInt()
                    timer.initialDelay = timer.delay
                    if (running) {
                        timer.restart()
                    }
                }
            }
        }
    }

    fun startTimer() {
        if (!timer.isRunning && !fired) {
            timer.start()
        }
    }

    fun stopTimer() {
        if (timer.isRunning) {
            timer.stop()
        }
    }

    override fun actionPerformed(ex: ActionEvent) {
        secondsRemaining--
        if (secondsRemaining <= 0 && !fired) {
            function()
            fired = true
            stopTimer()
        }
    }
}
