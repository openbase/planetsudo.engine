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
public class JaciStrategy extends AbstractStrategy {

	public JaciStrategy() {
	}

	public JaciStrategy(AgentInterface a) {
		super(a);
	}

	/**
	 * Wie viele Agenten sollen erstellt werde wird hier angegeben.
	 *
	 * @return Anzahl der Agenten
	 */
	@Override
	protected int loadAgentCount() {
		return 5;
	}

	@Override
	protected void loadRules() {
		//-------------------------------------------->
		createRule(new Rule(0, "Gehen") {
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
		createRule(new Rule(100, "zu Ressource gehen") {
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
		createRule(new Rule(200, "Ressource einsammeln") {
			@ Override
			protected boolean constraint() {
				return agent.isTouchingResource() && !agent.isTouchingResource(Resource.ResourceType.Mine);
			}

			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(300, "Ressource zu Mutterschiff") {
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
		createRule(new Rule(400, "lade Ressource ab") {
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
		createRule(new Rule(600, "Bekämpfe feindliches Mutterschiff") {
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
		createRule(new Rule(700, "Bekämpfe Feind") {
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
		createRule(new Rule(800, "Lade Mine bei Feind ab") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryAgent() && agent.hasMine();
			}

			@ Override
			protected void action() {
				agent.deployMine();
				agent.turnAround();
			}
		});//-------------------------------------------->
		createRule(new Rule(825, "Mutterschiff reparieren") {
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
		createRule(new Rule(850, "Gegen Attacke suchen") {
			@ Override
			protected boolean constraint() {
				return agent.isUnderAttack();
			}

			@ Override
			protected void action() {
				agent.turnLeft(20);

			}
		});
		//-------------------------------------------->
		createRule(new Rule(855, "Gegen Attacke wehren") {
			@ Override
			protected boolean constraint() {
				return agent.isUnderAttack() && agent.seeAdversaryAgent();
			}

			@ Override
			protected void action() {
				agent.fightWithAdversaryAgent();

			}
		});
		//-------------------------------------------->
		createRule(new Rule(860, "Mutterschiff beschützen") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged() && !agent.isAtMothership();
			}

			@ Override
			protected void action() {
				agent.goToMothership();

			}
		});

		//-------------------------------------------->
		createRule(new Rule(900, "Gehe tanken") {
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
		createRule(new Rule(905, "Auftanken") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && agent.getFuelInPercent() < 20;
			}

			@ Override
			protected void action() {
				agent.orderFuel(100);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(1000, "Vermeide Kollision") {
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
