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
class BerlusconiStrategy(agent: AgentInterface) : StrategyLevelLegacy(agent) {

    /**
     * Hier wird angegeben wie viele Agenten dem Team zur Verfügung stehen sollen.
     * @return Anzahl der Agenten
     */
    override fun loadAgentCount(): Int {
        return 8
    }

    override fun loadRules() {
        // -------------------------------------------->
        createRule(
            object : Rule(1000, "Drehe bei Wand") {
                override fun constraint(): Boolean {
                    return agent.isCollisionDetected
                }

                override fun action() {
                    agent.turnRandom()
                }
            },
        )

        // -------------------------------------------->
        createRule(
            object : Rule(700, "Sehe Resource") {
                override fun constraint(): Boolean {
                    return agent.seeResource
                }

                override fun action() {
                    agent.goToResource()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(800, "Schnapp die Resource") {
                override fun constraint(): Boolean {
                    return agent.isTouchingResource
                }

                override fun action() {
                    agent.pickupResource()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(900, "Bring Resource heim") {
                override fun constraint(): Boolean {
                    return agent.isCarryingResource
                }

                override fun action() {
                    agent.goToMothership()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(902, "Lade ab") {
                override fun constraint(): Boolean {
                    return agent.isAtMothership && agent.isCarryingResource
                }

                override fun action() {
                    agent.transferResourceToMothership()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(801, "Feuer") {
                override fun constraint(): Boolean {
                    return agent.seeEnemyAgent && agent.isCarryingResource != true
                }

                override fun action() {
                    agent.fightWithEnemyAgent()
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(500, "tanken") {
                override fun constraint(): Boolean {
                    return agent.isAtMothership && agent.fuelInPercent <= 80
                }

                override fun action() {
                    agent.orderFuel(100)
                }
            },
        )
        // -------------------------------------------->
        createRule(
            object : Rule(1, "gehe geradeaus") {
                override fun constraint(): Boolean {
                    return true
                }

                override fun action() {
                    agent.go()
                }
            },
        )
        /*//-------------------------------------------->
		createRule(new Rule(100, "Notfall") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged();
			}
			@ Override
			protected void action() {
				agent.moveOneStepInTheMothershipDirection();
			}
		});
                //-------------------------------------------->
		createRule(new Rule(101, "Notfall2") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged();
			}
			@ Override
			protected void action() {
				agent.releaseResource();
			}
		});
                //-------------------------------------------->
		createRule(new Rule(802, "Überlebensinstinkt") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent()<=30;
			}
			@ Override
			protected void action() {
				agent.moveOneStepInTheMothershipDirection();
			}
		}); */
    }
}
