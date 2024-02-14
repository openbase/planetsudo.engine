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
class Broken : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(0, 0)
        addPoint(2000, 0)
        addPoint(2000, 400)
        addPoint(1300, 700)
        addPoint(2000, 600)
        addPoint(1700, 900)
        addPoint(1900, 1100)
        addPoint(1500, 1300)
        addPoint(1800, 1500)
        addPoint(2000, 1800)
        addPoint(2000, 2000)
        addPoint(0, 2000)
        addPoint(0, 1600)
        addPoint(700, 1300)
        addPoint(0, 1400)
        addPoint(300, 1100)
        addPoint(100, 900)
        addPoint(500, 700)
        addPoint(200, 500)
        addPoint(0, 200)
    }

    override fun loadHomePositions(): List<Base2D> = listOf(
        Base2D(200.0, 1800.0, Direction2D.DOWN),
        Base2D(1800.0, 200.0, Direction2D.DOWN),
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(0, 0, 2000, 2000, 35, ResourceType.Normal),
        ResourceRandomRectangle(1400, 700, 300, 500, 35, ResourceType.ExtremPoint),
        ResourceRandomRectangle(300, 800, 300, 500, 35, ResourceType.ExtremPoint),
        ResourceRandomRectangle(700, 0, 600, 2000, 30, ResourceType.ExtraMothershipFuel),
        ResourceRandomRectangle(700, 0, 600, 2000, 50, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(0,500,500,1000,15,ResourceType.Tonic),
        ResourceRandomRectangle(1500,500,500,1000,15,ResourceType.Tonic)
    )

    override fun loadLevelColor() = Color(74, 164, 94)

    override fun loadLevelWallPolygons() = emptyList<Polygon>()
}
