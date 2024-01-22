/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.level.save

import org.openbase.planetsudo.geometry.Base2D
import org.openbase.planetsudo.geometry.Direction2D
import org.openbase.planetsudo.level.AbstractLevel
import org.openbase.planetsudo.level.ResourcePlacement
import java.awt.Color
import java.awt.Polygon

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class SimpleFight : AbstractLevel() {
    override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(0, 0)
        addPoint(1000, 0)
        addPoint(1000, 1000)
        addPoint(0, 1000)
    }

    override fun loadHomePositions() = listOf(
        Base2D(200.0, 500.0, Direction2D.DOWN),
        Base2D(800.0, 500.0, Direction2D.DOWN)
    )

    override fun loadResourcePlacement() = listOf<ResourcePlacement>()

    override fun loadLevelColor() = Color(255, 153, 0)

    override fun loadLevelWallPolygons() = emptyList<Polygon>()
}
