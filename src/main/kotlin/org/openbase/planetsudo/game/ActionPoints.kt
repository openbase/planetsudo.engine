/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game

import org.openbase.planetsudo.level.levelobjects.Agent
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class ActionPoints(private val agent: Agent) {

    private val lock = ReentrantLock()
    private val lockCondition: Condition = lock.newCondition()
    var actionPoints: Int = 0
        private set

    fun addActionPoint() {
        if (agent.isDisabled && !agent.isAtMothership) {
            return
        }
        lock.withLock {
            actionPoints++
            lockCondition.signalAll()
        }
    }

    val actionPoint: Unit
        get() {
            getActionPoint(1)
        }

    fun getActionPoint(orderedPoints: Int) {
        while (!Thread.currentThread().isInterrupted) {
            lock.withLock {
                if (actionPoints >= orderedPoints) {
                    actionPoints -= orderedPoints
                    return
                }
                lockCondition.await()
            }
        }
    }
}
