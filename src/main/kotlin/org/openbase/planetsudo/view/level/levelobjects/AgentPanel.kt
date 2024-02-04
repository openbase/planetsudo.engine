/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.view.level.levelobjects

import org.openbase.planetsudo.game.GameObjectImages
import org.openbase.planetsudo.geometry.Direction2D
import org.openbase.planetsudo.geometry.Point2D
import org.openbase.planetsudo.level.levelobjects.*
import org.openbase.planetsudo.view.MainGUI
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.Polygon
import java.awt.event.MouseEvent

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class AgentPanel(resource: Agent, parentResourcePanel: MothershipPanel) :
    AbstractLevelObjectPanel<Agent, MothershipPanel>(
        resource,
        resource.polygon,
        getAgentImage(resource).imagesURL,
        parentResourcePanel,
        DrawLayer.FORGROUND,
    ) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    val teamColor: Color? = resource.team.teamColor

    private var levelObject: AbstractLevelObject? = null
    private var g22: Graphics2D? = null
    private var x = 0
    private var y = 0
    private var direction: Direction2D? = null
    private var side: Direction2D? = null
    private var position: Point2D? = null
    private val xPoses = IntArray(3)
    private val yPoses = IntArray(4)

    // public static boolean viewFlag = true;
    init {
        logger.info("Create AgentPanel of $resource")
        // 		if(resource.getMothership().getTeam().getId() == 0) {
        //          MainGUI.levelView = resource.levelView
        // 		}
    }

    override fun paintComponent(g2: Graphics2D, gl: Graphics2D) {
        boundingBox = resource.bounds
        position = resource.position
        x = position!!.x.toInt()
        y = position!!.y.toInt()
        val trans_x = x + 60
        val trans_y = y + 60
        direction = resource.direction

        if (showStateLabel) {
            gl.color = Color.WHITE
            gl.drawLine(boundingBox.maxX.toInt(), boundingBox.maxY.toInt(), trans_x, trans_y)
        }

        if (DEBUG && MainGUI.levelView == resource.levelView) {
            resource.levelView?.drawLevelView(
                parentResourcePanel.boundingBox.x.toInt(),
                parentResourcePanel.boundingBox.y.toInt(),
                g2,
            )
        }

        // Paint Team Color
        g22 = g2.create() as Graphics2D
        g22!!.color = teamColor
        g22!!.transform(getBoundsTransformation(direction!!))
        // g22.fillRect(3, 10, 19, 9);
        g22!!.fill(TEAM_COLOR_POLYGON)
        if (resource.isSupportOrdered && animationFlag) {
// 				if(((System.currentTimeMillis()/500)%500)>5) {
            g22!!.color = COLOR_DISABLED
            // 				}
        }
        g22!!.fillOval(relative(33.0), relative(33.0), relative(35.0), relative(35.0)) // Oval zeichnen
        g22!!.dispose()

        // Paint Laser
        levelObject = resource.isFightingWith
        if (levelObject != null) {
            g2.color = teamColor
            side = Direction2D(direction!!.angle + 90)
            g2.drawLine(
                (x + (side!!.vector.x * resource.width / 2)).toInt(),
                (y + (side!!.vector.y * resource.height / 3)).toInt(),
                levelObject!!.position.x.toInt(),
                levelObject!!.position.y.toInt(),
            )
            side = Direction2D(direction!!.angle - 90)
            g2.drawLine(
                (x + (side!!.vector.x * resource.width / 2)).toInt(),
                (y + (side!!.vector.y * resource.height / 3)).toInt(),
                levelObject!!.position.x.toInt(),
                levelObject!!.position.y.toInt(),
            )
        }

        levelObject = resource.wasHelping()
        if (levelObject != null) {
            side = Direction2D(direction!!.angle + 90)
            xPoses[0] = (x + (side!!.vector.x * 15)).toInt()
            yPoses[0] = (y + (side!!.vector.y * 15)).toInt()
            xPoses[1] = (x - (side!!.vector.x * 15)).toInt()
            yPoses[1] = (y - (side!!.vector.y * 15)).toInt()
            xPoses[2] = levelObject!!.position.x.toInt()
            yPoses[2] = levelObject!!.position.y.toInt()
            g2.color = teamColor
            g2.fillPolygon(xPoses, yPoses, 3)
            // 			g2.drawPolygon((int) (resource.getPosition().xresource.getWidth()/2)),
// 						(int) (resource.getPosition().yheight/3)),
// 						(int) (levelObject.getPosition().x),
// 						(int) (levelObject.getPosition().y));
        }

        // paintShape(g2);
        paintImageRotated(direction!!, g2)

        // Explositon
        if (!resource.isAlive) {
            paintExplosion(g2)
        }

        // Paint FuelBarBackground
        gl.color = FUEL_BACKGROUND
        gl.fillRect(
            (x - FUEL_BAR_STATIC_WIDTH / 2).toInt(),
            (y - FUEL_BAR_STATIC_POSITION_Y).toInt(),
            FUEL_BAR_STATIC_WIDTH.toInt(),
            FUEL_BAR_STATIC_HEIGHT,
        )

        // Paint FuelBar
        gl.color = Color.GREEN
        gl.fillRect(
            (x - FUEL_BAR_STATIC_WIDTH / 2).toInt(),
            (y - FUEL_BAR_STATIC_POSITION_Y).toInt(),
            (FUEL_BAR_STATIC_WIDTH / resource.fuelVolume * resource.fuel).toInt(),
            FUEL_BAR_STATIC_HEIGHT,
        )

        // Paint StateLable
        if (showStateLabel) {
            val description = resource.description
            gl.font = Font(Font.SERIF, Font.BOLD, 25)
            gl.color = teamColor
            gl.fillRoundRect(trans_x - 10, trans_y - 30, 20 + gl.fontMetrics.stringWidth(description), 40, 50, 50)
            gl.color = Color.BLACK

            gl.drawRoundRect(trans_x - 10, trans_y - 30, 20 + gl.fontMetrics.stringWidth(description), 40, 50, 50)
            gl.drawString(description, trans_x, trans_y)
        }
    }

    override val isFocusable: Boolean
        get() = false

    override fun notifyMouseEntered() {
    }

    override fun notifyMouseClicked(evt: MouseEvent) {
    }

    companion object {
        val FUEL_BACKGROUND: Color = Color(255, 20, 20)
        val COLOR_DISABLED: Color = Color(154, 154, 154)
        const val FUEL_BAR_STATIC_WIDTH: Double = 40.0
        const val FUEL_BAR_STATIC_POSITION_Y: Double = 32.0
        const val FUEL_BAR_STATIC_HEIGHT: Int = 4

        @JvmField
        var showStateLabel: Boolean = false

        fun getAgentImage(agent: Agent): GameObjectImages {
            return if (agent.isCommander) {
                GameObjectImages.AgentCommander
            } else {
                GameObjectImages.Agent
            }
        }

        val TEAM_COLOR_POLYGON: Polygon = Polygon()

        init {
            TEAM_COLOR_POLYGON.addPoint(relative(7.0), relative(36.0))
            TEAM_COLOR_POLYGON.addPoint(relative(17.0), relative(36.0))
            TEAM_COLOR_POLYGON.addPoint(relative(19.0), relative(50.0))
            TEAM_COLOR_POLYGON.addPoint(relative(50.0), relative(12.0))
            TEAM_COLOR_POLYGON.addPoint(relative(80.0), relative(50.0))
            TEAM_COLOR_POLYGON.addPoint(relative(82.0), relative(36.0))
            TEAM_COLOR_POLYGON.addPoint(relative(92.0), relative(36.0))
            TEAM_COLOR_POLYGON.addPoint(relative(92.0), relative(82.0))
            TEAM_COLOR_POLYGON.addPoint(relative(83.0), relative(92.0))
            TEAM_COLOR_POLYGON.addPoint(relative(50.0), relative(71.0))
            TEAM_COLOR_POLYGON.addPoint(relative(16.0), relative(91.0))
            TEAM_COLOR_POLYGON.addPoint(relative(7.0), relative(82.0))
        }

        private fun relative(per: Double): Int {
            return ((Agent.AGENT_SIZE * per) / 100).toInt()
        }
    }
}
