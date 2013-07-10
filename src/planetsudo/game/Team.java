/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.game;

import java.awt.Color;
import planetsudo.game.strategy.AbstractStrategy;
import concepts.Manageable;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import de.dc.util.logging.Logger;
import planetsudo.level.levelobjects.Mothership;

/**
 *
 * @author divine
 */
public class Team implements Manageable, Serializable {

	public final static String POINT_STATE_CHANGE = "PointStateChange";

	private final int id;
	private final String name;
	private final Color teamColor;
	private Mothership mothership;
	private Class<? extends AbstractStrategy> strategy;
	private int points;
//	private int agentCount;
	private ArrayList<String> members;
	
	protected final PropertyChangeSupport changes;

	public Team(int id, String name, Color teamColor, Class<? extends AbstractStrategy> strategy, Collection<String> members) {
		this.id = id;
		this.name = name;
		this.teamColor = teamColor;
		this.strategy = strategy;
		this.points = 0;
		this.changes = new PropertyChangeSupport(this);
		this.members = new ArrayList<String>();
		this.members.addAll(members);
	}

	private int loadAgentCount() {
		int counter = 0;
		try {
			counter = strategy.newInstance().getAgentCount();
		} catch (InstantiationException ex) {
			Logger.error(this, "Could not create strategy instance!", ex);
		} catch (IllegalAccessException ex) {
			Logger.error(this, "Could not access strategy file!", ex);
		}
		if(counter > Mothership.MAX_AGENT_COUNT || counter <= 0){
			Logger.info(this, "Bad agent count! Corrected to "+Mothership.MAX_AGENT_COUNT);
			counter = Mothership.MAX_AGENT_COUNT;
		}
		return counter;
	}

	public void reset() {
		points = 0;
	}

	@Override
	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getPoints() {
		return points;
	}

	public int getAgentCount() {
		return loadAgentCount();
	}

	public void addPoint() {
		addPoints(1);
	}

	public void addPoints(int points) {
		this.points += points;
		changes.firePropertyChange(POINT_STATE_CHANGE, null, this.points);
	}

	public Mothership getMothership() {
		return mothership;
	}

	public void setMothership(Mothership mothership) {
		this.mothership = mothership;
	}

	public Class<? extends AbstractStrategy> getStrategy() {
		return strategy;
	}

	@ Override
	public String toString() {
		return name;
	}

	public Color getTeamColor() {
		return teamColor;
	}

	public ArrayList<String> getMembers() {
		return members;
	}



	public int getFinalPoints() {
		return mothership.getAgentsAtHomePosition() + mothership.getShieldPoints() + points;
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
	    changes.addPropertyChangeListener(l);
	    Logger.debug(this, "Add "+l.getClass()+" as new PropertyChangeListener.");
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
	    changes.removePropertyChangeListener(l);
	    Logger.debug(this, "Remove PropertyChangeListener "+l.getClass()+".");
	}
}
