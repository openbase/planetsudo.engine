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
 * @author divine
 */
public class BerlusconiStrategy extends AbstractStrategy {

	public BerlusconiStrategy() {
	}
	public BerlusconiStrategy(AgentInterface a) {
		super(a);
	}

	/**
	 * Wie viele Agenten sollen erstellt werde wird hier angegeben.
	 * @return Anzahl der Agenten
	 */
	@Override
	protected int loadAgentCount() {
		return 8;
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
		createRule(new Rule(700, "Sehe Resource") {
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
		createRule(new Rule(800, "Schnapp die Resource") {
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
		createRule(new Rule(900, "Bring Resource heim") {
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
		createRule(new Rule(902, "Lade ab") {
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
		createRule(new Rule(801, "Feuer") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryAgent()  && agent.isCarringResource()!=true;
			}
			@ Override
			protected void action() {
				agent.fightWithAdversaryAgent();
			}
		});
                //-------------------------------------------->
		createRule(new Rule(500, "tanken") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && agent.getFuelInPercent()<=80;
			}
			@ Override
			protected void action() {
				agent.orderFuel(100);
			}
		});
                //-------------------------------------------->
		createRule(new Rule(1, "gehe geradeaus") {
			@ Override
			protected boolean constraint() {
				return true;
			}
			@ Override
			protected void action() {
				agent.go();
			}
		});
                /*//-------------------------------------------->
		createRule(new Rule(100, "Notfall") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged();
			}
			@ Override
			protected void action() {
				agent.moveOneStepInTheMothershipDirection();
			}
		});
                //-------------------------------------------->
		createRule(new Rule(101, "Notfall2") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged();
			}
			@ Override
			protected void action() {
				agent.releaseResource();
			}
		});
                //-------------------------------------------->
		createRule(new Rule(802, "Überlebensinstinkt") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent()<=30;
			}
			@ Override
			protected void action() {
				agent.moveOneStepInTheMothershipDirection();
			}
		}); */
                	}
}
