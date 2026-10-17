package org.openbase.planetsudo.level.levelobjects

import org.openbase.jul.exception.CouldNotPerformException
import org.openbase.jul.visual.swing.engine.draw2d.AbstractResourcePanel
import org.openbase.planetsudo.game.GameManager
import org.openbase.planetsudo.game.GameSound
import org.openbase.planetsudo.geometry.Direction2D
import org.openbase.planetsudo.geometry.Point2D
import org.openbase.planetsudo.level.AbstractLevel
import org.openbase.planetsudo.level.LevelSize
import org.openbase.planetsudo.level.ResourcePlacement
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.geom.Rectangle2D
import java.util.concurrent.locks.ReentrantLock
import javax.swing.Timer
import kotlin.concurrent.withLock
import kotlin.math.max

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class Tower(id: Int, level: AbstractLevel, @JvmField val mothership: Mothership) :
    AbstractLevelObject(
        id,
        Tower::class.java.simpleName + "[" + id + "]",
        AbstractResourcePanel.ObjectType.Static,
        level,
        mothership.position,
        SIZE.toDouble(),
        SIZE.toDouble(),
        ObjectShape.Rec,
    ),
    ActionListener,
    TowerInterface {
    enum class TowerType {
        Unknown,
        DefenceTower,
        ObservationTower,
    }

    override var type: TowerType = TowerType.Unknown
        private set

    private val lock = ReentrantLock()
    private val condition = lock.newCondition()
    private val shieldLock = ReentrantLock()
    private val placement: ResourcePlacement? = null
    private val timer: Timer
    private val effectTimer: Timer
    private val rotationTimer: Timer

    private var scannedLevelSize: LevelSize = LevelSize.UNKNOWN
    override val levelSize: LevelSize
        get() = if (isOperational) scannedLevelSize else LevelSize.UNKNOWN

    @Volatile
    var shieldForce: Int = 0
        private set

    override var fuel: Int = 0
        private set

    override var isConstructed: Boolean = false
        private set

    val isOperational: Boolean
        get() = isConstructed && shieldForce > 0

    val revealsResources: Boolean
        get() = isOperational && type == TowerType.ObservationTower

    private val effectBounds: Rectangle2D.Double
        get() = Rectangle2D.Double(
            position.x - TOWER_EFFECT_RANGE,
            position.y - TOWER_EFFECT_RANGE,
            TOWER_EFFECT_RANGE * 2.0,
            TOWER_EFFECT_RANGE * 2.0,
        )

    override val fuelVolume: Int
        get() = TOWER_FUEL_VOLUME

    override val fuelInPercent: Int
        get() = (fuel * 100) / fuelVolume

    @JvmField
    val direction: Direction2D = Direction2D(0)
    val isAttacked: Boolean = false

    init {
        this.position = Point2D()
        this.timer = Timer(50, this)
        this.effectTimer = Timer(TOWER_EFFECT_INTERVAL_MILLIS) { performTowerEffect() }
        this.rotationTimer = Timer(TOWER_ROTATION_INTERVAL_MILLIS) { direction.angle += 10 }
        this.reset()
        logger.info("Create $this")
    }

    @Throws(CouldNotPerformException::class)
    fun construct(type: TowerType, commander: Agent) {
        if (!commander.isCommander) {
            commander.kill()
            throw CouldNotPerformException("Only the commander can construct a tower!")
        }
        if (type == TowerType.Unknown) {
            throw CouldNotPerformException("Cannot construct a tower with an unknown type!")
        }
        if (isConstructed) {
            throw CouldNotPerformException("Tower is already constructed!")
        }
        position.setLocation(commander.position)
        this.type = type
        isConstructed = true
        shieldLock.withLock { shieldForce = 100 }
        scannedLevelSize = LevelSize.UNKNOWN
        rotationTimer.start()
        effectTimer.start()
        changes.firePropertyChange(TOWER_CONSTRUCT, null, this)
        changes.firePropertyChange(TOWER_SHIELD_STATE_CHANGE, null, shieldForce)
        if (type == TowerType.ObservationTower) {
            scanLevelSize()
        }
    }

    @Throws(CouldNotPerformException::class)
    fun deconstruct(commander: Agent) {
        if (!commander.isCommander) {
            commander.kill()
            throw CouldNotPerformException("Only the commander can deconstruct a tower!")
        }
        if (!isConstructed) {
            throw CouldNotPerformException("Could not deconstruct the tower! The tower was never constructed!!")
        }
        isConstructed = false
        rotationTimer.stop()
        effectTimer.stop()
        timer.stop()
        shieldLock.withLock { shieldForce = 0 }
        scannedLevelSize = LevelSize.UNKNOWN
        changes.firePropertyChange(TOWER_SHIELD_STATE_CHANGE, null, shieldForce)
        changes.firePropertyChange(TOWER_SCAN_LEVEL, null, LevelSize.UNKNOWN)
        changes.firePropertyChange(TOWER_DECONSTRUCT, null, this)
    }

    public override fun reset() {
        val wasConstructed = isConstructed
        val oldFuel = fuel
        val hadScannedLevelSize = scannedLevelSize != LevelSize.UNKNOWN
        fuel = 100
        isConstructed = false
        rotationTimer.stop()
        effectTimer.stop()
        timer.stop()
        shieldLock.withLock { shieldForce = 0 }
        scannedLevelSize = LevelSize.UNKNOWN
        type = TowerType.Unknown
        if (oldFuel != fuel) changes.firePropertyChange(TOWER_FUEL_STATE_CHANGE, oldFuel, fuel)
        changes.firePropertyChange(TOWER_SHIELD_STATE_CHANGE, null, shieldForce)
        if (hadScannedLevelSize) changes.firePropertyChange(TOWER_SCAN_LEVEL, null, LevelSize.UNKNOWN)
        if (wasConstructed) changes.firePropertyChange(TOWER_DECONSTRUCT, null, this)
    }

    fun seeTower(agent: Agent): Boolean {
        if (!isConstructed) {
            return false
        }

        return isOperational && bounds.intersects(agent.bounds)
    }

    fun orderFuel(fuel: Int, agent: Agent?): Int {
        if (agent != null && !effectBounds.intersects(agent.bounds)) {
            return 0
        }
        val (orderedFuel, oldFuel, newFuel) = lock.withLock {
            val ordered = minOf(max(0, fuel), this.fuel)
            val oldFuel = this.fuel
            this.fuel -= ordered
            if (this.fuel == 0) condition.signalAll()
            Triple(ordered, oldFuel, this.fuel)
        }
        changes.firePropertyChange(TOWER_FUEL_STATE_CHANGE, oldFuel, newFuel)
        return orderedFuel
    }

    override fun hasFuel(): Boolean {
        return fuel > 0
    }

    protected fun spendFuel(value: Int) {
        val newFuel = lock.withLock {
            fuel = minOf(TOWER_FUEL_VOLUME, fuel + value)
            condition.signalAll()
            fuel
        }
        changes.firePropertyChange(TOWER_FUEL_STATE_CHANGE, null, newFuel)
    }

    override fun attack() {
        val result = shieldLock.withLock {
            if (!isConstructed || shieldForce == 0) return
            shieldForce--
            val shouldStartBurning = shieldForce <= Mothership.BURNING_TOWER && !timer.isRunning
            if (shouldStartBurning) timer.start()
            Triple(shieldForce, shouldStartBurning, shieldForce == 0)
        }
        if (result.second) {
            GameSound.MothershipExplosion.play()
        }
        if (result.third) {
            clearScannedLevelSize()
            effectTimer.stop()
        }
        changes.firePropertyChange(TOWER_SHIELD_STATE_CHANGE, null, result.first)
    }

    override fun repair() {
        val newShieldForce = shieldLock.withLock {
            if (!isConstructed || shieldForce >= 100) return
            (++shieldForce).also {
                if (it >= Mothership.BURNING_TOWER && timer.isRunning) timer.stop()
            }
        }
        if (newShieldForce > 0) {
            effectTimer.start()
        }
        changes.firePropertyChange(TOWER_SHIELD_STATE_CHANGE, null, newShieldForce)
    }

    override fun scanLevelSize() {
        if (!isOperational || scannedLevelSize != LevelSize.UNKNOWN) {
            return
        }
        scannedLevelSize = level.size
        changes.firePropertyChange(TOWER_SCAN_LEVEL, null, scannedLevelSize)
    }

    override val isBurning: Boolean
        get() = isConstructed && shieldForce < Mothership.BURNING_TOWER && hasFuel()

    override val shieldPoints: Int
        get() = shieldForce / 2

    override val isMaxDamaged: Boolean
        get() = shieldForce == 0

    override val isDamaged: Boolean
        get() = isConstructed && shieldForce < 100

    override fun actionPerformed(ex: ActionEvent) {
        if (isConstructed && !GameManager.gameManager.isPause) {
            orderFuel(max(0.0, (Mothership.BURNING_TOWER - shieldForce).toDouble()).toInt(), null)
        }
    }

    private fun performTowerEffect() {
        if (!isOperational || GameManager.gameManager.isPause) {
            return
        }
        when (type) {
            TowerType.DefenceTower -> {
                val enemyAgent = level.getAdversaryAgent(mothership.team, effectBounds) ?: return
                if (orderFuel(1, null) == 1) {
                    direction.turnTo(position, enemyAgent.position)
                    enemyAgent.attackByTower()
                    GameSound.Laser.play()
                }
            }

            TowerType.ObservationTower -> {
                val agent = mothership.agents
                    .filter { it.isAlive && it.fuel < it.fuelVolume && effectBounds.intersects(it.bounds) }
                    .minByOrNull { it.fuel.toDouble() / it.fuelVolume }
                    ?: return
                val fuelSupplied = orderFuel(OBSERVATION_FUEL_PER_TICK, agent)
                if (fuelSupplied > 0) {
                    spendFuel(agent.spendFuel(fuelSupplied))
                }
            }

            TowerType.Unknown -> Unit
        }
    }

    private fun clearScannedLevelSize() {
        if (scannedLevelSize != LevelSize.UNKNOWN) {
            scannedLevelSize = LevelSize.UNKNOWN
            changes.firePropertyChange(TOWER_SCAN_LEVEL, null, LevelSize.UNKNOWN)
        }
    }

    companion object {
        const val SIZE: Int = 50
        const val TOWER_FUEL_VOLUME: Int = 500
        const val TOWER_EFFECT_RANGE: Int = 300
        const val TOWER_EFFECT_INTERVAL_MILLIS: Int = 1000
        const val TOWER_ROTATION_INTERVAL_MILLIS: Int = 100
        const val OBSERVATION_FUEL_PER_TICK: Int = 10
        const val TOWER_FUEL_STATE_CHANGE: String = "FuelStateChange"
        const val TOWER_SHIELD_STATE_CHANGE: String = "ShieldStateChange"
        const val TOWER_SCAN_LEVEL: String = "ScanLevel"
        const val TOWER_CONSTRUCT: String = "construct tower"
        const val TOWER_DECONSTRUCT: String = "deconstruct tower"

        private val logger: Logger = LoggerFactory.getLogger(Tower::class.java)
    }
}
