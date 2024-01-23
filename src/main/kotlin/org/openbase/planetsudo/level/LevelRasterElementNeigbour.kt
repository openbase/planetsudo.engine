/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.level

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class LevelRasterElementNeigbour(val element: LevelRasterElement, val weight: Int, val type: NeigbourType) {
    enum class NeigbourType {
        North, West, East, South, NorthEast, SouthEast, SouthWest, NorthWest
    }

    val angle: Int

    init {
        this.angle = calcAngle()
    }

    private fun calcAngle(): Int {
        when (type) {
            NeigbourType.North -> return 270
            NeigbourType.NorthEast -> return 315
            NeigbourType.East -> return 0
            NeigbourType.SouthEast -> return 45
            NeigbourType.South -> return 90
            NeigbourType.SouthWest -> return 135
            NeigbourType.West -> return 180
            NeigbourType.NorthWest -> return 225
            else -> {
                logger.error("Unknown NeigbourType: $type")
                return 0
            }
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(LevelRasterElementNeigbour::class.java)
    }
}
