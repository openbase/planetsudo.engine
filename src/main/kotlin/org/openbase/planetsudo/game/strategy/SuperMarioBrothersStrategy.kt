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
class SuperMarioBrothersStrategy(agent: AgentInterface) : AbstractStrategy(agent) {

    /**
     * Hier wird angegeben wie viele Agenten dem Team zur Verfügung stehen sollen.
     * @return Anzahl der Agenten
     */
    override fun loadAgentCount(): Int {
        return 6
    }

    override fun loadRules() {
        // -------------------------------------------->
        // ___________________________________________________________
        createRule(
            object : Rule(0, "Gehe geradeaus") {
                override fun constraint(): Boolean {
                    return true
                }

                override fun action() {
                    agent.go()
                }
            },
        )
        // ------------------------------------------------------
        createRule(
            object : Rule(10000, "Erkenne Wand") {
                override fun constraint(): Boolean {
                    return agent.isCollisionDetected
                }

                override fun action() {
                    agent.turnRandom()
                }
            },
        )
        // ___________________________________________________________
        createRule(
            object : Rule(6900, "Feind Bekämpfen") {
                override fun constraint(): Boolean {
                    return agent.seeAdversaryAgent
                }

                override fun action() {
                    agent.fightWithAdversaryAgent()
                }
            },
        )
        // ------------------------------------------------------
        createRule(
            object : Rule(8600, "Mutterschiff angreifen") {
                override fun constraint(): Boolean {
                    return agent.seeAdversaryMothership
                }

                override fun action() {
                    agent.fightWithAdversaryMothership()
                }
            },
        )
        // ------------------------------------------------------
        createRule(
            object : Rule(8500, "Gegner Schiff Mine") {
                override fun constraint(): Boolean {
                    return agent.seeAdversaryMothership && agent.hasMine
                }

                override fun action() {
                    agent.go()
                    agent.deployMine()
                }
            },
        )
        // ------------------------------------------------------
        createRule(
            object : Rule(7000, "Recoursetyp 1 abholen") {
                override fun constraint(): Boolean {
                    return agent.seeResource && agent.resourceType == ResourceType.Normal
                }

                override fun action() {
                    agent.pickupResource()
                }
            },
        ) // ------------------------------------------------------
        createRule(
            object : Rule(7200, "Recoursetyp 2 abholen") {
                override fun constraint(): Boolean {
                    return agent.seeResource && agent.resourceType == ResourceType.DoublePoints
                }

                override fun action() {
                    agent.pickupResource()
                }
            },
        ) // ------------------------------------------------------
        createRule(
            object : Rule(7300, "Recoursetyp 3 abholen") {
                override fun constraint(): Boolean {
                    return agent.seeResource && agent.resourceType == ResourceType.ExtremPoint
                }

                override fun action() {
                    agent.pickupResource()
                }
            },
        ) // ------------------------------------------------------
        createRule(
            object : Rule(7400, "Recoursetyp 4 abholen") {
                override fun constraint(): Boolean {
                    return agent.seeResource && agent.resourceType == ResourceType.ExtraMothershipFuel
                }

                override fun action() {
                    agent.pickupResource()
                }
            },
        ) // ------------------------------------------------------
        createRule(
            object : Rule(7500, "Auftanken an Resource") {
                override fun constraint(): Boolean {
                    return agent.seeResource && agent.resourceType == ResourceType.ExtraAgentFuel && agent.fuelInPercent <= 85
                }

                override fun action() {
                    agent.pickupResource()
                }
            },
        )
        // ------------------------------------------------------
        createRule(
            object : Rule(7600, "zum Mutterschiff gehen") {
                override fun constraint(): Boolean {
                    return agent.isCarryingResource
                }

                override fun action() {
                    agent.goToMothership()
                }
            },
        )
        // ------------------------------------------------------
        createRule(
            object : Rule(7800, "Resource liefern") {
                override fun constraint(): Boolean {
                    return agent.isAtMothership && agent.isCarryingResource
                }

                override fun action() {
                    agent.deliverResourceToMothership()
                }
            },
        )
        // ------------------------------------------------------
        createRule(
            object : Rule(7700, "Tank füllen") {
                override fun constraint(): Boolean {
                    return (
                        agent.fuelInPercent <= 60 &&
                            mothership.hasFuel()
                        )
                }

                override fun action() {
                    agent.goToMothership()
                }
            },
        )
        // ------------------------------------------------------
        createRule(
            object : Rule(7750, "Tank füllen") {
                override fun constraint(): Boolean {
                    return (
                        agent.fuelInPercent <= 60 &&
                            agent.isAtMothership
                        )
                }

                override fun action() {
                    agent.orderFuel(100)
                }
            },
        )
        // ------------------------------------------------------
        createRule(
            object : Rule(8900, "Verteidigen") {
                override fun constraint(): Boolean {
                    return agent.isUnderAttack
                }

                override fun action() {
                    agent.fightWithAdversaryAgent()
                }
            },
        )
        // ------------------------------------------------------
        createRule(
            object : Rule(5000, "Helfen") {
                override fun constraint(): Boolean {
                    return agent.seeLostTeamAgent() && agent.fuelInPercent >= 60
                }

                override fun action() {
                    agent.spendTeamAgentFuel(400)
                }
            },
        )
        // ------------------------------------------------------
        createRule(
            object : Rule(9999, "Reparieren") {
                override fun constraint(): Boolean {
                    return mothership.isDamaged
                }

                override fun action() {
                    agent.repairMothership()
                }
            },
        )

        // ------------------------------------------------------
        createRule(
            object : Rule(9998, "Ende") {
                override fun constraint(): Boolean {
                    return !mothership.hasFuel()
                }

                override fun action() {
                    agent.goToMothership()
                }
            },
        )
    }
}
