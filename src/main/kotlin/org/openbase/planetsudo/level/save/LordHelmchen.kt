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
class LordHelmchen : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(300, 0)
        addPoint(700, 0)
        addPoint(850, 100)
        addPoint(1000, 700)
        addPoint(0, 700)
        addPoint(150, 100)
    }

    override fun loadHomePositions() = listOf(
        Base2D(350.0, 500.0, Direction2D.LEFT),
        Base2D(650.0, 500.0, Direction2D.RIGHT),
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(0, 0, 500, 700, 3, ResourceType.Normal),
        ResourceRandomRectangle(500,0,500,700,3,ResourceType.Normal),
        ResourceRandomRectangle(0, 0, 1000, 250, 6, ResourceType.DoublePoints),
        ResourceRandomRectangle(400, 0, 200, 100, 4, ResourceType.ExtremPoint),
        ResourceRandomRectangle(100, 0, 800, 250, 6, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(300, 0, 400, 250, 6, ResourceType.ExtraMothershipFuel),
        ResourceRandomRectangle(0, 0, 1000, 50, 15, ResourceType.Tonic),
    )

    override fun loadLevelColor() = Color(255, 153, 0)

    override fun loadLevelWallPolygons() = listOf(
        Polygon().apply {
            addPoint(350, 200)
            addPoint(650, 200)
            addPoint(650, 300)
            addPoint(550, 300)
            addPoint(550, 700)
            addPoint(450, 700)
            addPoint(450, 300)
            addPoint(350, 300)
        },
    )
}
