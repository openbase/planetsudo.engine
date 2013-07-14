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
public class KillAllStrategy extends AbstractStrategy {

	public KillAllStrategy() {
	}
	public KillAllStrategy(Agent a) {
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
			agent.goStraightAhead();
			}
		});
                		//-------------------------------------------->
		createRule(new Rule(1010, "Wand!") {
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
		createRule(new Rule(500, "Rohstoff S.") {
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
		createRule(new Rule(510, "Rohstoff T.") {
			@ Override
			protected boolean constraint() {
				return   agent.touchResourceType()!=Resource.ResourceType.Bomb &&
                                        agent.touchResource() 
                                        ;
			}
			@ Override
			protected void action() {
                            agent.pickupResource();
                         
			}
		});
                		//-------------------------------------------->
		createRule(new Rule(600, "Rohstoff Z.") {
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
		createRule(new Rule(1005, "Rohstoff A.") {
			@ Override
			protected boolean constraint() {
				return agent.isCarringResource() &&
                                        agent.isAtMothership();
			}
			@ Override
			protected void action() {
                            agent.deliverResourceToMothership();

			}
		});
        
                                		//-------------------------------------------->
		createRule(new Rule(997, "Tank Bewachung") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent()<=40;
			}
			@ Override
			protected void action() {
                            agent.moveOneStepInTheMothershipDirection();
                            
			}
		});
                                		//-------------------------------------------->
		createRule(new Rule(999, "Tankbewachung2.") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent()<=40 &&
			               agent.isAtMothership();
                        }
			@ Override
			protected void action() {
                            agent.orderFuel(100);

			}
		});
                                		//-------------------------------------------->
		createRule(new Rule(701, "Feinkontakt") {
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
		createRule(new Rule(702, "Big Feinkontakt") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryMothership();
			}
			@ Override
			protected void action() {
                            agent.fightWithAdversaryMothership();

			}
		});
                createRule(new Rule(900, "Toter freund") {
			@ Override
			protected boolean constraint() {
				return agent.seeLostTeamAgent();
			}
			@ Override
			protected void action() {
                            agent.spendFuelTeamAgent(400);

			}
		});
               
                createRule(new Rule(904, "unterbeschuss 2") {
			@ Override
			protected boolean constraint() {
				return agent.isUnderAttack() &&
                                        !agent.seeAdversaryAgent() &&
                                        agent.getFuelInPercent() >=50 ;
			}
			@ Override
			protected void action() {
                            agent.turnRight(33);

			}
		});
                createRule(new Rule(903, "unterbeschuss 2") {
			@ Override
			protected boolean constraint() {
				return agent.isUnderAttack() &&
                                        !agent.seeAdversaryAgent() &&
                                        agent.getFuelInPercent() <50 ;
			}
			@ Override
			protected void action() {
                            agent.moveOneStepInTheMothershipDirection();

			}
		});
                createRule(new Rule(903, "mothership repair") {
			@ Override
			protected boolean constraint() {
				return mothership.getShieldForce()<=55
                                      && !agent.isAtMothership() ;
			}
			@ Override
			protected void action() {
                            agent.moveOneStepInTheMothershipDirection();
                              
			}
		});
                 createRule(new Rule(903, "mothership repair") {
			@ Override
			protected boolean constraint() {
				return mothership.getShieldForce()<=55
                                      && agent.isAtMothership() ;
			}
			@ Override
			protected void action() {
                            agent.repaireMothership();
                              
			}
		});
                
               

                
	}
}
