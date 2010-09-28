
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.game.strategy;

import planetsudo.level.levelobjects.Mothership;
import planetsudo.level.levelobjects.Agent;
import java.util.TreeMap;
import logging.Logger;


/**
 *
 * @author divine
 */
public abstract class AbstractStrategy implements Runnable {

	protected final Agent agent;
	protected final Mothership mothership;
	private final TreeMap<Integer, Rule> rules;

	public AbstractStrategy(Agent agent) {
		this.agent = agent;
		this.mothership = agent.getMothership();
		this.rules = new TreeMap<Integer, Rule>();
		this.loadRules();
	}

	@ Override
	public void run() {
		while(agent.hasFuel()) {
			if(!agent.isDisabled()) {
				try {
					executeRule();
				} catch(Exception ex) {
					Logger.error(this, "Could not execute rule!", ex);
				}
			} else {
				break;
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException ex) {
				Logger.warn(this, "", ex);
			}
		}
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
				agent.setLastAction(rule.getName());
				rule.action();
				break;
			}
		}
	}

	protected abstract void loadRules();
}
