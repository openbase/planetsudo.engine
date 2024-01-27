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
class NoPlan(agent: AgentInterface) : AbstractStrategy(agent) {
    /**
     * Hier wird angegeben wie viele Agenten dem Team zur Verfügung stehen sollen.
     * @return Anzahl der Agenten
     */
    override fun loadAgentCount(): Int {
        return 4
    }

    override fun loadRules() {
        // -------------------------------------------->
        createRule(object : Rule(0, "Just Go") {
            override fun constraint(): Boolean {
                return true
            }

            override fun action() {
                agent.go()
                // agent.
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(999, "Wall") {
            override fun constraint(): Boolean {
                return agent.isCollisionDetected
            }

            override fun action() {
                agent.turnRight(95)
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(500, "Flee") {
            override fun constraint(): Boolean {
                return agent.seeAdversaryAgent()
            }

            override fun action() {
                if (agent.isCarryingResource) agent.releaseResource()
                agent.turnRight(90)
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(12, "Harvesting") {
            override fun constraint(): Boolean {
                return agent.isCarryingResource
            }

            override fun action() {
                if (agent.isAtMothership) {
                    agent.deliverResourceToMothership()
                } else {
                    agent.goToMothership()
                }
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(10, "Collecting") {
            override fun constraint(): Boolean {
                return agent.isTouchingResource
            }

            override fun action() {
                agent.pickupResource()
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(9, "MoveToRess") {
            override fun constraint(): Boolean {
                return agent.seeResource()
            }

            override fun action() {
                agent.goToResource()
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(13, "Help") {
            override fun constraint(): Boolean {
                return agent.seeLostTeamAgent()
            }

            override fun action() {
                agent.spendTeamAgentFuel(3)
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(800, "Fuel") {
            override fun constraint(): Boolean {
                return agent.isAtMothership && (agent.fuelInPercent <= 50)
            }

            override fun action() {
                agent.orderFuel(100)
            }
        })
    }
}
