/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game.strategy

import org.openbase.planetsudo.game.SwatTeam
import org.openbase.planetsudo.level.levelobjects.AgentInterface
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class StrategieNoxus(agent: AgentInterface) : StrategyLevel3(agent) {

    /**
     * Hier wird angegeben wie viele Agenten dem Team zur Verfügung stehen sollen.
     * @return Anzahl der Agenten
     */
    override fun loadAgentCount(): Int {
        return 2
    }

    /**
     * Hier kannst du SwatTeams aus mehreren Agenten bilden.
     * =====================================================
     * Die Agenten werden hierbei über ihre IDs hinzugefügt. Sind beispielsweise 4 Agenten in der Strategie angegeben,
     * so sind diese über die IDs 0 - 3 referenzierbar wobei Agent 0 immer für den Commander steht.
     * Bitte beachte somit, dass die Agenten ID nicht größer als N - 1 sein kann sofern N für die maximale Anzahl von Agenten steht.
     *
     * Die default Gruppen ALL und COMMANDER können anhand dieser Methode nicht modifiziert werden!
     */
    override fun loadSwatTeams() {
        createSwat(SwatTeam.ALPHA, 1)
    }

    override fun loadRules() {
        // -------------------------------------------->
        createRule(
            object : Rule(1000, "Kollision!", SwatTeam.ALL) {
                override fun constraint(): Boolean {
                    return agent.isCollisionDetected
                }

                override fun action() {
                    agent.turnRandom()
                }
            },
        )
        // -------------------------------------------->
        // -------------------------------------------->
        createRule(
            object : Rule(870, "Warte...") {
                override fun constraint(): Boolean {
                    return agent.fuelInPercent <= 6 && agent.isAtMothership && !mothership.hasFuel() && !agent.isUnderAttack && !mothership.isDamaged && !agent.isCarryingResource
                }

                override fun action() {
                }
            },
        )
        // -------------------------------------------->
        // -------------------------------------------->
        createRule(
            object : Rule(860, "Auftanken...") {
                override fun constraint(): Boolean {
                    return agent.fuelInPercent <= 7 && agent.isAtMothership && mothership.hasFuel()
                }

                override fun action() {
                    agent.orderFuel(100)
                }
            },
        )
        // -------------------------------------------->
        // -------------------------------------------->
        createRule(
            object : Rule(850, "Wenig Energie...") {
                override fun constraint(): Boolean {
                    return agent.fuelInPercent <= 7 && agent.hasFuel && !agent.isUnderAttack
                }

                override fun action() {
                    agent.goToMothership()
                }
            },
        )
        // -------------------------------------------->
        // -------------------------------------------->
        createRule(
            object : Rule(720, "Repariere Mutterschiff...") {
                override fun constraint(): Boolean {
                    return mothership.isDamaged && agent.isAtMothership && !agent.isUnderAttack
                }

                override fun action() {
                    agent.repairMothership()
                }
            },
        )
        // -------------------------------------------->
        // -------------------------------------------->
        createRule(
            object : Rule(800, "Mutterschiff helfen...") {
                override fun constraint(): Boolean {
                    return mothership.isDamaged && !agent.isAtMothership
                }

                override fun action() {
                    agent.goToMothership()
                }
            },
        )
        // -------------------------------------------->
        // -------------------------------------------->
        createRule(
            object : Rule(750, "Bekämpfe Agent...") {
                override fun constraint(): Boolean {
                    return agent.seeAdversaryAgent
                }

                override fun action() {
                    agent.fightWithAdversaryAgent()
                }
            },
        )
        // -------------------------------------------->
        // -------------------------------------------->
        createRule(
            object : Rule(740, "Suche Feind...") {
                override fun constraint(): Boolean {
                    return agent.isUnderAttack
                }

                override fun action() {
                    agent.turnRandom()
                }
            },
        )
        // -------------------------------------------->
        // -------------------------------------------->
        createRule(
            object : Rule(610, "Mutterschiff verminen...") {
                override fun constraint(): Boolean {
                    return agent.seeAdversaryMothership && agent.hasMine
                }

                override fun action() {
                    agent.deployMine()
                }
            },
        )
        // -------------------------------------------->
        // -------------------------------------------->
        createRule(
            object : Rule(600, "Bekämpfe Mutterschiff...") {
                override fun constraint(): Boolean {
                    return agent.seeAdversaryMothership
                }

                override fun action() {
                    agent.deployMarker()
                    agent.fightWithAdversaryMothership()
                }
            },
        )
        // -------------------------------------------->
        // -------------------------------------------->
        createRule(
            object : Rule(350, "Notkanal schließen.") {
                override fun constraint(): Boolean {
                    return agent.isSupportOrdered && agent.fuelInPercent >= 7
                }

                override fun action() {
                    agent.cancelSupport()
                }
            },
        )
        // -------------------------------------------->
        // -------------------------------------------->
        createRule(
            object : Rule(340, "Gestrandet...") {
                override fun constraint(): Boolean {
                    return !agent.hasFuel && !agent.isAtMothership
                }

                override fun action() {
                    agent.orderSupport()
                }
            },
        )
        // -------------------------------------------->
        // -------------------------------------------->
        createRule(
            object : Rule(310, "Energie Spenden...") {
                override fun constraint(): Boolean {
                    return mothership.needSomeoneSupport() && agent.seeLostTeamAgent()
                }

                override fun action() {
                    agent.spendTeamAgentFuel(7)
                }
            },
        )
        // -------------------------------------------->
        // -------------------------------------------->
        createRule(
            object : Rule(300, "Agenten Helfen...") {
                override fun constraint(): Boolean {
                    return mothership.needSomeoneSupport()
                }

                override fun action() {
                    agent.goToSupportAgent()
                }
            },
        )
        // -------------------------------------------->
        // -------------------------------------------->
        createRule(
            object : Rule(250, "Resource abliefern!") {
                override fun constraint(): Boolean {
                    return agent.isAtMothership && agent.isCarryingResource
                }

                override fun action() {
                    agent.deliverResourceToMothership()
                    agent.turnAround()
                }
            },
        )
        // -------------------------------------------->
        // -------------------------------------------->
        createRule(
            object : Rule(230, "Berge Resource...") {
                override fun constraint(): Boolean {
                    return agent.isCarryingResource
                }

                override fun action() {
                    agent.goToMothership()
                }
            },
        )
        // -------------------------------------------->
        // -------------------------------------------->
        createRule(
            object : Rule(220, "Mine!") {
                override fun constraint(): Boolean {
                    return (agent.isTouchingResource(ResourceType.Mine) || agent.isTouchingResource(ResourceType.ExtremPoint))
                }

                override fun action() {
                    agent.go()
                }
            },
        )
        // -------------------------------------------->
        // -------------------------------------------->
        createRule(
            object : Rule(210, "Resource aufheben!") {
                override fun constraint(): Boolean {
                    return agent.isTouchingResource && !agent.isTouchingResource(ResourceType.Mine) && !agent.isTouchingResource(
                        ResourceType.ExtremPoint,
                    )
                }

                override fun action() {
                    agent.pickupResource()
                }
            },
        )
        // -------------------------------------------->
        // -------------------------------------------->
        createRule(
            object : Rule(200, "Resource gesichtet...") {
                override fun constraint(): Boolean {
                    return agent.seeResource
                }

                override fun action() {
                    agent.goToResource()
                }
            },
        )
        // -------------------------------------------->
        // -------------------------------------------->
        createRule(
            object : Rule(30, "Zwischentanken...") {
                override fun constraint(): Boolean {
                    return agent.isAtMothership && mothership.hasFuel() && !mothership.isBurning && !mothership.isDamaged && !agent.isUnderAttack && agent.fuelInPercent <= 95
                }

                override fun action() {
                    agent.orderFuel(100)
                }
            },
        )
        // -------------------------------------------->
        // -------------------------------------------->
        createRule(
            object : Rule(20, "Marker entfernen.") {
                override fun constraint(): Boolean {
                    return agent.seeMarker()
                }

                override fun action() {
                    mothership.clearMarker()
                }
            },
        )
        // -------------------------------------------->
        // -------------------------------------------->
        createRule(
            object : Rule(10, "Bewege zu Marker...") {
                override fun constraint(): Boolean {
                    return mothership.isMarkerDeployed
                }

                override fun action() {
                    agent.goToMarker()
                }
            },
        )
        // -------------------------------------------->
        // -------------------------------------------->
        createRule(
            object : Rule(0, "Bewegung...") {
                override fun constraint(): Boolean {
                    return true
                }

                override fun action() {
                    agent.go()
                }
            },
        )
        // -------------------------------------------->
    }
}
