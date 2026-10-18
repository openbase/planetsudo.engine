/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game.strategy

import org.apache.commons.lang3.ArrayUtils.shift
import org.openbase.jps.core.JPService
import org.openbase.planetsudo.game.GameManager
import org.openbase.planetsudo.game.SwatTeam
import org.openbase.planetsudo.game.SwatTeam.*
import org.openbase.planetsudo.level.AbstractLevel
import org.openbase.planetsudo.level.levelobjects.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.beans.PropertyChangeEvent
import java.util.*

typealias StrategyLevel1 = AbstractStrategy<AgentBasicInterface<AgentBasicInterfaceGermanWrapper>>
typealias StrategyLevel2 = AbstractStrategy<AgentBattleInterface<AgentBattleInterfaceGermanWrapper>>
typealias StrategyLevel3 = AbstractStrategy<AgentSpecialInterface<AgentSpecialInterfaceGermanWrapper>>
typealias StrategyLevelLegacy = AbstractStrategy<AgentLegacyInterface>

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
abstract class AbstractStrategy<LEVEL : GlobalAgentInterface<*>>(agent: AgentInterface) : Runnable {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val strategyOwner: Agent by lazy { agent as Agent }
    val mothership: MothershipInterface by lazy { strategyOwner.mothership }

    val agent: LEVEL by lazy { agent as LEVEL }
    val tower: TowerInterface by lazy { strategyOwner.mothership.tower }
    val enemyAgent get() = strategyOwner.enemyAgent
    val teamAgent get() = strategyOwner.teamAgent
    private val mothershipInternal: Mothership by lazy { strategyOwner.mothership }
    private val rules: TreeMap<Int, Rule> = TreeMap()
    private val manualCommanderRules = mutableListOf<Rule>()
    private var manualCommanderPlayer: ManualPlayer? = null
    private val gameManager: GameManager = GameManager.gameManager

    @JvmField
    val agentCount: Int

    private var gameSpeedFactor: Double = GameManager.DEFAULT_GAME_SPEED_FACTOR.toDouble()

    init {
        this.agentCount = loadAgentCount()

        if (agent is Agent && !JPService.testMode()) {
            this.loadRules()
            if (agent.isCommander) {
                configureManualCommanderControl()
            }
            this.loadSwatTeams()
            connectToMothership()
            bindGameSpeed()
        }
    }

    private fun connectToMothership() {
        strategyOwner.mothership.level.addPropertyChangeListener { evt: PropertyChangeEvent ->
            if (evt.propertyName == AbstractLevel.GAME_SPEED_FACTOR_CHANGED) {
                gameSpeedFactor = evt.newValue as Double
            }
        }
    }

    private fun bindGameSpeed() {
        if (agent is Agent) {
            gameSpeedFactor = strategyOwner.mothership.level.getGameSpeedFactor()
        }
    }

    override fun run() {
        while (strategyOwner.isAlive) {
            if (gameManager.isGameOver) {
                break
            }
            try {
                if (!gameManager.isPause) {
                    executeRule()
                } else {
                    logger.debug("ignore rule because game is paused!")
                }
            } catch (ex: InterruptedException) {
                throw ex
            } catch (ex: Throwable) {
                logger.error(
                    "Could not execute rule[" + strategyOwner.lastAction + "] of strategy [${javaClass.simpleName}]!",
                    ex,
                )
                strategyOwner.kill()
            }
            delayByGameSpeed()
        }
        logger.info("AI of agent $agent is dead now!")
    }

    fun delayByGameSpeed() {
        Thread.sleep((DEFAULT_GAME_CYCLE / gameSpeedFactor).toLong())
    }

    fun createRule(rule: Rule): Rule {
        logger.debug("Create rule $rule")
        if (rules.containsKey(getKey(rule))) {
            logger.error("There exist min two rules with the same priority!")
            strategyOwner.kill()
        }
        rules[getKey(rule)] = rule
        return rule
    }

