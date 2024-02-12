package org.openbase.planetsudo.level.levelobjects

import org.openbase.jul.exception.CouldNotPerformException
import org.openbase.jul.exception.InvalidStateException
import org.openbase.jul.exception.printer.ExceptionPrinter
import org.openbase.jul.visual.swing.engine.draw2d.AbstractResourcePanel
import org.openbase.planetsudo.game.ActionPoints
import org.openbase.planetsudo.game.GameSound
import org.openbase.planetsudo.game.SwatTeam
import org.openbase.planetsudo.game.Team
import org.openbase.planetsudo.game.strategy.AbstractStrategy
import org.openbase.planetsudo.geometry.Direction2D
import org.openbase.planetsudo.geometry.Point2D
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType
import org.openbase.planetsudo.level.levelobjects.Tower.TowerType
import org.openbase.planetsudo.util.RandomGenerator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.geom.Rectangle2D
import java.lang.reflect.Constructor
import java.util.*
import java.util.logging.Level
import kotlin.math.min

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class Agent(
    name: String,
    commanderFlag: Boolean,
    fuelVolume: Int,
    mothership: Mothership,
) : AbstractLevelObject(
    mothership.computeNextAgentId(),
    name,
    AbstractResourcePanel.ObjectType.Dynamic,
    mothership.level,
    mothership.agentHomePosition,
    AGENT_SIZE.toDouble(),
    AGENT_SIZE.toDouble(),
    ObjectShape.Oval,
),
    AgentInterface {
    override val fuelVolume: Int
    val mothership: Mothership
    val ap: ActionPoints = ActionPoints(this)
    var direction: Direction2D = Direction2D()
        protected set
    override var fuel: Int = 0
        protected set
    override var tonic: Int = 0
        protected set

    override var isAlive: Boolean
        private set
    private var attacked: Boolean
    private var resource: Resource? = null
    private var hasMine = false
    private var hasTower = false
    override var isSupportOrdered: Boolean = false
        private set
    override val isCommander: Boolean
    var lastAction: String
    private var adversaryObject: AbstractLevelObject? = null
    override var isGameOverSoon: Boolean
        private set
    private val swatTeamSet: MutableSet<SwatTeam>

    override val angle: Int
        get() = direction.angle

    val description: String
        get() = lastAction + " AP[" + ap.actionPoints + "] " + SwatTeam.toString(swatTeamSet)

    fun spendFuel(value: Int) {
        fuel = min(fuelVolume.toDouble(), (fuel + value).toDouble()).toInt()
    }

    override fun reset() {
        fuel = fuelVolume
        isGameOverSoon = false
        position = mothership.agentHomePosition
        hasMine = mothership.orderMine()
        hasTower = isCommander
        try {
            direction = Direction2D(RandomGenerator.getRandom(1, 360))
        } catch (ex: InvalidStateException) {
            java.util.logging.Logger.getLogger(Agent::class.java.name).log(Level.SEVERE, null, ex)
        }
        isAlive = true
    }

    val viewBounds: Rectangle2D.Double
        get() {
            val point = direction.translate(
                Point2D(
                    position,
                ),
                AGENT_VIEW_DISTANCE,
            )
            return Rectangle2D.Double(
                point.x - AGENT_VIEW_DISTANCE,
                point.y - AGENT_VIEW_DISTANCE,
                (AGENT_VIEW_DISTANCE * 2).toDouble(),
                (AGENT_VIEW_DISTANCE * 2).toDouble(),
            )
        }

    override val actionPoints: Int get() = ap.actionPoints

    fun addActionPoint() {
        ap.addActionPoint()
    }

    fun addTonic() {
        tonic = min(tonic + 1, MAX_TONIC)
    }

    override val fuelInPercent: Int
        get() = (fuel * 100) / fuelVolume

    override val tonicInPercent: Int
        get() = (tonic / MAX_TONIC) * 100

    val team: Team
        get() = mothership.team

    override fun hasFuel(): Boolean {
        return fuel > 0
    }

    override fun hasMine(): Boolean {
        return hasMine
    }

    override fun hasTower(): Boolean {
        return hasTower
    }

    @Synchronized
    override fun deployMine() {
        ap.getActionPoint(50)
        if (useFuel(5) == 5 && hasMine) {
            val newMine = Resource(
                level.generateNewResourceID(),
                level,
                this,
            )
            level.addResource(newMine)
            hasMine = false
            GameSound.DeployMine.play()
        }
    }

    private fun calcSpeed(): Int {
        return if (isCarryingResource) DEFAULT_AGENT_SPEED / 2 else DEFAULT_AGENT_SPEED
    }

    private fun useFuel(): Boolean {
        return useFuel(1) == 1
    }

    private fun disable() {
        releaseResource()
        adversaryObject = null
        isHelping = false
    }

    @Synchronized
    private fun useFuel(value: Int): Int {
        var value = value
        ap.getActionPoint(1)
        if (fuel == 0) {
            disable()
            return 0
        } else if (fuel < value) {
            value = fuel
            fuel = 0
            disable()
            return value
        } else {
            fuel -= value
            return value
        }
    }

    override val isDisabled: Boolean
        get() = !isAlive || !hasFuel()

    override val carryingResourceType: ResourceType
        get() = resource?.type ?: ResourceType.Unknown

    override fun isCarryingResource(type: ResourceType): Boolean {
        if (isCarryingResource) {
            return resource?.type == type
        }
        return false
    }

    override val isCarryingResource: Boolean
        get() = resource != null

    override fun releaseResource() {
        if (isCarryingResource || resource != null) {
            resource?.release()
            resource = null
        }
    }

    fun getResource(): Resource? {
        val tmp2Resource = resource
        resource = null
        return tmp2Resource
    }

    override fun kill() {
        LOGGER.info("Kill $name")
        mothership.removeAgent(this)
        isAlive = false
        fuel = 0
        if (isSupportOrdered) {
            mothership.cancelSupport(this)
        }
        GameSound.AgentExplosion.play()
    }

    override val isCollisionDetected: Boolean
        get() = level.collisionDetected(futureBounds)

    val futureBounds: Rectangle2D
        get() {
            val futurePosition = direction.translate(position.clone(), calcSpeed())
            return Rectangle2D.Double(
                futurePosition.x.toInt() - (width / 2),
                futurePosition.y.toInt() - (height / 2),
                width,
                height,
            )
        }

    fun startGame() {
        LOGGER.info("startAgent$this")
        // ############## Load strategy #########################
        val constructor: Constructor<out AbstractStrategy>
        try {
            constructor = mothership.team.strategy.getConstructor(AgentInterface::class.java)
        } catch (ex: CouldNotPerformException) {
            LOGGER.error("Could not load strategy!", ex)
            kill()
            return
        }
        val strategy: AbstractStrategy
        try {
            strategy = constructor.newInstance(this)
        } catch (ex: CouldNotPerformException) {
            LOGGER.error("Could not load strategy!", ex)
            kill()
            return
        }
        Thread(strategy, name + "Thread").start()
        // ######################################################
    }

    override fun go() {
        if (isCarryingResource) {
            ap.getActionPoint(6)
        } else {
            ap.getActionPoint(3)
        }

        if (!useFuel()) {
            return
        }

        position.translate(direction, calcSpeed())
        if (level.collisionDetected(bounds)) { // Is collied with wall?
            kill()
        }
    }

    override fun turnAround() {
        ap.actionPoint
        if (useFuel()) {
            direction.invert()
        }
    }

    override fun goLeft(beta: Int) {
        go()
        if (hasFuel()) {
            direction.angle = direction.angle - beta
        }
    }

    override fun goRight(beta: Int) {
        go()
        if (hasFuel()) {
            direction.angle = direction.angle + beta
        }
    }

    override fun turnLeft(beta: Int) {
        ap.actionPoint
        if (useFuel()) {
            direction.angle = direction.angle - beta
        }
    }

    override fun turnRight(beta: Int) {
        ap.actionPoint
        if (useFuel()) {
            direction.angle = direction.angle + beta
        }
    }

    override fun turnRandom() {
        turnRandom(360)
    }

    override fun turnRandom(opening: Int) {
        ap.actionPoint
        try {
            if (useFuel()) {
                val randomAngle = RandomGenerator.getRandom(0, opening) - (opening / 2)
                direction.angle = direction.angle + randomAngle
            }
        } catch (ex: InvalidStateException) {
            LOGGER.error("Could not turn random.", ex)
        }
    }

    override fun goToMothership() {
        ap.actionPoint
        go()
        if (hasFuel()) {
            mothership.levelView?.getAbsolutAngle(this)?.also {
                direction.angle = it
            }
        }
    }

    override val isAtMothership: Boolean
        get() = mothership.bounds.contains(bounds)

    override fun orderFuel(percent: Int) {
        ap.getActionPoint(20)
        if (percent < 0 || percent > 100) {
            LOGGER.error("Could not refill fuel! Percent value[$percent] is not in bounds! Valuerange 0-100")
            return
        }

        if (mothership.hasFuel()) {
            for (toOrder in ((fuelVolume * percent) / 100) - fuel downTo 1) {
                fuel += mothership.orderFuel(1, this)
                ap.getActionPoint(2)
            }
            GameSound.RechargeFuel.play()
        }
    }

    override fun seeResource(): Boolean {
        return level.getCloseResource(this) != null
    }

    override fun seeResource(resourceType: ResourceType): Boolean {
        return level.getCloseResource(this, resourceType) != null
    }

    override fun goToResource() {
        ap.actionPoint
        if (useFuel()) {
            val resourceToGo = level.getCloseResource(this)
            if (resourceToGo != null) {
                go()
                direction.turnTo(position, resourceToGo.position)
            }
        }
    }

    override fun goToResource(resourceType: ResourceType) {
        ap.actionPoint
        if (useFuel()) {
            val resourceToGo = level.getCloseResource(this, resourceType)
            if (resourceToGo != null) {
                go()
                direction.turnTo(position, resourceToGo.position)
            }
        }
    }

    override fun deliverResourceToMothership() {
        ap.getActionPoint(10)
        try {
            if (isCarryingResource && useFuel() && isAtMothership) {
                mothership.passResource(this)
            }
        } catch (ex: CouldNotPerformException) {
            LOGGER.warn("Could not deliver resource to Mothership!", ex)
        }
    }

    override fun isTouchingResource(type: ResourceType): Boolean {
        return resourceType == type
    }

    override fun searchResources() {
        val startAngle = direction.angle
        for (i in 0..17) {
            ap.getActionPoint(10)
            if (seeResource()) {
                return
            }
            turnLeft(20)
        }
    }

    override val resourceType: ResourceType
        get() {
            val tmpResource = level.getTouchableResource(this) ?: return ResourceType.Unknown

            if (tmpResource.type == ResourceType.Mine && tmpResource.wasPlacedByTeam() != team) {
                return ResourceType.ExtremPoint
            }
            return tmpResource.type
        }

    override val isTouchingResource: Boolean
        get() = level.getTouchableResource(this) != null

    override fun pickupResource() {
        ap.getActionPoint(10)
        try {
            if (useFuel()) {
                val resourceToCollect = level.getTouchableResource(this)
                if (resourceToCollect != null && resourceToCollect.type != ResourceType.ExtraAgentFuel) {
                    releaseResource()
                }
                if (resourceToCollect != null && resourceToCollect.setBusy(team)) {
                    direction.turnTo(position, resourceToCollect.position)
                    ap.getActionPoint(resourceToCollect.capturingActionPoints)
                    carryResource(resourceToCollect)
                }
            }
        } catch (ex: InvalidStateException) {
            LOGGER.warn("Could not pickup resource!", ex)
        }
    }

    @Throws(InvalidStateException::class)
    private fun carryResource(resource: Resource?) {
        if (resource != null) {
            if (resource.capture(this)) {
                this.resource = resource
            }
        }
    }

    override fun seeAdversaryAgent(): Boolean {
        return level.getAdversaryAgent(this) != null
    }

    override fun seeTeamAgent(): Boolean {
        return level.getTeamAgent(this) != null
    }

    override fun seeAdversaryMothership(): Boolean {
        return level.getAdversaryMothership(this) != null
    }

    override val isUnderAttack: Boolean
        get() {
            val oldattackedValue = attacked
            attacked = false
            return oldattackedValue
        }
    private var catchedfuel = 0

    override fun fightWithAdversaryAgent() {
        ap.actionPoint
        if (useFuel()) {
            val adversaryAgent = level.getAdversaryAgent(this)
            adversaryObject = adversaryAgent
            if (adversaryAgent != null) {
                adversaryAgent.attacked = true
                ap.getActionPoint(20)
                direction.turnTo(position, adversaryAgent.position)
                if (adversaryAgent.hasFuel()) {
                    catchedfuel = (adversaryAgent.useFuel((Mothership.Companion.AGENT_FUEL_VOLUME / 500) * 2) / 3)
                    fuel = min(fuelVolume.toDouble(), (fuel + catchedfuel).toDouble()).toInt()
                }
                GameSound.Laser.play()
            }
        }
    }

    override fun fightWithAdversaryMothership() {
        ap.actionPoint
        if (useFuel()) {
            val adversaryMothership = level.getAdversaryMothership(this)
            adversaryObject = adversaryMothership
            if (adversaryMothership != null) {
                ap.getActionPoint(30)
                direction.turnTo(position, adversaryMothership.position)
                adversaryMothership.attack()
                GameSound.Laser.play()
            }
        }
    }

    override val isFighting: Boolean
        get() = adversaryObject != null

    val isFightingWith: AbstractLevelObject?
        get() {
            val adversary = adversaryObject
            adversaryObject = null
            return adversary
        }

    override fun seeLostTeamAgent(): Boolean {
        return level.getLostTeamAgent(this) != null
    }

    private var isHelping = false
    private var helpLevelObject: AbstractLevelObject? = null

    override fun spendTeamAgentFuel(value: Int) {
        if (useFuel()) {
            val teamAgent = level.getLostTeamAgent(this)
            if (teamAgent != null) {
                helpLevelObject = teamAgent
                isHelping = true
                direction.turnTo(position, teamAgent.position)
                ap.getActionPoint(value * 2)
                teamAgent.fuel += useFuel(value)
                GameSound.SpendFuel.play()
            }
        }
        isHelping = false
    }

    private var helpLevelObjectOld: AbstractLevelObject? = null

    init {
        LOGGER.info("Create $this")
        this.lastAction = "Init"
        this.fuelVolume = fuelVolume
        this.isCommander = commanderFlag
        this.mothership = mothership
        this.isAlive = true
        this.attacked = false
        this.isGameOverSoon = false
        this.swatTeamSet = TreeSet()
        if (commanderFlag) {
            swatTeamSet.add(SwatTeam.COMMANDER)
        }

        if (id == -1) { // check if valid
            kill()
        }
        reset()
    }

    fun wasHelping(): AbstractLevelObject? {
        if (!isHelping) {
            helpLevelObjectOld = helpLevelObject
            helpLevelObject = null
            return helpLevelObjectOld
        }
        return helpLevelObject
    }

    override fun repairMothership() {
        ap.getActionPoint(30)
        if (useFuel() && isAtMothership) {
            mothership.repair()
        }
    }

    override fun orderSupport() {
        ap.getActionPoint(5)
        if (!isSupportOrdered) {
            mothership.callForSupport(this)
            GameSound.CallForSupport.play()
        }
    }

    override fun goToSupportAgent() {
        try {
            val agentToSupport = mothership.getAgentToSupport(this)
            if (agentToSupport !== this) {
                go()
                agentToSupport.levelView?.getAbsolutAngle(this)?.also {
                    direction.angle = it
                }
            } else {
                throw CouldNotPerformException("Could not support himself!")
            }
        } catch (ex: CouldNotPerformException) {
            ExceptionPrinter.printHistory(CouldNotPerformException("Could not goToSupportAgent!", ex), LOGGER)
        }
    }

    fun setNeedSupport(needSupport: Boolean) {
        this.isSupportOrdered = needSupport
    }

    override fun cancelSupport() {
        ap.getActionPoint(5)
        if (isSupportOrdered) {
            mothership.cancelSupport(this)
        }
    }

    override fun deployMarker() {
        ap.getActionPoint(5)
        if (useFuel()) {
            mothership.placeMarker(position.clone())
        }
    }

    override fun goToMarker() {
        try {
            go()
            mothership.marker.levelView?.getAbsolutAngle(this)?.also {
                direction.angle = it
            }
        } catch (ex: CouldNotPerformException) {
            LOGGER.warn("Could not goToMarker!", ex)
        }
    }

    override fun seeMarker(): Boolean {
        return mothership.teamMarker.seeMarker(this)
    }

    override fun seeTower(): Boolean {
        return mothership.tower.seeTower(this)
    }

    override val teamPoints: Int
        get() = mothership.team.points

    fun joinSwatTeam(swatTeam: SwatTeam) {
        if (SwatTeam.NEGATED_SWATS.contains(swatTeam)) {
            LOGGER.error("It's not allowed to join any negated swat teams!")
            kill()
            return
        }
        if (swatTeam == SwatTeam.COMMANDER) {
            LOGGER.error("It's not allowed to setup the commander manually!")
            kill()
            return
        }
        if (swatTeam == SwatTeam.ALL) {
            LOGGER.error("ALL is not a valid swat team!")
            kill()
            return
        }
        swatTeamSet.add(swatTeam)
    }

    override fun isMemberOfSwatTeam(swatTeams: Collection<SwatTeam>): Boolean {
        // Check if agent was excluded.

        for (swat in swatTeams) {
            if (swat.negative) {
                if (swatTeamSet.contains(swat.opposition)) {
                    return false
                }
            }
        }

        // Check if wildcard was set
        if (swatTeams.contains(SwatTeam.ALL)) {
            return true
        }

        // Check if agent is included
        return !Collections.disjoint(swatTeams, swatTeamSet)
    }

    fun setGameOverSoon() {
        this.isGameOverSoon = true
        LOGGER.info("Game over soon!")
    }

    override fun erectTower(type: TowerType) {
        try {
            ap.getActionPoint(1500)
            useFuel(50)
            if (!isCommander) {
                LOGGER.warn("Only the commander is able to erect a tower, deployment failed!")
                return
            }
            mothership.tower.erect(type, this)
            hasTower = false
        } catch (ex: CouldNotPerformException) {
            kill()
            ExceptionPrinter.printHistory(CouldNotPerformException("Could not erect Tower!", ex), LOGGER)
        }
    }

    override fun dismantleTower() {
        try {
            ap.getActionPoint(500)
            useFuel(50)
            if (!isCommander) {
                LOGGER.warn("Only the commander is able to dismantle a tower, deployment failed!")
                return
            }

            if (!mothership.tower.seeTower(this)) {
                LOGGER.warn("The commander is not close enough to dismantle the tower!")
                return
            }
            mothership.tower.dismantle(this)
            hasTower = true
        } catch (ex: CouldNotPerformException) {
            kill()
            ExceptionPrinter.printHistory(CouldNotPerformException("Could not dismantle Tower!", ex), LOGGER)
        }
    }

    companion object {
        // 	public final static int DEFAULT_START_FUEL = 2000;
        // public final static int DEFAULT_START_FUEL = 1000;
        const val MAX_TONIC: Int = 3
        const val AGENT_SIZE: Int = 50
        const val AGENT_VIEW_DISTANCE: Int = AGENT_SIZE
        const val DEFAULT_AGENT_SPEED: Int = 6

        private val LOGGER: Logger = LoggerFactory.getLogger(Agent::class.java)
    }
}
