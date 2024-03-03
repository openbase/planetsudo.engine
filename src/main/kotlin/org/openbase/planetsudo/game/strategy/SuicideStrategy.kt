package org.openbase.planetsudo.game.strategy

import org.openbase.planetsudo.level.levelobjects.AgentInterface

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class SuicideStrategy(agent: AgentInterface) : StrategyLevelLegacy(agent) {

    /**
     * Hier wird angegeben wie viele Agenten dem Team zur Verfügung stehen sollen.
     *
     * @return Anzahl der Agenten
     */
    override fun loadAgentCount(): Int {
        return 1
    }

    override fun loadSwatTeams() {
    }

    override fun loadRules() {
        "Kill" all inCase { true } then { agent.kill() }
    }
}
