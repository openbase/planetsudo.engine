package org.openbase.planetsudo.geometry

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 * Rebuild because all functions are already supported from the Retange2D by the default swing framework
 */
class Direction2D(
    // Logger.info(this, "setAngle: "+alpha+" => "+this.alpha);
    angle: Int = 0
) {
    val vector: Point2D = Point2D()

    private var alpha: Int = angle
        set(value) {
            field = value
            calculateDirection()
        }

    var angle: Int
        get() = alpha
        set(angle) {
            alpha = angle
        }

    init {
        alpha = angle
    }

    private fun calculateDirection() {
        val radianAlpha = Math.toRadians(alpha.toDouble())
        vector.setLocation(cos(radianAlpha), sin(radianAlpha))
        // Logger.info(this, "CalcDirection out of alpha "+alpha+": "+direction);
    }

    fun invert() {
        alpha = alpha + 180
    }

    fun turnTo(source: Point2D, destination: Point2D) {
        alpha = Math.toDegrees(atan2(destination.xy[1] - source.xy[1], destination.xy[0] - source.xy[0])).toInt()
        // Logger.info(this, "Base:"+source+" Dest:"+destination+" Angle:"+(int) Math.toDegrees(Math.atan2(destination.xy[1] - source.xy[1], destination.xy[0] - source.xy[0])));
    }

    fun translate(point: Point2D, length: Int): Point2D {
        point.translate(this, length)
        return point
    }

    companion object {
        const val UP: Int = 270
        const val DOWN: Int = 90
        const val LEFT: Int = 180
        const val RIGHT: Int = 0
    }
}
