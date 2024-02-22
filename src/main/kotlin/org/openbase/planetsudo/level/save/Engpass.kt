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
class Engpass : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(0, 0)
        addPoint(500, 400)
        addPoint(1000, 0)
        addPoint(1000, 1000)
        addPoint(500, 600)
        addPoint(0, 1000)
    }

    override fun loadHomePositions() = listOf(
        Base2D(80.0, 500.0, Direction2D.LEFT),
        Base2D(920.0, 500.0, Direction2D.RIGHT),
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(0, 0, 1000, 1000, 5, ResourceType.Normal),
        ResourceRandomRectangle(0, 0, 450, 450, 5, ResourceType.DoublePoints),
        ResourceRandomRectangle(550, 550, 1000, 1000, 5, ResourceType.DoublePoints),
        ResourceRandomRectangle(495, 300, 5, 400, 20, ResourceType.ExtremPoint),
        ResourceRandomRectangle(0,0,100,100,5,ResourceType.Tonic),
        ResourceRandomRectangle(0,900,100,100,5,ResourceType.Tonic),
        ResourceRandomRectangle(900,0,100,100,5,ResourceType.Tonic),
        ResourceRandomRectangle(900,900,100,100,5,ResourceType.Tonic)
    )

    override fun loadLevelColor() = Color(67, 167, 197)

    override fun loadLevelWallPolygons() = emptyList<Polygon>()
}
