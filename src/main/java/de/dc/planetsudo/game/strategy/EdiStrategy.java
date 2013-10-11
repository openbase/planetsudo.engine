/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.game.strategy;

import de.dc.planetsudo.level.levelobjects.Agent;


/**
 *
 * @author divine
 */
public class EdiStrategy extends AbstractStrategy {

	public EdiStrategy() {
	}
	public EdiStrategy(Agent a) {
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
		createRule(new Rule(0, "Just Go") {
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
		createRule(new Rule(400, "resource sehen") {
			@ Override
			protected boolean constraint() {
				return agent.seeResource();
			}
			@ Override
			protected void action() {
                            agent.goToResource();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(500, "resource aufheben") {
			@ Override
			protected boolean constraint() {
				return agent.touchResource();
			}
			@ Override
			protected void action() {
                            agent.pickupResource();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(600, "zum Mutterschiff") {
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
		createRule(new Rule(700, "ablegen resource") {
			@ Override
			protected boolean constraint() {
				return agent.isCarringResource() && agent.isAtMothership();
			}
			@ Override
			protected void action() {
                            agent.deliverResourceToMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(750, "agent töten") {
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
		createRule(new Rule(720, "agent töten") {
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
		createRule(new Rule(800, "benzin tanken") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() < 20;
			}
			@ Override
			protected void action() {
                            agent.moveOneStepInTheMothershipDirection();
			}
		});
                //-------------------------------------------->
		createRule(new Rule(900, "benzin tanken") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() < 20 && agent.isAtMothership();
			}
			@ Override
			protected void action() {
                            agent.orderFuel(100);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(1000, "wände ausweichen") {
			@ Override
			protected boolean constraint() {
				return agent.collisionDetected();
			}
			@ Override
			protected void action() {
                            agent.turnRandom();
			}
		});
	}
}
