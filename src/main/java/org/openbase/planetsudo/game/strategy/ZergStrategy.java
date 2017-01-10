/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game.strategy;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2017 openbase.org
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
public class ZergStrategy extends AbstractStrategy {

	public ZergStrategy() {
	}

	public ZergStrategy(AgentInterface a) {
		super(a);
	}

	/**
	 * Hier wird angegeben wie viele Agenten dem Team zur Verfügung stehen sollen.
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
