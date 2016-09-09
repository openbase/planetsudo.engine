/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game;

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

import org.slf4j.Logger;
import org.openbase.planetsudo.level.levelobjects.Agent;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class ActionPoints {

    private static final Logger logger = LoggerFactory.getLogger(ActionPoints.class);
    
	private int points;
	private final Agent agent;

	public ActionPoints(Agent agent) {
		this.points = 0;
		this.agent = agent;
	}

	public void addActionPoint() {
		if (agent.isDisabled() && !agent.isAtMothership()) {
			return;
		}
		synchronized (this) {
			points++;
			this.notify();
		}
	}

	public void getActionPoint() {
		getActionPoint(1);
	}

	public void getActionPoint(int orderedPoints) {
		while (true) {
			synchronized (this) {
				if (points >= orderedPoints) {
					points -= orderedPoints;
					return;
				}
				try {
					this.wait();
				} catch (InterruptedException ex) {
					logger.warn("", ex);
				}
			}
		}
	}

	public int getActionPoints() {
		return points;
	}
}
