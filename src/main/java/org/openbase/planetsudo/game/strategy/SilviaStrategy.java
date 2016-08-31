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

/**
 *
 * @author divine
 */
public class SilviaStrategy extends AbstractStrategy {

	public SilviaStrategy() {
	}

	public SilviaStrategy(AgentInterface a) {
		super(a);
	}

	/**
	 * Wie viele Agenten sollen erstellt werde wird hier angegeben.
	 *
	 * @return Anzahl der Agenten
	 */
	@Override
	protected int loadAgentCount() {
		return 10;
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
		createRule(new Rule(700, "agent sieht resource") {
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
		createRule(new Rule(800, "agent ist bei resource") {
			@ Override
			protected boolean constraint() {
				return agent.isTouchingResource();
			}

			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(900, "agent sichert resource") {
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
		createRule(new Rule(910, "agent sichert resource") {
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
		createRule(new Rule(920, "agent hat wenig Treibstoff ") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() < 10;
			}

			@ Override
			protected void action() {
				agent.goToMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(930, "agent tankt") {
			@ Override
			protected boolean constraint() {
				return (agent.getFuelInPercent() < 35) && agent.isAtMothership();
			}

			@ Override
			protected void action() {
				agent.orderFuel(100);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(600, "agent sieht feind") {
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
		createRule(new Rule(590, "agent sieht feindliches Mutterschiff") {
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
		createRule(new Rule(595, "Mutterschiff attackiert") {
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
		createRule(new Rule(594, "agent wird attackiert") {
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
		createRule(new Rule(1000, "Just Go") {
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
;
}
