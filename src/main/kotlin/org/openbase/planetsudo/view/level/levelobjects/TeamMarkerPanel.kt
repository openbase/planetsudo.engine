/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.view.level.levelobjects

import org.openbase.planetsudo.level.levelobjects.Resource
import org.openbase.planetsudo.level.levelobjects.TeamMarker
import org.openbase.planetsudo.view.level.LevelPanel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class TeamMarkerPanel(resource: TeamMarker, parentResourcePanel: LevelPanel) :
    AbstractLevelObjectPanel<TeamMarker, LevelPanel>(
        resource,
        resource.polygon,
        null,
        parentResourcePanel,
        DrawLayer.BACKGROUND
    ),
    PropertyChangeListener {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val gg2: Graphics2D? = null
    private var animationCounter = 0

    init {
        logger.info("Create $this")
        resource.addPropertyChangeListener(this)
    }

    // 	private static final stroke = new Stroke.
    override fun paintComponent(g2: Graphics2D, gl: Graphics2D) {
        if (resource.isPlaced) {
            boundingBox = resource.bounds
            g2.stroke = BasicStroke(5f)
            when (animationCounter) {
                0 -> {
                    g2.color = resource.team.teamColor
                    g2.fillOval(boundingBox.centerX.toInt() - 4, boundingBox.centerY.toInt() - 4, 8, 8)
                    g2.color = Color.WHITE
                }

                1 -> {
                    g2.color = resource.team.teamColor
                    g2.drawOval(boundingBox.centerX.toInt() - 8, boundingBox.centerY.toInt() - 8, 16, 16)
                    g2.color = Color.WHITE
                    g2.fillOval(boundingBox.centerX.toInt() - 4, boundingBox.centerY.toInt() - 4, 8, 8)
                }

                2 -> {
                    g2.color = resource.team.teamColor
                    g2.drawOval(boundingBox.centerX.toInt() - 16, boundingBox.centerY.toInt() - 16, 32, 32)
                    g2.fillOval(boundingBox.centerX.toInt() - 4, boundingBox.centerY.toInt() - 4, 8, 8)
                    g2.color = Color.WHITE
                    g2.drawOval(boundingBox.centerX.toInt() - 8, boundingBox.centerY.toInt() - 8, 16, 16)
                }
            }
            // g2.drawOval((int) boundingBox.x, (int) boundingBox.y, (int) boundingBox.getWidth(), (int) boundingBox.height);
            animationCounter++
            if (animationCounter > 2) {
                animationCounter = 0
            }
        }
    }

    override fun propertyChange(evt: PropertyChangeEvent) {
        if (evt.propertyName == Resource.KILL_EVENT) {
            parentResourcePanel.removeChild(this)
        }
    }

    override val isFocusable: Boolean
        get() = false

    override fun notifyMouseEntered() {
    }
}
