package org.openbase.planetsudo.game.strategy

import org.openbase.planetsudo.game.SwatTeam
import org.openbase.planetsudo.level.levelobjects.AgentInterface
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType
import org.openbase.planetsudo.level.levelobjects.Tower

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class DivineStrategy(agent: AgentInterface) : StrategyLevelLegacy(agent) {

    /**
     * Hier wird angegeben wie viele Agenten dem Team zur Verfügung stehen sollen.
     *
     * @return Anzahl der Agenten
     */
    override fun loadAgentCount(): Int {
        return 7
    }

    override fun loadSwatTeams() {
        createSwat(SwatTeam.ALPHA, 1)
    }

    override fun loadRules() {
        // -------------------------------------------->
        createRule(
            object : Rule("Just Go", SwatTeam.ALL) {
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
            object : Rule("Discover", SwatTeam.COMMANDER) {
                override fun constraint(): Boolean {
                    return true
                }

                override fun action() {
                    agent.goRight(4)
                }
            },
        )
        // -------------------------------------------->
//        createRule(
//            object : Rule("Avoid Agents", SwatTeam.COMMANDER) {
//                override fun constraint(): Boolean {
//                    return agent.seeTeamAgent
//                }
//
//                override fun action() {
//                    agent.turnRandom()
//                }
//            },
//        )
        // -------------------------------------------->
        "See Resources" swat SwatTeam.NOT_COMMANDER inCase {
            agent.seeResource &&
                (agent.isTonicAtLimit && agent.seeResource(ResourceType.Tonic)).not()
        } then {
            agent.goToResource()
        }
        // -------------------------------------------->
        createRule(
            object : Rule("PickUp 1P Resource") {
                override fun constraint(): Boolean {
                    return !agent.isCommander && agent.isTouchingResource(ResourceType.Normal)
                }

                override fun action() {
                    agent.pickupResource()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("Go to Marker") {
                override fun constraint(): Boolean {
                    return !agent.isCommander && mothership.isMarkerDeployed
                }

                override fun action() {
                    agent.goToMarker()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("Search") {
                override fun constraint(): Boolean {
                    return !agent.isCommander && agent.seeMarker()
                }

                override fun action() {
                    mothership.clearMarker()
                    agent.searchResources()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("PickUp") {
                override fun constraint(): Boolean {
                    return !agent.isCommander && (
                        agent.isTouchingResource(ResourceType.DoublePoints) || agent.isTouchingResource(
                            ResourceType.ExtraMothershipFuel,
                        )
                        )
                }

                override fun action() {
                    agent.pickupResource()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("PickUp and Place") {
                override fun constraint(): Boolean {
                    return !agent.isCommander && agent.isTouchingResource(ResourceType.ExtremPoint)
                }

                override fun action() {
                    if (!mothership.isMarkerDeployed) {
                        agent.deployMarker()
                    }
                    agent.pickupResource()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("PickUp and Place") {
                override fun constraint(): Boolean {
                    return agent.isCommander && (
                        agent.isTouchingResource(ResourceType.DoublePoints) || agent.isTouchingResource(
                            ResourceType.ExtraMothershipFuel,
                        )
                        ) && !mothership.isMarkerDeployed && !agent.seeMarker()
                }

                override fun action() {
                    agent.deployMarker()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("PickUp 5P and Place") {
                override fun constraint(): Boolean {
                    return !agent.isCommander && agent.isTouchingResource(ResourceType.ExtremPoint)
                }

                override fun action() {
                    agent.deployMarker()
                    agent.pickupResource()
                }
            },
        )
        // -------------------------------------------->
        "PickUp Tonic" all inCase { agent.isTouchingResource(ResourceType.Tonic) && !agent.isTonicAtLimit } then {
            agent.pickupResource()
        }
        // -------------------------------------------->
        createRule(
            object : Rule("Discover 5P and Place Marker", SwatTeam.COMMANDER) {
                override fun constraint(): Boolean {
                    return agent.isTouchingResource(ResourceType.ExtremPoint) && !agent.seeMarker()
                }

                override fun action() {
                    agent.deployMarker()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("Place Tower", SwatTeam.COMMANDER) {
                override fun constraint(): Boolean {
                    return agent.isTouchingResource(ResourceType.ExtremPoint) && agent.hasTower
                }

                override fun action() {
                    agent.constructTower(Tower.TowerType.ObservationTower)
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("Ignore") {
                override fun constraint(): Boolean {
                    return agent.isTouchingResource(ResourceType.Mine) || agent.isTouchingResource(ResourceType.ExtraAgentFuel)
                }

                override fun action() {
                    agent.go()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("Secure") {
                override fun constraint(): Boolean {
                    return agent.isGameOverSoon && !agent.isAtMothership
                }

                override fun action() {
                    agent.shift()
                    agent.goToMothership()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("Mark Resource") {
                override fun constraint(): Boolean {
                    return agent.isCarryingResource && !mothership.isMarkerDeployed && agent.seeResource
                }

                override fun action() {
                    agent.deployMarker()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("Saved") {
                override fun constraint(): Boolean {
                    return agent.isGameOverSoon && agent.isAtMothership
                }

                override fun action() {
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("Order Fuel") {
                override fun constraint(): Boolean {
                    return agent.isAtMothership && !agent.isGameOverSoon && mothership.hasFuel() && agent.fuelInPercent < 90
                }

                override fun action() {
                    agent.orderFuel(100)
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("Support Agent") {
                override fun constraint(): Boolean {
                    return mothership.needSomeoneSupport() && !agent.hasRequestedSupport
                }

                override fun action() {
                    agent.goToSupportAgent()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("Save Resource") {
                override fun constraint(): Boolean {
                    return agent.isCarryingResource && !agent.isAtMothership
                }

                override fun action() {
                    agent.goToMothership()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("Shift for Extra Resource") {
                override fun constraint(): Boolean {
                    return agent.isCarryingResource(ResourceType.ExtremPoint) && agent.hasTonic && !agent.isShifting && !agent.isAtMothership
                }

                override fun action() {
                    agent.shift()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("HelpLostAgent") {
                override fun constraint(): Boolean {
                    return agent.seeLostTeamAgent() && !teamAgent.isAtMothership
                }

                override fun action() {
                    agent.spendTeamAgentFuel(300)
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("FightAgainstMothership") {
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
            object : Rule("FightAgainstMothership & Order Support") {
                override fun constraint(): Boolean {
                    return !agent.hasRequestedSupport && agent.seeEnemyMothership
                }

                override fun action() {
                    agent.fightWithEnemyMothership()
                    agent.requestSupport()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("TurnToAdversaryAgent") {
                override fun constraint(): Boolean {
                    return agent.isUnderAttack
                }

                override fun action() {
                    agent.turnLeft(60)
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("SaveMothership") {
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
            object : Rule("RepaireMothership") {
                override fun constraint(): Boolean {
                    return mothership.isDamaged && agent.isAtMothership
                }

                override fun action() {
                    agent.repairMothership()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("FightAgainstAgent") {
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
            object : Rule("PlaceMine") {
                override fun constraint(): Boolean {
                    return agent.hasMine && agent.isUnderAttack
                }

                override fun action() {
                    agent.deployMine()
                    agent.goLeft(180)
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("GoBackToMothership") {
                override fun constraint(): Boolean {
                    return agent.fuel < 300 && !agent.isAtMothership
                }

                override fun action() {
                    agent.goToMothership()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("OrderFuel") {
                override fun constraint(): Boolean {
                    return mothership.hasFuel() && (agent.fuelInPercent < 90) && (agent.isAtMothership)
                }

                override fun action() {
                    agent.orderFuel(100)
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("OrderFuelDuringFight") {
                override fun constraint(): Boolean {
                    return mothership.hasFuel() && (agent.fuel < 100) && agent.isUnderAttack && agent.isAtMothership
                }

                override fun action() {
                    agent.orderFuel(5)
                    agent.goLeft(10)
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("OrderFuelDuringFight") {
                override fun constraint(): Boolean {
                    return mothership.hasFuel() && (agent.fuel < 100) && agent.seeEnemyAgent && agent.isAtMothership
                }

                override fun action() {
                    agent.orderFuel(5)
                    agent.fightWithEnemyAgent()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("Pass Resource") {
                override fun constraint(): Boolean {
                    return agent.isCarryingResource && agent.isAtMothership
                }

                override fun action() {
                    agent.transferResourceToMothership()
                    agent.turnAround()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("CallForHelpDuringFight") {
                override fun constraint(): Boolean {
                    return !agent.hasRequestedSupport && agent.isUnderAttack
                }

                override fun action() {
                    agent.requestSupport()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("CallForHelp") {
                override fun constraint(): Boolean {
                    return !agent.hasRequestedSupport && !agent.isAtMothership && agent.fuel < 5
                }

                override fun action() {
                    agent.requestSupport()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("PickUp Fuel") {
                override fun constraint(): Boolean {
                    return agent.fuelInPercent < 50 && agent.isTouchingResource(ResourceType.ExtraAgentFuel)
                }

                override fun action() {
                    agent.pickupResource()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("Follow Wall", SwatTeam.COMMANDER) {
                override fun constraint(): Boolean {
                    return agent.isCollisionDetected
                }

                override fun action() {
                    agent.turnLeft(4)
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("Construct Tower", SwatTeam.COMMANDER) {
                override fun constraint(): Boolean {
                    return agent.hasTower && agent.seeResource
                }

                override fun action() {
                    agent.constructTower(Tower.TowerType.DefenceTower)
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("Follow Wall", SwatTeam.ALPHA) {
                override fun constraint(): Boolean {
                    return agent.isCollisionDetected
                }

                override fun action() {
                    agent.turnRight(4)
                }
            },
        )
        // -------------------------------------------->
        "Spy" all inCase { agent.isInvisible && agent.seeEnemyAgent } then {
            agent.goToEnemyAgent()
        }
        "Attack Mothership" all inCase { agent.isInvisible && agent.seeEnemyMothership } then {
            agent.fightWithEnemyMothership()
        }
        "Attack Agent" all inCase { agent.isInvisible && agent.seeEnemyAgent && agent.seeEnemyMothership } then {
            agent.fightWithEnemyAgent()
        }
        "Prepare for Attack" all inCase { agent.isInvisible && agent.seeEnemyMothership && agent.actionPoints < 10000 } then {
            // wait for APs
        }
        "Call Force" all inCase { agent.isInvisible && agent.seeEnemyMothership && !agent.hasRequestedSupport } then {
            agent.requestSupport()
        }
        // -------------------------------------------->
        "Make invisible" all inCase { agent.tonicInPercent == 100 && agent.isVisible && agent.isFighting } then {
            agent.makeInvisible()
        }
        // -------------------------------------------->
        createRule(
            object : Rule("AvoidWall", SwatTeam.NOT_ALPHA, SwatTeam.NOT_COMMANDER) {
                override fun constraint(): Boolean {
                    return agent.isCollisionDetected
                }

                override fun action() {
                    agent.turnRandom(150)
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule("Cancel Support") {
                override fun constraint(): Boolean {
                    return agent.hasRequestedSupport && !agent.seeEnemyMothership && agent.fuel > 10 && !agent.isUnderAttack && !agent.seeEnemyAgent
                }

                override fun action() {
                    agent.cancelSupport()
                }
            },
        )
        // -------------------------------------------->
    }
}
