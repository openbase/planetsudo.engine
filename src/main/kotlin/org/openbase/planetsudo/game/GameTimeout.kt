package org.openbase.planetsudo.game

import org.openbase.planetsudo.level.AbstractLevel
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.Timer

// this still de-syncs slightly from the timer in LevelMenuPanel
class GameTimeout(level: AbstractLevel, durationInMs: Long, val function: () -> Unit) : ActionListener {
    private val timer: Timer = Timer(calcTimerDelay(level.getGameSpeedFactor()), this)
    private var secondsRemaining = durationInMs / 1000
    private var fired: Boolean = false

    init { //This is a blatant copy of LevelMenuPanel, thus knowingly violating single source of truth
        level.addPropertyChangeListener {
            if (it.propertyName == AbstractLevel.GAME_SPEED_FACTOR_CHANGED) {
                timer.isRunning.let { running ->
                    updateDelay(it.newValue as Double)
                    if (running) {
                        timer.restart()
                    }
                }
            }
        }
    }

    private fun updateDelay(gameSpeed: Double) {
        val delay = calcTimerDelay(gameSpeed)
        timer.delay = delay
        timer.initialDelay = delay
    }

    private fun calcTimerDelay(gameSpeed: Double): Int {
        return (1000.0 / gameSpeed).toInt()
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
