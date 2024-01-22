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
class LevelOne(agent: AgentInterface) : AbstractStrategy(agent) {

    /**
     * Hier wird angegeben wie viele Agenten dem Team zur Verfügung stehen sollen.
     * @return Anzahl der Agenten
     */
    override fun loadAgentCount(): Int {
        return 10
    }

    override fun loadRules() {
        // -------------------------------------------->
        createRule(object : Rule(0, "Just Go") {
            override fun constraint(): Boolean {
                return true
            }

            override fun action() {
                agent!!.go()
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(20, "Search Resources") {
            override fun constraint(): Boolean {
                return agent!!.seeResource()
            }

            override fun action() {
                agent!!.goToResource()
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(30, "PickUp Resource") {
            override fun constraint(): Boolean {
                return agent!!.isTouchingResource && agent.resourceType != ResourceType.Mine
            }

            override fun action() {
                agent!!.pickupResource()
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(40, "Save Resource") {
            override fun constraint(): Boolean {
                return agent!!.isCarryingResource
            }

            override fun action() {
                agent!!.goToMothership()
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(50, "Pass Resource") {
            override fun constraint(): Boolean {
                return agent!!.isCarryingResource && agent.isAtMothership
            }

            override fun action() {
                agent!!.deliverResourceToMothership()
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(200, "GoBackToMothership") {
            override fun constraint(): Boolean {
                return agent!!.fuel < 300
            }

            override fun action() {
                agent!!.goToMothership()
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(400, "OrderFuel") {
            override fun constraint(): Boolean {
                return (agent!!.fuel < 300) && (agent.isAtMothership)
            }

            override fun action() {
                agent!!.orderFuel(100)
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(1000, "AvoidWall") {
            override fun constraint(): Boolean {
                return agent!!.isCollisionDetected
            }

            override fun action() {
                agent!!.turnRandom()
            }
        })
        // -------------------------------------------->
    }
}
