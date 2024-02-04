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
class ChemStation : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(1400, 0)
        addPoint(2400, 400)
        addPoint(2800, 1400)
        addPoint(2400, 2400)
        addPoint(1400, 2800)
        addPoint(400, 2400)
        addPoint(0, 1400)
        addPoint(400, 400)
    }

    override fun loadHomePositions() = listOf(
        Base2D(1400.0, 1000.0, Direction2D.DOWN),
        Base2D(1400.0, 1800.0, Direction2D.DOWN),
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(400, 400, 600, 2000, 25, ResourceType.Normal),
        ResourceRandomRectangle(1800, 400, 600, 2000, 25, ResourceType.Normal),
        ResourceRandomRectangle(100, 1200, 900, 400, 15, ResourceType.DoublePoints),
        ResourceRandomRectangle(1800, 1200, 900, 400, 15, ResourceType.DoublePoints),
        ResourceRandomRectangle(1000, 1300, 200, 200, 5, ResourceType.ExtremPoint),
        ResourceRandomRectangle(1600, 1300, 200, 200, 5, ResourceType.ExtremPoint),
        ResourceRandomRectangle(400, 400, 600, 2000, 30, ResourceType.ExtraMothershipFuel),
        ResourceRandomRectangle(1800, 400, 600, 2000, 30, ResourceType.ExtraMothershipFuel),
        ResourceRandomRectangle(100, 1200, 2600, 400, 70, ResourceType.ExtraAgentFuel),
    )

    override fun loadLevelColor(): Color {
        return Color(250, 250, 210)
    }

    override fun loadLevelWallPolygons() = listOf(
        Polygon().apply {
            addPoint(1200, 600)
            addPoint(1200, 1200)
            addPoint(1600, 1200)
            addPoint(1600, 600)
            addPoint(1800, 800)
            addPoint(1800, 1200)
            addPoint(1401, 1400)
            addPoint(1800, 1600)
            addPoint(1800, 2000)
            addPoint(1600, 2200)
            addPoint(1600, 1600)
            addPoint(1200, 1600)
            addPoint(1200, 2200)
            addPoint(1000, 2000)
            addPoint(1000, 1600)
            addPoint(1399, 1400)
            addPoint(1000, 1200)
            addPoint(1000, 800)
        },
    )
}
