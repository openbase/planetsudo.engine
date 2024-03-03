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
class KillAllStrategy(agent: AgentInterface) : StrategyLevelLegacy(agent) {

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
            object : Rule(0, "Just Go") {
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
            object : Rule(1010, "Wand!") {
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
            object : Rule(500, "Rohstoff S.") {
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
            object : Rule(510, "Rohstoff T.") {
                override fun constraint(): Boolean {
                    return agent.resourceType != ResourceType.Mine &&
                        agent.isTouchingResource
                }

                override fun action() {
                    agent.pickupResource()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(600, "Rohstoff Z.") {
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
            object : Rule(1005, "Rohstoff A.") {
                override fun constraint(): Boolean {
                    return agent.isCarryingResource &&
                        agent.isAtMothership
                }

                override fun action() {
                    agent.deliverResourceToMothership()
                }
            },
        )

        // -------------------------------------------->
        createRule(
            object : Rule(997, "Tank Bewachung") {
                override fun constraint(): Boolean {
                    return agent.fuelInPercent <= 40
                }

                override fun action() {
                    agent.goToMothership()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(999, "Tankbewachung2.") {
                override fun constraint(): Boolean {
                    return agent.fuelInPercent <= 40 &&
                        agent.isAtMothership
                }

                override fun action() {
                    agent.orderFuel(100)
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(701, "Feinkontakt") {
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
            object : Rule(702, "Big Feinkontakt") {
                override fun constraint(): Boolean {
                    return agent.seeAdversaryMothership
                }

                override fun action() {
                    agent.fightWithAdversaryMothership()
                }
            },
        )
        createRule(
            object : Rule(900, "Toter freund") {
                override fun constraint(): Boolean {
                    return agent.seeLostTeamAgent()
                }

                override fun action() {
                    agent.spendTeamAgentFuel(400)
                }
            },
        )

        createRule(
            object : Rule(904, "unterbeschuss 2") {
                override fun constraint(): Boolean {
                    return agent.isUnderAttack &&
                        !agent.seeAdversaryAgent && agent.fuelInPercent >= 50
                }

                override fun action() {
                    agent.turnRight(33)
                }
            },
        )
        createRule(
            object : Rule(903, "unterbeschuss 2") {
                override fun constraint(): Boolean {
                    return agent.isUnderAttack &&
                        !agent.seeAdversaryAgent && agent.fuelInPercent < 50
                }

                override fun action() {
                    agent.goToMothership()
                }
            },
        )
        createRule(
            object : Rule(903, "mothership repair") {
                override fun constraint(): Boolean {
                    return (
                        mothership.shieldForce <= 55 &&
                            !agent.isAtMothership
                        )
                }

                override fun action() {
                    agent.goToMothership()
                }
            },
        )
        createRule(
            object : Rule(903, "mothership repair") {
                override fun constraint(): Boolean {
                    return (
                        mothership.shieldForce <= 55 &&
                            agent.isAtMothership
                        )
                }

                override fun action() {
                    agent.repairMothership()
                }
            },
        )
    }
}
