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
class NoNameStrategy(agent: AgentInterface) : AbstractStrategy(agent) {

    /**
     * Hier wird angegeben wie viele Agenten dem Team zur Verfügung stehen sollen.
     * @return Anzahl der Agenten
     */
    override fun loadAgentCount(): Int {
        return 5
    }

    override fun loadRules() {
        // -------------------------------------------->
        createRule(
            object : Rule(0, "GO_AHEAD") {
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
            object : Rule(1000, "NO_COLLISION") {
                override fun constraint(): Boolean {
                    return agent.isCollisionDetected
                }

                override fun action() {
                    agent.turnRandom()
                }
            },
        ) // -------------------------------------------->
        createRule(
            object : Rule(600, "Find_Resource") {
                override fun constraint(): Boolean {
                    return agent.seeResource
                }

                override fun action() {
                    agent.goToResource()
                }
            },
        ) // -------------------------------------------->
        createRule(
            object : Rule(700, "Pick_up_Resource") {
                override fun constraint(): Boolean {
                    return agent.isTouchingResource && agent.resourceType != ResourceType.Mine
                }

                override fun action() {
                    agent.pickupResource()
                }
            },
        ) // -------------------------------------------->
        createRule(
            object : Rule(800, "Resource_to_Mothership") {
                override fun constraint(): Boolean {
                    return agent.isCarryingResource
                }

                override fun action() {
                    agent.goToMothership()
                }
            },
        ) // -------------------------------------------->
        createRule(
            object : Rule(900, "Resource_to_Mothership_2") {
                override fun constraint(): Boolean {
                    return agent.isCarryingResource && agent.isAtMothership
                }

                override fun action() {
                    agent.deliverResourceToMothership()
                }
            },
        ) // -------------------------------------------->
        createRule(
            object : Rule(850, "Get_Fuel") {
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
            object : Rule(950, "Get_Fuel_2") {
                override fun constraint(): Boolean {
                    return agent.fuelInPercent < 50 && agent.isAtMothership
                }

                override fun action() {
                    agent.orderFuel(100)
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(975, "Help") {
                override fun constraint(): Boolean {
                    return agent.seeLostTeamAgent() && agent.fuel >= 60
                }

                override fun action() {
                    agent.spendTeamAgentFuel(30)
                }
            },
        )

        // -------------------------------------------->
        createRule(
            object : Rule(650, "attack") {
                override fun constraint(): Boolean {
                    return agent.seeAdversaryAgent
                }

                override fun action() {
                    agent.fightWithAdversaryAgent()
                }
            },
        )

        // -------------------------------------------->
        createRule(
            object : Rule(650, "mine") {
                override fun constraint(): Boolean {
                    return agent.seeAdversaryMothership && agent.hasMine
                }

                override fun action() {
                    agent.deployMine()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(1010, "defend") {
                override fun constraint(): Boolean {
                    return agent.isUnderAttack && agent.seeAdversaryAgent
                }

                override fun action() {
                    agent.fightWithAdversaryAgent()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(890, "attack mother") {
                override fun constraint(): Boolean {
                    return agent.seeAdversaryMothership
                }

                override fun action() {
                    agent.fightWithAdversaryMothership()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(890, "basis1") {
                override fun constraint(): Boolean {
                    return mothership.isDamaged
                }

                override fun action() {
                    mothership.shieldForce
                }
            },
        )
    }
}
