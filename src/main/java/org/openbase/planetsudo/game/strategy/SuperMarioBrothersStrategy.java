/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.game.strategy;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2020 openbase.org
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
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class SuperMarioBrothersStrategy extends AbstractStrategy {

	public SuperMarioBrothersStrategy() {
	}
	public SuperMarioBrothersStrategy(AgentInterface a) {
		super(a);
	}

	/**
	 * Hier wird angegeben wie viele Agenten dem Team zur Verfügung stehen sollen.
	 * @return Anzahl der Agenten
	 */
	@Override
	protected int loadAgentCount() {
		return 6;
	}

	@Override
	protected void loadRules() {
		//-------------------------------------------->
         //___________________________________________________________
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
              //------------------------------------------------------
                	createRule(new Rule(10000, "Erkenne Wand") {
			@ Override
			protected boolean constraint() {
				return agent.isCollisionDetected();
			}
			@ Override
			protected void action() {
				agent.turnRandom();
			}
		});
               //___________________________________________________________
            createRule(new Rule(6900, "Feind Bekämpfen") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryAgent();
			}
			@ Override
			protected void action() {
				agent.fightWithAdversaryAgent();
			}
		});
              //------------------------------------------------------
            createRule(new Rule(8600, "Mutterschiff angreifen") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryMothership();
			}
			@ Override
			protected void action() {
				agent.fightWithAdversaryMothership();
			}
		});
              //------------------------------------------------------
            createRule(new Rule(8500, "Gegner Schiff Mine") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryMothership() && agent.hasMine();
                }
			@ Override
			protected void action() {
				agent.go();
                                agent.deployMine();
			}
		});
                  //------------------------------------------------------
            createRule(new Rule(7000, "Recoursetyp 1 abholen") {
			@ Override
			protected boolean constraint() {
				return agent.seeResource() && agent.getResourceType() == ResourceType.Normal;
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});                                //------------------------------------------------------
            createRule(new Rule(7200, "Recoursetyp 2 abholen") {
			@ Override
			protected boolean constraint() {
				return agent.seeResource() && agent.getResourceType() == ResourceType.DoublePoints;
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});                  //------------------------------------------------------
            createRule(new Rule(7300, "Recoursetyp 3 abholen") {
			@ Override
			protected boolean constraint() {
				return agent.seeResource() && agent.getResourceType() == ResourceType.ExtremPoint;
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});                  //------------------------------------------------------
            createRule(new Rule(7400, "Recoursetyp 4 abholen") {
			@ Override
			protected boolean constraint() {
				return agent.seeResource() &&agent.getResourceType() == ResourceType.ExtraMothershipFuel;
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});              //------------------------------------------------------
            createRule(new Rule(7500, "Auftanken an Resource") {
			@ Override
			protected boolean constraint() {
				return agent.seeResource() && agent.getResourceType() == ResourceType.ExtraAgentFuel && agent.getFuelInPercent() <=85;
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});
                 //------------------------------------------------------
            createRule(new Rule(7600, "zum Mutterschiff gehen"){
			@ Override
			protected boolean constraint() {
				return agent.isCarringResource();
			}
			@ Override
			protected void action() {
				agent.goToMothership();


			}
		});
                           //------------------------------------------------------
            createRule(new Rule(7800, "Resource liefern") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && agent.isCarringResource();
			}
			@ Override
			protected void action() {
				agent.deliverResourceToMothership();
			}
		});
                //------------------------------------------------------
            createRule(new Rule(7700, "Tank füllen") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() <=60
                                    && mothership.hasFuel();
			}
			@ Override
			protected void action() {
			        agent.goToMothership();
                 }
		});
                //------------------------------------------------------
            createRule(new Rule(7750, "Tank füllen") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() <=60 
                                    && agent.isAtMothership();
                                    
                               
			}
			@ Override
			protected void action() {
			        agent.orderFuel(100);
                 }
		});
             //------------------------------------------------------
            createRule(new Rule(8900, "Verteidigen") {
			@ Override
			protected boolean constraint() {
				return agent.isUnderAttack();

			}
			@ Override
			protected void action() {
			        agent.fightWithAdversaryAgent();
                 }
		});
             //------------------------------------------------------
            createRule(new Rule(5000, "Helfen") {
			@ Override
			protected boolean constraint() {
				return agent.seeLostTeamAgent() && agent.getFuelInPercent() >=60;

			}
			@ Override
			protected void action() {
			        agent.spendTeamAgentFuel(400);

                 }
		});
                         //------------------------------------------------------
            createRule(new Rule(9999, "Reparieren") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged();
			}
			@ Override
			protected void action() {
			        agent.repairMothership();

                 }
		});

                                  //------------------------------------------------------
            createRule(new Rule(9998, "Ende") {
			@ Override
			protected boolean constraint() {
				return !mothership.hasFuel();
			}
			@ Override
			protected void action() {
			        agent.goToMothership();

                 }
		});

	}
}
