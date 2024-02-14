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
class MarioWorld : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(0, 0)
        addPoint(500, 400)
        addPoint(1000, 0)
        addPoint(1000, 1000)
        addPoint(800, 1000)
        addPoint(800, 700)
        addPoint(500, 1000)
        addPoint(200, 700)
        addPoint(200, 1000)
        addPoint(0, 1000)
    }

    override fun loadHomePositions() = listOf(
        Base2D(100.0, 950.0, Direction2D.DOWN),
        Base2D(900.0, 950.0, Direction2D.DOWN),
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(0, 0, 200, 500, 10, ResourceType.Normal),
        ResourceRandomRectangle(250, 300, 550, 550, 15, ResourceType.DoublePoints),
        ResourceRandomRectangle(200, 900, 400, 100, 15, ResourceType.ExtremPoint),
        ResourceRandomRectangle(800, 0, 200, 500, 10, ResourceType.Normal),
        ResourceRandomRectangle(400,0,200,450,10,ResourceType.Tonic)
    )

    override fun loadLevelColor() = Color(174, 94, 94)

    override fun loadLevelWallPolygons() = emptyList<Polygon>()
}
