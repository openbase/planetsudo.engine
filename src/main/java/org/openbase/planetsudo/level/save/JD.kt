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
class JD : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(0, 550)
        addPoint(550, 550)
        addPoint(550, 0)
        addPoint(1250, 0)
        addPoint(1250, 550)
        addPoint(1800, 550)
        addPoint(1800, 1250)
        addPoint(1250, 1250)
        addPoint(1250, 1800)
        addPoint(550, 1800)
        addPoint(550, 1250)
        addPoint(0, 1250)
        addPoint(0, 550)
    }

    override fun loadHomePositions() = listOf(
        Base2D(750.0, 750.0, Direction2D.DOWN),
        Base2D(1050.0, 1050.0, Direction2D.DOWN),
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(0, 0, 1800, 1800, 10, ResourceType.Normal),
        ResourceRandomRectangle(0, 0, 1800, 1800, 20, ResourceType.DoublePoints),
        ResourceRandomRectangle(0, 0, 1800, 1800, 10, ResourceType.ExtremPoint),
        ResourceRandomRectangle(0, 0, 1800, 1800, 10, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(0, 0, 1800, 1800, 10, ResourceType.ExtraMothershipFuel),
    )

    override fun loadLevelColor() = Color(150, 150, 150)

    override fun loadLevelWallPolygons() = listOf(
        Polygon().apply {
            addPoint(200, 850)
            addPoint(300, 950)
            addPoint(500, 750)
            addPoint(600, 850)
            addPoint(850, 850)
            addPoint(850, 600)
            addPoint(950, 500)
            addPoint(750, 300)
            addPoint(850, 200)
            addPoint(950, 200)
            addPoint(850, 300)
            addPoint(1050, 500)
            addPoint(950, 600)
            addPoint(950, 850)
            addPoint(1200, 850)
            addPoint(1300, 950)
            addPoint(1400, 850)
            addPoint(1500, 750)
            addPoint(1600, 850)
            addPoint(1600, 950)
            addPoint(1500, 850)
            addPoint(1300, 1050)
            addPoint(1200, 950)
            addPoint(950, 950)
            addPoint(950, 1200)
            addPoint(850, 1300)
            addPoint(1050, 1500)
            addPoint(950, 1600)
            addPoint(850, 1600)
            addPoint(950, 1500)
            addPoint(750, 1300)
            addPoint(850, 1200)
            addPoint(850, 950)
            addPoint(600, 950)
            addPoint(500, 850)
            addPoint(300, 1050)
            addPoint(200, 950)
            addPoint(200, 850)
        }
    )
}
