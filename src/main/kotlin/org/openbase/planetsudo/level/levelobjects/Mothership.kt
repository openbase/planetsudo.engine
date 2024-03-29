package org.openbase.planetsudo.level.levelobjects

import org.openbase.jul.exception.CouldNotPerformException
import org.openbase.jul.exception.InvalidStateException
import org.openbase.jul.visual.swing.engine.draw2d.AbstractResourcePanel
import org.openbase.planetsudo.game.GameManager
import org.openbase.planetsudo.game.GameSound
import org.openbase.planetsudo.game.Team
import org.openbase.planetsudo.geometry.Base2D
import org.openbase.planetsudo.geometry.Point2D
import org.openbase.planetsudo.level.AbstractLevel
import org.openbase.planetsudo.main.GUIController
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.beans.PropertyChangeEvent
import java.util.*
import java.util.concurrent.locks.ReentrantReadWriteLock
import javax.swing.Timer
import kotlin.concurrent.read
import kotlin.concurrent.write
import kotlin.math.max

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class Mothership(id: Int, team: Team, level: AbstractLevel) :
    AbstractLevelObject(
        id,
        team.name + Mothership::class.java.simpleName,
        AbstractResourcePanel.ObjectType.Static,
        level,
        level.getMothershipBase(id).point,
        100.0,
        100.0,
        ObjectShape.Rec,
    ),
    ActionListener,
    MothershipInterface {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val agentLock = ReentrantReadWriteLock()
    private val supportChannelLock = ReentrantReadWriteLock()
    val base: Base2D = level.getMothershipBase(id)

    @JvmField
    val team: Team

    override val fuelVolume: Int
        get() = MOTHERSHIP_FUEL_VOLUME
    override var fuel: Int = 0
        private set
    private var strategyAgentCount = 0
    override var shieldForce: Int = 0
        private set
    private val timer: Timer
    var agents: List<Agent>
        private set
    private val supportChannel: MutableList<Agent>

    @JvmField
    val tower: Tower

    /**
     * Method just for visual purpose
     *
     * @return
     */
    @JvmField
    val teamMarker: TeamMarker
    private var mineCounter: Int

    public override fun reset() {
        GUIController.setEvent(
            PropertyChangeEvent(
                this,
                GUIController.LOADING_STATE_CHANGE,
                1,
                "Lade " + team.name + " Mutterschiff",
            ),
        )
        fuel = MOTHERSHIP_FUEL_VOLUME
        mineCounter = level.mineCounter
        agentLock.write {
            agents.forEach { it.kill() }
            strategyAgentCount = team.agentCount
            loadAgents()
        }
        this.shieldForce = 100
    }

    @Synchronized
    fun orderMine(): Boolean {
        if (mineCounter > 0) {
            mineCounter--
            return true
        }
        return false
    }

    private fun loadAgents() {
        agentLock.write {
            GUIController.setEvent(
                PropertyChangeEvent(
                    this,
                    GUIController.LOADING_STATE_CHANGE,
                    strategyAgentCount,
                    "Lade " + team.name + " Agent",
                ),
            )
            var agent: Agent
            val agentFuelVolume = (AGENT_FUEL_VOLUME / strategyAgentCount)
            var currentFuelVolume: Int
            var commanderFlag = true
            for (i in 0 until strategyAgentCount) {
                GUIController.setEvent(PropertyChangeEvent(this, GUIController.LOADING_STEP, null, i))
                currentFuelVolume =
                    if (commanderFlag) agentFuelVolume + COMMANDER_BONUS_FUEL_VOLUME else agentFuelVolume
                agent = Agent(team.name + "Agent", commanderFlag, currentFuelVolume, this)
                commanderFlag = false
                agents = agents.plus(agent)
            }
        }
    }

    fun orderFuel(fuel: Int, agent: Agent?): Int {
        var fuel = fuel
        if (agent == null || bounds.contains(agent.bounds)) {
            try {
                val oldFuel = this.fuel
                if (fuel <= 0) { // fuel emty
                    fuel = 0
                } else if (this.fuel < fuel) { // use last fuel
                    fuel = this.fuel
                    this.fuel = 0
                    synchronized(this) {
                        (this as Object).notifyAll()
                    }
                } else {
                    this.fuel -= fuel
                }
                changes.firePropertyChange(MOTHERSHIP_FUEL_STATE_CHANGE, oldFuel, this.fuel)
            } catch (ex: CouldNotPerformException) {
                logger.error("Could not order fuel!", ex)
            }
        } else {
            return 0
        }
        return fuel
    }

    override val fuelInPercent: Int
        get() = (fuel * 100) / fuelVolume

    override fun hasFuel(): Boolean {
        return fuel > 0
    }

    fun spendFuel(value: Int) {
        if (value + fuel > MOTHERSHIP_FUEL_VOLUME) {
            fuel = MOTHERSHIP_FUEL_VOLUME
        } else {
            fuel += value
        }
        changes.firePropertyChange(MOTHERSHIP_FUEL_STATE_CHANGE, null, this.fuel)
    }

    fun waitTillGameEnd() {
        synchronized(this) {
            if (fuel == 0) {
                return
            }
            synchronized(TILL_END_WAITER) {
                (TILL_END_WAITER as Object).wait()
            }
        }
    }

    fun startGame() {
        agentLock.read {
            agents.forEach { it.startGame() }
        }
    }

    private var agentIndex = 0

    //    private var agentKeyArray: Array<Int>
    private var nextAgent: Agent? = null

    init {
        logger.info("Create $this")
        this.team = team
        this.team.mothership = this
        this.agents = emptyList()
        this.supportChannel = ArrayList()
        this.teamMarker = TeamMarker(team, level)
        this.timer = Timer(50, this)
        this.mineCounter = 0
        this.tower = Tower(id, level, this)
    }

    fun addActionPoint() {
        agentLock.read {
            if (agents.isNotEmpty()) {
                agentIndex = (agentIndex + 1) % (agents.size)
                nextAgent = agents[agentIndex]
            }
            nextAgent?.addActionPoint()
        }
    }

    fun computeNextAgentId(): Int = agentLock.read { id * 10000 + agents.size }

    override val agentCount: Int get() = agentLock.read { agents.size }

    fun removeAgent(agent: Agent) = agentLock.write {
        agents
            .firstOrNull { it.id == agent.id }
            ?.also { agents = agents.minus(it) }
    }

    val agentHomePosition: Point2D
        get() = position.clone()

    @Throws(InvalidStateException::class)
    fun passResource(agent: Agent) {
        val resource = agent.getResource()
        if (resource != null) {
            if (bounds.contains(agent.bounds)) {
                team.addPoints(resource.use(agent))
            } else {
                resource.release()
            }
        }
    }

    @Synchronized
    fun attack() {
        logger.debug("Attack Mothership")
        if (shieldForce > 0) {
            if (shieldForce == 100) {
                GameSound.MothershipUnderAttack.play()
            }

            shieldForce--
            if (shieldForce <= BURNING_MOTHERSHIP) {
                if (!timer.isRunning) {
                    timer.start()
                    GameSound.MothershipExplosion.play()
                }
            }
            changes.firePropertyChange(MOTHERSHIP_SHIELD_STATE_CHANGE, null, shieldForce)
        }
    }

    @Synchronized
    fun repair() {
        if (shieldForce < 100) {
            shieldForce++
            if (shieldForce > BURNING_MOTHERSHIP && timer.isRunning) {
                timer.stop()
            }
            changes.firePropertyChange(MOTHERSHIP_SHIELD_STATE_CHANGE, null, shieldForce)
        }
    }

    override val isBurning: Boolean
        get() = shieldForce < BURNING_MOTHERSHIP && hasFuel()

    val shieldPoints: Int
        get() = shieldForce / 2

    override val isMaxDamaged: Boolean
        get() = shieldForce == 0

    override val isDamaged: Boolean
        get() = shieldForce < 100

    override fun actionPerformed(ex: ActionEvent) {
        if (!GameManager.gameManager.isPause) {
            orderFuel(
                max(0.0, (BURNING_MOTHERSHIP - shieldForce).toDouble()).toInt(),
                null,
            )
        }
    }

    val agentsAtHomePoints: Int
        get() = ((agentsAtHomePosition * 100) / strategyAgentCount)

    private val agentsAtHomePosition: Int
        get() {
            agentLock.read {
                var counter = 0
                agents.forEach { agent ->
                    if (bounds.contains(agent.bounds) || bounds.intersects(agent.bounds)) {
                        counter++
                    }
                }
                return counter
            }
        }

    fun callForSupport(agent: Agent) {
        supportChannelLock.write {
            if (supportChannel.contains(agent)) {
                return
            }
            supportChannel.add(agent)
            agent.setNeedSupport(true)
        }
    }

    override fun needSomeoneSupport(): Boolean {
        supportChannelLock.read {
            return supportChannel.size > 0
        }
    }

    @Throws(CouldNotPerformException::class)
    fun getAgentToSupport(helper: Agent): Agent {
        supportChannelLock.read {
            if (supportChannel.isEmpty()) {
                throw CouldNotPerformException("No support necessary.")
            }

            val supportCaller: Agent = supportChannel
                .filterNot { it === helper } // do not help yourself
                .minByOrNull { helper.levelView?.getDistance(it) ?: Int.MAX_VALUE }
                ?: throw CouldNotPerformException("No support possible.")

            // remove caller from support channel if support is possible
            if (supportCaller.bounds.intersects(helper.viewBounds)) {
                cancelSupport(supportCaller)
            }
            supportCaller.levelView?.updateObjectMovement()
            return supportCaller
        }
    }

    fun cancelSupport(agent: Agent) {
        supportChannelLock.write {
            supportChannel.remove(agent)
            agent.setNeedSupport(false)
        }
    }

    override val isMarkerDeployed: Boolean
        get() = teamMarker.isPlaced

    override fun clearMarker() {
        teamMarker.clear()
    }

    fun placeMarker(position: Point2D) {
        teamMarker.place(position)
    }

    @get:Throws(CouldNotPerformException::class)
    val marker: TeamMarker
        get() = teamMarker.marker

    fun setGameOverSoon() = agentLock.read { agents.forEach { it.setGameOverSoon() } }

    companion object {
        const val MOTHERSHIP_FUEL_STATE_CHANGE: String = "FuelStateChange"
        const val MOTHERSHIP_SHIELD_STATE_CHANGE: String = "ShieldStateChange"
        const val MOTHERSHIP_FUEL_VOLUME: Int = 15000
        const val AGENT_FUEL_VOLUME: Int = 12000
        const val COMMANDER_BONUS_FUEL_VOLUME: Int = 1000
        const val MAX_AGENT_COUNT: Int = 10 // range 0-9999
        const val BURNING_MOTHERSHIP: Int = 20
        const val BURNING_TOWER: Int = 20

        val TILL_END_WAITER: Any = Any()
    }
}
