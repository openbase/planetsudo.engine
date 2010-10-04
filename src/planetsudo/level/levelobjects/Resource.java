/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.level.levelobjects;

import data.Point2D;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import logging.Logger;
import planetsudo.game.Team;
import planetsudo.level.AbstractLevel;

/**
 *
 * @author divine
 */
public class Resource extends AbstractLevelObject {

	public final static String KILL_EVENT = "KillEvent";

	public enum ResourceType {NORMAL};
	private ResourceType type;
	private Agent owner;
	private final Set<Integer> conquerors;

	public Resource(int id, ResourceType type, AbstractLevel level, Point2D position) {
		super(id, Resource.class.getSimpleName()+"["+id+"]",STATIC_OBJECT, level, position, 25, 25, ObjectShape.Rec);
		this.type = type;
		this.owner = null;
		this.conquerors = Collections.synchronizedSet(new HashSet<Integer>());
		Logger.info(this, "Create "+this);
	}

	@Override
	public void reset() {
	}

	public boolean isOwned() {
		return owner != null;
	}

	public Agent getOwner() {
		return owner;
	}

	public ResourceType getType() {
		return type;
	}

	public boolean setBusy(Team team) {
		synchronized(conquerors) {
			if(conquerors.contains(team.getID())) {
				return false;
			}
			conquerors.add(team.getID());
			return true;
		}
	}

	protected synchronized boolean capture(Agent agent) {
		if(owner != null) {
			return false;
		}
		owner = agent;
		position = agent.getPosition();
		synchronized(conquerors) {
			for(int teamID : conquerors) {
				conquerors.remove(teamID);
			}
		}
		return true;
	}

	public int getCapturingActionPoints() {
		switch(type) {
			case NORMAL:
				return 200;
			default:
				Logger.error(this, "Could not calculate capturing time because resource type is unknown!");
				return 1000;
		}
	}

	protected void release() {
		owner = null;
		position = new Point2D(position);
	}

	public void use() {
		position = new Point2D(position);
		level.removeResource(this);
		changes.firePropertyChange(KILL_EVENT, null, null);
	}
}
