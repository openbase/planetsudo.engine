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
class JaciStrategy(agent: AgentInterface) : StrategyLevelLegacy(agent) {

    /**
     * Hier wird angegeben wie viele Agenten dem Team zur Verfügung stehen sollen.
     *
     * @return Anzahl der Agenten
     */
    override fun loadAgentCount(): Int {
        return 5
    }

    override fun loadRules() {
        // -------------------------------------------->
        createRule(
            object : Rule(0, "Gehen") {
                override fun constraint(): Boolean {
                    return true
                }

                override fun action() {
                    agent.go()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(100, "zu Ressource gehen") {
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
            object : Rule(200, "Ressource einsammeln") {
                override fun constraint(): Boolean {
                    return agent.isTouchingResource && !agent.isTouchingResource(ResourceType.Mine)
                }

                override fun action() {
                    agent.pickupResource()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(300, "Ressource zu Mutterschiff") {
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
            object : Rule(400, "lade Ressource ab") {
                override fun constraint(): Boolean {
                    return agent.isCarryingResource && agent.isAtMothership
                }

                override fun action() {
                    agent.transferResourceToMothership()
                }
            },
        )

        // -------------------------------------------->
        createRule(
            object : Rule(600, "Bekämpfe feindliches Mutterschiff") {
                override fun constraint(): Boolean {
                    return agent.seeEnemyMothership
                }

                override fun action() {
                    agent.fightWithEnemyMothership()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(700, "Bekämpfe Feind") {
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
            object : Rule(800, "Lade Mine bei Feind ab") {
                override fun constraint(): Boolean {
                    return agent.seeEnemyAgent && agent.hasMine
                }

                override fun action() {
                    agent.deployMine()
                    agent.turnAround()
                }
            },
        ) // -------------------------------------------->
        createRule(
            object : Rule(825, "Mutterschiff reparieren") {
                override fun constraint(): Boolean {
                    return mothership.isDamaged
                }

                override fun action() {
                    agent.repairMothership()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(850, "Gegen Attacke suchen") {
                override fun constraint(): Boolean {
                    return agent.isUnderAttack
                }

                override fun action() {
                    agent.turnLeft(20)
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(855, "Gegen Attacke wehren") {
                override fun constraint(): Boolean {
                    return agent.isUnderAttack && agent.seeEnemyAgent
                }

                override fun action() {
                    agent.fightWithEnemyAgent()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(860, "Mutterschiff beschützen") {
                override fun constraint(): Boolean {
                    return mothership.isDamaged && !agent.isAtMothership
                }

                override fun action() {
                    agent.goToMothership()
                }
            },
        )

        // -------------------------------------------->
        createRule(
            object : Rule(900, "Gehe tanken") {
                override fun constraint(): Boolean {
                    return agent.fuelInPercent < 20
                }

                override fun action() {
                    agent.goToMothership()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(905, "Auftanken") {
                override fun constraint(): Boolean {
                    return agent.isAtMothership && agent.fuelInPercent < 20
                }

                override fun action() {
                    agent.orderFuel(100)
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(1000, "Vermeide Kollision") {
                override fun constraint(): Boolean {
                    return agent.isCollisionDetected
                }

                override fun action() {
                    agent.turnRandom()
                }
            },
        )
    }
}
