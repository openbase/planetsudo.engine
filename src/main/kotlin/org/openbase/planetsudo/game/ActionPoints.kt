/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game

import org.openbase.planetsudo.level.levelobjects.Agent
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class ActionPoints(private val agent: Agent) {
    var actionPoints: Int = 0
        private set

    fun addActionPoint() {
        if (agent.isDisabled && !agent.isAtMothership) {
            return
        }
        synchronized(this) {
            actionPoints++
            (this as Object).notify()
        }
    }

    val actionPoint: Unit
        get() {
            getActionPoint(1)
        }

    fun getActionPoint(orderedPoints: Int) {
        while (true) {
            synchronized(this) {
                if (actionPoints >= orderedPoints) {
                    actionPoints -= orderedPoints
                    return
                }
                try {
                    (this as Object).wait()
                } catch (ex: InterruptedException) {
                    logger.warn("", ex)
                }
            }
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ActionPoints::class.java)
    }
}
