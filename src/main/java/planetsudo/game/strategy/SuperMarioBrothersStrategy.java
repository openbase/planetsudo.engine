/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.game.strategy;

import planetsudo.level.levelobjects.Agent;
import planetsudo.level.levelobjects.Resource.ResourceType;

/**
 *
 * @author divine
 */
public class SuperMarioBrothersStrategy extends AbstractStrategy {

	public SuperMarioBrothersStrategy() {
	}
	public SuperMarioBrothersStrategy(Agent a) {
		super(a);
	}

	/**
	 * Wie viele Agenten sollen erstellt werde wird hier angegeben.
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
				agent.goStraightAhead();
			}
		});
              //------------------------------------------------------
                	createRule(new Rule(10000, "Erkenne Wand") {
			@ Override
			protected boolean constraint() {
				return agent.collisionDetected();
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
				agent.goStraightAhead();
                                agent.placeMine();
			}
		});
                  //------------------------------------------------------
            createRule(new Rule(7000, "Recoursetyp 1 abholen") {
			@ Override
			protected boolean constraint() {
				return agent.seeResource() && agent.touchResourceType() == ResourceType.Normal;
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});                                //------------------------------------------------------
            createRule(new Rule(7200, "Recoursetyp 2 abholen") {
			@ Override
			protected boolean constraint() {
				return agent.seeResource() && agent.touchResourceType() == ResourceType.DoublePoints;
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});                  //------------------------------------------------------
            createRule(new Rule(7300, "Recoursetyp 3 abholen") {
			@ Override
			protected boolean constraint() {
				return agent.seeResource() && agent.touchResourceType() == ResourceType.ExtremPoint;
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});                  //------------------------------------------------------
            createRule(new Rule(7400, "Recoursetyp 4 abholen") {
			@ Override
			protected boolean constraint() {
				return agent.seeResource() &&agent.touchResourceType() == ResourceType.ExtraMothershipFuel;
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});              //------------------------------------------------------
            createRule(new Rule(7500, "Auftanken an Resource") {
			@ Override
			protected boolean constraint() {
				return agent.seeResource() && agent.touchResourceType() == ResourceType.ExtraAgentFuel && agent.getFuelInPercent() <=85;
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
				agent.moveOneStepInTheMothershipDirection();


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
                                    && agent.mothershipController.hasFuel();
			}
			@ Override
			protected void action() {
			        agent.moveOneStepInTheMothershipDirection();
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
			        agent.spendFuelTeamAgent(400);

                 }
		});
                         //------------------------------------------------------
            createRule(new Rule(9999, "Reparieren") {
			@ Override
			protected boolean constraint() {
				return agent.mothershipController.isDamaged();
			}
			@ Override
			protected void action() {
			        agent.repaireMothership();

                 }
		});

                                  //------------------------------------------------------
            createRule(new Rule(9998, "Ende") {
			@ Override
			protected boolean constraint() {
				return !agent.mothershipController.hasFuel();
			}
			@ Override
			protected void action() {
			        agent.moveOneStepInTheMothershipDirection();

                 }
		});

	}
}
