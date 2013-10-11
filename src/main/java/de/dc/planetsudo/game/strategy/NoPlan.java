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
public class NoPlan extends AbstractStrategy {

	public NoPlan(AgentInterface agent) {
		super(agent);
	}

	/**
	 * Wie viele Agenten sollen erstellt werde wird hier angegeben.
	 * @return Anzahl der Agenten
	 */
	@Override
	protected int loadAgentCount() {
		return 4;
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
				//agent.
			}
		});
		//-------------------------------------------->
		createRule(new Rule(999, "Wall") {
			@ Override
			protected boolean constraint() {
				return agent.isCollisionDetected();
			}
			@ Override
			protected void action() {
				agent.turnRight(95);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(500, "Flee") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryAgent();
			}
			@ Override
			protected void action() {
				if(agent.isCarringResource())
					agent.releaseResource();
				agent.turnRight(90);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(12, "Harvesting") {
			@ Override
			protected boolean constraint() {
				return agent.isCarringResource();
			}
			@ Override
			protected void action() {
				if(agent.isAtMothership()){
					agent.deliverResourceToMothership();
				}else{
					agent.goToMothership();
				}
			}
		});
		//-------------------------------------------->
		createRule(new Rule(10, "Collecting") {
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
		createRule(new Rule(9, "MoveToRess") {
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
		createRule(new Rule(13, "Help") {
			@ Override
			protected boolean constraint() {
				return agent.seeLostTeamAgent();
			}
			@ Override
			protected void action() {
				agent.spendTeamAgentFuel(3);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(800, "Fuel") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && (agent.getFuelInPercent()<=50);
			}
			@ Override
			protected void action() {
				agent.orderFuel(100);
			}
		});
	}
}
