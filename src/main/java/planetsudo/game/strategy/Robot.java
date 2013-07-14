/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.game.strategy;

import planetsudo.level.levelobjects.Agent;
import planetsudo.level.levelobjects.Resource;

/**
 *
 * @author divine
 */
public class Robot extends AbstractStrategy {

	public Robot() {
	}
	public Robot(Agent a) {
		super(a);
	}

	/**
	 * Wie viele Agenten sollen erstellt werde wird hier angegeben.
	 * @return Anzahl der Agenten
	 */
	@Override
	protected int loadAgentCount() {
		return 6;
	}

	@Override
	protected void loadRules() {
		//-------------------------------------------->
		createRule(new Rule(1000, "Drehe bei Wand") {
			@ Override
			protected boolean constraint() {
				return agent.collisionDetected();
			}
			@ Override
			protected void action() {
				agent.turnRandom();
			}
		});

		createRule(new Rule(970, "Verteidigung") {
			@ Override
			protected boolean constraint() {
				return agent.isUnderAttack();
			}
			@ Override
			protected void action() {
				agent.fightWithAdversaryAgent();
			}
		});
                createRule(new Rule(965, "Verteidigung MS") {
			@ Override
			protected boolean constraint() {
				return mothership.isBurning() || mothership.isDamaged();
			}
			@ Override
			protected void action() {
				agent.moveOneStepInTheMothershipDirection();
			}
		});
                createRule(new Rule(960, "Tanken") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && agent.getFuelInPercent() < 75;
			}
			@ Override
			protected void action() {
				agent.orderFuel(100);
			}
		});
                                createRule(new Rule(955, "Agent helfen") {
			@ Override
			protected boolean constraint() {
				return agent.seeLostTeamAgent();
			}
			@ Override
			protected void action() {
				agent.spendFuelTeamAgent(200);
			}
		});
                createRule(new Rule(950, "Mine setzten") {
			@ Override
			protected boolean constraint() {
				return agent.hasMine() && agent.seeAdversaryAgent();
			}
			@ Override
			protected void action() {
				agent.placeMine();
			}
		});
                createRule(new Rule(940, "Angriff Mothership") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryMothership();
			}
			@ Override
			protected void action() {
				agent.fightWithAdversaryMothership();
			}
		});
                createRule(new Rule(930, "Angriff Agent") {
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
		createRule(new Rule(820, "Resource ins Mutterschiff") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership()&& agent.isCarringResource();
			}
			@ Override
			protected void action() {
				agent.deliverResourceToMothership();
			}
		});
                //-------------------------------------------->
		createRule(new Rule(800, "Bewege zum Mutterschiff") {
			@ Override
			protected boolean constraint() {
				return agent.isCarringResource();
			}
			@ Override
			protected void action() {
				agent.moveOneStepInTheMothershipDirection();
			}
		});
                                 //-------------------------------------------->
		createRule(new Rule(595, "Bomben Vermeiden") {
			@ Override
			protected boolean constraint() {
				return agent.touchResource() && agent.touchResourceType()==Resource.ResourceType.Bomb ;
			}
			@ Override
			protected void action() {
				agent.turnLeft(42);
			}
		});
                 //-------------------------------------------->
		createRule(new Rule(590, "sammle resource") {
			@ Override
			protected boolean constraint() {
				return agent.touchResource() && agent.touchResourceType()!=Resource.ResourceType.Bomb ;
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});
          
	
                //-------------------------------------------->
		createRule(new Rule(500, "gehe zu resource") {
			@ Override
			protected boolean constraint() {
				return agent.seeResource ();
			}
			@ Override
			protected void action() {
				agent.goToResource();
			}
		});
                //-------------------------------------------->
		createRule(new Rule(0, "gehe geradeaus") {
			@ Override
			protected boolean constraint() {
				return true;
			}
			@ Override
			protected void action() {
				agent.goStraightAhead();
			}
		});
	}
}
