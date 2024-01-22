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
class Offensive : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(350, 600)
        addPoint(650, 600)
        addPoint(650, 850)
        addPoint(750, 850)
        addPoint(750, 350)
        addPoint(900, 350)
        addPoint(900, 600)
        addPoint(1150, 600)
        addPoint(1150, 300)
        addPoint(1450, 300)
        addPoint(1450, 600)
        addPoint(1700, 600)
        addPoint(1700, 350)
        addPoint(1850, 350)
        addPoint(1850, 850)
        addPoint(1950, 850)
        addPoint(1950, 600)
        addPoint(2250, 600)
        addPoint(2250, 1200)
        addPoint(1850, 1200)
        addPoint(1850, 1450)
        addPoint(1700, 1450)
        addPoint(1700, 1200)
        addPoint(1450, 1200)
        addPoint(1450, 1500)
        addPoint(1150, 1500)
        addPoint(1150, 1200)
        addPoint(900, 1200)
        addPoint(900, 1450)
        addPoint(750, 1450)
        addPoint(750, 1200)
        addPoint(350, 1200)
    }

    override fun loadHomePositions() = listOf(
        Base2D(500.0, 700.0, Direction2D.DOWN),
        Base2D(2100.0, 700.0, Direction2D.DOWN),
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(1700, 1200, 150, 150, 5, ResourceType.Normal),
        ResourceRandomRectangle(750, 1200, 150, 150, 5, ResourceType.Normal),
        ResourceRandomRectangle(750, 450, 150, 150, 5, ResourceType.Normal),
        ResourceRandomRectangle(1700, 450, 150, 150, 5, ResourceType.Normal),
        ResourceRandomRectangle(750, 350, 150, 100, 3, ResourceType.DoublePoints),
        ResourceRandomRectangle(1700, 350, 150, 100, 3, ResourceType.DoublePoints),
        ResourceRandomRectangle(750, 1350, 150, 100, 3, ResourceType.DoublePoints),
        ResourceRandomRectangle(1700, 1350, 150, 100, 3, ResourceType.DoublePoints),
        ResourceRandomRectangle(1150, 300, 300, 150, 8, ResourceType.ExtremPoint),
        ResourceRandomRectangle(1150, 1350, 300, 150, 8, ResourceType.ExtremPoint),
        ResourceRandomRectangle(1150, 800, 300, 200, 3, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(1150, 800, 300, 200, 2, ResourceType.ExtraMothershipFuel),
    )

    override fun loadLevelColor() = Color(255, 153, 0)

    override fun loadLevelWallPolygons() = emptyList<Polygon>()
}
