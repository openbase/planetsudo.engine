/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.level.save

import org.openbase.planetsudo.geometry.Base2D
import org.openbase.planetsudo.geometry.Direction2D
import org.openbase.planetsudo.level.AbstractLevel
import org.openbase.planetsudo.level.ResourcePlacement
import org.openbase.planetsudo.level.ResourceRandomRectangle
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType
import java.awt.Color
import java.awt.Polygon

/**
 *
 * @author noxus
 */
class AgentK : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(0, 0)
        addPoint(500, 0)
        addPoint(500, 500)
        addPoint(1100, 0)
        addPoint(1600, 0)
        addPoint(500, 1000)
        addPoint(1600, 2000)
        addPoint(1100, 2000)
        addPoint(500, 1500)
        addPoint(500, 2000)
        addPoint(0, 2000)
    }

    override fun loadHomePositions(): List<Base2D> = listOf(
        Base2D(1200.0, 100.0, Direction2D.DOWN),
        Base2D(1200.0, 1900.0, Direction2D.DOWN)
    )

    override fun loadResourcePlacement(): List<ResourcePlacement> = listOf(
        ResourceRandomRectangle(500, 0, 750, 1100, 10, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(500, 750, 750, 1100, 10, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(0, 0, 500, 50, 20, ResourceType.ExtremPoint),
        ResourceRandomRectangle(0, 1950, 500, 50, 20, ResourceType.ExtremPoint),
        ResourceRandomRectangle(0, 0, 500, 500, 15, ResourceType.ExtraMothershipFuel),
        ResourceRandomRectangle(0, 1500, 500, 500, 15, ResourceType.ExtraMothershipFuel),
        ResourceRandomRectangle(0, 500, 500, 500, 5, ResourceType.Normal),
        ResourceRandomRectangle(0, 1000, 500, 500, 5, ResourceType.Normal),
        ResourceRandomRectangle(0, 0, 500, 500, 5, ResourceType.DoublePoints),
        ResourceRandomRectangle(0, 1500, 500, 500, 5, ResourceType.DoublePoints)
    )

    override fun loadLevelColor(): Color = Color(137, 67, 162)

    override fun loadLevelWallPolygons(): List<Polygon> = emptyList<Polygon>()
}
