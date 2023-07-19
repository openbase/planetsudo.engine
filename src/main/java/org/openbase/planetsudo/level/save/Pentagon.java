/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.level.save;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2023 openbase.org
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
 * @author Grimmy
 */
public class Pentagon extends AbstractLevel {

	public Pentagon() {
	}

	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(1500, 0);
		levelBorders.addPoint(3000, 1100);
		levelBorders.addPoint(2500, 2900);
                levelBorders.addPoint(500, 2900);
                levelBorders.addPoint(0, 1100);
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(1200, 2700, Direction2D.DOWN);
		homePosition[1] = new Base2D(1800, 2700, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[5];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 1400, 3000, 1100, 50, ResourceType.Normal);
                resourcePlacement[1] = new ResourceRandomRectangle(0, 500, 3000, 900, 50, ResourceType.DoublePoints);
                resourcePlacement[2] = new ResourceRandomRectangle(0, 0, 3000, 500, 50, ResourceType.ExtremPoint);
                resourcePlacement[3] = new ResourceRandomRectangle(0, 0, 3000, 2000, 70, ResourceType.ExtraMothershipFuel);
                resourcePlacement[4] = new ResourceRandomRectangle(0, 0, 3000, 2900, 150, ResourceType.ExtraAgentFuel);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(0, 0, 210);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		Polygon[] walls = new Polygon[7];
		walls[0] = new Polygon();
		walls[0].addPoint(1500, 1200);
		walls[0].addPoint(1850, 1500);
		walls[0].addPoint(1750, 1950);
		walls[0].addPoint(1250, 1950);
                walls[0].addPoint(1150, 1500);
                walls[1] = new Polygon ();
                walls[1].addPoint(1000, 2450);
                walls[1].addPoint(2000, 2450);
                walls[1].addPoint(2000, 2500);
                walls[1].addPoint(1600, 2500);
                walls[1].addPoint(1600, 2900);
                walls[1].addPoint(1400, 2900);
                walls[1].addPoint(1400, 2500);
                walls[1].addPoint(1000, 2500);
                walls[2] = new Polygon();
                walls[2].addPoint(1100, 2200);
                walls[2].addPoint(1100, 2150);
                walls[2].addPoint(1900, 2150);
                walls[2].addPoint(1900, 2200);
                walls[3] = new Polygon();
                walls[3].addPoint(800, 2300);
                walls[3].addPoint(750, 2300);
                walls[3].addPoint(450, 1150);
                walls[3].addPoint(1300, 550);
                walls[3].addPoint(1300, 600);
                walls[3].addPoint(500, 1170);
                walls[4] = new Polygon();
                walls[4].addPoint(1000, 1950);
                walls[4].addPoint(820, 1400);
                walls[4].addPoint(1350, 1000);
                walls[4].addPoint(1350, 1050);
                walls[4].addPoint(900, 1400);
                walls[4].addPoint(1050, 1950);
                walls[5] = new Polygon();
                walls[5].addPoint(1950, 1950);
                walls[5].addPoint(2000, 1950);
                walls[5].addPoint(2180, 1400);
                walls[5].addPoint(1650, 1000);
                walls[5].addPoint(1650, 1050);
                walls[5].addPoint(2100, 1400);
                walls[6] = new Polygon();
                walls[6].addPoint(2200, 2300);
                walls[6].addPoint(2250, 2300);
                walls[6].addPoint(2550, 1150);
                walls[6].addPoint(1700, 550);
                walls[6].addPoint(1700, 600);
                walls[6].addPoint(2500, 1170);
		return walls;
	}
}
