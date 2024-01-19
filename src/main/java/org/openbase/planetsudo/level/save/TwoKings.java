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

import org.openbase.planetsudo.level.AbstractLevel;
import org.openbase.planetsudo.level.ResourcePlacement;
import org.openbase.planetsudo.level.ResourceRandomRectangle;
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType;
import java.awt.Color;
import java.awt.Polygon;
import org.openbase.planetsudo.geometry.Base2D;
import org.openbase.planetsudo.geometry.Direction2D;



/**
 *
 * @author noxus
 */
public class TwoKings extends AbstractLevel {
    
	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(0, 0);
		levelBorders.addPoint(375, 300);
		levelBorders.addPoint(750,0);
		levelBorders.addPoint(1125, 300);
		levelBorders.addPoint(1500, 0);
		levelBorders.addPoint(1500, 1500);
		levelBorders.addPoint(1125, 1200);
		levelBorders.addPoint(750, 1500);
		levelBorders.addPoint(375, 1200);
		levelBorders.addPoint(0, 1500);
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(750, 150, Direction2D.DOWN);
		homePosition[1] = new Base2D(750, 1350, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[10];
		resourcePlacement[0] = new ResourceRandomRectangle(350, 650, 100, 200, 10, ResourceType.ExtremPoint);
		resourcePlacement[1] = new ResourceRandomRectangle(1050, 650, 100, 200, 10, ResourceType.ExtremPoint);
		resourcePlacement[2] = new ResourceRandomRectangle(0, 0, 1500, 650, 5, ResourceType.ExtraAgentFuel);
		resourcePlacement[3] = new ResourceRandomRectangle(0, 850, 1500, 650, 5, ResourceType.ExtraAgentFuel);
		resourcePlacement[4] = new ResourceRandomRectangle(0, 0, 1500, 650, 5, ResourceType.ExtraMothershipFuel);
		resourcePlacement[5] = new ResourceRandomRectangle(0, 850, 1500, 650, 5, ResourceType.ExtraMothershipFuel);
		resourcePlacement[6] = new ResourceRandomRectangle(0, 0, 1500, 650, 10, ResourceType.Normal);
                resourcePlacement[7] = new ResourceRandomRectangle(0, 850, 1500, 650, 10, ResourceType.Normal);
                resourcePlacement[8] = new ResourceRandomRectangle(0, 0, 1500, 650, 5, ResourceType.DoublePoints);
                resourcePlacement[9] = new ResourceRandomRectangle(0, 850, 1500, 650, 5, ResourceType.DoublePoints);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(37,67,162);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		Polygon[] walls = new Polygon[3];
		walls[0] = new Polygon();
		walls[0].addPoint(0, 650);
		walls[0].addPoint(300, 650);
		walls[0].addPoint(300, 850);
		walls[0].addPoint(0, 850);
                walls[1] = new Polygon();
                walls[1].addPoint(500, 650);
                walls[1].addPoint(1000, 650);
                walls[1].addPoint(1000, 850);
                walls[1].addPoint(500, 850);
                walls[2] = new Polygon();
                walls[2].addPoint(1200, 650);
                walls[2].addPoint(1500, 650);
                walls[2].addPoint(1500, 850);
                walls[2].addPoint(1200, 850);
		return walls;
	}
}
/*
 *
 * @author Grimmy
 */
