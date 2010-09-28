/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.game.strategy;

import planetsudo.level.levelobjects.Agent;

/**
 *
 * @author divine
 */
public class DefaultStategy extends AbstractStrategy {

	public DefaultStategy(Agent agent) {
		super(agent);	}

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
		createRule(new Rule(20, "Search Resources") {
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
		createRule(new Rule(30, "PickUp Resource") {
			@ Override
			protected boolean constraint() {
				return agent.toucheResource();
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(40, "Save Resource") {
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
		createRule(new Rule(50, "Pass Resource") {
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
		createRule(new Rule(200, "GoBackToMothership") {
			@ Override
			protected boolean constraint() {
				return agent.getFuel() < 300;
			}
			@ Override
			protected void action() {
				agent.moveOneStepInTheMothershipDirection();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(400, "OrderFuel") {
			@ Override
			protected boolean constraint() {
				return (agent.getFuel() < 300) && (agent.isAtMothership());
			}
			@ Override
			protected void action() {
				agent.orderFuel(100);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(1000, "AvoidWall") {
			@ Override
			protected boolean constraint() {
				return agent.collisionDetected();
			}
			@ Override
			protected void action() {
				agent.turnRandom();
			}
		});
		//-------------------------------------------->
	}
}
