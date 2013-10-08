/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.level.levelobjects;

import com.sun.swing.internal.plaf.metal.resources.metal;
import de.dc.util.data.Point2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.Timer;
import de.dc.util.logging.Logger;
import de.dc.planetsudo.game.GameManager;
import de.dc.planetsudo.game.Team;
import de.dc.planetsudo.level.AbstractLevel;
import de.dc.planetsudo.main.GUIController;
import de.dc.util.exceptions.CouldNotPerformException;
import de.dc.util.exceptions.NotValidException;
import de.dc.util.view.engine.draw2d.AbstractResourcePanel;
import java.util.ArrayList;

/**
 *
 * @author divine
 */
public class Mothership extends AbstractLevelObject implements ActionListener {

	public final static String FUEL_STATE_CHANGE = "FuelStateChange";
	public final static String SHIELD_STATE_CHANGE = "ShieldStateChange";
	public final static int MOTHERSHIP_FUEL_VOLUME = 15000;
	public final static int AGENT_FUEL_VOLUME = 10000;
	public final static int MAX_AGENT_COUNT = 10; // range 0-9999
	public final static int BURNING_MOTHERSHIP = 20;
	public final Object AGENTLOCK = new Object();
	public final Object SUPPORT_CHANNEL_LOCK = new Object();
	private final Team team;
	private int fuel;
	private int stradegyAgentCount;
	private int shield;
	private final Timer timer;
	private final Map<Integer, Agent> agents;
	private final List<Agent> supportChannel;
	private final TeamMarker teamMarker;

	public Mothership(final int id, final Team team, final AbstractLevel level) {
		super(id, team.getName() + Mothership.class.getSimpleName(), AbstractResourcePanel.ObjectType.Static, level, level.getMothershipBase(id).getPoint(), 100, 100, ObjectShape.Rec);
		Logger.info(this, "Create " + this);
		this.team = team;
		this.team.setMothership(this);
		this.agents = new HashMap<Integer, Agent>();
		this.supportChannel = new ArrayList<Agent>();
		this.teamMarker = new TeamMarker(team, level);
		this.reset();
		this.timer = new Timer(50, this);
	}

	@ Override
	public final void reset() {
		GUIController.setEvent(new PropertyChangeEvent(this, GUIController.LOADING_STATE_CHANGE, 1, "Lade " + team.getName() + " Mutterschiff"));
		fuel = MOTHERSHIP_FUEL_VOLUME;

		final List<Agent> tmpCollection = new LinkedList<Agent>();
		synchronized (AGENTLOCK) {
			tmpCollection.addAll(agents.values());
		}
		for (Agent agent : tmpCollection) {
			agent.kill();
		}
		stradegyAgentCount = team.getAgentCount();
		loadAgents();
		this.shield = 100;
	}

	private void loadAgents() {
		GUIController.setEvent(new PropertyChangeEvent(this, GUIController.LOADING_STATE_CHANGE, stradegyAgentCount, "Lade " + team.getName() + " Agent"));
		Agent agent;
		final int agendFuelVolume = (AGENT_FUEL_VOLUME/stradegyAgentCount);
		boolean commanderFlag = true;
		for (int i = 0; i < stradegyAgentCount; i++) {
			GUIController.setEvent(new PropertyChangeEvent(this, GUIController.LOADING_STEP, null, i));
			agent = new Agent(team.getName() + "Agent", commanderFlag, agendFuelVolume, this);
			commanderFlag = false;
			Agent replacedAgent;
			synchronized (AGENTLOCK) {
				replacedAgent = agents.put(agent.getId(), agent);
			}
			if (replacedAgent != null) {
				Logger.error(this, "Agend with id " + id + " already defined! Kill old instance.");
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
				changes.firePropertyChange(FUEL_STATE_CHANGE, oldFuel, this.fuel);
			} catch (Exception e) {
				Logger.error(this, "Could not order fuel!", e);
			}
		} else {
			return 0;
		}
		return fuel;
	}

