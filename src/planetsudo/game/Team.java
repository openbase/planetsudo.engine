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
import logging.Logger;
import planetsudo.level.levelobjects.Mothership;

/**
 *
 * @author divine
 */
public class Team implements Manageable {

	public final static String POINT_STATE_CHANGE = "PointStateChange";

	private final int id;
	private final String name;
	private final Color teamColor;
	private Mothership mothership;
	private Class<? extends AbstractStrategy> strategy;
	private int points;
	
	protected final PropertyChangeSupport changes;

	public Team(int id, String name, Color teamColor, Class<? extends AbstractStrategy> strategy) {
		this.id = id;
		this.name = name;
		this.teamColor = teamColor;
		this.strategy = strategy;
		this.points = 0;
		this.changes = new PropertyChangeSupport(this);
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

	public void addPoint() {
		points++;
		changes.firePropertyChange(POINT_STATE_CHANGE, null, points);
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

	public void addPropertyChangeListener(PropertyChangeListener l) {
	    changes.addPropertyChangeListener(l);
	    Logger.debug(this, "Add "+l.getClass()+" as new PropertyChangeListener.");
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
	    changes.removePropertyChangeListener(l);
	    Logger.debug(this, "Remove PropertyChangeListener "+l.getClass()+".");
	}
}
