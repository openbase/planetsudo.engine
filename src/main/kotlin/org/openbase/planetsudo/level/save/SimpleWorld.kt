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
class SimpleWorld : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(0, 0)
        addPoint(1000, 0)
        addPoint(1000, 1000)
        addPoint(0, 1000)
    }

    override fun loadHomePositions() = listOf(
        Base2D(200.0, 500.0, Direction2D.DOWN),
        Base2D(800.0, 500.0, Direction2D.DOWN),
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(0, 0, 1000, 1000, 10, ResourceType.Normal),
        ResourceRandomRectangle(0, 0, 1000, 1000, 5, ResourceType.DoublePoints),
        ResourceRandomRectangle(400, 800, 200, 200, 10, ResourceType.ExtremPoint),
        ResourceRandomRectangle(0, 0, 1000, 1000, 10, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(400, 0, 200, 200, 10, ResourceType.ExtraMothershipFuel),
        ResourceRandomRectangle(0, 0, 1000, 50, 12, ResourceType.Tonic),
        ResourceRandomRectangle(0, 1000, 1000, 50, 12, ResourceType.Tonic)
    )

    override fun loadLevelColor() = Color(255, 153, 0)

    override fun loadLevelWallPolygons() = emptyList<Polygon>()
}
