/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.geometry

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class Base2D {
    var direction: Direction2D
        private set
    var point: Point2D
        private set

    constructor(x: Double, y: Double, alpha: Int) {
        this.direction = Direction2D(alpha)
        this.point = Point2D(x, y)
    }

    constructor(point: Point2D, direction: Direction2D) {
        this.direction = direction
        this.point = point
    }

    fun translateIntoBase(base: Point2D) {
        point.translateIntoBase(base)
    }
}
