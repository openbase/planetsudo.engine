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
class HeliLandeplatz : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(0, 0)
        addPoint(400, 0)
        addPoint(400, 300)
        addPoint(700, 300)
        addPoint(700, 0)
        addPoint(1100, 0)
        addPoint(1100, 1000)
        addPoint(700, 1000)
        addPoint(700, 700)
        addPoint(400, 700)
        addPoint(400, 1000)
        addPoint(0, 1000)
    }

    override fun loadHomePositions() = listOf(
        Base2D(200.0, 850.0, Direction2D.UP),
        Base2D(900.0, 150.0, Direction2D.DOWN),
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(0, 300, 1100, 300, 15, ResourceType.Normal),
        ResourceRandomRectangle(0, 0, 400, 300, 5, ResourceType.DoublePoints),
        ResourceRandomRectangle(700, 600, 400, 300, 5, ResourceType.DoublePoints),
        ResourceRandomRectangle(400, 300, 300, 300, 3, ResourceType.ExtremPoint),
        ResourceRandomRectangle(400, 300, 300, 300, 5, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(400, 300, 300, 300, 10, ResourceType.ExtraMothershipFuel),
        ResourceRandomRectangle(0, 0, 400, 50, 10, ResourceType.Tonic),
        ResourceRandomRectangle(700, 950, 400, 50, 10, ResourceType.Tonic),
    )

    override fun loadLevelColor() = Color(167, 167, 197)

    override fun loadLevelWallPolygons() = emptyList<Polygon>()
}
