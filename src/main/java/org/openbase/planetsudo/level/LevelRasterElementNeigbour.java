/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.level;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2024 openbase.org
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
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class LevelRasterElementNeigbour {

    private static final Logger logger = LoggerFactory.getLogger(LevelRasterElementNeigbour.class);
    
	public enum NeigbourType {North, West, East, South, NorthEast, SouthEast, SouthWest, NorthWest};

	private final LevelRasterElement element;
	private final NeigbourType type;
	private final int angle;
	private final int weight;

	public LevelRasterElementNeigbour(LevelRasterElement element, int weight, NeigbourType type) {
		this.type = type;
		this.element = element;
		this.weight = weight;
		this.angle = calcAngle();
	}

	public LevelRasterElement getElement() {
		return element;
	}

	public int getWeight() {
		return weight;
	}

	public NeigbourType getType() {
		return type;
	}

	public int getAngle() {
		return angle;
	}

	private int calcAngle() {
		switch(type) {
			case North:
				return 270;
			case NorthEast:
				return 315;
			case East:
				return 0;
			case SouthEast:
				return 45;
			case South:
				return 90;
			case SouthWest:
				return 135;
			case West:
				return 180;
			case NorthWest:
				return 225;
			default:
				logger.error("Unknown NeigbourType: "+type);
				return 0;
		}
	}
}
