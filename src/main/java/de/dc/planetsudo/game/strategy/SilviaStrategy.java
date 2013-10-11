/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.game.strategy;

import de.dc.planetsudo.level.levelobjects.Agent;
import de.dc.planetsudo.level.levelobjects.AgentInterface;

/**
 *
 * @author divine
 */
public class SilviaStrategy extends AbstractStrategy {

	public SilviaStrategy() {
	}

	public SilviaStrategy(AgentInterface a) {
		super(a);
	}

	/**
	 * Wie viele Agenten sollen erstellt werde wird hier angegeben.
	 *
	 * @return Anzahl der Agenten
	 */
	@Override
	protected int loadAgentCount() {
		return 10;
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
				agent.go();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(700, "agent sieht resource") {
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
		createRule(new Rule(800, "agent ist bei resource") {
			@ Override
			protected boolean constraint() {
				return agent.isTouchingResource();
			}

			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(900, "agent sichert resource") {
			@ Override
			protected boolean constraint() {
				return agent.isCarringResource();
			}

			@ Override
			protected void action() {
				agent.goToMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(910, "agent sichert resource") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && agent.isCarringResource();
			}

			@ Override
			protected void action() {
				agent.deliverResourceToMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(920, "agent hat wenig Treibstoff ") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() < 10;
			}

			@ Override
			protected void action() {
				agent.goToMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(930, "agent tankt") {
			@ Override
			protected boolean constraint() {
				return (agent.getFuelInPercent() < 35) && agent.isAtMothership();
			}

			@ Override
			protected void action() {
				agent.orderFuel(100);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(600, "agent sieht feind") {
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
		createRule(new Rule(590, "agent sieht feindliches Mutterschiff") {
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
		createRule(new Rule(595, "Mutterschiff attackiert") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged();
			}

			@ Override
			protected void action() {
				agent.repairMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(594, "agent wird attackiert") {
			@ Override
			protected boolean constraint() {
				return agent.isUnderAttack();
			}

			@ Override
			protected void action() {
				agent.fightWithAdversaryAgent();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(1000, "Just Go") {
			@ Override
			protected boolean constraint() {
				return agent.isCollisionDetected();
			}

			@ Override
			protected void action() {
				agent.turnRandom();
			}
		});
	}
;
}
