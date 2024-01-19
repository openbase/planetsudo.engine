/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.level.save;

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
public class Maze extends AbstractLevel {

	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(0, 0);
		levelBorders.addPoint(2000, 0);
		levelBorders.addPoint(2000, 200);
		levelBorders.addPoint(200, 200);
		levelBorders.addPoint(200, 300);
		levelBorders.addPoint(2000, 300);
		levelBorders.addPoint(2000, 1400);
		levelBorders.addPoint(200, 1400);
		levelBorders.addPoint(200, 1500);
		levelBorders.addPoint(2000, 1500);
		levelBorders.addPoint(2000, 2000);
		levelBorders.addPoint(0, 2000);
		levelBorders.addPoint(0, 1800);
		levelBorders.addPoint(1800, 1800);
		levelBorders.addPoint(1800, 1700);
		levelBorders.addPoint(0, 1700);
		levelBorders.addPoint(0, 600);
		levelBorders.addPoint(1800, 600);
		levelBorders.addPoint(1800, 500);
		levelBorders.addPoint(0, 500);
		return levelBorders;
	}



	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(1900, 100, Direction2D.DOWN);
		homePosition[1] = new Base2D(100, 1900, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[8];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 300, 2000, 200, 10, ResourceType.Normal);
                resourcePlacement[1] = new ResourceRandomRectangle(0, 1500, 2000, 200, 10, ResourceType.Normal);
		resourcePlacement[2] = new ResourceRandomRectangle(0, 600, 2000, 200, 7, ResourceType.DoublePoints);
		resourcePlacement[3] = new ResourceRandomRectangle(300, 900, 600, 200, 50, ResourceType.ExtremPoint);
		resourcePlacement[4] = new ResourceRandomRectangle(1100, 900, 600, 200, 50, ResourceType.ExtremPoint);
		resourcePlacement[5] = new ResourceRandomRectangle(0, 300, 2000, 1400, 40, ResourceType.ExtraAgentFuel);
		resourcePlacement[6] = new ResourceRandomRectangle(0, 300, 2000, 1400, 30, ResourceType.ExtraMothershipFuel);
		resourcePlacement[7] = new ResourceRandomRectangle(0, 1200, 2000, 200, 7, ResourceType.DoublePoints);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(255,211,155);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
	Polygon[] walls = new Polygon[2];
		walls[0] = new Polygon();
		walls[0].addPoint(200, 800);
		walls[0].addPoint(900, 800);
		walls[0].addPoint(900, 900);
		walls[0].addPoint(300, 900);
                walls[0].addPoint(300, 1100);
                walls[0].addPoint(900, 1100);
		walls[0].addPoint(900, 1200);
                walls[0].addPoint(200, 1200);
                walls[1] = new Polygon ();
                walls[1].addPoint(1100, 800);
                walls[1].addPoint(1800, 800);
                walls[1].addPoint(1800, 1200);
                walls[1].addPoint(1100, 1200);
                walls[1].addPoint(1100, 1100);
                walls[1].addPoint(1700, 1100);
                walls[1].addPoint(1700, 900);
                walls[1].addPoint(1100, 900);	
                return walls;
	}
}

