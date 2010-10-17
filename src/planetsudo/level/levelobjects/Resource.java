/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.level.levelobjects;

import data.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import logging.Logger;
import planetsudo.game.Team;
import planetsudo.level.AbstractLevel;

/**
 *
 * @author divine
 */
public class Resource extends AbstractLevelObject {

	public final static String KILL_EVENT = "KillEvent";
	public final static int RESOURCE_SIZE = 25;

	public enum ResourceType {Normal, DoublePoints, ExtremPoint, ExtraAgentFuel, ExtraMothershipFuel, Bomb};
	private ResourceType type;
	private Agent owner;
	private final List<Integer> conquerors;

	public Resource(int id, ResourceType type, AbstractLevel level, Point2D position) {
		super(id, Resource.class.getSimpleName()+"["+id+"]",STATIC_OBJECT, level, position, RESOURCE_SIZE, RESOURCE_SIZE, ObjectShape.Rec);
		this.type = type;
		this.owner = null;
		this.conquerors = Collections.synchronizedList(new ArrayList<Integer>());
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
			conquerors.clear();
		}
		setStatic(false);
		return true;
	}

	public int getCapturingActionPoints() {
		switch(type) {
			case Normal:
				return 200;
			case DoublePoints:
				return 400;
			case ExtremPoint:
				return 700;
			case ExtraAgentFuel:
				return 400;
			case ExtraMothershipFuel:
				return 400;
			case Bomb:
				return 200;
			default:
				Logger.error(this, "Could not calculate capturing time because resource type is unknown!");
				return 1000;
		}
	}

	protected void release() {
		owner = null;
		position = new Point2D(position);
		setStatic(true);
	}

	public int use(Agent agent) {
		position = new Point2D(position);
		level.removeResource(this);
		changes.firePropertyChange(KILL_EVENT, null, null);

		switch(type) {
			case Normal:
				return 1;
			case DoublePoints:
				return 2;
			case ExtremPoint:
				return 5;
			case ExtraAgentFuel:
				agent.spendFuel(Agent.DEFAULT_START_FUEL/2);
				return 0;
			case ExtraMothershipFuel:
				agent.getMothership().spendFuel(Agent.DEFAULT_START_FUEL/5);
				return 0;
			case Bomb:
				return 0;
			default:
				return 0;
		}
	}
}
