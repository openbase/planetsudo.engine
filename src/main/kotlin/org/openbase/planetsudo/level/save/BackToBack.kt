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
class BackToBack : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(0, 0)
        addPoint(1000, 0)
        addPoint(1000, 1000)
        addPoint(0, 1000)
    }

    override fun loadHomePositions() = listOf(
        Base2D(390.0, 450.0, Direction2D.LEFT),
        Base2D(610.0, 450.0, Direction2D.RIGHT),
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(0, 0, 1000, 1000, 10, ResourceType.Normal),
        ResourceRandomRectangle(0, 0, 1000, 1000, 5, ResourceType.DoublePoints),
        ResourceRandomRectangle(400, 800, 200, 200, 10, ResourceType.ExtremPoint),
        ResourceRandomRectangle(0, 0, 1000, 1000, 10, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(400, 0, 200, 200, 10, ResourceType.ExtraMothershipFuel),
        ResourceRandomRectangle(0,0,50,50,5,ResourceType.Tonic),
        ResourceRandomRectangle(0,950,50,50,5,ResourceType.Tonic),
        ResourceRandomRectangle(950,0,50,50,5,ResourceType.Tonic),
        ResourceRandomRectangle(950,950,50,50,5,ResourceType.Tonic)
    )

    override fun loadLevelColor() = Color(244, 164, 96)

    override fun loadLevelWallPolygons() = listOf(
        Polygon().apply {
            addPoint(450, 350)
            addPoint(450, 550)
            addPoint(550, 550)
            addPoint(550, 350)
        },
    )
}
