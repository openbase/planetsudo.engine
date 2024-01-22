/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.view.level

import org.openbase.jul.exception.printer.ExceptionPrinter
import org.openbase.planetsudo.game.GameObjectImages
import org.openbase.planetsudo.level.AbstractLevel
import org.openbase.planetsudo.level.levelobjects.Resource
import org.openbase.planetsudo.view.game.AbstractGameObjectPanel
import org.openbase.planetsudo.view.level.levelobjects.LevelResourcePanel
import org.openbase.planetsudo.view.level.levelobjects.MothershipPanel
import org.openbase.planetsudo.view.level.levelobjects.TowerPanel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

/**
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class LevelPanel(resource: AbstractLevel, parentPanel: LevelDisplayPanel) :
    AbstractGameObjectPanel<AbstractLevel, LevelPanel>(
        resource,
        resource.levelBorderPolygon,
        ObjectType.Dynamic,
        GameObjectImages.Default.imagesURL,
        parentPanel
    ),
    PropertyChangeListener {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val hasInternalWalls = resource.levelWallPolygons != null
    private var enabledLevelObjects: Boolean

    init {
        //        boundingBox = resource.levelBorderPolygon.getBounds2D();
        updateBounds()
        parentPanel.isDoubleBuffered = true
        resource.addPropertyChangeListener(this)
        enabledLevelObjects = false
    }

    fun loadLevelObjects() {
        try {
            enabledLevelObjects = true
            loadResourcePanels()
            loadTowerPanels()
            loadMothershipPanels()
            updateBounds()
        } catch (ex: Exception) {
            ExceptionPrinter.printHistory(ex, logger)
        }
    }

    private fun loadResourcePanels() {
        for (res in resource!!.getResources()) {
            LevelResourcePanel(res, this)
        }
    }

    private fun loadMothershipPanels() {
        for (mothership in resource!!.motherships) {
            MothershipPanel(mothership!!, this)
        }
    }

    private fun loadTowerPanels() {
        for (mothership in resource!!.motherships) {
            TowerPanel(mothership!!.tower, this)
        }
    }

    override fun paintComponent(g2: Graphics2D?, gl: Graphics2D?) {
        g2!!.fill(resource!!.levelBorderPolygon)
        // 		g2.fill(tranformedPlacement);
        if (hasInternalWalls) {
            g2.color = resource!!.color
            for (wall in resource!!.levelWallPolygons!!) {
                g2.fill(wall)
            }
        }
    }

    override fun propertyChange(evt: PropertyChangeEvent) {
        if (evt.propertyName == AbstractLevel.CREATE_RESOURCE && enabledLevelObjects) {
            LevelResourcePanel((evt.newValue as Resource), this)
        }
    }

    override val isFocusable: Boolean
        get() = false

    override fun notifyMouseEntered() {
    }

    override fun notifyMouseClicked(evt: MouseEvent) {
    }
}
