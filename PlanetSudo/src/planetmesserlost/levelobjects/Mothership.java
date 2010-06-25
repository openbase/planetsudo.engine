/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.levelobjects;

import data.Point2D;
import java.util.HashMap;
import java.util.Iterator;
import logging.Logger;
import planetmesserlost.game.ActionPoints;
import planetmesserlost.game.Team;
import planetmesserlost.level.Level;

/**
 *
 * @author divine
 */
public class Mothership extends AbstractLevelObject{

	public final static int DEFAULT_START_FUEL = 10000;
	public final static int DEFAULT_AGENT_COUNT = 1000; // range 0-9999

	private final Team team;
	private ActionPoints actionPoints;
	private int fuel;
	private int agentMaxCount;

	private final HashMap<Integer, Agent> agents;

	public Mothership(int id, Team team, Level level) {
		super(id, team.getName()+Mothership.class.getSimpleName(), level, level.getMothershipHomePosition(id), 100, 100, ObjectShape.Rec);
		Logger.info(this, "Create "+this);
		this.team = team;
		this.agents = new HashMap<Integer, Agent>();
		this.reset();
		this.loadAgents();
		
	}

	@ Override
	public void reset() {
		fuel = DEFAULT_START_FUEL;
		for(Agent agent : agents.values()) {
			agent.kill();
		}
		agentMaxCount = DEFAULT_AGENT_COUNT;
		actionPoints = new ActionPoints();
	}

	public void loadAgents() {
		Agent agent;
		for(int i=0;i<agentMaxCount;i++) {
			agent = new Agent(team.getName()+"Agent", this);
			agents.put(agent.getID(), agent);
		}
	}

	public synchronized int orderFuel(int fuel) {
		if(fuel ==  0) { // fuel emty
			fuel = 0;
		} else if(this.fuel < fuel) { // use last fuel
			fuel = this.fuel;
			this.fuel = 0;
			synchronized(this) {
				notify();
			}
		} else {
			this.fuel =- fuel;
		}
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

	public void addActionPoint() {
		actionPoints.addActionPoint();
	}

	public void getActionPoint() {
		actionPoints.getActionPoint();
	}

	public void getActionPoint(int points) {
		actionPoints.getActionPoint(points);
	}

	public int getActionPoints() {
		return actionPoints.getActionPoints();
	}

	public Team getTeam() {
		return team;
	}

	public synchronized int registerAgent() {
		int i;
		for(i=0;i<=agents.size();i++) {
			if(!agents.containsKey(i)) {
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

	public Iterator<Agent> getAgends() {
		return agents.values().iterator();
	}

}