    private fun getKey(rule: Rule): Int {
        // Auto generate priority if rule does not contain any priority.
        if (rule.priority == -1) {
            return -rules.size
        }
        return -rule.priority // invert priority to swap list order.
    }

    internal fun executeRule() {
        val applicableRules =
            if (manualCommanderPlayer != null) {
                manualCommanderRules.asReversed()
            } else {
                rules.values
            }
        for (rule in applicableRules) {
            if (rule.constraint() && strategyOwner.isMemberOfSwatTeam(rule.swatTeams)) {
                strategyOwner.lastAction = rule.name
                rule.action()
                strategyOwner.resetAfterRule()
                break
            }
        }
    }

    private fun manualPlayerForTeam(): ManualPlayer? =
        when (strategyOwner.mothership.id) {
            0 -> ManualPlayer.PLAYER_1
            1 -> ManualPlayer.PLAYER_2
            else -> null
        }

    internal fun configureManualCommanderControl() {
        if (!strategyOwner.isCommander) return
        val player = manualPlayerForTeam() ?: return
        enableManualCommanderControl(player)
    }

    private fun enableManualCommanderControl(player: ManualPlayer) {
        if (manualCommanderPlayer != null ||
            !strategyOwner.isCommander ||
            ManualControlInput.selectedGamepad(player) == null
        ) {
            return
        }

        manualCommanderPlayer = player
        addManualCommanderRule("Manual go forward", { manualControl(player, ManualAction.GO_FORWARD) }) {
            go()
        }
        addManualCommanderRule("Manual turn left", { manualControl(player, ManualAction.TURN_LEFT) }) {
            turnLeft(5)
        }
        addManualCommanderRule("Manual turn right", { manualControl(player, ManualAction.TURN_RIGHT) }) {
            turnRight(5)
        }
        addManualCommanderRule(
            "Manual go left",
            { manualControl(player, ManualAction.GO_FORWARD) && manualControl(player, ManualAction.TURN_LEFT) },
        ) {
            goLeft(10)
        }
        addManualCommanderRule(
            "Manual go right",
            { manualControl(player, ManualAction.GO_FORWARD) && manualControl(player, ManualAction.TURN_RIGHT) },
        ) {
            goRight(10)
        }
        addManualCommanderRule(
            "Manual fight enemy mothership",
            { manualControl(player, ManualAction.FIGHT_ENEMY) && strategyOwner.seeEnemyMothership },
        ) {
            fightWithEnemyMothership()
        }
        addManualCommanderRule(
            "Manual fight enemy agent",
            { manualControl(player, ManualAction.FIGHT_ENEMY) && strategyOwner.seeEnemyAgent },
        ) {
            fightWithEnemyAgent()
        }
        addManualCommanderRule(
            "Manual set marker",
            { manualControl(player, ManualAction.SET_MARKER) },
        ) {
            if (mothership.isMarkerDeployed) {
                mothership.clearMarker()
            } else {
                deployMarker()
            }
        }
        addManualCommanderRule(
            "Manual place mine",
            { manualControl(player, ManualAction.PLACE_MINE) && strategyOwner.hasMine },
        ) {
            deployMine()
        }
        addManualCommanderRule("Manual primary action", { manualControl(player, ManualAction.PRIMARY_ACTION) }) {
            when {
                isCarryingResource && isAtMothership -> transferResourceToMothership()
                mothership.isDamaged && isAtMothership -> repairMothership()
                isAtMothership && fuelInPercent < 100 && mothership.hasFuel() -> orderFuel(100)
                seeLostTeamAgent() -> spendTeamAgentFuel(10)
                isTouchingResource -> pickupResource()
                seeResource -> goToResource()
                isCarryingResource -> releaseResource()
                else -> Unit
            }
        }
        addManualCommanderRule("Manual order support", { manualControl(player, ManualAction.ORDER_SUPPORT) }) {
            if (strategyOwner.hasRequestedSupport) {
                strategyOwner.cancelSupport()
            } else {
                strategyOwner.requestSupport()
            }
        }
        addManualCommanderRule(
            "Manual shift",
            { manualControl(player, ManualAction.SHIFT) && strategyOwner.hasTonic },
        ) {
            shift()
        }
    }

