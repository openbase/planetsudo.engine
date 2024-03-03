/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.level.levelobjects

import org.openbase.jul.exception.InvalidStateException
import org.openbase.jul.visual.swing.engine.draw2d.AbstractResourcePanel
import org.openbase.planetsudo.game.GameSound
import org.openbase.planetsudo.game.Team
import org.openbase.planetsudo.geometry.Point2D
import org.openbase.planetsudo.level.AbstractLevel
import org.openbase.planetsudo.level.ResourcePlacement
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class Resource(id: Int, @JvmField val type: ResourceType, level: AbstractLevel, position: Point2D) :
    AbstractLevelObject(
        id,
        Resource::class.java.simpleName + "[" + id + "]",
        AbstractResourcePanel.ObjectType.Dynamic,
        level,
        position,
        RESOURCE_SIZE.toDouble(),
        RESOURCE_SIZE.toDouble(),
        ObjectShape.Rec,
    ) {
    enum class ResourceType {
        Unknown, Normal, DoublePoints, ExtremPoint, ExtraAgentFuel, ExtraMothershipFuel, Mine, Tonic
    }

    var owner: Agent? = null
        private set
    private val conquerors: MutableList<String?> = ArrayList()
    private val RESOURCE_LOCK = Any()
    var isUsed: Boolean = false
        private set
    var placement: ResourcePlacement? = null
        private set

    // Constructor just for mine
    constructor(id: Int, level: AbstractLevel, placer: Agent) : this(
        id,
        ResourceType.Mine,
        level,
        Point2D(placer.position),
    ) {
        this.placedBy = placer.team
    }

    constructor(id: Int, type: ResourceType, level: AbstractLevel, placement: ResourcePlacement) : this(
        id,
        type,
        level,
        placement.calcRandomLevelPosition(level),
    ) {
        this.placement = placement
    }

    public override fun reset() {
    }

    val isOwned: Boolean
        get() = owner != null

    fun setBusy(team: Team?): Boolean {
        if (isOwned) {
            return false
        }
        synchronized(RESOURCE_LOCK) {
            if (conquerors.contains(team!!.name)) {
                return false
            }
            conquerors.add(team.name)
            return true
        }
    }

    fun isSaveFor(agent: Agent): Boolean {
        return wasPlacedByTeam() != agent.team
    }

    @Throws(InvalidStateException::class)
    fun capture(agent: Agent): Boolean {
        synchronized(RESOURCE_LOCK) {
            if (owner != null) {
                return false
            }
            owner = agent
            conquerors.clear()
        }
        position = agent.position
        when (type) {
            ResourceType.ExtraAgentFuel -> {
                use(agent)
                return false
            }

            ResourceType.Mine -> {
                use(agent)
                return false
            }

            ResourceType.Tonic -> {
                use(agent)
                return false
            }

            else -> return true
        }
    }

    val capturingActionPoints: Int
        get() {
            when (type) {
                ResourceType.Normal -> return 200
                ResourceType.DoublePoints -> return 400
                ResourceType.ExtremPoint -> return 700
                ResourceType.Tonic -> return 1000
                ResourceType.ExtraAgentFuel -> return 400
                ResourceType.ExtraMothershipFuel -> return 400
                ResourceType.Mine -> return 0
                else -> {
                    logger.error("Could not calculate capturing ap because resource type is unknown!")
                    return 1000
                }
            }
        }
    private var placedBy: Team? = null

    init {
        logger.info("Create $this")
    }

    fun wasPlacedByTeam(): Team? {
        return placedBy
    }

    fun release() {
        synchronized(RESOURCE_LOCK) {
            owner = null
            position = position.clone()
        }
    }

    @Throws(InvalidStateException::class)
    fun use(agent: Agent): Int {
        synchronized(RESOURCE_LOCK) {
            if (agent !== owner) {
                logger.warn("Resource not owned!")
                throw InvalidStateException("Resource not owned by user!")
            }
            if (!isUsed) {
                level.removeResource(this)
                isUsed = true
                position = Point2D(position)
                changes.firePropertyChange(KILL_EVENT, null, null)

                when (type) {
                    ResourceType.Normal -> {
                        GameSound.EarnNormalResource.play()
                        return 10
                    }

                    ResourceType.DoublePoints -> {
                        GameSound.EarnDoubleResource.play()
                        return 20
                    }

                    ResourceType.ExtremPoint -> {
                        GameSound.EarnExtremResource.play()
                        return 50
                    }

                    ResourceType.ExtraAgentFuel -> {
                        agent.spendFuel(Mothership.AGENT_FUEL_VOLUME / 10)
                        GameSound.EarnAgentFuel.play()
                        return 0
                    }

                    ResourceType.ExtraMothershipFuel -> {
                        agent.mothership.spendFuel(Mothership.MOTHERSHIP_FUEL_VOLUME / 5)
                        GameSound.EarnMothershipFuel.play()
                        return 0
                    }

                    ResourceType.Mine -> {
                        agent.kill()
                        return 0
                    }

                    ResourceType.Tonic -> {
                        agent.addTonic()
                        return 0
                    }

                    else -> return 0
                }
            }
        }
        logger.warn("Ignore double resource use!")
        return 0
    }

    companion object {
        const val KILL_EVENT: String = "KillEvent"
        const val RESOURCE_SIZE: Int = 25

        private val logger: Logger = LoggerFactory.getLogger(Resource::class.java)
    }
}
