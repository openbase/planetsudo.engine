package org.openbase.planetsudo.game.strategy

import org.openbase.planetsudo.game.SwatTeam
import org.openbase.planetsudo.level.LevelSize
import org.openbase.planetsudo.level.levelobjects.AgentInterface
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType
import org.openbase.planetsudo.level.levelobjects.Tower
import java.awt.event.KeyEvent

class DivineManualStrategy(agent: AgentInterface) : StrategyLevelLegacy(agent) {

    override fun loadAgentCount(): Int = 7

    override fun loadSwatTeams() {
        createSwat(SwatTeam.ALPHA, 1)
    }

    override fun loadRules() {
        "Workers explore" nonCommander inCase { true } then {
            agent.go()
        }
        "Workers return resources" nonCommander inCase {
            agent.isCarryingResource && !agent.isAtMothership
        } then {
            agent.goToMothership()
        }
        "Workers deliver resources" nonCommander inCase {
            agent.isCarryingResource && agent.isAtMothership
        } then {
            agent.transferResourceToMothership()
        }
        "Workers collect resources" nonCommander inCase {
            !agent.isCarryingResource && agent.seeResource && !agent.isTouchingResource(ResourceType.Mine)
        } then {
            agent.goToResource()
        }
        "Workers pick up resources" nonCommander inCase {
            !agent.isCarryingResource && agent.isTouchingResource(ResourceType.Normal)
        } then {
            agent.pickupResource()
        }
        "Workers pick up double points" nonCommander inCase {
            !agent.isCarryingResource && agent.isTouchingResource(ResourceType.DoublePoints)
        } then {
            agent.pickupResource()
        }
        "Workers pick up extreme points" nonCommander inCase {
            !agent.isCarryingResource && agent.isTouchingResource(ResourceType.ExtremPoint)
        } then {
            agent.pickupResource()
        }
        "Workers pick up mothership fuel" nonCommander inCase {
            !agent.isCarryingResource && agent.isTouchingResource(ResourceType.ExtraMothershipFuel)
        } then {
            agent.pickupResource()
        }
        "Workers collect tonic" nonCommander inCase {
            !agent.isTonicAtLimit && agent.isTouchingResource(ResourceType.Tonic)
        } then {
            agent.pickupResource()
        }
        "Workers collect fuel" nonCommander inCase {
            agent.fuelInPercent < 100 && agent.isTouchingResource(ResourceType.ExtraAgentFuel)
        } then {
            agent.pickupResource()
        }
        "Workers fight enemies" nonCommander inCase { agent.seeEnemyAgent } then {
            agent.fightWithEnemyAgent()
        }
        "Workers request help" nonCommander inCase {
            !agent.hasRequestedSupport && (agent.isUnderAttack || (!agent.isAtMothership && agent.fuel < 5))
        } then {
            agent.requestSupport()
        }
        "Workers refill at mothership" nonCommander inCase {
            agent.isAtMothership && agent.fuelInPercent < 90 && mothership.hasFuel()
        } then {
            agent.orderFuel(100)
        }
        "Workers become invisible in combat" nonCommander inCase {
            agent.tonicInPercent == 100 && agent.isVisible && agent.isFighting
        } then {
            agent.makeInvisible()
        }
        "Workers avoid walls" nonCommander inCase { agent.isCollisionDetected } then {
            agent.turnRandom(150)
        }

        "Commander move forward" commander inCase { manualControl(KeyEvent.VK_UP) } then {
            agent.go()
        }
        "Commander turn around" commander inCase { manualControl(KeyEvent.VK_DOWN) } then {
            agent.turnAround()
        }
        "Commander move left" commander inCase { manualControl(KeyEvent.VK_LEFT) } then {
            agent.goLeft(5)
        }
        "Commander move right" commander inCase { manualControl(KeyEvent.VK_RIGHT) } then {
            agent.goRight(5)
        }
        "Commander turn left" commander inCase { manualControl(KeyEvent.VK_A) } then {
            agent.turnLeft(15)
        }
        "Commander turn right" commander inCase { manualControl(KeyEvent.VK_D) } then {
            agent.turnRight(15)
        }
        "Commander shift" commander inCase { manualControl(KeyEvent.VK_SPACE) && agent.hasTonic } then {
            agent.shift()
        }
        "Commander go to marker" commander inCase { manualControl(KeyEvent.VK_G) && agent.seeMarker() } then {
            agent.goToMarker()
        }
        "Commander deploy marker" commander inCase {
            manualControl(KeyEvent.VK_M) && !mothership.isMarkerDeployed
        } then {
            agent.deployMarker()
        }
        "Commander clear marker" commander inCase {
            manualControl(KeyEvent.VK_X) && mothership.isMarkerDeployed
        } then {
            mothership.clearMarker()
        }
        "Commander request support" commander inCase {
            manualControl(KeyEvent.VK_H) && !agent.hasRequestedSupport
        } then {
            agent.requestSupport()
        }
        "Commander cancel support" commander inCase {
            manualControl(KeyEvent.VK_J) && agent.hasRequestedSupport
        } then {
            agent.cancelSupport()
        }
        "Commander seek support" commander inCase {
            manualControl(KeyEvent.VK_K) && mothership.needSomeoneSupport()
        } then {
            agent.goToSupportAgent()
        }
        "Commander order fuel" commander inCase {
            manualControl(KeyEvent.VK_F) && agent.isAtMothership && agent.fuelInPercent < 100 && mothership.hasFuel()
        } then {
            agent.orderFuel(100)
        }
        "Commander collect resource" commander inCase {
            manualControl(KeyEvent.VK_R) && agent.seeResource
        } then {
            agent.goToResource()
        }
        "Commander pick up resource" commander inCase {
            manualControl(KeyEvent.VK_P) && agent.isTouchingResource
        } then {
            agent.pickupResource()
        }
        "Commander release resource" commander inCase {
            manualControl(KeyEvent.VK_O) && agent.isCarryingResource
        } then {
            agent.releaseResource()
        }
        "Commander fight enemy agent" commander inCase {
            manualControl(KeyEvent.VK_E) && agent.seeEnemyAgent
        } then {
            agent.fightWithEnemyAgent()
        }
        "Commander fight enemy mothership" commander inCase {
            manualControl(KeyEvent.VK_Q) && agent.seeEnemyMothership
        } then {
            agent.fightWithEnemyMothership()
        }
        "Commander turn to enemy" commander inCase {
            manualControl(KeyEvent.VK_B) && agent.seeEnemyAgent
        } then {
            agent.turnToEnemyAgent()
        }
        "Commander make invisible" commander inCase {
            manualControl(KeyEvent.VK_I) && agent.isVisible && agent.tonicInPercent >= 30
        } then {
            agent.makeInvisible()
        }
        "Commander deploy mine" commander inCase { manualControl(KeyEvent.VK_N) && agent.hasMine } then {
            agent.deployMine()
        }
        "Commander build observation tower" commander inCase {
            manualControl(KeyEvent.VK_1) && agent.hasTower
        } then {
            agent.constructTower(Tower.TowerType.ObservationTower)
        }
        "Commander deconstruct tower" commander inCase {
            manualControl(KeyEvent.VK_2) && agent.seeTower()
        } then {
            agent.deconstructTower()
        }
        "Commander repair mothership" commander inCase {
            manualControl(KeyEvent.VK_3) && agent.isAtMothership
        } then {
            agent.repairMothership()
        }
        "Commander navigate to tower" commander inCase {
            manualControl(KeyEvent.VK_T) && agent.seeTower()
        } then {
            agent.goToTower()
        }
        "Commander scan tower" commander inCase {
            manualControl(KeyEvent.VK_S) &&
                tower.type == Tower.TowerType.ObservationTower &&
                tower.levelSize == LevelSize.UNKNOWN
        } then {
            tower.scanLevelSize()
        }
    }
}
