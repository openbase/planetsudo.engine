/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.game.strategy;

import de.dc.planetsudo.level.levelobjects.AgentInterface;
import de.dc.planetsudo.level.levelobjects.Resource.ResourceType;

/**
 *
 * @author divine
 */
public class DivineStrategy extends AbstractStrategy {

	public DivineStrategy() {
	}
	
	public DivineStrategy(AgentInterface a) {
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
				agent.go();
				
			}
		});
		//-------------------------------------------->
		createRule(new Rule(1, "Discover") {
			@ Override
			protected boolean constraint() {
				return agent.isCommander();
			}
			@ Override
			protected void action() {
				agent.goRight(3);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(5, "Go to Marker") {
			@ Override
			protected boolean constraint() {
				return !agent.isCommander() && mothership.isMarkerDeployed();
			}
			@ Override
			protected void action() {
				agent.goToMarker();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(10, "Search") {
			@ Override
			protected boolean constraint() {
				return agent.seeMarker();
			}
			@ Override
			protected void action() {
				mothership.clearMarker();
				agent.searchResources();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(20, "See Resources") {
			@ Override
			protected boolean constraint() {
				return !agent.isCommander() &&agent.seeResource();
			}
			@ Override
			protected void action() {
				agent.goToResource();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(30, "PickUp 1P Resource") {
			@ Override
			protected boolean constraint() {
				return !agent.isCommander() && agent.isTouchingResource(ResourceType.Normal);
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(31, "PickUp and Place") {
			@ Override
			protected boolean constraint() {
				return !agent.isCommander() && (agent.isTouchingResource(ResourceType.ExtremPoint) || agent.isTouchingResource(ResourceType.DoublePoints) || agent.isTouchingResource(ResourceType.ExtraMothershipFuel));
			}
			@ Override
			protected void action() {
				if(!mothership.isMarkerDeployed()) {
					agent.deployMarker();
				}
				agent.pickupResource();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(32, "PickUp 5P and Place") {
			@ Override
			protected boolean constraint() {
				return agent.isTouchingResource(ResourceType.ExtremPoint);
			}
			@ Override
			protected void action() {
				agent.deployMarker();
				agent.pickupResource();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(33, "Ignore") {
			@ Override
			protected boolean constraint() {
				return agent.isTouchingResource(ResourceType.Mine) || agent.isTouchingResource(ResourceType.ExtraAgentFuel);
			}
			@ Override
			protected void action() {
				agent.go();
			}
		});


		//-------------------------------------------->
		createRule(new Rule(34, "Secure") {
			@ Override
			protected boolean constraint() {
				return agent.isGameOverSoon();
			}
			@ Override
			protected void action() {
				agent.goToMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(35, "Saved") {
			@ Override
			protected boolean constraint() {
				return agent.isGameOverSoon() && agent.isAtMothership();
			}
			@ Override
			protected void action() {

			}
		});
		//-------------------------------------------->
		createRule(new Rule(36, "Order Fuel") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && agent.isGameOverSoon() && mothership.hasFuel()&& agent.getFuelInPercent() < 100;
			}
			@ Override
			protected void action() {
				agent.orderFuel(100);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(39, "Support Agent") {
			@ Override
			protected boolean constraint() {
				return mothership.needSomeoneSupport() && !agent.isSupportOrdered();
			}
			@ Override
			protected void action() {
				agent.goToSupportAgent();
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
				agent.goToMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(60, "HelpLostAgent") {
			@ Override
			protected boolean constraint() {
				return agent.seeLostTeamAgent();
			}
			@ Override
			protected void action() {
				agent.spendTeamAgentFuel(300);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(90, "FightAgainstMothership") {
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
		createRule(new Rule(91, "FightAgainstMothership & Order Support") {
			@ Override
			protected boolean constraint() {
				return !agent.isSupportOrdered() && agent.seeAdversaryMothership();
			}
			@ Override
			protected void action() {
				agent.fightWithAdversaryMothership();
				agent.orderSupport();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(100, "TurnToAdversaryAgent") {
			@ Override
			protected boolean constraint() {
				return agent.isUnderAttack();
			}
			@ Override
			protected void action() {
				agent.turnLeft(60);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(110, "SaveMothership") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged();
			}
			@ Override
			protected void action() {
				agent.goToMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(120, "RepaireMothership") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged() && agent.isAtMothership();
			}
			@ Override
			protected void action() {
				agent.repairMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(190, "FightAgainstAgent") {
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
		createRule(new Rule(195, "PlaceMine") {
			@ Override
			protected boolean constraint() {
				return agent.hasMine() && agent.isUnderAttack();
			}
			@ Override
			protected void action() {
				agent.deployMine();
				agent.goLeft(180);
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
				agent.goToMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(400, "OrderFuel") {
			@ Override
			protected boolean constraint() {
				return mothership.hasFuel() && (agent.getFuelInPercent() < 90) && (agent.isAtMothership());
			}
			@ Override
			protected void action() {
				agent.orderFuel(100);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(500, "OrderFuelDuringFight") {
			@ Override
			protected boolean constraint() {
				return mothership.hasFuel() && (agent.getFuel() < 100) && agent.isUnderAttack() && agent.isAtMothership();
			}
			@ Override
			protected void action() {
				agent.orderFuel(5);
				agent.goLeft(10);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(501, "OrderFuelDuringFight") {
			@ Override
			protected boolean constraint() {
				return mothership.hasFuel() && (agent.getFuel() < 100) && agent.seeAdversaryAgent() && agent.isAtMothership();
			}
			@ Override
			protected void action() {
				agent.orderFuel(5);
				agent.fightWithAdversaryAgent();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(550, "Pass Resource") {
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
		createRule(new Rule(600, "CallForHelp") {
			@ Override
			protected boolean constraint() {
				return (!agent.isSupportOrdered()) && ((agent.getFuel() < 5) || agent.isUnderAttack());
			}
			@ Override
			protected void action() {
				agent.orderSupport();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(800, "PickUp Fuel") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() < 50 && agent.isTouchingResource(ResourceType.ExtraAgentFuel);
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(900, "Follow Wall") {
			@ Override
			protected boolean constraint() {
				return agent.isCommander() && agent.isCollisionDetected();
			}
			@ Override
			protected void action() {
				agent.turnLeft(3);
				
			}
		});
		//-------------------------------------------->
		createRule(new Rule(1000, "AvoidWall") {
			@ Override
			protected boolean constraint() {
				return !agent.isCommander() && agent.isCollisionDetected();
			}
			@ Override
			protected void action() {
				agent.turnRandom(150);

			}
		});
//		//-------------------------------------------->
//		createRule(new Rule(1001, "Cancel Support") {
//			@ Override
//			protected boolean constraint() {
//				return agent.isSupportOrdered() && !agent.seeAdversaryMothership() && agent.getFuel() > 10 && !agent.isUnderAttack() && !agent.seeAdversaryAgent();
//			}
//			@ Override
//			protected void action() {
//				agent.cancelSupport();
//
//			}
//		});
//		//-------------------------------------------->
	}
}
