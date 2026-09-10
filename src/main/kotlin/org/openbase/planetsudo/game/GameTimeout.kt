package org.openbase.planetsudo.game

import org.openbase.jul.schedule.Timeout

// TODO this is currently not aware of game speed factor
class GameTimeout(duration: Long, val function: () -> Unit) : Timeout(duration) {

    private var pausedAt: Long? = null

    override fun expired() {
        function()
    }

    fun pause() {
        pausedAt = timeLeftUntilTimeout
        cancel()
    }

    fun tryUnpause() {
        if (isActive || isExpired) {
            return
        }
        pausedAt?.let { restart(it) }
    }
}
