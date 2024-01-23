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
class Revolution : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(0, 0)
        addPoint(700, 0)
        addPoint(700, 300)
        addPoint(350, 300)
        addPoint(350, 400)
        addPoint(800, 400)
        addPoint(800, 0)
        addPoint(1000, 0)
        addPoint(1000, 600)
        addPoint(900, 650)
        addPoint(1000, 700)
        addPoint(1000, 1000)
        addPoint(300, 1000)
        addPoint(300, 700)
        addPoint(650, 700)
        addPoint(650, 600)
        addPoint(200, 600)
        addPoint(200, 1000)
        addPoint(0, 1000)
        addPoint(0, 400)
        addPoint(100, 350)
        addPoint(0, 300)
        addPoint(0, 0)
    }

    override fun loadHomePositions() = listOf(
        Base2D(450.0, 100.0, Direction2D.DOWN),
        Base2D(550.0, 900.0, Direction2D.DOWN)
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(800, 0, 200, 200, 20, ResourceType.DoublePoints),
        ResourceRandomRectangle(0, 800, 200, 200, 20, ResourceType.DoublePoints),
        ResourceRandomRectangle(350, 450, 300, 100, 20, ResourceType.ExtremPoint),
        ResourceRandomRectangle(350, 450, 300, 100, 10, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(350, 450, 300, 100, 10, ResourceType.ExtraMothershipFuel)
    )

    override fun loadLevelColor() = Color(255, 153, 0)

    override fun loadLevelWallPolygons() = emptyList<Polygon>()
}
