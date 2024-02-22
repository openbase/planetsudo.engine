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
 * @author Jodi_Pa
 */
class Hexagon : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(500,0)
        addPoint(1500,0)
        addPoint(2000,866)
        addPoint(1500,1732)
        addPoint(500,1732)
        addPoint(0,866)
    }

    override fun loadHomePositions() = listOf(
        Base2D(500.0,866.0, Direction2D.RIGHT),
        Base2D(1500.0, 866.0, Direction2D.LEFT)
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(0, 0, 2000, 1732, 15, ResourceType.Normal),
        ResourceRandomRectangle(0, 0, 2000, 200, 10, ResourceType.DoublePoints),
        ResourceRandomRectangle(0, 1632, 2000, 200, 10, ResourceType.DoublePoints),
        ResourceRandomRectangle(950, 0, 100, 1732, 10, ResourceType.ExtremPoint),
        ResourceRandomRectangle(500, 433, 1000, 866, 20, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(500, 433, 1000, 866, 15, ResourceType.ExtraMothershipFuel),
        ResourceRandomRectangle(0,816,100,50,12,ResourceType.Tonic),
        ResourceRandomRectangle(1900,816,100,100,12,ResourceType.Tonic),
    )

    override fun loadLevelColor() = Color(255, 153, 0)

    override fun loadLevelWallPolygons() = listOf(
        Polygon().apply {
            addPoint(950,433)
            addPoint(1050,433)
            addPoint(1050,1299)
            addPoint(950,1299)
        }
    )
}
