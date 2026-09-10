package org.openbase.planetsudo.view.level.levelobjects

import org.openbase.planetsudo.game.GameObjectImages
import org.openbase.planetsudo.geometry.Direction2D
import org.openbase.planetsudo.level.levelobjects.Tower
import org.openbase.planetsudo.level.levelobjects.Tower.TowerType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import java.awt.geom.Arc2D
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import javax.swing.Timer
import kotlin.math.hypot

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
        DrawLayer.BACKGROUND,
    ),
    PropertyChangeListener {
    private val tower: Tower?

    private var gg2: Graphics2D? = null
    private var direction: Direction2D? = null

    // scanning animation state: expanding wave rings
    private var scanning: Boolean = false
    private val scanRings: MutableList<Double> = ArrayList()
    private var scanTimer: Timer? = null
    private var scanMaxRadius: Double = 0.0
    private val ringSpeed: Double = 12.0 // pixels per tick
    private val ringSpacing: Double = 40.0 // spacing between consecutive rings

    init {
        LOGGER.info("Create $this")
        this.tower = tower
        tower.addPropertyChangeListener(this)

        // Timer advances rings outward
        scanTimer = Timer(40) {
            if (!scanning) return@Timer
            val it = scanRings.listIterator()
            while (it.hasNext()) {
                val idx = it.nextIndex()
                val v = it.next()
                val nv = v + ringSpeed
                it.set(nv)
                // remove ring if beyond max radius + spacing
                if (nv > scanMaxRadius + ringSpacing * 2) {
                    it.remove()
                }
            }
            // stop when no rings left
            if (scanRings.isEmpty()) {
                scanning = false
                scanTimer?.stop()
            }
            parentPanel.repaint()
        }
    }

    override fun paintComponent(g2: Graphics2D, gl: Graphics2D) {
        // Guard against stale or already-removed tower references during asynchronous Swing repaints.
        val currentTower = tower ?: return
        if (!resource.isConstructed) {
            return
        }

        boundingBox = resource.bounds
        direction = resource.direction

        gg2 = g2.create() as Graphics2D
        paintImageRotated(currentTower.direction, gg2!!)

        // draw scanning waves if active
        if (scanning) {
            try {
                val overlay = g2.create() as Graphics2D
                overlay.composite = java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1.0f)
                overlay.stroke = BasicStroke(3.0f)

                val cx = boundingBox.centerX
                val cy = boundingBox.centerY

                for (r in scanRings) {
                    if (r <= 0) continue
                    val progress = (r / scanMaxRadius).coerceIn(0.0, 1.0)
                    val alpha = (1.0 - progress) * 0.7
                    val color = Color(0, 220, 255, (alpha * 255).toInt().coerceIn(0, 255))
                    overlay.color = color
                    val ir = r
                    overlay.draw(Arc2D.Double(cx - ir, cy - ir, ir * 2, ir * 2, 0.0, 360.0, Arc2D.OPEN))
                }

                overlay.dispose()
            } catch (ex: Exception) {
                // ignore painting errors
            }
        }

        gg2!!.dispose()
    }

    override fun propertyChange(evt: PropertyChangeEvent) {
        //        if (evt.getPropertyName().equals(Tower.REMOVE_TOWER)) {
        //            if (((Tower) evt.getNewValue()).equals(resource)) {
        //                parentResourcePanel.removeChild(this);
        //            }
        //        }
        if (evt.propertyName == Tower.TOWER_SCAN_LEVEL && tower != null) {
            // start wave animation: compute max radius to level borders
            val levelBounds = tower.level.levelBorderPolygon.bounds2D
            val cx = tower.position.x
            val cy = tower.position.y
            // compute distance to all 4 corners and take max
            val corners = arrayOf(
                doubleArrayOf(levelBounds.minX, levelBounds.minY),
                doubleArrayOf(levelBounds.minX + levelBounds.width, levelBounds.minY),
                doubleArrayOf(levelBounds.minX, levelBounds.minY + levelBounds.height),
                doubleArrayOf(levelBounds.minX + levelBounds.width, levelBounds.minY + levelBounds.height),
            )
            var maxd = 0.0
            for (c in corners) {
                val dx = c[0] - cx
                val dy = c[1] - cy
                val d = hypot(dx, dy)
                if (d > maxd) maxd = d
            }
            scanMaxRadius = maxd

            // prime a few rings so the wave looks continuous
            scanRings.clear()
            scanRings.add(0.0)
            scanRings.add(-ringSpacing)
            scanRings.add(-ringSpacing * 2)
            scanning = true
            scanTimer?.restart()
            return
        }
        // other property changes can be handled here
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
