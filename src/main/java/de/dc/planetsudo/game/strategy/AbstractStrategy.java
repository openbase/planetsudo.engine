
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.game.strategy;

import de.dc.planetsudo.level.levelobjects.Agent;
import java.util.TreeMap;
import de.dc.util.logging.Logger;
import de.dc.planetsudo.game.GameManager;
import de.dc.planetsudo.level.levelobjects.AgentController;
import de.dc.planetsudo.level.levelobjects.MothershipController;

/**
 *
 * @author divine
 */
public abstract class AbstractStrategy implements Runnable {

	private final Agent strategyOwner;
	protected final AgentController agent;
	protected final MothershipController mothership;
	private final TreeMap<Integer, Rule> rules;
	private final GameManager gameManager;
	private final int agentCount;

	public AbstractStrategy() {
		this.mothership = null;
		this.rules = null;
		this.gameManager = null;
		this.strategyOwner = null;
		this.agent = null;
		this.agentCount = loadAgentCount();
		Logger.info(this, null);
	}

	public AbstractStrategy(Agent agent) {
		this.gameManager = GameManager.getInstance();
		this.strategyOwner = agent;
		this.agent = new AgentController(strategyOwner);
		this.mothership = this.agent.getMothership();
		this.rules = new TreeMap<Integer, Rule>();
		this.agentCount = loadAgentCount();
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
				Logger.debug(this, "Select "+rule);
				strategyOwner.setLastAction(rule.getName());
				rule.action();
				break;
			}
		}
	}

	public int getAgentCount() {
		return agentCount;
	}

	protected abstract void loadRules();
	protected abstract int loadAgentCount();
}

