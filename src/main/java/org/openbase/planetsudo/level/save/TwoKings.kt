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
class TwoKings : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(0, 0)
        addPoint(375, 300)
        addPoint(750, 0)
        addPoint(1125, 300)
        addPoint(1500, 0)
        addPoint(1500, 1500)
        addPoint(1125, 1200)
        addPoint(750, 1500)
        addPoint(375, 1200)
        addPoint(0, 1500)
    }

    override fun loadHomePositions() = listOf(
        Base2D(750.0, 150.0, Direction2D.DOWN),
        Base2D(750.0, 1350.0, Direction2D.DOWN),
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(350, 650, 100, 200, 10, ResourceType.ExtremPoint),
        ResourceRandomRectangle(1050, 650, 100, 200, 10, ResourceType.ExtremPoint),
        ResourceRandomRectangle(0, 0, 1500, 650, 5, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(0, 850, 1500, 650, 5, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(0, 0, 1500, 650, 5, ResourceType.ExtraMothershipFuel),
        ResourceRandomRectangle(0, 850, 1500, 650, 5, ResourceType.ExtraMothershipFuel),
        ResourceRandomRectangle(0, 0, 1500, 650, 10, ResourceType.Normal),
        ResourceRandomRectangle(0, 850, 1500, 650, 10, ResourceType.Normal),
        ResourceRandomRectangle(0, 0, 1500, 650, 5, ResourceType.DoublePoints),
        ResourceRandomRectangle(0, 850, 1500, 650, 5, ResourceType.DoublePoints),
    )

    override fun loadLevelColor() = Color(37, 67, 162)

    override fun loadLevelWallPolygons() = listOf(
        Polygon().apply {
            addPoint(0, 650)
            addPoint(300, 650)
            addPoint(300, 850)
            addPoint(0, 850)
        }, Polygon().apply {
            addPoint(500, 650)
            addPoint(1000, 650)
            addPoint(1000, 850)
            addPoint(500, 850)
        }, Polygon().apply {
            addPoint(1200, 650)
            addPoint(1500, 650)
            addPoint(1500, 850)
            addPoint(1200, 850)
        }
    )
}
