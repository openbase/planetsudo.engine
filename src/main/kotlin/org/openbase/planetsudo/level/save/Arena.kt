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
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class Arena : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(0, 200)
        addPoint(400, 200)
        addPoint(400, 0)
        addPoint(600, 0)
        addPoint(600, 200)
        addPoint(1000, 200)
        addPoint(1000, 800)
        addPoint(600, 800)
        addPoint(600, 1000)
        addPoint(400, 1000)
        addPoint(400, 800)
        addPoint(0, 800)
    }

    override fun loadHomePositions() = listOf(
        Base2D(500.0, 100.0, Direction2D.UP),
        Base2D(500.0, 900.0, Direction2D.UP),
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(0, 0, 1000, 1000, 10, ResourceType.Normal),
        ResourceRandomRectangle(300, 325, 400, 50, 10, ResourceType.DoublePoints),
        ResourceRandomRectangle(300, 675, 400, 50, 10, ResourceType.DoublePoints),
        ResourceRandomRectangle(400, 480, 200, 40, 10, ResourceType.ExtremPoint),
        ResourceRandomRectangle(0, 0, 1000, 1000, 10, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(0, 0, 1000, 1000, 5, ResourceType.ExtraMothershipFuel),
        ResourceRandomRectangle(0,200,100,100,5,ResourceType.Tonic),
        ResourceRandomRectangle(900,200,100,100,5,ResourceType.Tonic),
        ResourceRandomRectangle(0,700,100,100,5,ResourceType.Tonic),
        ResourceRandomRectangle(900,700,100,100,5,ResourceType.Tonic)
    )

    override fun loadLevelColor() = Color(67, 167, 197)

    override fun loadLevelWallPolygons(): List<Polygon> = emptyList<Polygon>()
}
