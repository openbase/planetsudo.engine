package org.openbase.planetsudo.level.levelobjects

import org.openbase.jul.iface.Identifiable
import org.openbase.jul.visual.swing.engine.draw2d.AbstractResourcePanel
import org.openbase.planetsudo.game.AbstractGameObject
import org.openbase.planetsudo.geometry.Point2D
import org.openbase.planetsudo.level.AbstractLevel
import org.openbase.planetsudo.level.LevelView
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Polygon
import java.awt.geom.Rectangle2D
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
abstract class AbstractLevelObject @JvmOverloads constructor(
    private val id: Int,
    private val name: String = "Level",
    objectType: AbstractResourcePanel.ObjectType = AbstractResourcePanel.ObjectType.Static,
    @JvmField val level: AbstractLevel,
    @JvmField var position: Point2D = Point2D(),
    @JvmField var width: Double = 100.0,
    @JvmField var height: Double = 100.0,
    @JvmField val shape: ObjectShape? = null
) : AbstractGameObject, Identifiable<Int> {
    enum class ObjectShape {
        Oval, Rec
    }

    var levelView: LevelView? = null
        protected set

    protected val changes: PropertyChangeSupport = PropertyChangeSupport(this)

    init {
        if (this !is Resource) {
            this.levelView = LevelView(this)
        }
    }

    val bounds: Rectangle2D.Double
        get() = Rectangle2D.Double(
            position.x.toInt() - (width / 2),
            position.y.toInt() - (height / 2),
            width,
            height
        )

    override fun getId(): Int = id
    override fun getName(): String = name
    protected abstract fun reset()

    val polygon: Polygon
        get() {
            val polygon = Polygon()
            polygon.addPoint((position.x - width / 2).toInt(), (position.y - height / 2).toInt())
            polygon.addPoint((position.x + width / 2).toInt(), (position.y - height / 2).toInt())
            polygon.addPoint((position.x - width / 2).toInt(), (position.y + height / 2).toInt())
            polygon.addPoint((position.x + width / 2).toInt(), (position.y + height / 2).toInt())
            return polygon
        }

    fun addPropertyChangeListener(l: PropertyChangeListener) {
        changes.addPropertyChangeListener(l)
        logger.debug("Add " + l.javaClass + " as new PropertyChangeListener.")
    }

    fun removePropertyChangeListener(l: PropertyChangeListener) {
        changes.removePropertyChangeListener(l)
        logger.debug("Remove PropertyChangeListener " + l.javaClass + ".")
    }

    override fun toString(): String {
        return name
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AbstractLevelObject::class.java)
        const val OBJECT_TYPE_UPDATE: String = "ObjectTypeUpdate"
    }
}
