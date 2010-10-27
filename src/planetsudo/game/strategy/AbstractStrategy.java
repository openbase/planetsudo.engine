
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.game.strategy;

import planetsudo.level.levelobjects.Mothership;
import planetsudo.level.levelobjects.Agent;
import java.util.TreeMap;
import logging.Logger;
import planetsudo.game.GameManager;
import planetsudo.level.levelobjects.AgentController;

/**
 *
 * @author divine
 */
public abstract class AbstractStrategy implements Runnable {

	private final Agent strategyOwner;
	protected final AgentController agent;
	private final Mothership mothership;
	private final TreeMap<Integer, Rule> rules;
	private final GameManager gameManager;

	public AbstractStrategy(Agent agent) {
		this.gameManager = GameManager.getInstance();
		this.strategyOwner = agent;
		this.agent = new AgentController(strategyOwner);
		this.mothership = agent.getMothership();
		this.rules = new TreeMap<Integer, Rule>();
		this.loadRules();
	}

	@ Override
	public void run() {
		while(strategyOwner.isAlive()) {
				if(gameManager.isGameOver()) {
					//agent.kill();
					break;
				}
				try {
					if(!gameManager.isPause()) {
						executeRule();
					} else {
						Logger.debug(this, "ignore roule because game is paused!");
					}
				} catch(Exception ex) {
					Logger.error(this, "Could not execute rule["+strategyOwner.getLastAction()+"]!", ex);
					strategyOwner.kill();
				}
			try {
				Thread.sleep(50);
			} catch (InterruptedException ex) {
				Logger.warn(this, "", ex);
			}
		}
		Logger.info(this, "AI dies from agent "+agent+"!");
	}

	public void createRule(Rule rule) {
		Logger.debug(this, "Create rule "+rule);
		if(rules.containsKey(getKey(rule))) {
			Logger.error(this, "There exist min two rules with the same priority!");
		}
		rules.put(getKey(rule), rule);
	}

	public int getKey(Rule rule) {
		return -rule.getPriority(); // invert priority to swap list order.
	}

	protected void executeRule() {
		for(Rule rule : rules.values()) {
			if(rule.constraint()) {
				//Logger.debug(this, "Select "+rule);
				strategyOwner.setLastAction(rule.getName());
				rule.action();
				break;
			}
		}
	}

	protected abstract void loadRules();
}
x
