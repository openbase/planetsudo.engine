/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.game.strategy;

import planetmesserlost.levelobjects.Agent;

/**
 *
 * @author divine
 */
public class DefaultStategy extends AbstractStrategy {

	public DefaultStategy(Agent agent) {
		super(agent);	}

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
		createRule(new Rule(1, "Avoid Wall") {
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
	}
}
