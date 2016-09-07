/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.game.strategy;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2016 openbase.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import org.openbase.planetsudo.level.levelobjects.Agent;
import org.openbase.planetsudo.level.levelobjects.AgentInterface;
import org.openbase.planetsudo.level.levelobjects.Resource;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a
 */
public class KillAllStrategy extends AbstractStrategy {

	public KillAllStrategy() {
	}
	public KillAllStrategy(AgentInterface a) {
		super(a);
	}

	/**
	 * Hier wird angegeben wie viele Agenten dem Team zur Verfügung stehen sollen.
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
		createRule(new Rule(1010, "Wand!") {
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
				return   agent.getResourceType()!=Resource.ResourceType.Mine &&
                                        agent.isTouchingResource()
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
                            agent.goToMothership();
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
                            agent.goToMothership();
                            
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
                            agent.spendTeamAgentFuel(400);

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
                            agent.goToMothership();

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
                            agent.goToMothership();
                              
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
                            agent.repairMothership();
                              
			}
		});
                
               

                
	}
}
