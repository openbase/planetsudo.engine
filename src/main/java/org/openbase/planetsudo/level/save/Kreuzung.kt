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
class Kreuzung : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(0, 0)
        addPoint(1000, 0)
        addPoint(1000, 1000)
        addPoint(0, 1000)
    }

    override fun loadHomePositions() = listOf(
        Base2D(500.0, 100.0, Direction2D.UP),
        Base2D(500.0, 900.0, Direction2D.UP)
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(0, 0, 1000, 1000, 10, ResourceType.Normal),
        ResourceRandomRectangle(375, 350, 300, 10, 8, ResourceType.DoublePoints),
        ResourceRandomRectangle(375, 650, 300, 10, 8, ResourceType.DoublePoints),
        ResourceRandomRectangle(450, 450, 100, 100, 8, ResourceType.ExtremPoint),
        ResourceRandomRectangle(400, 400, 200, 200, 0, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(400, 400, 200, 200, 0, ResourceType.ExtraMothershipFuel)
    )

    override fun loadLevelColor(): Color {
        return Color(67, 167, 197)
    }

    override fun loadLevelWallPolygons() = listOf(
        Polygon().apply {
            addPoint(0, 350)
            addPoint(350, 350)
            addPoint(350, 0)
            addPoint(0, 0)
        },
        Polygon().apply {
            addPoint(350, 1000)
            addPoint(350, 650)
            addPoint(0, 650)
            addPoint(0, 1000)
        },
        Polygon().apply {
            addPoint(650, 0)
            addPoint(1000, 0)
            addPoint(1000, 350)
            addPoint(650, 350)
        },
        Polygon().apply {
            addPoint(650, 1000)
            addPoint(650, 650)
            addPoint(1000, 650)
            addPoint(1000, 1000)
        }
    )
}