    private fun addManualCommanderRule(
        name: String,
        condition: () -> Boolean,
        action: Agent.() -> Unit,
    ) {
        manualCommanderRules.add(
            RuleBuilder(name = name)
                .swat(COMMANDER)
                .inCase(condition)
                .action { action(strategyOwner) }
                .build(),
        )
    }

    /**
     * Mit dieser Methode ist es möglich neue SwatTeams aus mehreren Agenten zu bilden.
     * Die Agenten werden hierbei über ihre IDs hinzugefügt. Sind beispielsweise 4 Agenten in der Strategie angegeben, so sind diese über die IDs 0 - 3 referenzierbar wobei Agent 0 immer für den Commander steht.
     * Bitte beachte somit, dass die Agenten ID nicht größer als N - 1 sein kann sofern N für die maximale Anzahl von agenten steht.
     *
     * Die default Gruppen ALL und COMMANDER können anhand dieser Methode nicht modifiziert werden!
     *
     * @param swatTeam Das SwatTeam welchem die Agenten zugeteilt werden sollen.
     * @param agents Die Ids der Agenten.
     */
    protected fun createSwat(swatTeam: SwatTeam, vararg agentNumbers: Int) {
        when (swatTeam) {
            ALL, COMMANDER -> {
                logger.error("SwatTeam[" + swatTeam.name + "] is not modifiable!")
                strategyOwner.kill()
                return
            }

            else -> {
                if (swatTeam.negative) {
                    logger.error("SwatTeam[" + swatTeam.name + "] is not modifiable!")
                    strategyOwner.kill()
                    return
                }
            }
        }
        val agentList = mothershipInternal.agents
        for (agentNumber in agentNumbers) {
            if (agentNumber < 0) {
                logger.error("Could not create SwatTeam[" + swatTeam.name + "] because negative agent number is not allowed!")
                strategyOwner.kill()
                return
            }

            if (agentNumber >= agentCount || agentNumber >= agentList.size) {
                logger.error("Could not create SwatTeam[" + swatTeam.name + "] because team has not enough members!")
                strategyOwner.kill()
                return
            }

            agentList[agentNumber].joinSwatTeam(swatTeam)
        }
    }

    protected abstract fun loadRules()

    protected open fun loadSwatTeams() {
        // Please overwrite to create swats within your strategy!
    }

    protected abstract fun loadAgentCount(): Int

    protected infix fun RuleBuilder.then(action: AgentInterface.() -> Unit): Rule = this
        .apply { this.action = { strategyOwner.action() } }
        .build()
        .let { createRule(it) }

    protected infix fun String.all(builder: RuleBuilder): RuleBuilder =
        RuleBuilder(name = this@all).merge(builder).swat(ALL)

    protected infix fun String.commander(builder: RuleBuilder): RuleBuilder =
        RuleBuilder(name = this@commander).merge(builder).swat(COMMANDER)

    protected infix fun String.nonCommander(builder: RuleBuilder): RuleBuilder =
        RuleBuilder(name = this@nonCommander).merge(builder).swat(NOT_COMMANDER)

    protected infix fun String.swat(swats: () -> Collection<SwatTeam>): RuleBuilder =
        RuleBuilder(this@swat).swat(swats())

    protected infix fun String.swat(swat: SwatTeam): RuleBuilder =
        RuleBuilder(this@swat).swat(swat)

    protected infix fun RuleBuilder.and(swat: SwatTeam) = this.swat(swat)

    fun inCase(condition: () -> Boolean): RuleBuilder = RuleBuilder().inCase(condition)

    companion object {
        const val DEFAULT_GAME_CYCLE: Long = 50 // ms
    }
}
