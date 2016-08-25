/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game.strategy;

import org.openbase.planetsudo.level.levelobjects.Agent;
import org.openbase.planetsudo.level.levelobjects.AgentInterface;
import org.openbase.planetsudo.level.levelobjects.Resource;

/**
 *
 * @author divine
 */
public class JaciStrategy extends AbstractStrategy {

	public JaciStrategy() {
	}

	public JaciStrategy(AgentInterface a) {
		super(a);
	}

	/**
	 * Wie viele Agenten sollen erstellt werde wird hier angegeben.
	 *
	 * @return Anzahl der Agenten
	 */
	@Override
	protected int loadAgentCount() {
		return 5;
	}

	@Override
	protected void loadRules() {
		//-------------------------------------------->
		createRule(new Rule(0, "Gehen") {
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
		createRule(new Rule(100, "zu Ressource gehen") {
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
		createRule(new Rule(200, "Ressource einsammeln") {
			@ Override
			protected boolean constraint() {
				return agent.isTouchingResource() && !agent.isTouchingResource(Resource.ResourceType.Mine);
			}

			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(300, "Ressource zu Mutterschiff") {
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
		createRule(new Rule(400, "lade Ressource ab") {
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
		createRule(new Rule(600, "Bekämpfe feindliches Mutterschiff") {
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
		createRule(new Rule(700, "Bekämpfe Feind") {
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
		createRule(new Rule(800, "Lade Mine bei Feind ab") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryAgent() && agent.hasMine();
			}

			@ Override
			protected void action() {
				agent.deployMine();
				agent.turnAround();
			}
		});//-------------------------------------------->
		createRule(new Rule(825, "Mutterschiff reparieren") {
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
		createRule(new Rule(850, "Gegen Attacke suchen") {
			@ Override
			protected boolean constraint() {
				return agent.isUnderAttack();
			}

			@ Override
			protected void action() {
				agent.turnLeft(20);

			}
		});
		//-------------------------------------------->
		createRule(new Rule(855, "Gegen Attacke wehren") {
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
		createRule(new Rule(860, "Mutterschiff beschützen") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged() && !agent.isAtMothership();
			}

			@ Override
			protected void action() {
				agent.goToMothership();

			}
		});

		//-------------------------------------------->
		createRule(new Rule(900, "Gehe tanken") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() < 20;
			}

			@ Override
			protected void action() {
				agent.goToMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(905, "Auftanken") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && agent.getFuelInPercent() < 20;
			}

			@ Override
			protected void action() {
				agent.orderFuel(100);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(1000, "Vermeide Kollision") {
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
}
