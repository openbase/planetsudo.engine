/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.level

import org.openbase.planetsudo.geometry.Point2D
import org.openbase.planetsudo.level.levelobjects.Resource

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
interface ResourcePlacement {
    fun getResources(level: AbstractLevel): ArrayList<Resource>
    val resourceCount: Int
    fun calcRandomLevelPosition(level: AbstractLevel): Point2D
    fun translateIntoBase(base: Point2D)
}
