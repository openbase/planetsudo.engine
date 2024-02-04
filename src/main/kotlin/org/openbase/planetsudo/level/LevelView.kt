/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.level

import org.openbase.jul.exception.CouldNotPerformException
import org.openbase.planetsudo.geometry.Point2D
import org.openbase.planetsudo.level.levelobjects.AbstractLevelObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Color
import java.awt.Graphics2D
import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class LevelView(level: AbstractLevel, levelObject: AbstractLevelObject) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    val levelObject: AbstractLevelObject
    protected val levelRepresentation: Array<LevelRasterElement?>
    private val x_base: Int
    private val y_base: Int
    var width: Int = 0
    var height: Int = 0
    val rasterSize: Int
    private var lastPosition: Point2D? = null

    constructor(levelObject: AbstractLevelObject) : this(levelObject.level, levelObject)

    init {
        logger.debug("Create new level view.")
        this.levelObject = levelObject
        this.rasterSize = RASTER_SIZE
        val bounds = level.levelBorderPolygon.bounds2D

        val widthCast = (bounds.width / rasterSize).toInt().toDouble()
        val heightCast = (bounds.height.toInt() / rasterSize).toDouble()

        this.x_base = bounds.x.toInt()
        this.y_base = bounds.y.toInt()

        if (widthCast < bounds.width / rasterSize) {
            this.width = (widthCast.toInt()) + 1
        } else {
            this.width = widthCast.toInt()
        }
        if (heightCast < bounds.height / rasterSize) {
            this.height = (heightCast.toInt()) + 1
        } else {
            this.height = heightCast.toInt()
        }
        this.levelRepresentation = arrayOfNulls(width * height)
        this.initLevelRepresentation()
        this.updateObjectMovement(true)
    }

    private fun initLevelRepresentation() {
        logger.debug("initLevelRepresentation")
        for (i in levelRepresentation.indices) {
            levelRepresentation[i] = LevelRasterElement(i, this)
        }

        for (element in levelRepresentation) {
            element!!.calculateNeigbours()
        }
    }

    operator fun get(x: Int, y: Int): LevelRasterElement {
        try {
            return levelRepresentation[x + y * width]!!
        } catch (ex: CouldNotPerformException) {
            logger.error("x_base[" + x_base + "] y_base[" + y_base + "] x[" + x + "] y[" + y + "] width[" + width + "] bounds[" + levelRepresentation.size + "]")
            throw RuntimeException(ex)
        }
    }

    fun drawLevelView(x: Int, y: Int, g2: Graphics2D) {
//        logger.info("Draw LevelView")
        var greyValue: Int
        for (element in levelRepresentation) {
            if (element!!.isPartOfWall) {
                g2.color = Color.RED
            } else if (element.isNextToWall) {
                g2.color = Color.GREEN
            } else {
                greyValue = min(255.0, (max(0.0, element.distance.toDouble()) / 3).toDouble())
                    .toInt()
                g2.color = Color(greyValue, greyValue, greyValue)
            }
            g2.fill(element.rasterLevelRectangle)
            // 			g2.setColor(Color.ORANGE);
// 			g2.draw(element.getRasterLevelRectangle());
        }
    }

    private fun calcLevelRasterElement(levelObject: AbstractLevelObject): LevelRasterElement {
        logger.debug("CalcLevelRasterElement")
        return get(
            (levelObject.position.x.toInt() - levelObject.level.x) / rasterSize,
            (levelObject.position.y.toInt() - levelObject.level.y) / rasterSize
        )
    }

    fun getAbsolutAngle(levelObject: AbstractLevelObject): Int {
// 		logger.info("BEGIN: GetAbsoluteAngle");
        val angle = getAngle(calcLevelRasterElement(levelObject), calcLevelRasterElement(this.levelObject))
        assert(angle != -1)
        return angle
    }

    fun getDistance(levelObject: AbstractLevelObject): Int {
// 		logger.debug("GetDistance");
        return getDistance(calcLevelRasterElement(levelObject), calcLevelRasterElement(this.levelObject))
    }

    fun dijkstraTest() {
// 		logger.debug("dijkstraTest");
        dijkstra(levelRepresentation[0]!!, levelRepresentation[width * height - 1]!!)
    }

    private fun getDistance(position: LevelRasterElement, destination: LevelRasterElement): Int {
// 		logger.debug("getDistance");
        dijkstra(position, destination)
        return position.distance
    }

    private fun getAngle(position: LevelRasterElement, destination: LevelRasterElement): Int {
// 		logger.debug("getAngle");
        dijkstra(position, destination)
        var neigbourNextToDestination: LevelRasterElementNeigbour? = null
        for (neigbour in position.neigbours) {
            if (neigbourNextToDestination == null || neigbourNextToDestination.element.distance > neigbour.element.distance) {
                neigbourNextToDestination = neigbour
            }
        }
        if (neigbourNextToDestination == null) {
            logger.error("No way to destionation found!")
            return 0
        }
        return neigbourNextToDestination.angle
    }

    @JvmOverloads
    fun updateObjectMovement(force: Boolean = false) {
        logger.debug("UpdateObjectMovement")
        dijkstra(null, calcLevelRasterElement(levelObject), force)
    }

    private fun dijkstra2(position: LevelRasterElement, destination: LevelRasterElement, force: Boolean) {
        if (!force && stablePosition()) {
            logger.debug("Skip dijkstra.")
            return
        }
        assert(!destination.isPartOfWall)
        // Initialisation
        val distanceQueue = TreeMap<Int, ArrayList<LevelRasterElement?>>()
        for (element in levelRepresentation) {
            element!!.reset(destination)
        }
        // find shortest distance
        var element: LevelRasterElement? = destination
        do {
            element?.isVisited = true
            for (neigbour in element!!.neigbours) {
                if (!neigbour.element.isVisited) {
                    neigbour.element.setDistance(element.distance + neigbour.weight)
                    if (!distanceQueue.containsKey(neigbour.element.distance)) {
                        distanceQueue[neigbour.element.distance] = ArrayList()
                    }
                    if (!distanceQueue[neigbour.element.distance]!!.contains(neigbour.element)) {
                        distanceQueue[neigbour.element.distance]!!.add(neigbour.element)
                    }
                }
            }

            if (!distanceQueue.isEmpty()) {
                element = distanceQueue.firstEntry().value.removeAt(0)
                if (distanceQueue.firstEntry().value.isEmpty()) {
                    distanceQueue.pollFirstEntry()
                }
            }
        } while (element !== position && element != null) //
    }

    private fun stablePosition(): Boolean {
        return levelObject.position.equals(lastPosition)
    }

    private fun dijkstra(position: LevelRasterElement, destination: LevelRasterElement) {
        dijkstra(position, destination, false)
    }

    @Synchronized
    private fun dijkstra(position: LevelRasterElement?, destination: LevelRasterElement, force: Boolean) {
        if (!force && stablePosition()) {
            logger.debug("Skip dijkstra.")
            return
        }
        this.lastPosition = levelObject.position
        // 		logger.debug("BEGINN: dijkstra new calc.");
// 		long startTime = System.currentTimeMillis();
        // Initialisation
        val distanceQueue = PriorityQueue<LevelRasterElement?>()
        for (element in levelRepresentation) {
            element!!.reset(destination)
        }
        // find next element with closed distance
        var element: LevelRasterElement? = destination
        do {
            element?.isVisited = true
            for (neigbour in element!!.neigbours) {
                if (!neigbour.element.isVisited) {
                    neigbour.element.setDistance(element.distance + neigbour.weight)

                    if (!distanceQueue.contains(neigbour.element)) {
                        distanceQueue.add(neigbour.element)
                    }
                }
            }
            element = distanceQueue.poll()
            // } while (element != null && element != position);
        } while (element != null)
        // 		logger.debug("END["+(System.currentTimeMillis()-startTime)+"]: dijkstra new calc.");
    }

    companion object {
        const val RASTER_SIZE: Int = 10
    }
}
