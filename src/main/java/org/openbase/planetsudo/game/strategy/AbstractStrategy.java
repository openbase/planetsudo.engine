
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
import java.util.ArrayList;
import org.openbase.planetsudo.game.SwatTeam;
import org.openbase.planetsudo.level.levelobjects.Mothership;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a
 */
public abstract class AbstractStrategy implements Runnable {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    private final Agent strategyOwner;
    public final AgentInterface agent;
    public final MothershipInterface mothership;
    public final Mothership mothershipInternal;
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
        this.mothershipInternal = null;
        this.agentCount = loadAgentCount();
    }

    public AbstractStrategy(AgentInterface agent) {
        this.gameManager = GameManager.getInstance();
        this.strategyOwner = (Agent) agent;
        this.agent = agent;
        this.mothership = strategyOwner.getMothership();
        this.mothershipInternal = strategyOwner.getMothership();
        this.rules = new TreeMap<>();
        this.agentCount = loadAgentCount();
        this.loadRules();
        this.loadSwatTeams();
        this.gameSpeed = strategyOwner.getMothership().getLevel().getGameSpeed();
        this.strategyOwner.getMothership().getLevel().addPropertyChangeListener((final PropertyChangeEvent evt) -> {
            if (evt.getPropertyName().equals(AbstractLevel.GAME_SPEED_CHANGED)) {
                gameSpeed = (Integer) evt.getNewValue();
            }
        });
    }

    @Override
    public void run() {
        while (strategyOwner.isAlive()) {
            if (gameManager.isGameOver()) {
                break;
            }
            try {
                if (!gameManager.isPause()) {
                    executeRule();
                } else {
                    logger.debug("ignore rule because game is paused!");
                }
            } catch (Exception ex) {
                logger.error("Could not execute rule[" + strategyOwner.getLastAction() + "]!", ex);
                strategyOwner.kill();
            }
            try {
                Thread.sleep(gameSpeed);
            } catch (Exception ex) {
                logger.warn("", ex);
            }
        }
        logger.info("AI dies from agent " + agent + "!");
    }

    public void createRule(Rule rule) {
        logger.debug("Create rule " + rule);
        if (rules.containsKey(getKey(rule))) {
            logger.error("There exist min two rules with the same priority!");
            agent.kill();
        }
        rules.put(getKey(rule), rule);
    }

    public int getKey(Rule rule) {
        // Auto generate priority if rule does not contain any priority. 
        if(rule.getPriority() == -1) {
            return -rules.size();
        }
        return -rule.getPriority(); // invert priority to swap list order.
    }

    protected void executeRule() {
        for (final Rule rule : rules.values()) {
            if (rule.constraint() && agent.isMemberOfSwatTeam(rule.getSwatTeams())) {
                logger.debug("Select " + rule);
                strategyOwner.setLastAction(rule.getName());
                rule.action();
                break;
            }
        }
    }

    /**
     * Mit dieser Methode ist es möglich neue SwatTeams aus mehreren Agenten zu bilden.
     * Die Agenten werden hierbei über ihre IDs hinzugefügt. Sind beispielsweise 4 Agenten in der Strategie angegeben, so sind diese über die IDs 0 - 3 referenzierbar wobei Agent 0 immer für den Kommander steht.
     * Bitte beachte somit, dass die Agenten ID nicht größer als N - 1 sein kann sofern N für die maximale Anzahl von agenten steht.
     * 
     * Die default Gruppen ALL und COMMANDER können anhand dieser Methode nicht modifiziert werden!
     *
     * @param swatTeam Das SwatTeam welchem die Agenten zugeteilt werden sollen.
     * @param agents Die Ids der Agenten.
     */
    protected void createSwat(final SwatTeam swatTeam, final Integer... agents) {

        switch (swatTeam) {
            case ALL:
            case COMMANDER:
                logger.error("SwatTeam[" + swatTeam.name() + "] is not modifiable!");
                agent.kill();
                return;
        }

        ArrayList<Agent> agentList = new ArrayList<>(mothershipInternal.getAgents());
        for (Integer agentID : agents) {
            if (agentID >= agentCount || agentID >= agentList.size()) {
                logger.error("Could not create SwatTeam[" + swatTeam.name() + "] because team has not enought member!");
                agent.kill();
                return;
            }

            agentList.get(agentID).joinSwatTeam(swatTeam);
        }
    }

    public int getAgentCount() {
        return agentCount;
    }

    protected abstract void loadRules();

    protected void loadSwatTeams() {
        // Please overwrite to create swats within your strategy!
    }

    protected abstract int loadAgentCount();
}
