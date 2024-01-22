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
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class Entropie : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(0, 0)
        addPoint(0, 400)
        addPoint(450, 400)
        addPoint(450, 600)
        addPoint(150, 600)
        addPoint(150, 800)
        addPoint(450, 800)
        addPoint(450, 1000)
        addPoint(0, 1000)
        addPoint(0, 1400)
        addPoint(900, 1400)
        addPoint(900, 0)
    }

    override fun loadHomePositions() = listOf(
        Base2D(100.0, 200.0, Direction2D.DOWN),
        Base2D(100.0, 1200.0, Direction2D.DOWN),
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(450, 0, 450, 1400, 10, ResourceType.Normal),
        ResourceRandomRectangle(450, 0, 450, 1400, 20, ResourceType.DoublePoints),
        ResourceRandomRectangle(150, 600, 300, 200, 50, ResourceType.ExtremPoint),
        ResourceRandomRectangle(800, 0, 100, 1400, 20, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(150, 600, 300, 200, 10, ResourceType.ExtraMothershipFuel),
    )

    override fun loadLevelColor(): Color {
        return Color(177, 44, 140)
    }

    override fun loadLevelWallPolygons() = emptyList<Polygon>()
}
