/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.geometry

import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.Point
import java.awt.geom.AffineTransform
import kotlin.math.max
import kotlin.math.min

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */

class BoundingBox {
    var maxX: Int
        private set
    var maxY: Int
        private set
    var minX: Int
        private set
    var minY: Int
        private set

    init {
        this.maxX = Int.MIN_VALUE
        this.maxY = Int.MIN_VALUE
        this.minX = Int.MAX_VALUE
        this.minY = Int.MAX_VALUE
    }

    val centerX: Int
        get() = minX + ((maxX - minX) / 2)

    val centerY: Int
        get() = minY + ((maxY - minY) / 2)

    val center: Point
        get() = Point(centerX, centerY)

    val height: Int
        get() = (if (checkYValidation()) maxY - minY else 0)
    val width: Int
        get() = (if (checkXValidation()) maxX - minX else 0)

    fun insertX(x: Int) {
        minX = min(minX.toDouble(), x.toDouble()).toInt()
        maxX = max(maxX.toDouble(), x.toDouble()).toInt()
    }

    fun insertY(y: Int) {
        minY = min(minY.toDouble(), y.toDouble()).toInt()
        maxY = max(maxY.toDouble(), y.toDouble()).toInt()
    }

    fun insertBoundingBox(boundingBox: BoundingBox) {
        this.maxX = max(maxX.toDouble(), boundingBox.maxX.toDouble()).toInt()
        this.maxY = max(maxY.toDouble(), boundingBox.maxY.toDouble()).toInt()
        this.minX = min(minX.toDouble(), boundingBox.minX.toDouble()).toInt()
        this.minY = min(minY.toDouble(), boundingBox.minY.toDouble()).toInt()
    }

    fun checkXValidation(): Boolean {
        return minX < maxX
    }

    fun checkYValidation(): Boolean {
        return minY < maxY
    }

    fun paint(g: Graphics2D, trans: AffineTransform?) {
        if (!(checkXValidation() || checkYValidation())) {
            return
        }

        val g2 = g.create() as Graphics2D
        g2.transform(trans)
        g2.color = Color.MAGENTA
        g2.drawRect(minX, minY, maxX - minX, maxY - minY)
        g2.color = Color.ORANGE
        g2.drawLine(centerX, centerY, minX, minY)
        g2.drawLine(centerX, centerY, maxX, maxY)
        g2.drawLine(centerX, centerY, maxX, minY)
        g2.drawLine(centerX, centerY, minX, maxY)
        g2.dispose()
    }

    val dimension: Dimension
        get() = Dimension(width, height)
}
