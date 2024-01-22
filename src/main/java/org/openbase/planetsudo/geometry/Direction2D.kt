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
    @JvmField var angle: Int = 0
) {
    val vector: Point2D = Point2D()
    private var alpha = 0

    private fun calculateDirection() {
        val radianAlpha = Math.toRadians(angle.toDouble())
        vector.setLocation(cos(radianAlpha), sin(radianAlpha))
        // Logger.info(this, "CalcDirection out of alpha "+alpha+": "+direction);
    }

    fun invert() {
        angle = angle + 180
    }

    fun turnTo(source: Point2D, destination: Point2D) {
        angle = Math.toDegrees(atan2(destination.xy[1] - source.xy[1], destination.xy[0] - source.xy[0])).toInt()
        // Logger.info(this, "Base:"+source+" Dest:"+destination+" Angle:"+(int) Math.toDegrees(Math.atan2(destination.xy[1] - source.xy[1], destination.xy[0] - source.xy[0])));
    }

    fun translate(point: Point2D, lenght: Int): Point2D {
        point.translate(this, lenght)
        return point
    }

    companion object {
        const val UP: Int = 270
        const val DOWN: Int = 90
        const val LEFT: Int = 180
        const val RIGHT: Int = 0
    }
}