	public int getFuel() {
		return fuel;
	}

	public boolean hasFuel() {
		return fuel > 0;
	}

	protected void spendFuel(final int value) {
		if (value + fuel > MOTHERSHIP_FUEL_VOLUME) {
			fuel = MOTHERSHIP_FUEL_VOLUME;
		} else {
			fuel += value;
		}
		changes.firePropertyChange(FUEL_STATE_CHANGE, null, this.fuel);
	}

	public void waitTillGameEnd() {
		synchronized (this) {
			Logger.info(this, "Create " + this);
			if (fuel == 0) {
				return;
			}
			try {
				this.wait();
			} catch (InterruptedException ex) {
				Logger.error(this, "", ex);
			}
		}
	}

	public void startGame() {
		synchronized (AGENTLOCK) {
			for (Agent agent : agents.values()) {
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
				nextAgent.getActionPoints().addActionPoint();
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
			if (i >= stradegyAgentCount || i > 9999) {
				Logger.error(this, "Already to many agents alive.");
				return -1;
			}
			return getId() * 10000 + i;
		}
	}

	/**
	 * Methode just for visual purpose
	 *
	 * @return
	 */
	public TeamMarker getTeamMarker() {
		return teamMarker;
	}

	public void getAgentCount() {
		synchronized (AGENTLOCK) {
			agents.size();
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

	protected void passResource(final Agent agent) throws NotValidException {
		final Resource resource = agent.getResource();
		if (resource != null && getBounds().contains(agent.getBounds())) {
			team.addPoints(resource.use(agent));
		}
	}

	public Collection<Agent> getAgents() {
		synchronized (AGENTLOCK) {
			return agents.values();
		}
	}

	public synchronized void attack() {
		Logger.info(this, "Attack A. Mothership");
		if (shield > 0) {
			shield--;
			if (shield <= BURNING_MOTHERSHIP) {
				if (!timer.isRunning()) {
					timer.start();
				}
			}
			changes.firePropertyChange(SHIELD_STATE_CHANGE, null, shield);
		}
	}

	public synchronized void repair() {
		if (shield < 100) {
			shield++;
			if (shield > BURNING_MOTHERSHIP && timer.isRunning()) {
				timer.stop();
			}
			changes.firePropertyChange(SHIELD_STATE_CHANGE, null, shield);
		}
	}

	public boolean isBurning() {
		return shield < BURNING_MOTHERSHIP && hasFuel();
	}

	public int getShieldForce() {
		return shield;
	}

	public int getShieldPoints() {
		return shield / 10;
	}

	public boolean isMaxDamaged() {
		return shield == 0;
	}

	public boolean isDamaged() {
		return shield < 100;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (!GameManager.getInstance().isPause()) {
			orderFuel(Math.max(0, BURNING_MOTHERSHIP - shield), null);
		}
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

	public boolean needSomeoneSupport(final Agent helper) {
		synchronized (SUPPORT_CHANNEL_LOCK) {
			switch (supportChannel.size()) {
				case 0:
					return false;
				case 1:
					return !supportChannel.contains(helper);
				default:
					return true;
			}
		}
	}

	public Agent getAgentSupportDirection(final Agent helper) throws CouldNotPerformException {
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
				supportChannel.remove(supportCaller);
				supportCaller.setNeedSupport(false);
			}
			return supportCaller;
		}
	}

	public void cancelSupport(Agent agent) {
		synchronized (SUPPORT_CHANNEL_LOCK) {
			supportChannel.remove(agent);
			agent.setNeedSupport(false);
		}
	}

	public boolean existMarker() {
		return teamMarker.isPlaced();
	}

	public void clearMarker() {
		teamMarker.clear();
	}

	public void placeMarker(final Point2D position) {
		teamMarker.place(position);
	}

	public TeamMarker getMarker() throws CouldNotPerformException {
		return teamMarker.getMarker();
	}
}
