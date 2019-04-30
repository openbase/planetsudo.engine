/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.game.strategy;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2019 openbase.org
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
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class Robot extends AbstractStrategy {

	public Robot() {
	}
	public Robot(AgentInterface a) {
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

		createRule(new Rule(970, "Verteidigung") {
			@ Override
			protected boolean constraint() {
				return agent.isUnderAttack();
			}
			@ Override
			protected void action() {
				agent.fightWithAdversaryAgent();
			}
		});
                createRule(new Rule(965, "Verteidigung MS") {
			@ Override
			protected boolean constraint() {
				return mothership.isBurning() || mothership.isDamaged();
			}
			@ Override
			protected void action() {
				agent.goToMothership();
			}
		});
                createRule(new Rule(960, "Tanken") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && agent.getFuelInPercent() < 75;
			}
			@ Override
			protected void action() {
				agent.orderFuel(100);
			}
		});
                                createRule(new Rule(955, "Agent helfen") {
			@ Override
			protected boolean constraint() {
				return agent.seeLostTeamAgent();
			}
			@ Override
			protected void action() {
				agent.spendTeamAgentFuel(200);
			}
		});
                createRule(new Rule(950, "Mine setzten") {
			@ Override
			protected boolean constraint() {
				return agent.hasMine() && agent.seeAdversaryAgent();
			}
			@ Override
			protected void action() {
				agent.deployMine();
			}
		});
                createRule(new Rule(940, "Angriff Mothership") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryMothership();
			}
			@ Override
			protected void action() {
				agent.fightWithAdversaryMothership();
			}
		});
                createRule(new Rule(930, "Angriff Agent") {
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
		createRule(new Rule(820, "Resource ins Mutterschiff") {
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
		createRule(new Rule(800, "Bewege zum Mutterschiff") {
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
		createRule(new Rule(595, "Bomben Vermeiden") {
			@ Override
			protected boolean constraint() {
				return agent.isTouchingResource() && agent.getResourceType()==Resource.ResourceType.Mine ;
			}
			@ Override
			protected void action() {
				agent.turnLeft(42);
			}
		});
                 //-------------------------------------------->
		createRule(new Rule(590, "sammle resource") {
			@ Override
			protected boolean constraint() {
				return agent.isTouchingResource() && agent.getResourceType()!=Resource.ResourceType.Mine ;
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});
          
	
                //-------------------------------------------->
		createRule(new Rule(500, "gehe zu resource") {
			@ Override
			protected boolean constraint() {
				return agent.seeResource ();
			}
			@ Override
			protected void action() {
				agent.goToResource();
			}
		});
                //-------------------------------------------->
		createRule(new Rule(0, "gehe geradeaus") {
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
