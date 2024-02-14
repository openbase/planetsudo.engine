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
class FrauenWG : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(100, 0)
        addPoint(450, 0)
        addPoint(450, 200)
        addPoint(500, 200)
        addPoint(500, 250)
        addPoint(850, 250)
        addPoint(1150, 550)
        addPoint(1450, 550)
        addPoint(1450, 1100)
        addPoint(1500, 1150)
        addPoint(1500, 850)
        addPoint(1850, 850)
        addPoint(1900, 900)
        addPoint(1900, 300)
        addPoint(1750, 150)
        addPoint(1900, 0)
        addPoint(2050, 150)
        addPoint(2050, 300)
        addPoint(2450, 300)
        addPoint(2450, 1100)
        addPoint(1900, 1100)
        addPoint(1850, 1150)
        addPoint(1950, 1250)
        addPoint(2150, 1250)
        addPoint(2200, 1300)
        addPoint(2350, 1300)
        addPoint(2350, 1650)
        addPoint(2000, 1650)
        addPoint(2000, 1450)
        addPoint(1950, 1450)
        addPoint(1950, 1400)
        addPoint(1500, 1400)
        addPoint(1200, 1100)
        addPoint(900, 1100)
        addPoint(900, 550)
        addPoint(850, 500)
        addPoint(850, 800)
        addPoint(600, 800)
        addPoint(550, 750)
        addPoint(550, 1350)
        addPoint(700, 1500)
        addPoint(550, 1650)
        addPoint(400, 1500)
        addPoint(400, 1350)
        addPoint(0, 1350)
        addPoint(0, 550)
        addPoint(550, 550)
        addPoint(600, 500)
        addPoint(500, 400)
        addPoint(300, 400)
        addPoint(300, 350)
        addPoint(100, 350)
        addPoint(100, 0)
    }

    override fun loadHomePositions() = listOf(
        Base2D(750.0, 700.0, Direction2D.DOWN),
        Base2D(1600.0, 950.0, Direction2D.DOWN),
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(0, 550, 550, 800, 30, ResourceType.Normal),
        ResourceRandomRectangle(1900, 300, 500, 800, 30, ResourceType.Normal),
        ResourceRandomRectangle(900, 550, 550, 550, 15, ResourceType.ExtraMothershipFuel),
        ResourceRandomRectangle(100, 0, 350, 350, 50, ResourceType.DoublePoints),
        ResourceRandomRectangle(2000, 1300, 350, 450, 50, ResourceType.DoublePoints),
        ResourceRandomRectangle(400, 1350, 300, 300, 50, ResourceType.ExtremPoint),
        ResourceRandomRectangle(1750, 0, 300, 300, 50, ResourceType.ExtremPoint),
        ResourceRandomRectangle(900, 550, 550, 550, 30, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(0,0,100,1650,15,ResourceType.Tonic),
        ResourceRandomRectangle(2350,0,100,1650,15,ResourceType.Tonic)
    )

    override fun loadLevelColor() = Color(0, 0, 0)

    override fun loadLevelWallPolygons() = emptyList<Polygon>()
}
