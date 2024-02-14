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
 * @author noxus
 */
class LuckyLoop : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(0, 0)
        addPoint(300, 0)
        addPoint(450, 150)
        addPoint(550, 150)
        addPoint(650, 50)
        addPoint(1000, 50)
        addPoint(1050, 100)
        addPoint(1200, 100)
        addPoint(1300, 0)
        addPoint(1500, 0)
        addPoint(1550, 50)
        addPoint(1750, 50)
        addPoint(1800, 100)
        addPoint(1800, 350)
        addPoint(1700, 450)
        addPoint(1850, 600)
        addPoint(1700, 1000)
        addPoint(1850, 1150)
        addPoint(1850, 1450)
        addPoint(1550, 1450)
        addPoint(1400, 1300)
        addPoint(1300, 1300)
        addPoint(1200, 1400)
        addPoint(850, 1400)
        addPoint(800, 1350)
        addPoint(650, 1350)
        addPoint(550, 1450)
        addPoint(350, 1450)
        addPoint(300, 1400)
        addPoint(100, 1400)
        addPoint(50, 1350)
        addPoint(50, 1100)
        addPoint(150, 1000)
        addPoint(0, 850)
        addPoint(150, 450)
        addPoint(0, 300)
        addPoint(0, 0)
    }

    override fun loadHomePositions() = listOf(
        Base2D(150.0, 150.0, Direction2D.DOWN),
        Base2D(1650.0, 1300.0, Direction2D.DOWN),
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(0, 0, 925, 1450, 10, ResourceType.Normal),
        ResourceRandomRectangle(925, 0, 925, 1450, 10, ResourceType.Normal),
        ResourceRandomRectangle(50, 1100, 300, 300, 10, ResourceType.DoublePoints),
        ResourceRandomRectangle(1500, 50, 300, 300, 10, ResourceType.DoublePoints),
        ResourceRandomRectangle(950, 350, 300, 300, 40, ResourceType.ExtremPoint),
        ResourceRandomRectangle(550, 800, 300, 300, 40, ResourceType.ExtremPoint),
        ResourceRandomRectangle(0, 0, 925, 725, 30, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(0, 725, 925, 725, 30, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(925, 0, 925, 725, 30, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(925, 725, 925, 725, 30, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(950, 350, 300, 300, 20, ResourceType.ExtraMothershipFuel),
        ResourceRandomRectangle(550, 800, 300, 300, 20, ResourceType.ExtraMothershipFuel),
        ResourceRandomRectangle(1650,0,200,100,10,ResourceType.Tonic),
        ResourceRandomRectangle(0,1350,200,100,10,ResourceType.Tonic)
    )

    override fun loadLevelColor(): Color {
        return Color(0, 102, 102)
    }

    override fun loadLevelWallPolygons() = listOf(
        Polygon().apply {
            addPoint(750, 300)
            addPoint(800, 250)
            addPoint(850, 250)
            addPoint(950, 300)
            addPoint(1000, 300)
            addPoint(1050, 350)
            addPoint(950, 450)
            addPoint(950, 550)
            addPoint(1050, 650)
            addPoint(1200, 650)
            addPoint(1300, 550)
            addPoint(1300, 450)
            addPoint(1200, 350)
            addPoint(1300, 250)
            addPoint(1500, 250)
            addPoint(1500, 300)
            addPoint(1400, 400)
            addPoint(1550, 550)
            addPoint(1450, 650)
            addPoint(1400, 650)
            addPoint(1300, 750)
            addPoint(1200, 750)
            addPoint(1150, 800)
            addPoint(1150, 900)
            addPoint(1100, 950)
            addPoint(1100, 1150)
            addPoint(1050, 1200)
            addPoint(1000, 1200)
            addPoint(900, 1150)
            addPoint(850, 1150)
            addPoint(800, 1100)
            addPoint(900, 1000)
            addPoint(900, 900)
            addPoint(800, 800)
            addPoint(650, 800)
            addPoint(550, 900)
            addPoint(550, 1000)
            addPoint(650, 1100)
            addPoint(550, 1200)
            addPoint(350, 1200)
            addPoint(350, 1150)
            addPoint(450, 1050)
            addPoint(300, 900)
            addPoint(400, 800)
            addPoint(450, 800)
            addPoint(550, 700)
            addPoint(650, 700)
            addPoint(700, 650)
            addPoint(700, 550)
            addPoint(750, 500)
        },
    )
}
