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
class QuadratischPraktischBoese : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(0, 0)
        addPoint(500, 0)
        addPoint(500, 300)
        addPoint(800, 0)
        addPoint(1100, 300)
        addPoint(1100, 0)
        addPoint(1600, 0)
        addPoint(1600, 500)
        addPoint(1300, 500)
        addPoint(1600, 800)
        addPoint(1300, 1100)
        addPoint(1600, 1100)
        addPoint(1600, 1600)
        addPoint(1100, 1600)
        addPoint(1100, 1300)
        addPoint(800, 1600)
        addPoint(500, 1300)
        addPoint(500, 1600)
        addPoint(0, 1600)
        addPoint(0, 1100)
        addPoint(300, 1100)
        addPoint(0, 800)
        addPoint(300, 500)
        addPoint(0, 500)
    }

    override fun loadHomePositions() = listOf(
        Base2D(200.0, 200.0, Direction2D.DOWN),
        Base2D(1400.0, 1400.0, Direction2D.DOWN),
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(500, 300, 600, 200, 10, ResourceType.Normal),
        ResourceRandomRectangle(500, 1100, 600, 200, 10, ResourceType.Normal),
        ResourceRandomRectangle(500, 0, 600, 200, 35, ResourceType.DoublePoints),
        ResourceRandomRectangle(1350, 0, 250, 250, 50, ResourceType.ExtremPoint),
        ResourceRandomRectangle(0, 1350, 250, 250, 50, ResourceType.ExtremPoint),
        ResourceRandomRectangle(500, 500, 600, 600, 10, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(500, 500, 600, 600, 10, ResourceType.ExtraMothershipFuel),
        ResourceRandomRectangle(500, 1400, 600, 200, 35, ResourceType.DoublePoints),
        ResourceRandomRectangle(0, 700, 100, 200, 12, ResourceType.Tonic),
        ResourceRandomRectangle(1000, 700, 100, 200, 12, ResourceType.Tonic)
    )

    override fun loadLevelColor() = Color(93, 0, 0)

    override fun loadLevelWallPolygons() = listOf(
        Polygon().apply {
            addPoint(500, 1000)
            addPoint(1000, 500)
            addPoint(1100, 600)
            addPoint(600, 1100)
        },
    )
}
