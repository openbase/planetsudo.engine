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
public class Broken extends AbstractLevel {

	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(0, 0);
		levelBorders.addPoint(2000, 0);
		levelBorders.addPoint(2000, 400);
		levelBorders.addPoint(1300, 700);
		levelBorders.addPoint(2000, 600);
		levelBorders.addPoint(1700, 900);
		levelBorders.addPoint(1900, 1100);
		levelBorders.addPoint(1500, 1300);
		levelBorders.addPoint(1800, 1500);
		levelBorders.addPoint(2000, 1800);
                levelBorders.addPoint(2000, 2000);
		levelBorders.addPoint(0, 2000);
		levelBorders.addPoint(0, 1600);
		levelBorders.addPoint(700, 1300);
		levelBorders.addPoint(0, 1400);
		levelBorders.addPoint(300, 1100);
		levelBorders.addPoint(100, 900);
		levelBorders.addPoint(500, 700);
		levelBorders.addPoint(200, 500);
		levelBorders.addPoint(0, 200);
		return levelBorders;
	} 



	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(200, 1800, Direction2D.DOWN);
		homePosition[1] = new Base2D(1800, 200, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[5];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 0, 2000, 2000, 35, ResourceType.Normal);
		resourcePlacement[1] = new ResourceRandomRectangle(1400, 700, 300, 500, 35, ResourceType.ExtremPoint);
		resourcePlacement[2] = new ResourceRandomRectangle(300, 800, 300, 500, 35, ResourceType.ExtremPoint);
		resourcePlacement[3] = new ResourceRandomRectangle(700, 0, 600, 2000, 30, ResourceType.ExtraMothershipFuel);
                resourcePlacement[4] = new ResourceRandomRectangle (700, 0, 600, 2000, 50, ResourceType.ExtraAgentFuel);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(74,164,94);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		return null;
	}
}
