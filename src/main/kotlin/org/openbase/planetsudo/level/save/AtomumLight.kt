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
class AtomumLight : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(1000, 0)
        addPoint(2000, 1000)
        addPoint(1000, 2000)
        addPoint(0, 1000)
    }

    override fun loadHomePositions() = listOf(
        Base2D(150.0, 1000.0, Direction2D.DOWN),
        Base2D(1850.0, 1000.0, Direction2D.DOWN),
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(800, 800, 400, 400, 15, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(800, 0, 300, 150, 50, ResourceType.ExtremPoint),
        ResourceRandomRectangle(800, 1850, 300, 150, 50, ResourceType.ExtremPoint),
        ResourceRandomRectangle(900, 1200, 300, 250, 15, ResourceType.ExtraMothershipFuel),
        ResourceRandomRectangle(900, 550, 300, 250, 15, ResourceType.ExtraMothershipFuel),
        ResourceRandomRectangle(500, 850, 250, 300, 5, ResourceType.Normal),
        ResourceRandomRectangle(1250, 850, 250, 300, 5, ResourceType.Normal),
        ResourceRandomRectangle(550, 450, 100, 100, 3, ResourceType.DoublePoints),
        ResourceRandomRectangle(700, 300, 100, 100, 3, ResourceType.DoublePoints),
        ResourceRandomRectangle(400, 600, 100, 100, 3, ResourceType.DoublePoints),
        ResourceRandomRectangle(300, 1300, 100, 100, 3, ResourceType.DoublePoints),
        ResourceRandomRectangle(450, 1450, 100, 100, 3, ResourceType.DoublePoints),
        ResourceRandomRectangle(600, 1600, 100, 100, 3, ResourceType.DoublePoints),
        ResourceRandomRectangle(1300, 300, 100, 100, 3, ResourceType.DoublePoints),
        ResourceRandomRectangle(1450, 450, 100, 100, 3, ResourceType.DoublePoints),
        ResourceRandomRectangle(1600, 600, 100, 100, 3, ResourceType.DoublePoints),
        ResourceRandomRectangle(1600, 1300, 100, 100, 3, ResourceType.DoublePoints),
        ResourceRandomRectangle(1440, 1450, 100, 100, 3, ResourceType.DoublePoints),
        ResourceRandomRectangle(1300, 1600, 100, 100, 3, ResourceType.DoublePoints),
        ResourceRandomRectangle(300, 300, 400, 300, 14, ResourceType.Tonic),
        ResourceRandomRectangle(300, 1300, 400, 400, 14, ResourceType.Tonic),
        ResourceRandomRectangle(1300, 300, 400, 400, 14, ResourceType.Tonic),
        ResourceRandomRectangle(1300, 1300, 400, 400, 14, ResourceType.Tonic),
    )

    override fun loadLevelColor() = Color(169, 249, 4)

    override fun loadLevelWallPolygons() = listOf(
        Polygon().apply {
            addPoint(375, 750)
            addPoint(875, 250)
            addPoint(900, 300)
            addPoint(900, 800)
            addPoint(750, 850)
            addPoint(500, 850)
            addPoint(400, 900)
            addPoint(350, 900)
        },
        Polygon().apply {
            addPoint(1625, 750)
            addPoint(1125, 250)
            addPoint(1100, 300)
            addPoint(1100, 800)
            addPoint(1250, 850)
            addPoint(1500, 850)
            addPoint(1600, 900)
            addPoint(1650, 900)
        },
        Polygon().apply {
            addPoint(375, 1250)
            addPoint(875, 1750)
            addPoint(900, 1700)
            addPoint(900, 1200)
            addPoint(750, 1150)
            addPoint(500, 1150)
            addPoint(400, 1100)
            addPoint(350, 1100)
        },
        Polygon().apply {
            addPoint(1625, 1250)
            addPoint(1125, 1750)
            addPoint(1100, 1700)
            addPoint(1100, 1200)
            addPoint(1250, 1150)
            addPoint(1500, 1150)
            addPoint(1600, 1100)
            addPoint(1650, 1100)
        },
    )
}
