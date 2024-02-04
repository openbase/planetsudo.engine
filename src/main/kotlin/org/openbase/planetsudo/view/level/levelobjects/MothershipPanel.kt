/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.view.level.levelobjects

import org.openbase.planetsudo.game.GameObjectImages
import org.openbase.planetsudo.level.levelobjects.Mothership
import org.openbase.planetsudo.view.level.LevelPanel
import java.awt.Graphics2D

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class MothershipPanel(resource: Mothership, parentResourcePanel: LevelPanel) :
    AbstractLevelObjectPanel<Mothership, LevelPanel>(
        resource,
        resource.polygon,
        GameObjectImages.Mothership.imagesURL,
        parentResourcePanel,
        DrawLayer.BACKGROUND,
    ) {
    init {
        loadMarkerPanel()
        loadAgentPanels()
    }

    private fun loadMarkerPanel() {
        try {
            TeamMarkerPanel(resource.teamMarker, parentResourcePanel)
        } catch (ex: Throwable) {
            ex.printStackTrace()
        }
    }

    private fun loadAgentPanels() {
        for (agent in resource.getAgents()) {
            AgentPanel(agent, this)
        }
    }

    override fun paintComponent(g2: Graphics2D, gl: Graphics2D) {
        boundingBox = resource.bounds
        // paintShape(g2);
        g2.color = resource.team.teamColor
        g2.fillRect(boundingBox.centerX.toInt() - 45, boundingBox.centerY.toInt() - 45, 90, 90)
        paintImage(g2)
        if (resource.isBurning) {
            paintExplosion(g2)
        }
    }

    override val isFocusable: Boolean
        get() = false

    override fun notifyMouseEntered() {
    }
}
