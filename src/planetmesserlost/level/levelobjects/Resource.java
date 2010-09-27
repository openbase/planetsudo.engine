/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.level.levelobjects;

import data.Point2D;
import logging.Logger;
import planetmesserlost.level.AbstractLevel;

/**
 *
 * @author divine
 */
public class Resource extends AbstractLevelObject {

	public enum ResourceType {NORMAL};
	private ResourceType type;
	private boolean owned;

	public Resource(int id, ResourceType type, AbstractLevel level, Point2D position) {
		super(id, Resource.class.getSimpleName()+"["+id+"]",STATIC_OBJECT, level, position, 25, 25, ObjectShape.Oval);
		this.type = type;
		this.owned = false;
		Logger.info(this, "Create "+this);
	}

	@Override
	public void reset() {
	}

	public boolean isOwned() {
		return owned;
	}

	public ResourceType getType() {
		return type;
	}

	protected boolean own(Agent agent) {
		if(owned) {
			return false;
		}
		owned = true;
		position = agent.getPosition();
		return true;
	}

	protected void release() {
		owned = false;
		position = new Point2D(position);
	}

	public void use() {
		position = new Point2D(position);
		level.removeResource(this);
	}
}
