
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.game.strategy;

import org.openbase.planetsudo.level.levelobjects.Agent;
import java.util.TreeMap;
import org.openbase.planetsudo.game.GameManager;
import org.openbase.planetsudo.level.AbstractLevel;
import org.openbase.planetsudo.level.levelobjects.AgentInterface;
import org.openbase.planetsudo.level.levelobjects.MothershipInterface;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.slf4j.LoggerFactory;


/**
 *
 * @author divine
 */
public abstract class AbstractStrategy implements Runnable {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
    
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
						logger.debug("ignore roule because game is paused!");
					}
				} catch(Exception ex) {
					logger.error("Could not execute rule["+strategyOwner.getLastAction()+"]!", ex);
					strategyOwner.kill();
				}
			try {
				Thread.sleep(gameSpeed);
			} catch (Exception ex) {
				logger.warn("", ex);
			}
		}
		logger.info("AI dies from agent "+agent+"!");
	}

	public void createRule(Rule rule) {
		logger.debug("Create rule "+rule);
		if(rules.containsKey(getKey(rule))) {
			logger.error("There exist min two rules with the same priority!");
            agent.kill();
		}
		rules.put(getKey(rule), rule);
	}

	public int getKey(Rule rule) {
		return -rule.getPriority(); // invert priority to swap list order.
	}



	protected void executeRule() {
		for(Rule rule : rules.values()) {
			if(rule.constraint()) {
				logger.debug("Select "+rule);
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

