/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game.strategy

import org.openbase.planetsudo.level.levelobjects.AgentInterface

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class MarcosStrategy(agent: AgentInterface) : StrategyLevel3(agent) {

    /**
     * Hier wird angegeben wie viele Agenten dem Team zur Verfügung stehen sollen.
     * @return Anzahl der Agenten
     */
    override fun loadAgentCount(): Int {
        return 1
    }

    override fun loadRules() {
        // -------------------------------------------->
        createRule(
            object : Rule(10, "Fly arround") {
                override fun constraint(): Boolean {
                    return true
                }

                override fun action() {
                    agent.go()
                    agent.turnRight(1)
                }
            },
        )

        createRule(
            object : Rule(20, "Gehe zu Resource") {
                override fun constraint(): Boolean {
                    return agent.seeResource
                }

                override fun action() {
                    agent.goToResource()
                }
            },
        )

        createRule(
            object : Rule(30, "Resource aufsammeln") {
                override fun constraint(): Boolean {
                    return agent.isTouchingResource
                }

                override fun action() {
                    agent.pickupResource()
                }
            },
        )

        createRule(
            object : Rule(40, "Helfe Teammitglied") {
                override fun constraint(): Boolean {
                    return agent.seeLostTeamAgent()
                }

                override fun action() {
                    agent.spendTeamAgentFuel(agent.fuelVolume / 10)
                }
            },
        )

        createRule(
            object : Rule(50, "Carry Resource back") {
                override fun constraint(): Boolean {
                    return agent.isCarryingResource
                }

                override fun action() {
                    agent.goToMothership()
                }
            },
        )

        createRule(
            object : Rule(60, "Resource abgeben") {
                override fun constraint(): Boolean {
                    return agent.isAtMothership && agent.isCarryingResource
                }

                override fun action() {
                    agent.deliverResourceToMothership()
                }
            },
        )

        createRule(
            object : Rule(70, "Attackiere feindl. Mutterschiff") {
                override fun constraint(): Boolean {
                    return agent.seeAdversaryMothership
                }

                override fun action() {
                    agent.fightWithAdversaryMothership()
                }
            },
        )

        createRule(
            object : Rule(80, "Leerer Tank - Zurück gehen") {
                override fun constraint(): Boolean {
                    return agent.fuel <= agent.fuelVolume / 5 && mothership.hasFuel()
                }

                override fun action() {
                    agent.goToMothership()
                }
            },
        )

        createRule(
            object : Rule(90, "Tanken") {
                override fun constraint(): Boolean {
                    return agent.isAtMothership && agent.fuel <= agent.fuelVolume / 5 && mothership.hasFuel()
                }

                override fun action() {
                    agent.orderFuel(100)
                }
            },
        )

        createRule(
            object : Rule(100, "Fire in the house!") {
                override fun constraint(): Boolean {
                    return mothership.isDamaged
                }

                override fun action() {
                    agent.goToMothership()
                }
            },
        )

        createRule(
            object : Rule(110, "Repariere Mutterschiff") {
                override fun constraint(): Boolean {
                    return mothership.isDamaged && agent.isAtMothership
                }

                override fun action() {
                    agent.repairMothership()
                }
            },
        )

        createRule(
            object : Rule(120, "Fight!") {
                override fun constraint(): Boolean {
                    return agent.seeAdversaryAgent
                }

                override fun action() {
                    agent.fightWithAdversaryAgent()
                }
            },
        )

        createRule(
            object : Rule(130, "Alarm!") {
                override fun constraint(): Boolean {
                    return mothership.isBurning
                }

                override fun action() {
                    agent.goToMothership()
                }
            },
        )

        createRule(
            object : Rule(1000, "Collision detected") {
                override fun constraint(): Boolean {
                    return agent.isCollisionDetected
                }

                override fun action() {
                    agent.turnLeft(45)
                }
            },
        )
    }
}
