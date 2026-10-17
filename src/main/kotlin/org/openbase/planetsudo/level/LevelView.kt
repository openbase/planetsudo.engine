/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.level

import org.openbase.jul.exception.CouldNotPerformException
import org.openbase.planetsudo.level.levelobjects.AbstractLevelObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Color
import java.awt.Graphics2D
import java.util.*
import java.util.concurrent.CancellationException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
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
    private var lastDestinationIndex: Int? = null
    private val viewUpdateLock = ReentrantLock()

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
        this.updateObjectMovement()
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
            (levelObject.position.y.toInt() - levelObject.level.y) / rasterSize,
        )
    }

    fun getDistance(otherLevelObject: AbstractLevelObject): Int {
        val destination = calcLevelRasterElement(levelObject)
        val distances = distanceMapFor(destination)
        return distances[otherLevelObject.toRasterElement().index]
    }

    fun getRelativeDirection(otherLevelObject: AbstractLevelObject): Int? {
        val destination = calcLevelRasterElement(levelObject)
        val distances = distanceMapFor(destination)
        return calcLevelRasterElement(otherLevelObject).neigbours
            .minByOrNull { distances[it.element.index] }
            ?.angle
    }

    fun updateObjectMovement() {
        logger.debug("UpdateObjectMovement")
        updateLevelView(calcLevelRasterElement(levelObject))
    }

    /** Updates the view's distance field for the object's current raster cell. */
    private fun updateLevelView(destination: LevelRasterElement) {
        val distances = distanceMapFor(destination)

        viewUpdateLock.withLock {
            if (calcLevelRasterElement(levelObject).index != destination.index) {
                return
            }
            if (lastDestinationIndex == destination.index) {
                logger.debug("Skip update since destination raster cell has not changed.")
                return
            }
            lastDestinationIndex = destination.index

            for (element in levelRepresentation) {
                element!!.reset(destination)
                element.setDistance(distances[element.index])
            }
        }
    }

    private fun calculateDistanceMap(destination: LevelRasterElement): IntArray {
        val distances = IntArray(levelRepresentation.size) { Int.MAX_VALUE }
        val distanceQueue = PriorityQueue(compareBy<DistanceEntry> { it.distance })
        distances[destination.index] = 0
        distanceQueue.add(DistanceEntry(destination.index, 0))

        while (!distanceQueue.isEmpty() && !Thread.currentThread().isInterrupted) {
            val current = distanceQueue.remove()
            if (current.distance != distances[current.index]) {
                continue
            }

            for (neighbour in levelRepresentation[current.index]!!.neigbours) {
                val neighbourIndex = neighbour.element.index
                val candidateDistance = current.distance + neighbour.weight
                if (candidateDistance < distances[neighbourIndex]) {
                    distances[neighbourIndex] = candidateDistance
                    distanceQueue.add(DistanceEntry(neighbourIndex, candidateDistance))
                }
            }
        }
        return distances
    }

    private data class DistanceEntry(val index: Int, val distance: Int)

    private fun distanceMapFor(destination: LevelRasterElement): IntArray =
        distanceMapFor(destination.index) { calculateDistanceMap(destination) }

    private fun distanceMapFor(destinationIndex: Int, compute: () -> IntArray): IntArray {
        val cache = distanceMapCacheLock.withLock {
            distanceMapCache
        }

        while (true) {
            var shouldCompute = false
            val computation = cache.lock.withLock {
                cache.completed[destinationIndex]?.let {
                    logger.debug("Reuse cached distance map for raster cell {}.", destinationIndex)
                    return it
                }
                cache.inFlight[destinationIndex] ?: CompletableFuture<IntArray>().also {
                    cache.inFlight[destinationIndex] = it
                    shouldCompute = true
                }
            }

            if (!shouldCompute) {
                try {
                    return computation.join()
                } catch (_: CancellationException) {
                    continue
                } catch (ex: CompletionException) {
                    throw ex.cause ?: ex
                }
            }

            try {
                val distances = compute()
                cache.lock.withLock {
                    cache.inFlight.remove(destinationIndex, computation)
                    if (!Thread.currentThread().isInterrupted) {
                        cache.completed[destinationIndex] = distances
                    }
                }
                if (Thread.currentThread().isInterrupted) {
                    computation.completeExceptionally(CancellationException("Distance-map computation interrupted"))
                    return distances
                }
                computation.complete(distances)
                return distances
            } catch (ex: Throwable) {
                cache.lock.withLock {
                    cache.inFlight.remove(destinationIndex, computation)
                }
                computation.completeExceptionally(ex)
                throw ex
            }
        }
    }

    private class DistanceMapCache {
        val lock = ReentrantLock()
        val completed = object : LinkedHashMap<Int, IntArray>(16, 0.75f, true) {
            override fun removeEldestEntry(eldest: MutableMap.MutableEntry<Int, IntArray>?): Boolean =
                size > DISTANCE_MAP_CACHE_SIZE
        }
        val inFlight = HashMap<Int, CompletableFuture<IntArray>>()
    }

    private fun AbstractLevelObject.toRasterElement() = calcLevelRasterElement(this)

    companion object {
        const val RASTER_SIZE: Int = 10
        private const val DISTANCE_MAP_CACHE_SIZE = 128
        private val distanceMapCacheLock = ReentrantLock()
        private var distanceMapCache = DistanceMapCache()

        fun clearDistanceMapCache() {
            distanceMapCacheLock.withLock {
                distanceMapCache = DistanceMapCache()
            }
        }
    }
}
