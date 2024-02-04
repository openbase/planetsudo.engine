package org.openbase.planetsudo.level.levelobjects

import org.openbase.jul.exception.CouldNotPerformException
import org.openbase.jul.exception.InvalidStateException
import org.openbase.jul.exception.printer.ExceptionPrinter
import org.openbase.jul.schedule.GlobalCachedExecutorService
import org.openbase.jul.visual.swing.engine.draw2d.AbstractResourcePanel
import org.openbase.planetsudo.game.GameManager
import org.openbase.planetsudo.game.GameSound
import org.openbase.planetsudo.geometry.Direction2D
import org.openbase.planetsudo.geometry.Point2D
import org.openbase.planetsudo.level.AbstractLevel
import org.openbase.planetsudo.level.ResourcePlacement
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.concurrent.Executors
import java.util.concurrent.locks.ReentrantLock
import javax.swing.Timer
import kotlin.concurrent.withLock
import kotlin.coroutines.CoroutineContext
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
        ObjectShape.Rec
    ),
    ActionListener {
    enum class TowerType {
        DefenceTower, ObservationTower
    }

    var type: TowerType? = null
        private set
    private val lock = ReentrantLock()
    private val condition = lock.newCondition()
    private val placement: ResourcePlacement? = null
    private val timer: Timer

    var shieldForce: Int = 0
        private set
    var fuel: Int = 0
        private set

    @JvmField
    val direction: Direction2D = Direction2D(0)
    val isAttacked: Boolean = false
    var isErected: Boolean = false
        private set

    init {
        this.position = Point2D()
        this.timer = Timer(50, this)
        this.reset()
        logger.info("Create $this")


        Thread {
            try {
                while (!Thread.interrupted()) {
                    if (!isErected) {
                        Thread.sleep(1000)
                        continue
                    }
                    direction.angle = direction.angle + 10
                    Thread.sleep(100)
                }
            } catch (ex: InterruptedException) {
                Thread.currentThread().interrupt()
            } catch (ex: Exception) {
                ExceptionPrinter.printHistory(InvalidStateException("TowerRotationThread crashed.", ex), logger)
            }
        }.start()
    }

    @Throws(CouldNotPerformException::class)
    fun erect(type: TowerType?, commander: Agent) {
        if (!commander.isCommander) {
            commander.kill()
            throw CouldNotPerformException("Only the commander can erect a tower!")
        }
        if (isErected) {
            throw CouldNotPerformException("Tower is already erected!")
        }
        position.setLocation(commander.position)
        this.type = type
        isErected = true
        changes.firePropertyChange(TOWER_ERECT, null, this)
    }

    @Throws(CouldNotPerformException::class)
    fun dismantle(commander: Agent) {
        if (!commander.isCommander) {
            commander.kill()
            throw CouldNotPerformException("Only the commander can dismantle a tower!")
        }
        if (!isErected) {
            throw CouldNotPerformException("Could not dismantle the tower! The tower was never erected!!")
        }
        isErected = false
        changes.firePropertyChange(TOWER_DISMANTLE, null, this)
    }

    public override fun reset() {
        fuel = 100
        isErected = false
    }

    fun seeTower(agent: Agent): Boolean {
        if (!isErected) {
            return false
        }

        return bounds.intersects(agent.bounds)
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
                    lock.withLock {
                        condition.signalAll()
                    }
                } else {
                    this.fuel -= fuel
                }
                changes.firePropertyChange(TOWER_FUEL_STATE_CHANGE, oldFuel, this.fuel)
            } catch (ex: CouldNotPerformException) {
                logger.error("Could not order fuel!", ex)
            }
        } else {
            return 0
        }
        return fuel
    }

    fun hasFuel(): Boolean {
        return fuel > 0
    }

    protected fun spendFuel(value: Int) {
        if (value + fuel > TOWER_FUEL_VOLUME) {
            fuel = TOWER_FUEL_VOLUME
        } else {
            fuel += value
        }
        changes.firePropertyChange(TOWER_FUEL_STATE_CHANGE, null, this.fuel)
    }

    @Synchronized
    fun attack() {
        logger.debug("Attack Mothership")
        if (shieldForce > 0) {
            shieldForce--
            if (shieldForce <= Mothership.Companion.BURNING_TOWER) {
                if (!timer.isRunning) {
                    timer.start()
                    GameSound.MothershipExplosion.play()
                }
            }
            changes.firePropertyChange(TOWER_SHIELD_STATE_CHANGE, null, shieldForce)
        }
    }

    @Synchronized
    fun repair() {
        if (shieldForce < 100) {
            shieldForce++
            if (shieldForce > Mothership.Companion.BURNING_TOWER && timer.isRunning) {
                timer.stop()
            }
            changes.firePropertyChange(TOWER_SHIELD_STATE_CHANGE, null, shieldForce)
        }
    }

    val isBurning: Boolean
        get() = shieldForce < Mothership.BURNING_TOWER && hasFuel()

    val shieldPoints: Int
        get() = shieldForce / 2

    val isMaxDamaged: Boolean
        get() = shieldForce == 0

    val isDamaged: Boolean
        get() = shieldForce < 100

    override fun actionPerformed(ex: ActionEvent) {
        if (!GameManager.gameManager.isPause) {
            orderFuel(max(0.0, (Mothership.BURNING_TOWER - shieldForce).toDouble()).toInt(), null)
        }
    }

    companion object {
        const val SIZE: Int = 50
        const val TOWER_FUEL_VOLUME: Int = 500
        const val TOWER_FUEL_STATE_CHANGE: String = "FuelStateChange"
        const val TOWER_SHIELD_STATE_CHANGE: String = "ShieldStateChange"
        const val TOWER_ERECT: String = "erect tower"
        const val TOWER_DISMANTLE: String = "dismantle tower"

        private val logger: Logger = LoggerFactory.getLogger(Tower::class.java)
    }
}
