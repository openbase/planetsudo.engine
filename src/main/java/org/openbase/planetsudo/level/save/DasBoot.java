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
public class DasBoot extends AbstractLevel {
	
	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(0, 750);
		levelBorders.addPoint(750, 750);
		levelBorders.addPoint(750, 375);
		levelBorders.addPoint(1000, 375);
                levelBorders.addPoint(1000, 0);
		levelBorders.addPoint(1650, 0);
		levelBorders.addPoint(1650, 225);
		levelBorders.addPoint(1150, 225);
                levelBorders.addPoint(1150, 375);
		levelBorders.addPoint(1425, 375);
		levelBorders.addPoint(1425, 750);
		levelBorders.addPoint(2175, 750);
                levelBorders.addPoint(1725, 1500);
		levelBorders.addPoint(450, 1500);
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(600, 1350, Direction2D.DOWN);
		homePosition[1] = new Base2D(1575, 1350, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[6];
		resourcePlacement[0] = new ResourceRandomRectangle(1125, 0, 525, 225, 40, ResourceType.ExtremPoint);
		resourcePlacement[1] = new ResourceRandomRectangle(1050, 0, 75, 525, 30, ResourceType.ExtraAgentFuel);
		resourcePlacement[2] = new ResourceRandomRectangle(825, 750, 600, 450, 15, ResourceType.ExtraMothershipFuel);
		resourcePlacement[3] = new ResourceRandomRectangle(350, 750, 375, 300, 10, ResourceType.Normal);
                resourcePlacement[4] = new ResourceRandomRectangle(1500, 750, 375, 300, 10, ResourceType.Normal);
                resourcePlacement[5] = new ResourceRandomRectangle(750, 375, 675, 375, 15, ResourceType.DoublePoints);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(0,191,255);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
            		Polygon[] resourcePlacement = new Polygon[3];
		resourcePlacement[0] = new Polygon();
		resourcePlacement[0].addPoint(675, 900);
		resourcePlacement[0].addPoint(825, 900);
		resourcePlacement[0].addPoint(825, 1050);
		resourcePlacement[0].addPoint(675, 1050);
                resourcePlacement[1] = new Polygon();
		resourcePlacement[1].addPoint(1350, 900);
		resourcePlacement[1].addPoint(1500, 900);
		resourcePlacement[1].addPoint(1500, 1050);
		resourcePlacement[1].addPoint(1350, 1050);
                resourcePlacement[2] = new Polygon();
		resourcePlacement[2].addPoint(1012, 1200);
		resourcePlacement[2].addPoint(1163, 1200);
		resourcePlacement[2].addPoint(1163, 1500);
		resourcePlacement[2].addPoint(1012, 1500);
		return resourcePlacement;
	}
}
/*
 *
 * @author Grimmy
 */
