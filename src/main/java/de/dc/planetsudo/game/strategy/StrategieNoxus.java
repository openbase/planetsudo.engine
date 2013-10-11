/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.game.strategy;

import de.dc.planetsudo.level.levelobjects.Agent;
import de.dc.planetsudo.level.levelobjects.Resource;

/**
 *
 * @author divine
 */
public class StrategieNoxus extends AbstractStrategy {

	public StrategieNoxus() {
	}
	public StrategieNoxus(Agent a) {
		super(a);
	}

	/**
	 * Wie viele Agenten sollen erstellt werde wird hier angegeben.
	 * @return Anzahl der Agenten
	 */
	@Override
	protected int loadAgentCount() {
		return 2;
	}

	@Override
	protected void loadRules() {
		//-------------------------------------------->
		createRule(new Rule(99, "Kollision!") {
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
		//-------------------------------------------->
		createRule(new Rule(87, "Warte...") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() <= 6 && agent.isAtMothership() && !mothership.hasFuel() && !agent.isUnderAttack() && !mothership.isDamaged();
			}
			@ Override
			protected void action() {
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(86, "Auftanken...") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() <= 7 && agent.isAtMothership() && mothership.hasFuel();
			}
			@ Override
			protected void action() {
				agent.orderFuel(100);
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(85, "Wenig Energie...") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() <= 7 && agent.hasFuel() && !agent.isUnderAttack();
			}
			@ Override
			protected void action() {
				agent.moveOneStepInTheMothershipDirection();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(72, "Repariere Mutterschiff...") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged() && agent.isAtMothership() && !agent.isUnderAttack();
			}
			@ Override
			protected void action() {
				agent.repairMothership();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(80, "Mutterschiff helfen...") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged() && !agent.isAtMothership();
			}
			@ Override
			protected void action() {
				agent.moveOneStepInTheMothershipDirection();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(75, "Bekämpfe Agent...") {
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
		//-------------------------------------------->
		createRule(new Rule(74, "Suche Feind...") {
			@ Override
			protected boolean constraint() {
				return agent.isUnderAttack();
			}
			@ Override
			protected void action() {
				agent.turnRandom();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(61, "Mutterschiff verminen...") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryMothership() && agent.hasMine();
			}
			@ Override
			protected void action() {
				agent.placeMine();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(60, "Bekämpfe Mutterschiff...") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryMothership();
			}
			@ Override
			protected void action() {
				agent.placeMarker();
				agent.fightWithAdversaryMothership();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(35, "Notkanal schließen.") {
			@ Override
			protected boolean constraint() {
				return agent.supportOrdered() && agent.getFuelInPercent() >=7;
			}
			@ Override
			protected void action() {
				agent.cancelSupport();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(34, "Gestrandet...") {
			@ Override
			protected boolean constraint() {
				return !agent.hasFuel() && !agent.isAtMothership();
			}
			@ Override
			protected void action() {
				agent.orderSupport();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(31, "Energie Spenden...") {
			@ Override
			protected boolean constraint() {
				return mothership.needSomeoneSupport() && agent.seeLostTeamAgent();
			}
			@ Override
			protected void action() {
				agent.spendFuelTeamAgent(7);
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(30, "Agenten Helfen...") {
			@ Override
			protected boolean constraint() {
				return mothership.needSomeoneSupport();
			}
			@ Override
			protected void action() {
				agent.goToSuppordAgent();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(25, "Resource abliefern!") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && agent.isCarringResource();
			}
			@ Override
			protected void action() {
				agent.deliverResourceToMothership();
				agent.turnAround();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(23, "Berge Resource...") {
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
		//-------------------------------------------->
		createRule(new Rule(22, "Resource aufheben!") {
			@ Override
			protected boolean constraint() {
				return agent.touchResource() && (agent.touchResourceType(Resource.ResourceType.Mine) || agent.touchResourceType(Resource.ResourceType.ExtremPoint));
			}
			@ Override
			protected void action() {
				agent.goStraightAhead();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(21, "Resource aufheben!") {
			@ Override
			protected boolean constraint() {
				return agent.touchResource() && !agent.touchResourceType(Resource.ResourceType.Mine) && !agent.touchResourceType(Resource.ResourceType.ExtremPoint);
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(20, "Resource gesichtet...") {
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
		//-------------------------------------------->
		createRule(new Rule(3, "Zwischentanken...") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && mothership.hasFuel() && !mothership.isBurning() && !mothership.isDamaged() && !agent.isUnderAttack() && agent.getFuelInPercent()<=95;
			}
			@ Override
			protected void action() {
				agent.orderFuel(100);
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(2, "Marker entfernen.") {
			@ Override
			protected boolean constraint() {
				return agent.seeMarker();
			}
			@ Override
			protected void action() {
				mothership.clearMarker();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(1, "Bewege zu Marker...") {
			@ Override
			protected boolean constraint() {
				return mothership.existMarker();
			}
			@ Override
			protected void action() {
				agent.goToMarker();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(0, "Bewegung...") {
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
	}
}
