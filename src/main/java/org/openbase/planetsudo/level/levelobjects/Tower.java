/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.level.levelobjects;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2016 openbase.org
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
 * @author divine
 */
public class Tower extends AbstractLevelObject {

	public final static int SIZE = 100;

	public enum TowerType {

		DefenceTower, ObservationTower, DoublePoints, ExtremPoint, ExtraAgentFuel, ExtraMothershipFuel, Mine
	};
    
    private static final Logger logger = LoggerFactory.getLogger(Tower.class);
    
	private TowerType type;
	private Agent owner;
	private final List<String> conquerors;
	private final Object RESOURCE_LOCK = new Object();
	private boolean used;
	private ResourcePlacement placement;


	// Constructor just for mine
	public Tower(int id, AbstractLevel level, Agent placer) {
		this(id, TowerType.Mine, level, new Point2D(placer.getPosition()));
		this.placedBy = placer.getTeam();
	}

	public Tower(int id, TowerType type, AbstractLevel level, ResourcePlacement placement) {
		this(id, type, level, placement.calcRandomLevelPosition(level));
		this.placement = placement;
	}

	public Tower(int id, TowerType type, AbstractLevel level, Point2D position) {
		super(id, Tower.class.getSimpleName() + "[" + id + "]", AbstractResourcePanel.ObjectType.Dynamic, level, position, SIZE, SIZE, ObjectShape.Rec);
		this.type = type;
		this.owner = null;
		this.used = false;
		this.conquerors = new ArrayList<>();
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

	public TowerType getType() {
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

	public boolean isUsed() {
		return used;
	}
}
