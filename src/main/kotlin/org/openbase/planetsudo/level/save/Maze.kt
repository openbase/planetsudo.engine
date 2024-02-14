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
class Maze : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(0, 0)
        addPoint(2000, 0)
        addPoint(2000, 200)
        addPoint(200, 200)
        addPoint(200, 300)
        addPoint(2000, 300)
        addPoint(2000, 1400)
        addPoint(200, 1400)
        addPoint(200, 1500)
        addPoint(2000, 1500)
        addPoint(2000, 2000)
        addPoint(0, 2000)
        addPoint(0, 1800)
        addPoint(1800, 1800)
        addPoint(1800, 1700)
        addPoint(0, 1700)
        addPoint(0, 600)
        addPoint(1800, 600)
        addPoint(1800, 500)
        addPoint(0, 500)
    }

    override fun loadHomePositions() = listOf(
        Base2D(1900.0, 100.0, Direction2D.DOWN),
        Base2D(100.0, 1900.0, Direction2D.DOWN),
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(0, 300, 2000, 200, 10, ResourceType.Normal),
        ResourceRandomRectangle(0, 1500, 2000, 200, 10, ResourceType.Normal),
        ResourceRandomRectangle(0, 600, 2000, 200, 7, ResourceType.DoublePoints),
        ResourceRandomRectangle(300, 900, 600, 200, 50, ResourceType.ExtremPoint),
        ResourceRandomRectangle(1100, 900, 600, 200, 50, ResourceType.ExtremPoint),
        ResourceRandomRectangle(0, 300, 2000, 1400, 40, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(0, 300, 2000, 1400, 30, ResourceType.ExtraMothershipFuel),
        ResourceRandomRectangle(0, 1200, 2000, 200, 7, ResourceType.DoublePoints),
        ResourceRandomRectangle(0,300,2000,200,20,ResourceType.Tonic),
        ResourceRandomRectangle(0,1500,2000,200,20,ResourceType.Tonic)
    )

    override fun loadLevelColor() = Color(255, 211, 155)

    override fun loadLevelWallPolygons() = listOf(
        Polygon().apply {
            addPoint(200, 800)
            addPoint(900, 800)
            addPoint(900, 900)
            addPoint(300, 900)
            addPoint(300, 1100)
            addPoint(900, 1100)
            addPoint(900, 1200)
            addPoint(200, 1200)
        },
        Polygon().apply {
            addPoint(1100, 800)
            addPoint(1800, 800)
            addPoint(1800, 1200)
            addPoint(1100, 1200)
            addPoint(1100, 1100)
            addPoint(1700, 1100)
            addPoint(1700, 900)
            addPoint(1100, 900)
        },
    )
}
