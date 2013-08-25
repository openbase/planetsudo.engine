/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.game.strategy;

import de.dc.planetsudo.level.levelobjects.Agent;
import de.dc.planetsudo.level.levelobjects.Resource;

/**
 *
 * @author divine
 */
public class NoNameStrategy extends AbstractStrategy {

	public NoNameStrategy() {
	}
	public NoNameStrategy(Agent a) {
		super(a);
	}

	/**
	 * Wie viele Agenten sollen erstellt werde wird hier angegeben.
	 * @return Anzahl der Agenten
	 */
	@Override
	protected int loadAgentCount() {
		return 5;
	}

	@Override
	protected void loadRules() {
		//-------------------------------------------->
		createRule(new Rule(0, "GO_AHEAD") {
			@ Override
			protected boolean constraint() {
				return true;
			}
			@ Override
			protected void action() {
                            agent.goStraightAhead();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(1000, "NO_COLLISION") {
			@ Override
			protected boolean constraint() {
				return agent.collisionDetected();
			}
			@ Override
			protected void action() {
                            agent.turnRandom();
			}
		});//-------------------------------------------->
		createRule(new Rule(600, "Find_Resource") {
			@ Override
			protected boolean constraint() {
				return agent.seeResource();
			}
			@ Override
			protected void action() {
                            agent.goToResource();
			}
		});//-------------------------------------------->
		createRule(new Rule(700, "Pick_up_Resource") {
			@ Override
			protected boolean constraint() {
				return agent.touchResource() && agent.touchResourceType() != Resource.ResourceType.Bomb;
			}
			@ Override
			protected void action() {
                            agent.pickupResource();
			}
		});//-------------------------------------------->
		createRule(new Rule(800, "Resource_to_Mothership") {
			@ Override
			protected boolean constraint() {
				return agent.isCarringResource();
			}
			@ Override
			protected void action() {
                            agent.moveOneStepInTheMothershipDirection();
			}
		});//-------------------------------------------->
		createRule(new Rule(900, "Resource_to_Mothership_2") {
			@ Override
			protected boolean constraint() {
				return agent.isCarringResource()&&agent.isAtMothership();
			}
			@ Override
			protected void action() {
                            agent.deliverResourceToMothership();
			}
		});//-------------------------------------------->
		createRule(new Rule(850, "Get_Fuel") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent()<20;
			}
			@ Override
			protected void action() {
                            agent.moveOneStepInTheMothershipDirection();
			}
		});
                //-------------------------------------------->
		createRule(new Rule(950, "Get_Fuel_2") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent()<50&&agent.isAtMothership();
			}
			@ Override
			protected void action() {
                            agent.orderFuel(100);
			}
		});
                 //-------------------------------------------->
		createRule(new Rule(975, "Help") {
			@ Override
			protected boolean constraint() {
				return agent.seeLostTeamAgent()&& agent.getFuel()>=60;
			}
			@ Override
			protected void action() {
                            agent.spendFuelTeamAgent(30);
			}
		});

                 //-------------------------------------------->
		createRule(new Rule(650, "attack") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryAgent();
			}
			@ Override
			protected void action() {
                            agent.fightWithAdversaryAgent();
			}
		});

                 //-------------------------------------------->
		createRule(new Rule(650, "mine") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryMothership() && agent.hasMine();
			}
			@ Override
			protected void action() {
                            agent.placeMine();

			}
		});
                //-------------------------------------------->
		createRule(new Rule(1010, "defend") {
			@ Override
			protected boolean constraint() {
				return agent.isUnderAttack() && agent.seeAdversaryAgent();
			}
			@ Override
			protected void action() {
                            agent.fightWithAdversaryAgent();

			}
		});
                  //-------------------------------------------->
		createRule(new Rule(890, "attack mother") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryMothership();
			}
			@ Override
			protected void action() {
                            agent.fightWithAdversaryMothership();

			}
		});
                //-------------------------------------------->
		createRule(new Rule(890, "basis1") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged();
			}
			@ Override
			protected void action() {
                            mothership.getShieldForce();

			}
		});

	}
}