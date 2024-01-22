/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.level.save

import org.openbase.planetsudo.geometry.Base2D
import org.openbase.planetsudo.geometry.Direction2D
import org.openbase.planetsudo.level.AbstractLevel
import org.openbase.planetsudo.level.ResourceRandomRectangle
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType
import java.awt.Color
import java.awt.Polygon

/**
 *
 * @author Grimmy
 */
class Pentagon : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(1500, 0)
        addPoint(3000, 1100)
        addPoint(2500, 2900)
        addPoint(500, 2900)
        addPoint(0, 1100)
    }

    override fun loadHomePositions() = listOf(
        Base2D(1200.0, 2700.0, Direction2D.DOWN),
        Base2D(1800.0, 2700.0, Direction2D.DOWN)
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(0, 1400, 3000, 1100, 50, ResourceType.Normal),
        ResourceRandomRectangle(0, 500, 3000, 900, 50, ResourceType.DoublePoints),
        ResourceRandomRectangle(0, 0, 3000, 500, 50, ResourceType.ExtremPoint),
        ResourceRandomRectangle(0, 0, 3000, 2000, 70, ResourceType.ExtraMothershipFuel),
        ResourceRandomRectangle(0, 0, 3000, 2900, 150, ResourceType.ExtraAgentFuel)
    )

    override fun loadLevelColor() = Color(0, 0, 210)

    override fun loadLevelWallPolygons() = listOf(
        Polygon().apply {
            addPoint(1500, 1200)
            addPoint(1850, 1500)
            addPoint(1750, 1950)
            addPoint(1250, 1950)
            addPoint(1150, 1500)
        },
        Polygon().apply {
            addPoint(1000, 2450)
            addPoint(2000, 2450)
            addPoint(2000, 2500)
            addPoint(1600, 2500)
            addPoint(1600, 2900)
            addPoint(1400, 2900)
            addPoint(1400, 2500)
            addPoint(1000, 2500)
        },
        Polygon().apply {
            addPoint(1100, 2200)
            addPoint(1100, 2150)
            addPoint(1900, 2150)
            addPoint(1900, 2200)
        },
        Polygon().apply {
            addPoint(800, 2300)
            addPoint(750, 2300)
            addPoint(450, 1150)
            addPoint(1300, 550)
            addPoint(1300, 600)
            addPoint(500, 1170)
        },
        Polygon().apply {
            addPoint(1000, 1950)
            addPoint(820, 1400)
            addPoint(1350, 1000)
            addPoint(1350, 1050)
            addPoint(900, 1400)
            addPoint(1050, 1950)
        },
        Polygon().apply {
            addPoint(1950, 1950)
            addPoint(2000, 1950)
            addPoint(2180, 1400)
            addPoint(1650, 1000)
            addPoint(1650, 1050)
            addPoint(2100, 1400)
        },
        Polygon().apply {
            addPoint(2200, 2300)
            addPoint(2250, 2300)
            addPoint(2550, 1150)
            addPoint(1700, 550)
            addPoint(1700, 600)
            addPoint(2500, 1170)
        }
    )
}
