/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.level.levelobjects

import org.openbase.jul.exception.CouldNotPerformException
import org.openbase.jul.visual.swing.engine.draw2d.AbstractResourcePanel
import org.openbase.planetsudo.game.GameSound
import org.openbase.planetsudo.game.Team
import org.openbase.planetsudo.geometry.Point2D
import org.openbase.planetsudo.level.AbstractLevel

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class TeamMarker(@JvmField val team: Team, level: AbstractLevel) : AbstractLevelObject(
    0,
    team.name + "Marker",
    AbstractResourcePanel.ObjectType.Static,
    level,
    Point2D(),
    MARKER_SIZE,
    MARKER_SIZE,
    ObjectShape.Oval
) {
    private val MARKER_LOCK = Any()
    var isPlaced: Boolean = false
        private set

    fun place(position: Point2D) {
        synchronized(MARKER_LOCK) {
            if (isPlaced && this.position!!.equals(position)) {
                return
            }
            this.position = position
            levelView!!.updateObjectMovement()
            isPlaced = true
        }
        GameSound.DeployMarker.play()
    }

    fun clear() {
        synchronized(MARKER_LOCK) {
            isPlaced = false
        }
    }

    @get:Throws(CouldNotPerformException::class)
    val marker: TeamMarker
        get() {
            synchronized(MARKER_LOCK) {
                if (!isPlaced) {
                    throw CouldNotPerformException("Marker not placed!")
                }
                return this
            }
        }

    override fun reset() {
        clear()
    }

    fun seeMarker(agent: Agent): Boolean {
        if (!isPlaced) {
            return false
        }

        return bounds.intersects(agent.bounds)
    }

    companion object {
        const val MARKER_SIZE: Double = 50.0
    }
}
