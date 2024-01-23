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
class UTurn : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(0, 0)
        addPoint(0, 800)
        addPoint(200, 1000)
        addPoint(800, 1000)
        addPoint(1000, 800)
        addPoint(1000, 0)
        addPoint(600, 0)
        addPoint(600, 500)
        addPoint(400, 500)
        addPoint(400, 0)
    }

    override fun loadHomePositions() = listOf(
        Base2D(200.0, 200.0, Direction2D.DOWN),
        Base2D(800.0, 200.0, Direction2D.DOWN)
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(0, 0, 1000, 1000, 5, ResourceType.Normal),
        ResourceRandomRectangle(0, 0, 1000, 1000, 5, ResourceType.DoublePoints),
        ResourceRandomRectangle(0, 0, 1000, 1000, 10, ResourceType.ExtremPoint),
        ResourceRandomRectangle(0, 0, 1000, 1000, 10, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(0, 0, 1000, 1000, 10, ResourceType.ExtraMothershipFuel)
    )

    override fun loadLevelColor(): Color {
        return Color(67, 167, 197)
    }

    override fun loadLevelWallPolygons() = emptyList<Polygon>()
}
