/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.geometry

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class Point2D(x: kotlin.Double = 0.0, y: kotlin.Double = 0.0) : java.awt.geom.Point2D(), Cloneable {

    var xy: DoubleArray = DoubleArray(2)

    var modified: Boolean
        private set
        get() = field.also { field = false }

    init {
        xy[0] = x
        xy[1] = y
        modified = true
    }

    constructor(point: Point2D) : this(point.xy[0], point.xy[1])

    override fun getX(): kotlin.Double = xy[0]

    override fun getY(): kotlin.Double = xy[1]

    fun setX(x: kotlin.Double) {
        xy[0] = x
        modified = true
    }

    fun setY(y: kotlin.Double) {
        xy[1] = y
        modified = true
    }

    override fun setLocation(x: kotlin.Double, y: kotlin.Double) {
        xy[0] = x
        xy[1] = y
        modified = true
    }

    override fun clone(): Point2D {
        return Point2D(xy[0], xy[1])
    }

    fun translateIntoBase(base: Point2D) {
        xy[0] -= base.x
        xy[1] -= base.y
        modified = true
    }

    fun translate(direction: Direction2D, lenght: Int) {
        xy[0] += direction.vector.x * lenght
        xy[1] += direction.vector.y * lenght
        modified = true
    }

    override fun hashCode(): Int {
        return HashCodeBuilder(17, 31)
            .append(xy[0])
            .append(xy[1])
            .toHashCode()
    }

    override fun equals(obj: Any?): Boolean {
        if (obj == null) {
            return false
        }
        if (obj === this) {
            return true
        }
        if (obj.javaClass != javaClass) {
            return false
        }

        val point = obj as Point2D
        // if deriving: appendSuper(super.equals(obj)).
        return EqualsBuilder()
            .append(xy[0], point.xy[0])
            .append(xy[1], point.xy[1])
            .isEquals
    }

    override fun toString(): String {
        return javaClass.simpleName + "[" + xy[0] + "|" + xy[1] + "]"
    }
}
