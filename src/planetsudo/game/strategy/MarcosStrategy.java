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
public class MarcosStrategy extends AbstractStrategy {

	public MarcosStrategy(Agent agent) {
		super(agent);	
	}

	@Override
	protected void loadRules() {
		//-------------------------------------------->
		createRule(new Rule(10, "Fly arround") {
			@ Override
			protected boolean constraint() {
				return true;
			}
			@ Override
			protected void action() {
				agent.goStraightAhead();
				agent.turnRight(1);
			}
		});

		createRule(new Rule(20, "Gehe zu Resource") {
			@ Override
			protected boolean constraint() {
				return agent.seeResource();
			}
			@ Override
			protected void action() {
				agent.goToResource();
			}
		});

		createRule(new Rule(30, "Resource aufsammeln") {
			@ Override
			protected boolean constraint() {
				return agent.touchResource();
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});

		createRule(new Rule(40, "Helfe Teammitglied") {
			@ Override
			protected boolean constraint() {
				return agent.seeLostTeamAgent();
			}
			@ Override
			protected void action() {
				agent.spendFuelTeamAgent(Agent.DEFAULT_START_FUEL/4);
			}
		});

		createRule(new Rule(50, "Carry Resource back") {
			@ Override
			protected boolean constraint() {
				return agent.isCarringResource();
			}
			@ Override
			protected void action() {
				agent.moveOneStepInTheMothershipDirection();
			}
		});

		createRule(new Rule(60, "Resource abgeben") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && agent.isCarringResource();
			}
			@ Override
			protected void action() {
				agent.deliverResourceToMothership();
			}
		});

		createRule(new Rule(70, "Attackiere feindl. Mutterschiff") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryMothership();
			}
			@ Override
			protected void action() {
				agent.fightWithAdversaryMothership();
			}
		});

		createRule(new Rule(80, "Leerer Tank - Zurück gehen") {
			@ Override
			protected boolean constraint() {
				return agent.getFuel() <= Agent.DEFAULT_START_FUEL/3 && agent.getMothership().hasFuel();
			}
			@ Override
			protected void action() {
				agent.moveOneStepInTheMothershipDirection();
			}
		});

		createRule(new Rule(90, "Tanken") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && agent.getFuel() <= Agent.DEFAULT_START_FUEL/3 && agent.getMothership().hasFuel();
			}
			@ Override
			protected void action() {
				agent.orderFuel(100);
			}
		});

		createRule(new Rule(100, "Fire in the house!") {
			@ Override
			protected boolean constraint() {
				return agent.getMothership().isDamaged();
			}
			@ Override
			protected void action() {
				agent.moveOneStepInTheMothershipDirection();
			}
		});

		createRule(new Rule(110, "Repariere Mutterschiff") {
			@ Override
			protected boolean constraint() {
				return agent.getMothership().isDamaged() && agent.isAtMothership();
			}
			@ Override
			protected void action() {
				agent.repaireMothership();
			}
		});

		createRule(new Rule(120, "Fight!") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryAgent();
			}
			@ Override
			protected void action() {
				agent.fightWithAdversaryAgent();
			}
		});

		createRule(new Rule(130, "Alarm!") {
			@ Override
			protected boolean constraint() {
				return agent.getMothership().isBurning();
			}
			@ Override
			protected void action() {
				agent.moveOneStepInTheMothershipDirection();
			}
		});

		createRule(new Rule(1000, "Collision detected") {
			@ Override
			protected boolean constraint() {
				return agent.collisionDetected();
			}
			@ Override
			protected void action() {
				agent.turnLeft(45);
			}
		});

	}
}
