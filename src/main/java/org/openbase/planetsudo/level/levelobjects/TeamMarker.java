/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.level.levelobjects;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2020 openbase.org
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

import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.visual.swing.engine.draw2d.AbstractResourcePanel.ObjectType;
import org.openbase.planetsudo.game.GameSound;
import org.openbase.planetsudo.game.Team;
import org.openbase.planetsudo.geometry.Point2D;
import org.openbase.planetsudo.level.AbstractLevel;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class TeamMarker extends AbstractLevelObject {

	public static final double MARKER_SIZE = 50;
	private final Object MARKER_LOCK = new Object();
	private boolean placed;
	private Team team;

	public TeamMarker(final Team team, final AbstractLevel level) {
		super(0, team.getName() + "Marker", ObjectType.Static, level, new Point2D(), MARKER_SIZE, MARKER_SIZE, ObjectShape.Oval);
		this.placed = false;
		this.team = team;
	}

	public void place(final Point2D position) {
		synchronized (MARKER_LOCK) {
			if(placed && this.position.equals(position)) {
				return;
			}
			this.position = position;
			levelView.updateObjectMovement();
			placed = true;
		}
		GameSound.DeployMarker.play();
	}

	public void clear() {
		synchronized (MARKER_LOCK) {
			placed = false;
		}
	}

	public boolean isPlaced() {
		return placed;
	}

	public TeamMarker getMarker() throws CouldNotPerformException {
		synchronized (MARKER_LOCK) {
			if (!placed) {
				throw new CouldNotPerformException("Marker not placed!");
			}
			return this;
		}
	}

	public Team getTeam() {
		return team;
	}
	
	@Override
	protected void reset() {
		clear();
	}

	protected boolean seeMarker(Agent agent) {
		if(!placed) {
			return false;
		}

		return getBounds().intersects(agent.getBounds());
	}
}
