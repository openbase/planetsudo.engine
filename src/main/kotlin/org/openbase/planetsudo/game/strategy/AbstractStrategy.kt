/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game.strategy

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
abstract class AbstractStrategy<LEVEL : GlobalAgentInterface<*>>(val agent: AgentInterface) : Runnable {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val strategyOwner: Agent by lazy { agent as Agent }
    val mothership: MothershipInterface by lazy { strategyOwner.mothership }
    val enemyAgent: GlobalAgentInterface<*> get() = strategyOwner.enemyAgent
    val teamAgent: GlobalAgentInterface<*> get() = strategyOwner.teamAgent
    private val mothershipInternal: Mothership by lazy { strategyOwner.mothership }
    private val rules: TreeMap<Int, Rule> = TreeMap()
    private val gameManager: GameManager = GameManager.gameManager

    @JvmField
    val agentCount: Int

    private var gameSpeedFactor: Double = GameManager.DEFAULT_GAME_SPEED_FACTOR.toDouble()

    init {
        this.agentCount = loadAgentCount()

        if (agent is Agent) {
            this.loadRules()
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

    private fun executeRule() {
        for (rule in rules.values) {
            if (rule.constraint() && strategyOwner.isMemberOfSwatTeam(rule.swatTeams)) {
                strategyOwner.lastAction = rule.name
                rule.action()
                strategyOwner.resetActionCounter()
                break
            }
        }
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
