package org.openbase.planetsudo.view.level.levelobjects

import org.openbase.planetsudo.game.GameObjectImages
import org.openbase.planetsudo.geometry.Direction2D
import org.openbase.planetsudo.level.levelobjects.Tower
import org.openbase.planetsudo.view.level.LevelPanel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Color
import java.awt.Graphics2D
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class TowerPanel(tower: Tower, parentPanel: LevelPanel) :
    AbstractLevelObjectPanel<Tower, LevelPanel>(
        tower,
        tower.polygon,
        getImageURI(),
        parentPanel,
        DrawLayer.BACKGROUND,
    ),
    PropertyChangeListener {
    private var towerTopPanel: TowerTopPanel? = null
    private val towerDirection2D: Direction2D
    private val teamColor: Color?

    private var gg2: Graphics2D? = null
    private var direction: Direction2D? = null

    init {
        LOGGER.info("Create $this")
        tower.addPropertyChangeListener(this)
        this.towerDirection2D = Direction2D(0)
        this.teamColor = tower.level.getTeamColor(tower.mothership.team)
    }

    override fun paintComponent(g2: Graphics2D, gl: Graphics2D) {
        // only paint if constructed
        if (!resource.isConstructed) {
            return
        }

        boundingBox = resource.bounds
        direction = resource.direction

        gg2 = g2.create() as Graphics2D
        gg2!!.color = teamColor
        gg2!!.fillOval(
            boundingBox.x.toInt(),
            boundingBox.y.toInt(),
            boundingBox.width.toInt(),
            boundingBox.height.toInt(),
        )
        paintImageRotated(towerDirection2D, gg2!!)
        if (resource.isBurning) {
            paintExplosion(gg2!!)
        }
        gg2!!.dispose()
    }

    override fun propertyChange(evt: PropertyChangeEvent) {
        if (evt.propertyName == Tower.TOWER_CONSTRUCT) {
            if ((evt.newValue as Tower) == resource) {
                if (towerTopPanel == null) {
                    towerTopPanel = TowerTopPanel(resource, this)
                }
            }
        } else if (evt.propertyName == Tower.TOWER_DECONSTRUCT) {
            if ((evt.newValue as Tower) == resource) {
                if (towerTopPanel != null) {
                    towerTopPanel!!.dispose()
                    removeChild(towerTopPanel!!)
                    towerTopPanel = null
                }
            }
        } else if (evt.propertyName == Tower.TOWER_SHIELD_STATE_CHANGE ||
            evt.propertyName == Tower.TOWER_FUEL_STATE_CHANGE
        ) {
            parentPanel.repaint()
        }
    }

    override val isFocusable: Boolean
        get() = false

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(TowerPanel::class.java)

        private fun getImageURI() = GameObjectImages.Tower.imagesURL
    }
}
