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
class DasBoot : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(0, 750)
        addPoint(750, 750)
        addPoint(750, 375)
        addPoint(1000, 375)
        addPoint(1000, 0)
        addPoint(1650, 0)
        addPoint(1650, 225)
        addPoint(1150, 225)
        addPoint(1150, 375)
        addPoint(1425, 375)
        addPoint(1425, 750)
        addPoint(2175, 750)
        addPoint(1725, 1500)
        addPoint(450, 1500)
    }

    override fun loadHomePositions() = listOf(
        Base2D(600.0, 1350.0, Direction2D.DOWN),
        Base2D(1575.0, 1350.0, Direction2D.DOWN),
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(1125, 0, 525, 225, 40, ResourceType.ExtremPoint),
        ResourceRandomRectangle(1050, 0, 75, 525, 30, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(825, 750, 600, 450, 15, ResourceType.ExtraMothershipFuel),
        ResourceRandomRectangle(350, 750, 375, 300, 10, ResourceType.Normal),
        ResourceRandomRectangle(1500, 750, 375, 300, 10, ResourceType.Normal),
        ResourceRandomRectangle(750, 375, 675, 375, 15, ResourceType.DoublePoints),
        ResourceRandomRectangle(750,375,675,175,15,ResourceType.Tonic)
    )

    override fun loadLevelColor() = Color(0, 191, 255)

    override fun loadLevelWallPolygons() = listOf(
        Polygon().apply {
            addPoint(675, 900)
            addPoint(825, 900)
            addPoint(825, 1050)
            addPoint(675, 1050)
        },
        Polygon().apply {
            addPoint(1350, 900)
            addPoint(1500, 900)
            addPoint(1500, 1050)
            addPoint(1350, 1050)
        },
        Polygon().apply {
            addPoint(1012, 1200)
            addPoint(1163, 1200)
            addPoint(1163, 1500)
            addPoint(1012, 1500)
        },
    )
}
