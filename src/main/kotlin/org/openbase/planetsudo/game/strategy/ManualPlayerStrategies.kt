package org.openbase.planetsudo.game.strategy

import org.openbase.planetsudo.game.SwatTeam
import org.openbase.planetsudo.level.levelobjects.AgentInterface

abstract class ManualPlayerStrategy(
    agent: AgentInterface,
    private val player: ManualPlayer,
) : StrategyLevelLegacy(agent) {

    override fun loadAgentCount(): Int = 2

    override fun loadSwatTeams() {
        createSwat(SwatTeam.ALPHA, 1)
    }

    override fun loadRules() {
        "Commander go forward" commander inCase { manualControl(player, ManualAction.GO_FORWARD) } then {
            agent.go()
        }
        "Commander turn left" commander inCase { manualControl(player, ManualAction.TURN_LEFT) } then {
            agent.turnLeft(5)
        }
        "Commander turn right" commander inCase { manualControl(player, ManualAction.TURN_RIGHT) } then {
            agent.turnRight(5)
        }
        "Commander go left" commander inCase {
            manualControl(player, ManualAction.GO_FORWARD) && manualControl(
                player,
                ManualAction.TURN_LEFT,
            )
        } then {
            agent.goLeft(10)
        }
        "Commander go right" commander inCase {
            manualControl(player, ManualAction.GO_FORWARD) && manualControl(
                player,
                ManualAction.TURN_RIGHT,
            )
        } then {
            agent.goRight(10)
        }

        "Commander fight enemy" commander inCase {
            manualControl(player, ManualAction.FIGHT_ENEMY) && agent.seeEnemyMothership
        } then {
            agent.fightWithEnemyMothership()
        }

        "Commander fight enemy" commander inCase {
            manualControl(player, ManualAction.FIGHT_ENEMY) && agent.seeEnemyAgent
        } then {
            agent.fightWithEnemyAgent()
        }

        "Commander set marker" commander inCase {
            manualControl(player, ManualAction.SET_MARKER) && !mothership.isMarkerDeployed
        } then {
            if (mothership.isMarkerDeployed) {
                mothership.clearMarker()
            }
            agent.deployMarker()
        }
        "Commander place mine" commander inCase {
            manualControl(player, ManualAction.PLACE_MINE) && agent.hasMine
        } then {
            agent.deployMine()
        }
        "Commander pick up resource" commander inCase {
            manualControl(player, ManualAction.PRIMARY_ACTION)
        } then {
            when {
                agent.isCarryingResource && agent.isAtMothership -> agent.transferResourceToMothership()
                mothership.isDamaged && agent.isAtMothership -> agent.repairMothership()
                agent.isAtMothership && agent.fuelInPercent < 100 && mothership.hasFuel() -> agent.orderFuel(100)
                agent.seeLostTeamAgent() -> agent.spendTeamAgentFuel(10)
                agent.isTouchingResource -> agent.pickupResource()
                agent.seeResource -> agent.goToResource()
                agent.isCarryingResource -> agent.releaseResource()
                else -> {}
            }
        }
        "Shift" commander inCase { manualControl(player, ManualAction.SHIFT) && agent.hasTonic } then {
            agent.shift()
        }
    }
}

class ManualPlayer1(agent: AgentInterface) : ManualPlayerStrategy(agent, ManualPlayer.PLAYER_1)

class ManualPlayer2(agent: AgentInterface) : ManualPlayerStrategy(agent, ManualPlayer.PLAYER_2)
