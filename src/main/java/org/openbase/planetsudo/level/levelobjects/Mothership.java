package org.openbase.planetsudo.level.levelobjects;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2020 openbase.org
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.Timer;
import org.slf4j.Logger;
import org.openbase.planetsudo.game.GameManager;
import org.openbase.planetsudo.game.GameSound;
import org.openbase.planetsudo.game.Team;
import org.openbase.planetsudo.level.AbstractLevel;
import org.openbase.planetsudo.main.GUIController;
import java.util.ArrayList;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.InvalidStateException;
import org.openbase.jul.exception.NotAvailableException;
import org.openbase.jul.visual.swing.engine.draw2d.AbstractResourcePanel;
import org.openbase.planetsudo.geometry.Point2D;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class Mothership extends AbstractLevelObject implements ActionListener, MothershipInterface {

	public final static String MOTHERSHIP_FUEL_STATE_CHANGE = "FuelStateChange";
	public final static String MOTHERSHIP_SHIELD_STATE_CHANGE = "ShieldStateChange";
	public final static int MOTHERSHIP_FUEL_VOLUME = 15000;
	public final static int TOWER_FUEL_VOLUME = 2000;
	public final static int AGENT_FUEL_VOLUME = 12000;
	public final static int COMMANDER_BONUS_FUEL_VOLUME = 1000;
	public final static int MAX_AGENT_COUNT = 10; // range 0-9999
	public final static int BURNING_MOTHERSHIP = 20;
	public final static int BURNING_TOWER = 20;
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
	public final Object AGENTLOCK = new Object();
	public final Object SUPPORT_CHANNEL_LOCK = new Object();
	private final Team team;
	private int fuel;
	private int strategyAgentCount;
	private int shield;
	private final Timer timer;
	private final Map<Integer, Agent> agents;
	private final List<Agent> supportChannel;
    private final Tower tower;
	private final TeamMarker teamMarker;
	private int mineCounter;

	public Mothership(final int id, final Team team, final AbstractLevel level) {
		super(id, team.getName() + Mothership.class.getSimpleName(), AbstractResourcePanel.ObjectType.Static, level, level.getMothershipBase(id).getPoint(), 100, 100, ObjectShape.Rec);
		logger.info("Create " + this);
		this.team = team;
		this.team.setMothership(this);
		this.agents = new HashMap<Integer, Agent>();
		this.supportChannel = new ArrayList<Agent>();
		this.teamMarker = new TeamMarker(team, level);
		this.timer = new Timer(50, this);
		this.mineCounter = 0;
        this.tower = new Tower(id, level, this);
	}

	@ Override
	public final void reset() {
		GUIController.setEvent(new PropertyChangeEvent(this, GUIController.LOADING_STATE_CHANGE, 1, "Lade " + team.getName() + " Mutterschiff"));
		fuel = MOTHERSHIP_FUEL_VOLUME;
		mineCounter = level.getMineCounter();

		final List<Agent> tmpCollection = new LinkedList<Agent>();
		synchronized (AGENTLOCK) {
			tmpCollection.addAll(agents.values());
		}
		for (Agent agent : tmpCollection) {
			agent.kill();
		}
		strategyAgentCount = team.getAgentCount();
		loadAgents();
		this.shield = 100;
	}

	public synchronized boolean orderMine() {
		if (mineCounter > 0) {
			mineCounter--;
			return true;
		}
		return false;
	}

	private void loadAgents() {
		GUIController.setEvent(new PropertyChangeEvent(this, GUIController.LOADING_STATE_CHANGE, strategyAgentCount, "Lade " + team.getName() + " Agent"));
		Agent agent;
		final int agentFuelVolume = (AGENT_FUEL_VOLUME / strategyAgentCount);
		int currentFuelVolume;
		boolean commanderFlag = true;
		for (int i = 0; i < strategyAgentCount; i++) {
			GUIController.setEvent(new PropertyChangeEvent(this, GUIController.LOADING_STEP, null, i));
			currentFuelVolume = commanderFlag ? agentFuelVolume + COMMANDER_BONUS_FUEL_VOLUME : agentFuelVolume;
			agent = new Agent(team.getName() + "Agent", commanderFlag, currentFuelVolume, this);
			commanderFlag = false;
			Agent replacedAgent;
			synchronized (AGENTLOCK) {
				replacedAgent = agents.put(agent.getId(), agent);
			}
			if (replacedAgent != null) {
				logger.error("Agent with id " + id + " already defined! Kill old instance.");
				replacedAgent.kill();
			}
		}
		synchronized (AGENTLOCK) {
			agentCount = agents.size();
			agentKeyArray = new Integer[agents.size()];
			agentKeyArray = agents.keySet().toArray(agentKeyArray);
		}
	}

	public int orderFuel(int fuel, final Agent agent) {
		if (agent == null || getBounds().contains(agent.getBounds())) {
			try {
				final int oldFuel = this.fuel;
				if (fuel <= 0) { // fuel emty
					fuel = 0;
				} else if (this.fuel < fuel) { // use last fuel
					fuel = this.fuel;
					this.fuel = 0;
					synchronized (this) {
						notifyAll();
					}
				} else {
					this.fuel -= fuel;
				}
				changes.firePropertyChange(MOTHERSHIP_FUEL_STATE_CHANGE, oldFuel, this.fuel);
			} catch (Exception ex) {
				logger.error("Could not order fuel!", ex);
			}
		} else {
			return 0;
		}
		return fuel;
	}

    @Override
	public int getFuel() {
		return fuel;
	}
    
    @Override
    public int getFuelVolume() {
        return TOWER_FUEL_VOLUME;
    }
    
    @Override
    public int getFuelInPercent() {
        return (fuel * 100) / TOWER_FUEL_VOLUME;
    }


    @Override
	public boolean hasFuel() {
		return fuel > 0;
	}

	protected void spendFuel(final int value) {
		if (value + fuel > MOTHERSHIP_FUEL_VOLUME) {
			fuel = MOTHERSHIP_FUEL_VOLUME;
		} else {
			fuel += value;
		}
		changes.firePropertyChange(MOTHERSHIP_FUEL_STATE_CHANGE, null, this.fuel);
	}
	public static final Object TILL_END_WAITER = new Object();

	public void waitTillGameEnd() {
		synchronized (this) {
			if (fuel == 0) {
				return;
			}
			try {
				synchronized (TILL_END_WAITER) {
					TILL_END_WAITER.wait();
				}
			} catch (InterruptedException ex) {
				logger.error("", ex);
			}
		}
	}

	public void startGame() {
		synchronized (AGENTLOCK) {
			for (Agent agent : new ArrayList<>(agents.values())) {
				agent.startGame();
			}
		}
	}
	private int agentIndex = 0;
	private Integer[] agentKeyArray;
	private Agent nextAgent;
	private int agentCount = 0;

	public void addActionPoint() {
		if (agentCount != 0) {
			agentIndex = (agentIndex + 1) % (agentCount);
			synchronized (AGENTLOCK) {
				nextAgent = agents.get(agentKeyArray[agentIndex]);
			}
			if (nextAgent != null) {
				nextAgent.addActionPoint();
			}
		}
	}

	public Team getTeam() {
		return team;
	}

	public int registerAgent() {
		synchronized (AGENTLOCK) {
			int i;
			for (i = 0; i <= agents.size(); i++) {
				if (!agents.containsKey(getId() * 10000 + i)) {
					break;
				}
			}
			if (i >= strategyAgentCount || i > 1000) {
				logger.error("Already to many agents alive.");
				return -1;
			}
			return getId() * 10000 + i;
		}
	}

	/**
	 * Method just for visual purpose
	 *
	 * @return
	 */
	public TeamMarker getTeamMarker() {
		return teamMarker;
	}

	@Override
	public int getAgentCount() {
		synchronized (AGENTLOCK) {
			return agents.size();
		}
	}

	protected void removeAgent(final Agent agent) {
		synchronized (AGENTLOCK) {
			agents.remove(agent.getId());
		}
	}

	public Point2D getAgentHomePosition() {
		return position.clone();
	}

	protected void passResource(final Agent agent) throws InvalidStateException {
		final Resource resource = agent.getResource();
		if (resource != null){
			if(getBounds().contains(agent.getBounds())) {
				team.addPoints(resource.use(agent));
			} else {
				resource.release();
			}
			
		}
	}

	public Collection<Agent> getAgents() {
		synchronized (AGENTLOCK) {
			return agents.values();
		}
	}

	public synchronized void attack() {
		logger.debug("Attack Mothership");
		if (shield > 0) {
			shield--;
			if (shield <= BURNING_MOTHERSHIP) {
				if (!timer.isRunning()) {
					timer.start();
					GameSound.MothershipExplosion.play();
				}
			}
			changes.firePropertyChange(MOTHERSHIP_SHIELD_STATE_CHANGE, null, shield);
		}
	}

	public synchronized void repair() {
		if (shield < 100) {
			shield++;
			if (shield > BURNING_MOTHERSHIP && timer.isRunning()) {
				timer.stop();
			}
			changes.firePropertyChange(MOTHERSHIP_SHIELD_STATE_CHANGE, null, shield);
		}
	}

	public boolean isBurning() {
		return shield < BURNING_MOTHERSHIP && hasFuel();
	}

	public int getShieldForce() {
		return shield;
	}

	public int getShieldPoints() {
		return shield / 2;
	}

	public boolean isMaxDamaged() {
		return shield == 0;
	}

	public boolean isDamaged() {
		return shield < 100;
	}
    
    public Tower getTower() {
        return tower;
    }

	@Override
	public void actionPerformed(final ActionEvent ex) {
		if (!GameManager.getInstance().isPause()) {
			orderFuel(Math.max(0, BURNING_MOTHERSHIP - shield), null);
		}
	}

	public int getAgentsAtHomePoints() {
		return ((getAgentsAtHomePosition() * 100) / agentCount);
	}

	public int getAgentsAtHomePosition() {
		int counter = 0;
		synchronized (AGENTLOCK) {
			for (Agent agent : agents.values()) {
				if (getBounds().contains(agent.getBounds()) || getBounds().intersects(agent.getBounds())) {
					counter++;
				}
			}
		}
		return counter;
	}

	public void callForSupport(final Agent agent) {
		synchronized (SUPPORT_CHANNEL_LOCK) {
			if (supportChannel.contains(agent)) {
				return;
			}
			supportChannel.add(agent);
			agent.setNeedSupport(true);
		}
	}

	public boolean needSomeoneSupport() {
		synchronized (SUPPORT_CHANNEL_LOCK) {
			return supportChannel.size() > 0;
		}
	}

	public Agent getAgentToSupport(final Agent helper) throws CouldNotPerformException {
		synchronized (SUPPORT_CHANNEL_LOCK) {
			if (supportChannel.isEmpty()) {
				throw new CouldNotPerformException("No support necessary.");
			}
			Agent supportCaller = null;
			int distance = Integer.MAX_VALUE;
			int tmpDistance;
			for (Agent a : supportChannel) {
				if (a == helper) { // do not help yourself
					continue;
				}
				tmpDistance = helper.getLevelView().getDistance(a);
				if (supportCaller == null || tmpDistance < distance) {
					supportCaller = a;
					distance = tmpDistance;
				}
			}

			if (supportCaller == null) {
				throw new CouldNotPerformException("No support possible.");
			}

			// remove caller from support channel if support possible
			if (supportCaller.getBounds().intersects(helper.getViewBounds())) {
				cancelSupport(supportCaller);
			}
			supportCaller.getLevelView().updateObjectMovement(true);
			return supportCaller;
		}
	}

	public void cancelSupport(final Agent agent) {
		synchronized (SUPPORT_CHANNEL_LOCK) {
			supportChannel.remove(agent);
			agent.setNeedSupport(false);
		}
	}

	@Override
	public boolean isMarkerDeployed() {
		return teamMarker.isPlaced();
	}

	@Override
	public void clearMarker() {
		teamMarker.clear();
	}

	protected void placeMarker(final Point2D position) {
		teamMarker.place(position);
	}

	protected TeamMarker getMarker() throws CouldNotPerformException {
		return teamMarker.getMarker();
	}

	public void setGameOverSoon() {
		for(Agent agent : agents.values()) {
			agent.setGameOverSoon();
		}
	}
}
