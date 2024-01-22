/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.view.level.levelobjects

import org.openbase.jul.exception.InvalidStateException
import org.openbase.jul.visual.swing.engine.draw2d.ResourceDisplayPanel
import org.openbase.jul.visual.swing.engine.draw2d.ResourcePanel
import org.openbase.planetsudo.geometry.Direction2D
import org.openbase.planetsudo.level.levelobjects.AbstractLevelObject
import org.openbase.planetsudo.util.RandomGenerator.getRandom
import org.openbase.planetsudo.view.game.AbstractGameObjectPanel
import org.openbase.planetsudo.view.level.levelobjects.AbstractLevelObjectPanel
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.Polygon
import java.awt.geom.AffineTransform
import java.awt.geom.Dimension2D
import java.util.logging.Level
import java.util.logging.Logger
import javax.swing.Timer

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
abstract class AbstractLevelObjectPanel<R : AbstractLevelObject, PRP : ResourcePanel> :
    AbstractGameObjectPanel<R, PRP> {
    constructor(
        resource: R,
        placementPolygon: Polygon,
        imageURI: String?,
        parentResourcePanel: PRP,
        drawLayer: DrawLayer,
    ) : super(resource, placementPolygon, ObjectType.Static, imageURI, parentResourcePanel, drawLayer)

    constructor(
        resource: R,
        placementPolygon: Polygon,
        imageURI: String,
        parentPanel: ResourceDisplayPanel<ResourcePanel>,
    ) : super(resource, placementPolygon, ObjectType.Dynamic, imageURI, parentPanel)

    protected fun paintShape(g2: Graphics2D) {
        when (resource.shape) {
            AbstractLevelObject.ObjectShape.Oval -> g2.fillOval(
                resource.position.getX() as Int - (resource.width as Int / 2),
                resource.position.getY() as Int - (resource.height as Int / 2),
                resource.width as Int,
                resource.height as Int
            )

            AbstractLevelObject.ObjectShape.Rec -> g2.fillRect(
                resource.position.getX() as Int - (resource.width as Int / 2),
                resource.position.getY() as Int - (resource.height as Int / 2),
                resource.width as Int,
                resource.height as Int
            )

            else -> {
            }
        }
    }

    protected fun paintImageRotated(direction: Direction2D, g2: Graphics2D) {
        var transformation = skaleImageToBoundsTransformation
        transformation =
            rotateTransformation(direction, image!!.width.toDouble(), image!!.height.toDouble(), transformation)
        g2.drawImage(image, transformation, parentPanel)
    }

    private val dimension2D: Dimension2D = Dimension()

    fun rotateTransformation(
        direction: Direction2D,
        width: Double,
        height: Double,
        affineTransform: AffineTransform,
    ): AffineTransform {
        dimension2D.setSize(width, height)
        return getRotationTransformation(direction, dimension2D, affineTransform)
    }

    fun getRotationTransformation(
        direction: Direction2D,
        dimension: Dimension2D,
        affineTransform: AffineTransform,
    ): AffineTransform {
        affineTransform.rotate(
            Math.toRadians(direction.angle + 90.0),
            dimension.width / 2.0,
            dimension.height / 2.0
        )
        return affineTransform
    }

    fun getBoundsTransformation(direction: Direction2D): AffineTransform {
        return rotateTransformation(direction, boundingBox.width, boundingBox.height, boundsTransformation)
    }

    val boundsTransformation: AffineTransform
        get() = AffineTransform(
            1.0, 0.0,
            0.0, 1.0,
            boundingBox.x, boundingBox.y
        )
    override val skaleImageToBoundsTransformation: AffineTransform
        get() {
            assert(boundingBox != null)
            assert(image != null)
            return AffineTransform(
                boundingBox.width / image!!.width, 0.0,
                0.0, boundingBox.height / image!!.height,
                boundingBox.x, boundingBox.y
            )
        }

    override fun toString(): String {
        return javaClass.simpleName + "[" + resource!!.id + "]"
    }

    private var xa1 = resource.position.getX() as Int
    private var xa2 = resource.position.getX() as Int
    private var xa3 = resource.position.getX() as Int
    private var ya1 = resource.position.getY() as Int
    private var ya2 = resource.position.getY() as Int
    private var ya3 = resource.position.getY() as Int
    private var xb1 = resource.position.getX() as Int
    private var xb2 = resource.position.getX() as Int
    private var xb3 = resource.position.getX() as Int
    private var yb1 = resource.position.getY() as Int
    private var yb2 = resource.position.getY() as Int
    private var yb3 = resource.position.getY() as Int

    protected fun paintExplosion(g22: Graphics2D) {
        try {
            xa3 = xa2
            ya3 = ya2
            xa2 = xa1
            ya2 = ya1
            xa1 = xb3
            ya1 = yb3
            xb3 = xb2
            yb3 = yb2
            xb2 = xb1
            yb2 = yb1
            xb1 = getRandom(boundingBox.minX.toInt(), boundingBox.maxX.toInt())
            yb1 = getRandom(boundingBox.minY.toInt(), boundingBox.maxY.toInt())

            g22.color = Color.YELLOW
            g22.fillOval(xa3 - 9, ya3 - 9, 18, 18)
            g22.color = Color.ORANGE
            g22.fillOval(xa1 - 6, ya1 - 6, 12, 12)
            g22.fillOval(xa2 - 6, ya2 - 6, 12, 12)
            g22.fillOval(xa3 - 6, ya3 - 6, 12, 12)
            g22.color = Color.RED
            //g22.drawOval(xa3-3, ya3-3, 6, 6);
            g22.fillOval(xa2 - 3, ya2 - 3, 6, 6)
            g22.fillOval(xa1 - 3, ya1 - 3, 6, 6)

            g22.color = Color.YELLOW
            g22.fillOval(xb3 - 9, yb3 - 9, 18, 18)
            g22.color = Color.ORANGE
            g22.fillOval(xb1 - 6, yb1 - 6, 12, 12)
            g22.fillOval(xb2 - 6, yb2 - 6, 12, 12)
            g22.fillOval(xb3 - 6, yb3 - 6, 12, 12)
            g22.color = Color.RED
            //g22.drawOval(xb3-3, yb3-3, 6, 6);
            g22.fillOval(xb2 - 3, yb2 - 3, 6, 6)
            g22.fillOval(xb1 - 3, yb1 - 3, 6, 6)
        } catch (ex: InvalidStateException) {
            Logger.getLogger(AbstractLevelObjectPanel::class.java.name).log(Level.SEVERE, null, ex)
        }
    }

    companion object {
        @JvmStatic
        protected var animationFlag: Boolean = false

        init {
            Timer(200) { animationFlag = !animationFlag }
                .start()
        }
    }
}
