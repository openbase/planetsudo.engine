/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.level

import org.openbase.planetsudo.geometry.Point2D
import org.openbase.planetsudo.level.levelobjects.Resource
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType
import java.awt.geom.Rectangle2D

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class ResourceGausOval(x: Int, y: Int, width: Int, height: Int, override val resourceCount: Int) :
    Rectangle2D.Double(
        x.toDouble(),
        y.toDouble(),
        width.toDouble(),
        height.toDouble(),
    ),
    ResourcePlacement {
    override fun getResources(level: AbstractLevel): ArrayList<Resource> {
        val resources = ArrayList<Resource>()
        for (i in 0 until resourceCount) {
            resources.add(
                Resource(
                    level.generateNewResourceID(),
                    ResourceType.Normal,
                    level,
                    Point2D(
                        centerX,
                        centerY,
                    ),
                ),
            )
        }
        return resources
    }

    override fun calcRandomLevelPosition(level: AbstractLevel): Point2D {
        throw UnsupportedOperationException("Not supported yet.")
    }

    override fun translateIntoBase(base: Point2D) {
        x -= base.x
        y -= base.y
    }
}
