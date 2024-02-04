/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.view.level.levelobjects

import org.openbase.planetsudo.game.GameObjectImages
import org.openbase.planetsudo.geometry.Direction2D
import org.openbase.planetsudo.level.levelobjects.*
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType
import org.openbase.planetsudo.view.level.LevelPanel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Color
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class LevelResourcePanel(resource: Resource, parentResourcePanel: LevelPanel) :
    AbstractLevelObjectPanel<Resource, LevelPanel>(
        resource,
        resource.polygon,
        getImageURI(resource.type),
        parentResourcePanel,
        DrawLayer.BACKGROUND,
    ),
    PropertyChangeListener {
    private val mine: Boolean
    private var mineColor: Color? = null

    private var owner: Agent? = null
    private var gg2: Graphics2D? = null
    private var direction: Direction2D? = null

    init {
        logger.info("Create $this")
        resource.addPropertyChangeListener(this)
        this.mine = resource.type == ResourceType.Mine
        if (mine && resource.wasPlacedByTeam() != null) {
            this.mineColor = resource.wasPlacedByTeam()!!.teamColor
        } else {
            this.mineColor = Color.BLACK
        }
    }

    override fun paintComponent(g2: Graphics2D, gl: Graphics2D) {
        boundingBox = resource.bounds
        owner = resource.owner

        if (owner == null) {
            if (mine) {
                if (AbstractLevelObjectPanel.Companion.animationFlag) {
                    g2.color = mineColor
                } else {
                    g2.color = Color.BLACK
                }
                g2.fillRect(boundingBox.centerX.toInt() - 5, boundingBox.centerY.toInt() - 5, 10, 10)
            }
            paintImage(g2)
        } else {
            direction = owner!!.direction
            gg2 = g2.create() as Graphics2D
            gg2!!.translate(-direction!!.vector.x * owner!!.width * 0.35, -direction!!.vector.y * owner!!.height * 0.35)
            paintImageRotated(direction!!, gg2!!)
            gg2!!.dispose()
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

    override fun notifyMouseClicked(evt: MouseEvent) {
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(LevelResourcePanel::class.java)

        private fun getImageURI(type: ResourceType): String {
            return GameObjectImages.valueOf("Resource" + type.name).imagesURL
        }
    }
}
