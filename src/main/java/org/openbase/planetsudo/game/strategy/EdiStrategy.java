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
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class EdiStrategy extends AbstractStrategy {

	public EdiStrategy() {
	}
	public EdiStrategy(AgentInterface a) {
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
		createRule(new Rule(400, "resource sehen") {
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
		createRule(new Rule(500, "resource aufheben") {
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
		createRule(new Rule(600, "zum Mutterschiff") {
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
		createRule(new Rule(700, "ablegen resource") {
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
		createRule(new Rule(750, "agent töten") {
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
		createRule(new Rule(720, "agent töten") {
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
		createRule(new Rule(800, "benzin tanken") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() < 20;
			}
			@ Override
			protected void action() {
				agent.goToMothership();
			}
		});
                //-------------------------------------------->
		createRule(new Rule(900, "benzin tanken") {
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
		createRule(new Rule(1000, "wände ausweichen") {
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
}
