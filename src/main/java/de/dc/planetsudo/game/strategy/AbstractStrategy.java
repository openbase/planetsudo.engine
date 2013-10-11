
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.game.strategy;

import de.dc.planetsudo.level.levelobjects.Agent;
import java.util.TreeMap;
import de.dc.util.logging.Logger;
import de.dc.planetsudo.game.GameManager;
import de.dc.planetsudo.level.AbstractLevel;
import de.dc.planetsudo.level.levelobjects.AgentController;
import de.dc.planetsudo.level.levelobjects.AgentInterface;
import de.dc.planetsudo.level.levelobjects.MothershipInterface;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 *
 * @author divine
 */
public abstract class AbstractStrategy implements Runnable {

	private final Agent strategyOwner;
	public final AgentInterface agent;
	public final MothershipInterface mothership;
	private final TreeMap<Integer, Rule> rules;
	private final GameManager gameManager;
	private final int agentCount;
	private int gameSpeed;

	public AbstractStrategy() {
		this.mothership = null;
		this.rules = null;
		this.gameManager = null;
		this.strategyOwner = null;
		this.agent = null;
		this.agentCount = loadAgentCount();
		Logger.info(this, null);
	}

	public AbstractStrategy(AgentInterface agent) {
		this.gameManager = GameManager.getInstance();
		this.strategyOwner = (Agent) agent;
		this.agent = agent;
		this.mothership = strategyOwner.getMothership();
		this.rules = new TreeMap<Integer, Rule>();
		this.agentCount = loadAgentCount();
		this.loadRules();
		this.gameSpeed = strategyOwner.getMothership().getLevel().getGameSpeed();
		strategyOwner.getMothership().getLevel().addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals(AbstractLevel.GAME_SPEED_CHANGED)) {
					gameSpeed = (Integer) evt.getNewValue();
				}
			}
		});
	}

	@ Override
	public void run() {
		while(strategyOwner.isAlive()) {
				if(gameManager.isGameOver()) {
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
				Thread.sleep(gameSpeed);
			} catch (Exception ex) {
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

