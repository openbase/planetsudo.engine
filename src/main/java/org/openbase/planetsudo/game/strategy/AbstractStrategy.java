
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

