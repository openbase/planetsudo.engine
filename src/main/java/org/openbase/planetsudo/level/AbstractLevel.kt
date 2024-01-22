package org.openbase.planetsudo.level

import org.openbase.jul.schedule.SyncObject
import org.openbase.planetsudo.game.AbstractGameObject
import org.openbase.planetsudo.game.GameManager
import org.openbase.planetsudo.game.Team
import org.openbase.planetsudo.game.strategy.AbstractStrategy
import org.openbase.planetsudo.geometry.Base2D
import org.openbase.planetsudo.geometry.Point2D
import org.openbase.planetsudo.level.levelobjects.Agent
import org.openbase.planetsudo.level.levelobjects.Mothership
import org.openbase.planetsudo.level.levelobjects.Resource
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType
import org.openbase.planetsudo.main.GUIController
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Color
import java.awt.Polygon
import java.awt.geom.Rectangle2D
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import kotlin.math.min

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
abstract class AbstractLevel : AbstractGameObject, Runnable {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val RESOURCES_LOCK: Any = SyncObject("ResourcesLock")
    private val TOWER_LOCK: Any = SyncObject("TowerLock")
    private val base: Point2D

    @JvmField
    val levelBorderPolygon: Polygon

    @JvmField
    val levelWallPolygons: List<Polygon>?
    val homePositions: List<Base2D>
    val resourcePlacement: List<ResourcePlacement>

    @JvmField
    val color: Color
    private val changes = PropertyChangeSupport(this)
    private val name: String = javaClass.simpleName

    @JvmField
    val motherships: Array<Mothership?> = arrayOfNulls(2)
    private val resources: MutableList<Resource>
    private var gameSpeedFactor: Double

    @JvmField
    val x: Int

    @JvmField
    val y: Int
    private var resourceKeyCounter = 0
    private val teams = arrayOfNulls<Team>(2)

    override fun run() {
        logger.info("Start Level $this")
        try {
            for (mothership in motherships) {
                mothership!!.startGame()
            }
        } catch (ex: Exception) {
            logger.warn("Could not start Level!", ex)
            return
        }

        while (!isGameOver) {
            if (!GameManager.gameManager.isPause) {
                for (i in 0 until AP_PER_ROUND) {
                    for (mothership in motherships) {
                        mothership!!.addActionPoint()
                    }
                }
            }
            try {
                Thread.sleep((AbstractStrategy.DEFAULT_GAME_CYCLE / gameSpeedFactor).toLong())
            } catch (ex: InterruptedException) {
                return
            }
        }
        logger.info("Level Ends.")
    }

    var mineCounter: Int = 0
        private set

    init {
        // Load level
        this.levelBorderPolygon = loadLevelBorderPolygon()
        this.levelWallPolygons = loadLevelWallPolygons()
        this.homePositions = loadHomePositions()
        this.resourcePlacement = loadResourcePlacement()

        // Base transformation
        this.base = calculateBasePosition(levelBorderPolygon)

        levelBorderPolygon.translate(-base.x.toInt(), -base.y.toInt())
        if (levelWallPolygons != null) {
            for (p in levelWallPolygons) {
                p.translate(-base.x.toInt(), -base.y.toInt())
            }
        }
        for (homepos in homePositions) {
            homepos.translateIntoBase(base)
        }

        for (placement in resourcePlacement) {
            placement.translateIntoBase(base)
        }

        this.color = loadLevelColor()
        this.resources = ArrayList()
        this.gameSpeedFactor = GameManager.gameManager.getGameSpeedFactor()

        val bounds = levelBorderPolygon.bounds2D
        this.x = bounds.x.toInt()
        this.y = bounds.y.toInt()
    }

    override fun getName(): String = name

