/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.game.strategy;

import de.dc.planetsudo.level.levelobjects.Agent;
import de.dc.planetsudo.level.levelobjects.AgentInterface;
import de.dc.planetsudo.level.levelobjects.Resource;

/**
 *
 * @author divine
 */
public class ZergStrategy extends AbstractStrategy {

	public ZergStrategy() {
	}

	public ZergStrategy(AgentInterface a) {
		super(a);
	}

	/**
	 * Wie viele Agenten sollen erstellt werde wird hier angegeben.
	 *
	 * @return Anzahl der Agenten
	 */
	@Override
	protected int loadAgentCount() {
		return 6;
	}

	@Override
	protected void loadRules() {
		//-------------------------------------------->
		createRule(new Rule(1000, "Angriff bei Sichtung vom Feind") {
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
		createRule(new Rule(900, "Angriff Auf das Mutterschiff") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryMothership();
			}

			@ Override
			protected void action() {
				agent.fightWithAdversaryMothership();
				agent.deployMine();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(800, "Mutterschiff Reparieren") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && (agent.getFuelInPercent() > 15) && (mothership.isDamaged() || mothership.isBurning());
			}

			@ Override
			protected void action() {
				agent.repairMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(725, "Tanken") {
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
		createRule(new Rule(700, "Tanken gehen") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() < 35;
			}

			@ Override
			protected void action() {
				agent.goToMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(600, "Helfen") {
			@ Override
			protected boolean constraint() {
				return agent.seeLostTeamAgent() && agent.getFuelInPercent() > 70;
			}

			@ Override
			protected void action() {
				agent.spendTeamAgentFuel((int) (agent.getFuelVolume() * 0.10));
			}
		});
		//-------------------------------------------->
		createRule(new Rule(500, "Dreh Bei Wand") {
			@ Override
			protected boolean constraint() {
				return agent.isCollisionDetected();
			}

			@ Override
			protected void action() {
				agent.turnRandom();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(750, "Gib Ressource Mutterschiff") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && agent.isCarringResource();
			}

			@ Override
			protected void action() {
				agent.deliverResourceToMothership();
				agent.orderFuel(100);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(300, "Mit Ressource zum Mutterschiff") {
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
		createRule(new Rule(200, "Sammel Ressource") {
			@ Override
			protected boolean constraint() {
				return agent.isTouchingResource() && agent.getResourceType() != Resource.ResourceType.Mine;
			}

			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(100, "Geh zu Ressource") {
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
		createRule(new Rule(0, "Gehe Geradeaus") {
			@ Override
			protected boolean constraint() {
				return true;
			}

			@ Override
			protected void action() {
				agent.go();
			}
		});
	}
}
