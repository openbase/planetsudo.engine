/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.level.save;

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

import java.awt.Color;
import java.awt.Polygon;
import org.openbase.planetsudo.geometry.Base2D;
import org.openbase.planetsudo.geometry.Direction2D;
import org.openbase.planetsudo.level.AbstractLevel;
import org.openbase.planetsudo.level.ResourcePlacement;
import org.openbase.planetsudo.level.ResourceRandomRectangle;
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class Hjab extends AbstractLevel {

	public Hjab() {
	}

	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(1400, 0);
		levelBorders.addPoint(2400, 400);
		levelBorders.addPoint(2800, 1400);
                levelBorders.addPoint(2400, 2400);
                levelBorders.addPoint(1400, 2800);
                levelBorders.addPoint(400, 2400);
                levelBorders.addPoint(0, 1400);
                levelBorders.addPoint(400, 400);
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(1400, 1000, Direction2D.DOWN);
		homePosition[1] = new Base2D(1400, 1800, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[6];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 0, 0, 0, 0, ResourceType.Normal);
                resourcePlacement[1] = new ResourceRandomRectangle(0, 0, 0, 0, 0, ResourceType.Normal);
		resourcePlacement[2] = new ResourceRandomRectangle(0, 0, 0, 0, 0, ResourceType.DoublePoints);
                resourcePlacement[3] = new ResourceRandomRectangle(0, 0, 0, 0, 0, ResourceType.DoublePoints);
		resourcePlacement[4] = new ResourceRandomRectangle(0, 0, 0, 0, 0, ResourceType.ExtremPoint);
		resourcePlacement[5] = new ResourceRandomRectangle(0, 0, 0, 0, 0, ResourceType.ExtraMothershipFuel);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(250, 250, 210);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		Polygon[] walls = new Polygon[1];
		walls[0] = new Polygon();
		walls[0].addPoint(1200, 600);
		walls[0].addPoint(1200, 1200);
		walls[0].addPoint(1600, 1200);
		walls[0].addPoint(1600, 600);
                walls[0].addPoint(1800, 800);
                walls[0].addPoint(1800, 1200);
		walls[0].addPoint(1401, 1400);
		walls[0].addPoint(1800, 1600);
		walls[0].addPoint(1800, 2000);
                walls[0].addPoint(1600, 2200);
                walls[0].addPoint(1600, 1600);
		walls[0].addPoint(1200, 1600);
		walls[0].addPoint(1200, 2200);
		walls[0].addPoint(1000, 2000);
                walls[0].addPoint(1000, 1600);
                walls[0].addPoint(1399, 1400);
		walls[0].addPoint(1000, 1200);
		walls[0].addPoint(1000, 800);
		return walls;
	}
}
