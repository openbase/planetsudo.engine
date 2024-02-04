package org.openbase.planetsudo.view.level.levelobjects

import org.openbase.planetsudo.game.GameObjectImages
import org.openbase.planetsudo.geometry.Direction2D
import org.openbase.planetsudo.level.levelobjects.Tower
import org.openbase.planetsudo.level.levelobjects.Tower.TowerType
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
        getImageURI(tower.type),
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
        this.teamColor = tower.mothership.team.teamColor
    }

    override fun paintComponent(g2: Graphics2D, gl: Graphics2D) {
        // only paint if erected
        if (!resource.isErected) {
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
        gg2!!.dispose()
    }

    override fun propertyChange(evt: PropertyChangeEvent) {
        if (evt.propertyName == Tower.TOWER_ERECT) {
            if ((evt.newValue as Tower) == resource) {
                if (towerTopPanel == null) {
                    towerTopPanel = TowerTopPanel(resource, this)
                }
            }
        } else if (evt.propertyName == Tower.TOWER_DISMANTLE) {
            if ((evt.newValue as Tower) == resource) {
                if (towerTopPanel != null) {
                    removeChild(towerTopPanel!!)
                    towerTopPanel = null
                }
            }
        }
    }

    override val isFocusable: Boolean
        get() = false

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(TowerPanel::class.java)

        private fun getImageURI(type: TowerType?): String {
            return GameObjectImages.Tower.imagesURL
        }
    }
}
