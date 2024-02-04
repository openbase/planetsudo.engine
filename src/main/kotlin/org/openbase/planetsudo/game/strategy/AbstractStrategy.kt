/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game.strategy

import org.openbase.planetsudo.game.GameManager
import org.openbase.planetsudo.game.SwatTeam
import org.openbase.planetsudo.game.SwatTeam.*
import org.openbase.planetsudo.level.AbstractLevel
import org.openbase.planetsudo.level.levelobjects.Agent
import org.openbase.planetsudo.level.levelobjects.AgentInterface
import org.openbase.planetsudo.level.levelobjects.Mothership
import org.openbase.planetsudo.level.levelobjects.MothershipInterface
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.beans.PropertyChangeEvent
import java.util.*

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
abstract class AbstractStrategy(val agent: AgentInterface) : Runnable {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val strategyOwner: Agent by lazy { agent as Agent }
    val mothership: MothershipInterface by lazy { strategyOwner.mothership }
    val mothershipInternal: Mothership by lazy { strategyOwner.mothership }
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
            strategyOwner.mothership.level.addPropertyChangeListener { evt: PropertyChangeEvent ->
                if (evt.propertyName == AbstractLevel.GAME_SPEED_FACTOR_CHANGED) {
                    gameSpeedFactor = evt.newValue as Double
                }
            }
            gameSpeedFactor = agent.mothership.level.getGameSpeedFactor()
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
            Thread.sleep((DEFAULT_GAME_CYCLE / gameSpeedFactor).toLong())
        }
        logger.info("AI of agent $agent is dead now!")
    }

    fun createRule(rule: Rule) {
        logger.debug("Create rule $rule")
        if (rules.containsKey(getKey(rule))) {
            logger.error("There exist min two rules with the same priority!")
            agent.kill()
        }
        rules[getKey(rule)] = rule
    }

    fun getKey(rule: Rule): Int {
        // Auto generate priority if rule does not contain any priority.
        if (rule.priority == -1) {
            return -rules.size
        }
        return -rule.priority // invert priority to swap list order.
    }

    protected fun executeRule() {
        for (rule in rules.values) {
            if (rule.constraint() && agent.isMemberOfSwatTeam(rule.swatTeam)) {
//                logger.debug("Select $rule")
                strategyOwner.lastAction = rule.name
                rule.action()
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
    protected fun createSwat(swatTeam: SwatTeam, vararg agentNumber: Int) {
        when (swatTeam) {
            ALL, COMMANDER -> {
                logger.error("SwatTeam[" + swatTeam.name + "] is not modifiable!")
                agent.kill()
                return
            }

            else -> {}
        }
        val agentList = mothershipInternal.getAgents()
        for (agentID in agentNumber) {
            if (agentID >= agentCount || agentID >= agentList.size) {
                logger.error("Could not create SwatTeam[" + swatTeam.name + "] because team has not enough members!")
                agent.kill()
                return
            }

            agentList.firstOrNull { it.id == agentID }?.joinSwatTeam(swatTeam)
        }
    }

    protected abstract fun loadRules()

    protected open fun loadSwatTeams() {
        // Please overwrite to create swats within your strategy!
    }

    protected abstract fun loadAgentCount(): Int

    protected infix fun RuleBuilder.then(action: AgentInterface.() -> Unit): Unit = this
        .apply { this.action = { agent.action() } }
        .build()
        .let { createRule(it) }

    protected infix fun String.all(builder: RuleBuilder): RuleBuilder =
        RuleBuilder(name = this@all).merge(builder).swat(ALL)

    protected infix fun String.commander(builder: RuleBuilder): RuleBuilder =
        RuleBuilder(name = this@commander).merge(builder).swat(COMMANDER)

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
