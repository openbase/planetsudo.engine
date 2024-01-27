package org.openbase.planetsudo.view.level.levelobjects

import org.openbase.planetsudo.game.GameObjectImages
import org.openbase.planetsudo.geometry.Direction2D
import org.openbase.planetsudo.level.levelobjects.Tower
import org.openbase.planetsudo.level.levelobjects.Tower.TowerType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class TowerTopPanel(tower: Tower, parentPanel: TowerPanel) :
    AbstractLevelObjectPanel<Tower, TowerPanel>(
        tower,
        tower.polygon,
        getImageURI(tower.type),
        parentPanel,
        DrawLayer.BACKGROUND
    ),
    PropertyChangeListener {
    private val tower: Tower

    private var gg2: Graphics2D? = null
    private var direction: Direction2D? = null

    init {
        LOGGER.info("Create $this")
        this.tower = tower
        tower.addPropertyChangeListener(this)
    }

    override fun paintComponent(g2: Graphics2D, gl: Graphics2D) {
        // only paint if erected
        if (!resource.isErected) {
            return
        }

        boundingBox = resource.bounds
        direction = resource.direction
        gg2 = g2.create() as Graphics2D
        paintImageRotated(tower.direction, gg2!!)
        gg2!!.dispose()
    }

    override fun propertyChange(evt: PropertyChangeEvent) {
//        if (evt.getPropertyName().equals(Tower.REMOVE_TOWER)) {
//            if (((Tower) evt.getNewValue()).equals(resource)) {
//                parentResourcePanel.removeChild(this);
//            }
//        }
    }

    override val isFocusable: Boolean
        get() = false

    override fun notifyMouseEntered() {
    }

    override fun notifyMouseClicked(evt: MouseEvent) {
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(TowerTopPanel::class.java)

        private fun getImageURI(type: TowerType?): String {
            return GameObjectImages.valueOf(type!!.name + "Top").imagesURL
        }
    }
}