    @Synchronized
    fun reset() {
        mineCounter = if (teams[0] != null && teams[1] != null) {
            (min(teams[0]!!.agentCount.toDouble(), teams[1]!!.agentCount.toDouble()) - 1).toInt()
        } else {
            0
        }

        logger.info("Load default values.")
        if (motherships[0] != null) {
            motherships[0]!!.reset()
        }
        if (motherships[1] != null) {
            motherships[1]!!.reset()
        }

        synchronized(RESOURCES_LOCK) {
            resources.clear()
        }
        resourceKeyCounter = 0
        logger.info("Place resources in $this")
        placeResources()
        logger.info("Add " + resources.size + " Resources")
    }

    @Synchronized
    fun generateNewResourceID(): Int {
        return resourceKeyCounter++
    }

    fun setTeamA(team: Team?) {
        teams[0] = team
        motherships[0] = Mothership(0, team!!, this)
    }

    fun setTeamB(team: Team?) {
        teams[1] = team
        motherships[1] = Mothership(1, team!!, this)
    }

    fun containsWall(bounds: Rectangle2D?): Boolean {
        logger.debug("CalcWallContain")
        if (levelWallPolygons != null) {
            for (wall in levelWallPolygons) {
                if (wall.intersects(bounds) or wall.contains(bounds)) {
                    return true
                }
            }
        }
        return !levelBorderPolygon.contains(bounds)
    }

    fun collisionDetected(bounds: Rectangle2D?): Boolean {
        return containsWall(bounds)
    }

    fun waitTillGameOver() {
        for (mothership in motherships) {
            mothership!!.waitTillGameEnd()
        }
    }

    val isGameOver: Boolean
        get() = GameManager.gameManager.isGameOver

    protected abstract fun loadLevelBorderPolygon(): Polygon

    protected abstract fun loadLevelWallPolygons(): List<Polygon>

    protected abstract fun loadHomePositions(): List<Base2D>

    protected abstract fun loadResourcePlacement(): List<ResourcePlacement>

    protected abstract fun loadLevelColor(): Color

    fun getMothershipBase(mothershipID: Int): Base2D {
        return homePositions[mothershipID]
    }

    fun getResources(): List<Resource> {
        synchronized(RESOURCES_LOCK) {
            return ArrayList(resources)
        }
    }

    override fun toString(): String {
        return name
    }

    private fun placeResources() {
        synchronized(RESOURCES_LOCK) {
            val resourcePlacements: List<ResourcePlacement> = resourcePlacement
            var resourceCounter = 0
            for (placement in resourcePlacements) {
                resourceCounter += placement.resourceCount
            }
            GUIController.setEvent(
                PropertyChangeEvent(
                    this,
                    GUIController.LOADING_STATE_CHANGE,
                    resourceCounter,
                    "Lade Resourcen"
                )
            )
            for (placement in resourcePlacements) {
                resources.addAll(placement.getResources(this))
            }
        }
    }

    fun removeResource(resource: Resource) {
        synchronized(RESOURCES_LOCK) {
            resources.remove(resource)
        }
        if (resource.type == ResourceType.Normal) {
            spornNewResource(resource.placement)
        }
    }

    private fun spornNewResource(placement: ResourcePlacement?) {
        val newResource = Resource(generateNewResourceID(), ResourceType.Normal, this, placement!!)
        addResource(newResource)
    }

    fun addResource(resource: Resource) {
        synchronized(RESOURCES_LOCK) {
            resources.add(resource)
        }
        changes.firePropertyChange(CREATE_RESOURCE, null, resource)
    }

    /**
     * WARNING: method returns null in case of no close resource.
     *
     * @param agent
     * @param resourceType
     * @return
     */
    fun getCloseResource(agent: Agent, resourceType: ResourceType): Resource? {
        synchronized(RESOURCES_LOCK) {
            for (resource in resources) {
                if (!resource.isUsed && resource.type == resourceType && (!resource.isOwned || resource.owner!!.team != agent.team) &&
                    resource.isSaveFor(agent) &&
                    resource.bounds.intersects(agent.viewBounds)
                ) {
                    return resource
                }
            }
        }
        return null
    }

