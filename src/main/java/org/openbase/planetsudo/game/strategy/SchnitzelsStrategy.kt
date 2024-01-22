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
class SchnitzelsStrategy(agent: AgentInterface) : AbstractStrategy(agent) {

    /**
     * Hier wird angegeben wie viele Agenten dem Team zur Verfügung stehen sollen.
     * @return Anzahl der Agenten
     */
    override fun loadAgentCount(): Int {
        return 5
    }

    override fun loadRules() {
        // -------------------------------------------->
        createRule(object : Rule(1000, "Drehe bei Wand") {
            override fun constraint(): Boolean {
                return agent!!.isCollisionDetected
            }

            override fun action() {
                agent!!.turnRandom()
            }
        })

        // -------------------------------------------->
        createRule(object : Rule(0, "Gehe geradeaus") {
            override fun constraint(): Boolean {
                return true
            }

            override fun action() {
                agent!!.go()
            }
        })

        // -------------------------------------------->
        createRule(object : Rule(900, "Sehe Resource") {
            override fun constraint(): Boolean {
                return agent!!.seeResource()
            }

            override fun action() {
                agent!!.goToResource()
            }
        })

        // -------------------------------------------->
        createRule(object : Rule(901, "Berührt Resource") {
            override fun constraint(): Boolean {
                return agent!!.isTouchingResource && agent.resourceType !=
                    ResourceType.Mine
            }

            override fun action() {
                agent!!.pickupResource()
            }
        })

        // -------------------------------------------->
        createRule(object : Rule(902, "Trägt Resource") {
            override fun constraint(): Boolean {
                return agent!!.isCarryingResource
            }

            override fun action() {
                agent!!.goToMothership()
            }
        })

        // -------------------------------------------->
        createRule(object : Rule(903, "Beim Mutterschiff und Tankcheck") {
            override fun constraint(): Boolean {
                return agent!!.isAtMothership && agent.fuelInPercent <= 65
            }

            override fun action() {
                agent!!.orderFuel(100)
            }
        })

        // -------------------------------------------->
        createRule(object : Rule(904, "Beim Mutterschiff und Resource") {
            override fun constraint(): Boolean {
                return agent!!.isAtMothership && agent.isCarryingResource
            }

            override fun action() {
                agent!!.deliverResourceToMothership()
            }
        })

        // -------------------------------------------->
        createRule(object : Rule(950, "Unter beschuss") {
            override fun constraint(): Boolean {
                return agent!!.isUnderAttack
            }

            override fun action() {
                agent!!.fightWithAdversaryAgent()
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(945, "Feindkontakt") {
            override fun constraint(): Boolean {
                return agent!!.seeAdversaryAgent()
            }

            override fun action() {
                agent!!.fightWithAdversaryAgent()
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(940, "Agent helfen") {
            override fun constraint(): Boolean {
                return agent!!.seeLostTeamAgent()
            }

            override fun action() {
                agent!!.spendTeamAgentFuel(75)
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(799, "Feindliches Mutterschiff") {
            override fun constraint(): Boolean {
                return agent!!.seeAdversaryMothership()
            }

            override fun action() {
                agent!!.fightWithAdversaryMothership()
            }
        })
        // -------------------------------------------->
        createRule(object : Rule(400, "Mutterschiff reparieren") {
            override fun constraint(): Boolean {
                return mothership!!.isDamaged
            }

            override fun action() {
                agent!!.repairMothership()
            }
        })
    }
}
