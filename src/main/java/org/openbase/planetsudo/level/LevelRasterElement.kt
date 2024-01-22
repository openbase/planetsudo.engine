/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.level

import org.openbase.planetsudo.level.LevelRasterElementNeigbour.NeigbourType
import org.openbase.planetsudo.level.levelobjects.Agent
import java.awt.geom.Rectangle2D
import kotlin.math.min

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class LevelRasterElement(val index: Int, private val levelView: LevelView) : Comparable<LevelRasterElement> {
    val x: Int = index % levelView.width
    val y: Int = index / levelView.width
    private val xLevelPosition: Int
    private val yLevelPosition: Int
    val rasterLevelRectangle: Rectangle2D
    val size: Int = levelView.rasterSize
    val isPartOfWall: Boolean
    val isNextToWall: Boolean
    val neigbours: ArrayList<LevelRasterElementNeigbour>

    var isVisited: Boolean
    var distance: Int
        private set

    init {
        this.xLevelPosition = levelView.levelObject.level.x + (x * size + size / 2)
        this.yLevelPosition = levelView.levelObject.level.y + (y * size + size / 2)
        this.rasterLevelRectangle = Rectangle2D.Double(
            (xLevelPosition - size / 2).toDouble(),
            (yLevelPosition - size / 2).toDouble(),
            size.toDouble(),
            size.toDouble()
        )
        val agentBoundsRectangle: Rectangle2D = Rectangle2D.Double(
            (xLevelPosition - Agent.AGENT_SIZE).toDouble(),
            (yLevelPosition - Agent.AGENT_SIZE).toDouble(),
            (Agent.AGENT_SIZE * 2).toDouble(),
            (Agent.AGENT_SIZE * 2).toDouble()
        )
        this.isPartOfWall = levelView.levelObject.level.containsWall(rasterLevelRectangle)
        this.isNextToWall = levelView.levelObject.level.containsWall(agentBoundsRectangle)
        this.isVisited = false
        this.distance = Int.MAX_VALUE
        this.neigbours = ArrayList()
    }

    fun reset(source: LevelRasterElement?) {
        isVisited = false
        if (source!!.index == index) {
            distance = 0
            return
        }
        distance = Int.MAX_VALUE
    }

    fun getxLevelPosition(): Int {
        return xLevelPosition
    }

    fun getyLevelPosition(): Int {
        return yLevelPosition
    }

    fun setDistance(distance: Int) {
        this.distance = min(this.distance.toDouble(), distance.toDouble()).toInt()
    }

    override fun compareTo(element: LevelRasterElement): Int {
        return distance.compareTo(element.distance)
    }

    fun calculateNeigbours() {
        var weight = 0
        // North
        if (y != 0) {
            weight = if (levelView[x, y - 1]!!.isNextToWall) 50 else 2
            //weight += levelView.get(x, y-1).isPartOfWall() ? Integer.MAX_VALUE : 0;
            addNeigbour(LevelRasterElementNeigbour(levelView[x, y - 1], weight, NeigbourType.North))
        }

        // NorthEast
        if (x != levelView.width - 1 && y != 0) {
            weight = if (levelView[x + 1, y - 1]!!.isNextToWall) 75 else 3
            //weight += levelView.get(x, y-1).isPartOfWall() ? Integer.MAX_VALUE : 0;
            addNeigbour(LevelRasterElementNeigbour(levelView[x + 1, y - 1], weight, NeigbourType.NorthEast))
        }

        // East
        if (x != levelView.width - 1) {
            weight = if (levelView[x + 1, y]!!.isNextToWall) 50 else 2
            //weight += levelView.get(x+1, y).isPartOfWall() ? Integer.MAX_VALUE : 0;
            addNeigbour(LevelRasterElementNeigbour(levelView[x + 1, y], weight, NeigbourType.East))
        }

        // SouthEast
        if (x != levelView.width - 1 && y != levelView.height - 1) {
            weight = if (levelView[x + 1, y + 1]!!.isNextToWall) 75 else 3
            //weight += levelView.get(x+1, y).isPartOfWall() ? Integer.MAX_VALUE : 0;
            addNeigbour(LevelRasterElementNeigbour(levelView[x + 1, y + 1], weight, NeigbourType.SouthEast))
        }
        // South
        if (y != levelView.height - 1) {
            weight = if (levelView[x, y + 1]!!.isNextToWall) 50 else 2
            //weight += levelView.get(x, y+1).isPartOfWall() ? Integer.MAX_VALUE : 0;
            addNeigbour(LevelRasterElementNeigbour(levelView[x, y + 1], weight, NeigbourType.South))
        }
        // SouthWest
        if (x != 0 && y != levelView.height - 1) {
            weight = if (levelView[x - 1, y + 1]!!.isNextToWall) 75 else 3
            //weight += levelView.get(x, y+1).isPartOfWall() ? Integer.MAX_VALUE : 0;
            addNeigbour(LevelRasterElementNeigbour(levelView[x - 1, y + 1], weight, NeigbourType.SouthWest))
        }
        // West
        if (x != 0) {
            weight = if (levelView[x - 1, y]!!.isNextToWall) 50 else 2
            //weight += levelView.get(x-1, y).isPartOfWall() ? Integer.MAX_VALUE : 0;
            addNeigbour(LevelRasterElementNeigbour(levelView[x - 1, y], weight, NeigbourType.West))
        }
        // NorthWest
        if (x != 0 && y != 0) {
            weight = if (levelView[x - 1, y - 1]!!.isNextToWall) 75 else 3
            //weight += levelView.get(x-1, y).isPartOfWall() ? Integer.MAX_VALUE : 0;
            addNeigbour(LevelRasterElementNeigbour(levelView[x - 1, y - 1], weight, NeigbourType.NorthWest))
        }
    }

    private fun addNeigbour(neigbour: LevelRasterElementNeigbour): Boolean {
        if (neigbour.element.isPartOfWall) {
            return false
        }
        return neigbours.add(neigbour)
    }

    fun getNeigbours(): Collection<LevelRasterElementNeigbour> {
        return neigbours
    }

    override fun toString(): String {
        return LevelRasterElement::class.java.simpleName + "[Distance:" + distance + "|visited:" + isVisited + "|neigbours:" + neigbours.size + "]"
    }
}
