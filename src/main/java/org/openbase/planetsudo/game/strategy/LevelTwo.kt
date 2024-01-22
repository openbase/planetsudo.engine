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
class LevelTwo(agent: AgentInterface) : AbstractStrategy(agent) {

    /**
     * Hier wird angegeben wie viele Agenten dem Team zur Verfügung stehen sollen.
     * @return Anzahl der Agenten
     */
    override fun loadAgentCount(): Int {
        return 5
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
        createRule(object : Rule(60, "HelpLostAgent") {
            override fun constraint(): Boolean {
                return agent!!.seeLostTeamAgent()
            }

            override fun action() {
                agent!!.spendTeamAgentFuel(300)
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(90, "FightAgainstMothership") {
            override fun constraint(): Boolean {
                return agent!!.seeAdversaryMothership()
            }

            override fun action() {
                agent!!.fightWithAdversaryMothership()
                agent.orderSupport()
            }
        })

        // -------------------------------------------->
        createRule(object : Rule(100, "TurnToAdversaryAgent") {
            override fun constraint(): Boolean {
                return agent!!.isUnderAttack
            }

            override fun action() {
                agent!!.turnLeft(60)
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(110, "SaveMothership") {
            override fun constraint(): Boolean {
                return mothership!!.isDamaged
            }

            override fun action() {
                agent!!.goToMothership()
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(120, "RepaireMothership") {
            override fun constraint(): Boolean {
                return mothership!!.isDamaged && agent!!.isAtMothership
            }

            override fun action() {
                agent!!.repairMothership()
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(190, "FightAgainstAgent") {
            override fun constraint(): Boolean {
                return agent!!.seeAdversaryAgent()
            }

            override fun action() {
                agent!!.fightWithAdversaryAgent()
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
        createRule(object : Rule(500, "OrderFuelDuringFight") {
            override fun constraint(): Boolean {
                return (agent!!.fuel < 100) && (agent.seeAdversaryAgent() || agent.isUnderAttack) && agent.isAtMothership
            }

            override fun action() {
                agent!!.orderFuel(10)
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(550, "Pass Resource") {
            override fun constraint(): Boolean {
                return agent!!.isCarryingResource && agent.isAtMothership
            }

            override fun action() {
                agent!!.deliverResourceToMothership()
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
