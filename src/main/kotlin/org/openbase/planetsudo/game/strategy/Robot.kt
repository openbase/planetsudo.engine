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
class Robot(agent: AgentInterface) : StrategyLevelLegacy(agent) {

    /**
     * Hier wird angegeben wie viele Agenten dem Team zur Verfügung stehen sollen.
     * @return Anzahl der Agenten
     */
    override fun loadAgentCount(): Int {
        return 6
    }

    override fun loadRules() {
        // -------------------------------------------->
        createRule(
            object : Rule(1000, "Drehe bei Wand") {
                override fun constraint(): Boolean {
                    return agent.isCollisionDetected
                }

                override fun action() {
                    agent.turnRandom()
                }
            },
        )

        createRule(
            object : Rule(970, "Verteidigung") {
                override fun constraint(): Boolean {
                    return agent.isUnderAttack
                }

                override fun action() {
                    agent.fightWithEnemyAgent()
                }
            },
        )
        createRule(
            object : Rule(965, "Verteidigung MS") {
                override fun constraint(): Boolean {
                    return mothership.isBurning || mothership.isDamaged
                }

                override fun action() {
                    agent.goToMothership()
                }
            },
        )
        createRule(
            object : Rule(960, "Tanken") {
                override fun constraint(): Boolean {
                    return agent.isAtMothership && agent.fuelInPercent < 75
                }

                override fun action() {
                    agent.orderFuel(100)
                }
            },
        )
        createRule(
            object : Rule(955, "Agent helfen") {
                override fun constraint(): Boolean {
                    return agent.seeLostTeamAgent()
                }

                override fun action() {
                    agent.spendTeamAgentFuel(200)
                }
            },
        )
        createRule(
            object : Rule(950, "Mine setzten") {
                override fun constraint(): Boolean {
                    return agent.hasMine && agent.seeEnemyAgent
                }

                override fun action() {
                    agent.deployMine()
                }
            },
        )
        createRule(
            object : Rule(940, "Angriff Mothership") {
                override fun constraint(): Boolean {
                    return agent.seeEnemyMothership
                }

                override fun action() {
                    agent.fightWithEnemyMothership()
                }
            },
        )
        createRule(
            object : Rule(930, "Angriff Agent") {
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
            object : Rule(820, "Resource ins Mutterschiff") {
                override fun constraint(): Boolean {
                    return agent.isAtMothership && agent.isCarryingResource
                }

                override fun action() {
                    agent.transferResourceToMothership()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(800, "Bewege zum Mutterschiff") {
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
            object : Rule(595, "Bomben Vermeiden") {
                override fun constraint(): Boolean {
                    return agent.isTouchingResource && agent.resourceType == ResourceType.Mine
                }

                override fun action() {
                    agent.turnLeft(42)
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(590, "sammle resource") {
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
            object : Rule(500, "gehe zu resource") {
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
            object : Rule(0, "gehe geradeaus") {
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
