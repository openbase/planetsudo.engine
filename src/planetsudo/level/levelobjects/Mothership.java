/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.level.levelobjects;

import data.Point2D;
import java.util.Collection;
import java.util.HashMap;
import logging.Logger;
import planetsudo.game.Team;
import planetsudo.level.AbstractLevel;

/**
 *
 * @author divine
 */
public class Mothership extends AbstractLevelObject {

	public final static String FUEL_STATE_CHANGE = "FuelStateChange";

	public final static int DEFAULT_START_FUEL = 30000;
	public final static int DEFAULT_AGENT_COUNT = 10; // range 0-9999

	private final Team team;
	private int fuel;
	private int agentMaxCount;
	private int shield;

	private final HashMap<Integer, Agent> agents;
	

	public Mothership(int id, Team team, AbstractLevel level) {
		super(id, team.getName()+Mothership.class.getSimpleName(), STATIC_OBJECT, level, level.getMothershipHomePosition(id), 100, 100, ObjectShape.Rec);
		Logger.info(this, "Create "+this);
		this.team = team;
		this.team.setMothership(this);
		this.agents = new HashMap<Integer, Agent>();
		this.reset();
	}

	@ Override
	public void reset() {
		fuel = DEFAULT_START_FUEL;
		for(Agent agent : agents.values()) {
			agent.kill();
		}
		agentMaxCount = DEFAULT_AGENT_COUNT;
		loadAgents();
		this.shield = 100;
	}

	public void loadAgents() {
		Agent agent;
		for(int i=0;i<agentMaxCount;i++) {
			agent = new Agent(team.getName()+"Agent", this);
			Agent replacedAgent = agents.put(agent.getID(), agent);
			if(replacedAgent != null){
				Logger.error(this, "Add agent with same id like an other one! Kill old one.");
				replacedAgent.kill();
			}
		}
		agentKeyArray = new Integer[agents.size()];
		agentKeyArray = agents.keySet().toArray(agentKeyArray);
	}

	public synchronized int orderFuel(int fuel) {
		int oldFuel = this.fuel;
		if(fuel ==  0) { // fuel emty
			fuel = 0;
		} else if(this.fuel < fuel) { // use last fuel
			fuel = this.fuel;
			this.fuel = 0;
			synchronized(this) {
				notify();
			}
		} else {
			this.fuel -= fuel;
		}
		changes.firePropertyChange(FUEL_STATE_CHANGE, oldFuel, this.fuel);
		return fuel;
	}

	public int getFuel() {
		return fuel;
	}

	public boolean hasFuel() {
		return fuel > 0;
	}

	public void waitTillFuelRunsOut() {
		synchronized(this) {Logger.info(this, "Create "+this);
			if(fuel == 0) return;
			try {
				this.wait();
			} catch (InterruptedException ex) {
				Logger.error(this, "", ex);
			}
		}
	}

	public void startGame() {
		for(Agent agent : agents.values()) {
			agent.startGame();
		}
	}

	private int agentIndex = 0;
	private Integer[] agentKeyArray;
	public void addActionPoint() {
		agents.get(agentKeyArray[agentIndex]).getActionPoints().addActionPoint();
		agentIndex = (agentIndex+1) % (agents.size());
	}

	public Team getTeam() {
		return team;
	}

	public synchronized int registerAgent() {
		int i;
		for(i=0;i<=agents.size();i++) {
			if(!agents.containsKey(getID()*10000+i)) {
				break;
			}
		}
		if(i>=agentMaxCount || i>9999) {
			Logger.error(this, "Already to many agents alive.");
			return -1;
		}
		return getID()*10000+i;
	}

	public void getAgentCount() {
		agents.size();
	}

	protected void removeAgent(Agent agent) {
		agents.remove(agent.getID());
	}

	public Point2D getAgentHomePosition() {
		return position.clone();
	}

	protected void passResource(Agent agent) {
		Resource resource = agent.getResource();
		if(resource != null) {
			team.addPoint();
			resource.use();
		}
	}

	public Collection<Agent> getAgends() {
		return agents.values();
	}

	public synchronized void attack() {
		if(shield > 0) {
			shield--;
		}
	}

	public synchronized void repaire() {
		if(shield < 100) {
			shield++;
		}
	}

	public boolean isBurning() {
		return shield < 33;
	}

	public int getShieldForce() {
		return shield;
	}

	public boolean isMaxDamaged() {
		return shield == 0;
	}

	public boolean isDamaged() {
		return shield < 100;
	}



}