    /**
     * WARNING: method returns null in case of no close resource.
     *
     * @param agent
     * @return
     */
    fun getCloseResource(agent: Agent): Resource? {
        synchronized(RESOURCES_LOCK) {
            for (resource in resources) {
                if (!resource.isUsed &&
                    (!resource.isOwned || (resource.owner!!.team != agent.team || !resource.owner!!.hasFuel())) &&
                    resource.isSaveFor(agent) &&
                    resource.bounds.intersects(agent.viewBounds)
                ) {
                    return resource
                }
            }
        }
        return null
    }

    /**
     * WARNING: method returns null in case of no touchable resource.
     *
     * @param agent
     * @return
     */
    fun getTouchableResource(agent: Agent): Resource? {
        synchronized(RESOURCES_LOCK) {
            for (resource in resources) {
                if (!resource.isUsed && resource.bounds.intersects(agent.bounds) &&
                    (resource.owner == null || resource.owner!!.team != agent.team)
                ) {
                    return resource
                }
            }
        }
        return null
    }

    fun getAdversaryMothership(agent: Agent): Mothership? {
        for (mothership in motherships) {
            if ((mothership!!.team != agent.team) &&
                !mothership.isMaxDamaged &&
                mothership.bounds.intersects(agent.viewBounds)
            ) {
                return mothership
            }
        }
        return null
    }

    fun getAdversaryAgent(agent: Agent): Agent? {
        for (mothership in motherships) {
            if (mothership!!.team != agent.team) {
                for (adversaryAgent in mothership.getAgents()) {
                    if (adversaryAgent.hasFuel() && adversaryAgent.bounds.intersects(agent.viewBounds)) {
                        return adversaryAgent
                    }
                }
            }
        }
        return null
    }

    fun getTeamAgent(agent: Agent): Agent? {
        for (mothership in motherships) {
            if (mothership!!.team == agent.team) {
                for (teamAgent in mothership.getAgents()) {
                    if (teamAgent == agent) {
                        continue
                    }
                    if (teamAgent.hasFuel() && teamAgent.bounds.intersects(agent.viewBounds)) {
                        return teamAgent
                    }
                }
            }
        }
        return null
    }

    fun getLostTeamAgent(agent: Agent): Agent? {
        for (mothership in motherships) {
            if (mothership!!.team == agent.team) {
                for (teamAgent in mothership.getAgents()) {
                    if (!teamAgent.hasFuel() && teamAgent.bounds.intersects(agent.viewBounds)) {
                        return teamAgent
                    }
                }
            }
        }
        return null
    }

    fun setGameSpeedFactor(factor: Double) {
        if (gameSpeedFactor == factor) {
            return
        }
        gameSpeedFactor = factor
        logger.info("Game speed $factor -> $gameSpeedFactor")
        changes.firePropertyChange(GAME_SPEED_FACTOR_CHANGED, null, gameSpeedFactor)
    }

    fun addPropertyChangeListener(l: PropertyChangeListener) {
        changes.addPropertyChangeListener(l)
        logger.debug("Add " + l.javaClass + " as new PropertyChangeListener.")
    }

    fun removePropertyChangeListener(l: PropertyChangeListener) {
        changes.removePropertyChangeListener(l)
        logger.debug("Remove PropertyChangeListener " + l.javaClass + ".")
    }

    fun getGameSpeedFactor(): Double {
        return gameSpeedFactor
    }

    fun setGameOverSoon() {
        motherships[0]!!.setGameOverSoon()
        motherships[1]!!.setGameOverSoon()
    }

    companion object {
        const val AP_PER_ROUND: Long = (AbstractStrategy.DEFAULT_GAME_CYCLE * 0.8).toLong()

        const val CREATE_RESOURCE: String = "create resource"
        const val GAME_SPEED_FACTOR_CHANGED: String = "SpeedChanged"

        private fun calculateBasePosition(polygon: Polygon): Point2D {
            val bounds = polygon.bounds2D
            return Point2D(bounds.minX, bounds.minY)
        }
    }
}
