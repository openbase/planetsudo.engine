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
public class BerlusconiStrategy extends AbstractStrategy {

	public BerlusconiStrategy() {
	}
	public BerlusconiStrategy(Agent a) {
		super(a);
	}

	/**
	 * Wie viele Agenten sollen erstellt werde wird hier angegeben.
	 * @return Anzahl der Agenten
	 */
	@Override
	protected int loadAgentCount() {
		return 8;
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

                //-------------------------------------------->
		createRule(new Rule(700, "Sehe Resource") {
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
		createRule(new Rule(800, "Schnapp die Resource") {
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
		createRule(new Rule(900, "Bring Resource heim") {
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
		createRule(new Rule(902, "Lade ab") {
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
		createRule(new Rule(801, "Feuer") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryAgent()  && agent.isCarringResource()!=true;
			}
			@ Override
			protected void action() {
				agent.fightWithAdversaryAgent();
			}
		});
                //-------------------------------------------->
		createRule(new Rule(500, "tanken") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && agent.getFuelInPercent()<=80;
			}
			@ Override
			protected void action() {
				agent.orderFuel(100);
			}
		});
                //-------------------------------------------->
		createRule(new Rule(1, "gehe geradeaus") {
			@ Override
			protected boolean constraint() {
				return true;
			}
			@ Override
			protected void action() {
				agent.goStraightAhead();
			}
		});
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
