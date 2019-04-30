/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.level.levelobjects;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2019 openbase.org
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

import java.util.ArrayList;
import java.util.List;
import org.openbase.jul.exception.InvalidStateException;
import org.openbase.jul.visual.swing.engine.draw2d.AbstractResourcePanel;
import org.slf4j.Logger;
import org.openbase.planetsudo.game.Team;
import org.openbase.planetsudo.geometry.Point2D;
import org.openbase.planetsudo.level.AbstractLevel;
import org.openbase.planetsudo.level.ResourcePlacement;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class Resource extends AbstractLevelObject {

	public final static String KILL_EVENT = "KillEvent";
	public final static int RESOURCE_SIZE = 25;

	public enum ResourceType {

		Unknown, Normal, DoublePoints, ExtremPoint, ExtraAgentFuel, ExtraMothershipFuel, Mine
	};
    
    private static final Logger logger = LoggerFactory.getLogger(Resource.class);
    
	private ResourceType type;
	private Agent owner;
	private final List<String> conquerors;
	private final Object RESOURCE_LOCK = new Object();
	private boolean used;
	private ResourcePlacement placement;


	// Constructor just for mine
	public Resource(int id, AbstractLevel level, Agent placer) {
		this(id, ResourceType.Mine, level, new Point2D(placer.getPosition()));
		this.placedBy = placer.getTeam();
	}

	public Resource(int id, ResourceType type, AbstractLevel level, ResourcePlacement placement) {
		this(id, type, level, placement.calcRandomLevelPosition(level));
		this.placement = placement;
	}

	public Resource(int id, ResourceType type, AbstractLevel level, Point2D position) {
		super(id, Resource.class.getSimpleName() + "[" + id + "]", AbstractResourcePanel.ObjectType.Dynamic, level, position, RESOURCE_SIZE, RESOURCE_SIZE, ObjectShape.Rec);
		this.type = type;
		this.owner = null;
		this.used = false;
		this.conquerors = new ArrayList<String>();
		logger.info("Create " + this);
	}

	public ResourcePlacement getPlacement() {
		return placement;
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

	public boolean setBusy(final Team team) {
		if(isOwned()) {
			return false;
		}
		synchronized (RESOURCE_LOCK) {
			if (conquerors.contains(team.getName())) {
				return false;
			}
			conquerors.add(team.getName());
			return true;
		}
	}

	public boolean isSaveFor(final Agent agent) {
		return wasPlacedByTeam() != agent.getTeam();
	}

	protected boolean capture(final Agent agent) throws InvalidStateException {
		synchronized (RESOURCE_LOCK) {
			if (owner != null) {
				return false;
			}

			owner = agent;
			conquerors.clear();
		}
		position = agent.getPosition();
		switch (type) {
			case ExtraAgentFuel:
				use(agent);
				return false;
			case Mine:
				use(agent);
				return false;
			default:
				return true;
		}
	}

	public int getCapturingActionPoints() {
		switch (type) {
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
			case Mine:
				return 200;
			default:
				logger.error("Could not calculate capturing time because resource type is unknown!");
				return 1000;
		}
	}
	private Team placedBy = null;

	public Team wasPlacedByTeam() {
		return placedBy;
	}

	protected void release() {
		synchronized (RESOURCE_LOCK) {
			owner = null;
			position = position.clone();
		}
	}

	public int use(Agent agent) throws InvalidStateException {
		synchronized (RESOURCE_LOCK) {
			if (agent != owner) {
				logger.warn("Resource not owned!");
				throw new InvalidStateException("Resource not owned by user!");
			}
			if (!used) {
				used = true;
				position = new Point2D(position);
				level.removeResource(this);
				changes.firePropertyChange(KILL_EVENT, null, null);

				switch (type) {
					case Normal:
						return 10;
					case DoublePoints:
						return 20;
					case ExtremPoint:
						return 50;
					case ExtraAgentFuel:
						agent.spendFuel(Mothership.AGENT_FUEL_VOLUME / 10);
						return 0;
					case ExtraMothershipFuel:
						agent.getMothership().spendFuel(Mothership.MOTHERSHIP_FUEL_VOLUME / 5);
						return 0;
					case Mine:
						agent.kill();
						return 0;
					default:
						return 0;
				}
			}
		}
		logger.warn("Ignore double resource use!");
		return 0;
	}

	public boolean isUsed() {
		return used;
	}
}
