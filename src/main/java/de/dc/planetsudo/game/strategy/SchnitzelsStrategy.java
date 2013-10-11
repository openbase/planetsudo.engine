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
public class SchnitzelsStrategy extends AbstractStrategy {

	public SchnitzelsStrategy() {
	}
	public SchnitzelsStrategy(AgentInterface a) {
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
		createRule(new Rule(1000, "Drehe bei Wand") {
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
		createRule(new Rule(0, "Gehe geradeaus") {
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
		createRule(new Rule(900, "Sehe Resource") {
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
		createRule(new Rule(901, "Berührt Resource") {
			@ Override
			protected boolean constraint() {
		   return agent.isTouchingResource() && agent.getResourceType() !=
Resource.ResourceType.Mine;

			}
			@ Override
			protected void action() {
			    agent.pickupResource();
			}
		});

		//-------------------------------------------->
		createRule(new Rule(902, "Trägt Resource") {
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
		createRule(new Rule(903, "Beim Mutterschiff und Tankcheck") {
			@ Override
			protected boolean constraint() {
			      return agent.isAtMothership() && agent.getFuelInPercent()<=65;
			}
			@ Override
			protected void action() {
			    agent.orderFuel(100);
			}
		});

		//-------------------------------------------->
		createRule(new Rule(904, "Beim Mutterschiff und Resource") {
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
		createRule(new Rule(950, "Unter beschuss") {
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
		createRule(new Rule(945, "Feindkontakt") {
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
		createRule(new Rule(940, "Agent helfen") {
			@ Override
			protected boolean constraint() {
			      return agent.seeLostTeamAgent();
			}
			@ Override
			protected void action() {
			    agent.spendTeamAgentFuel(75);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(799, "Feindliches Mutterschiff") {
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
		createRule(new Rule(400, "Mutterschiff reparieren") {
			@ Override
			protected boolean constraint() {
			      return 
                                      mothership.isDamaged();

			}
			@ Override
			protected void action() {
			    agent.repairMothership();
			}
		});
                
           
			
		
	}
}
