/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game.strategy

import org.openbase.planetsudo.level.levelobjects.AgentInterface
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class ZergStrategy(agent: AgentInterface) : StrategyLevelLegacy(agent) {

    /**
     * Hier wird angegeben wie viele Agenten dem Team zur Verfügung stehen sollen.
     *
     * @return Anzahl der Agenten
     */
    override fun loadAgentCount(): Int {
        return 6
    }

    override fun loadRules() {
        // -------------------------------------------->
        createRule(
            object : Rule(1000, "Angriff bei Sichtung vom Feind") {
                override fun constraint(): Boolean {
                    return agent.seeEnemyAgent
                }

                override fun action() {
                    agent.fightWithEnemyAgent()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(900, "Angriff Auf das Mutterschiff") {
                override fun constraint(): Boolean {
                    return agent.seeEnemyMothership
                }

                override fun action() {
                    agent.fightWithEnemyMothership()
                    agent.deployMine()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(800, "Mutterschiff Reparieren") {
                override fun constraint(): Boolean {
                    return agent.isAtMothership && (agent.fuelInPercent > 15) && (mothership.isDamaged || mothership.isBurning)
                }

                override fun action() {
                    agent.repairMothership()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(725, "Tanken") {
                override fun constraint(): Boolean {
                    return agent.fuelInPercent < 20 && agent.isAtMothership
                }

                override fun action() {
                    agent.orderFuel(100)
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(700, "Tanken gehen") {
                override fun constraint(): Boolean {
                    return agent.fuelInPercent < 35
                }

                override fun action() {
                    agent.goToMothership()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(600, "Helfen") {
                override fun constraint(): Boolean {
                    return agent.seeLostTeamAgent() && agent.fuelInPercent > 70
                }

                override fun action() {
                    agent.spendTeamAgentFuel((agent.fuelVolume * 0.10).toInt())
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(500, "Dreh Bei Wand") {
                override fun constraint(): Boolean {
                    return agent.isCollisionDetected
                }

                override fun action() {
                    agent.turnRandom()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(750, "Gib Ressource Mutterschiff") {
                override fun constraint(): Boolean {
                    return agent.isAtMothership && agent.isCarryingResource
                }

                override fun action() {
                    agent.transferResourceToMothership()
                    agent.orderFuel(100)
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(300, "Mit Ressource zum Mutterschiff") {
                override fun constraint(): Boolean {
                    return agent.isCarryingResource
                }

                override fun action() {
                    agent.goToMothership()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(200, "Sammel Ressource") {
                override fun constraint(): Boolean {
                    return agent.isTouchingResource && agent.resourceType != ResourceType.Mine
                }

                override fun action() {
                    agent.pickupResource()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(100, "Geh zu Ressource") {
                override fun constraint(): Boolean {
                    return agent.seeResource
                }

                override fun action() {
                    agent.goToResource()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(0, "Gehe Geradeaus") {
                override fun constraint(): Boolean {
                    return true
                }

                override fun action() {
                    agent.go()
                }
            },
        )
    }
}
