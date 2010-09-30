/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.level.levelobjects;

import data.Point2D;
import logging.Logger;
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

	public Resource(int id, ResourceType type, AbstractLevel level, Point2D position) {
		super(id, Resource.class.getSimpleName()+"["+id+"]",STATIC_OBJECT, level, position, 25, 25, ObjectShape.Oval);
		this.type = type;
		this.owner = null;
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

	protected boolean capture(Agent agent) {
		if(owner != null) {
			return false;
		}
		owner = agent;
		try {
			Thread.sleep(getCapturingTime());
		} catch (InterruptedException ex) {
			Logger.warn(this, "Resource collection time interrupted!", ex);
		}
		position = agent.getPosition();
		return true;
	}

	private long getCapturingTime() {
		switch(type) {
			case NORMAL:
				return 5000;
			default:
				Logger.error(this, "Could not calculate capturing time because resource type is unknown!");
				return 5000;
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
