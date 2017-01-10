/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.level.save;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2017 openbase.org
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
public class AtomumLight extends AbstractLevel {
	
	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(1000, 0);
		levelBorders.addPoint(2000, 1000);
		levelBorders.addPoint(1000, 2000);
		levelBorders.addPoint(0, 1000);
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(150, 1000, Direction2D.DOWN);
		homePosition[1] = new Base2D(1850, 1000, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[19];
		resourcePlacement[0] = new ResourceRandomRectangle(800, 800, 400, 400, 15, ResourceType.ExtraAgentFuel);
		resourcePlacement[1] = new ResourceRandomRectangle(800, 0, 300, 150, 50, ResourceType.ExtremPoint);
		resourcePlacement[2] = new ResourceRandomRectangle(800, 1850, 300, 150, 50, ResourceType.ExtremPoint);
		resourcePlacement[3] = new ResourceRandomRectangle(900, 1200, 300, 250, 15, ResourceType.ExtraMothershipFuel);
		resourcePlacement[4] = new ResourceRandomRectangle(900, 550, 300, 250, 15, ResourceType.ExtraMothershipFuel);
		resourcePlacement[5] = new ResourceRandomRectangle(500, 850, 250, 300, 5, ResourceType.Normal);
                resourcePlacement[6] = new ResourceRandomRectangle(1250, 850, 250, 300, 5, ResourceType.Normal);
                resourcePlacement[7] = new ResourceRandomRectangle(550, 450, 100, 100, 3, ResourceType.DoublePoints);
                resourcePlacement[8] = new ResourceRandomRectangle(700, 300, 100, 100, 3, ResourceType.DoublePoints);
                resourcePlacement[9] = new ResourceRandomRectangle(400, 600, 100, 100, 3, ResourceType.DoublePoints);
                resourcePlacement[10] = new ResourceRandomRectangle(300, 1300, 100, 100, 3, ResourceType.DoublePoints);
                resourcePlacement[11] = new ResourceRandomRectangle(450, 1450, 100, 100, 3, ResourceType.DoublePoints);
                resourcePlacement[12] = new ResourceRandomRectangle(600, 1600, 100, 100, 3, ResourceType.DoublePoints);
                resourcePlacement[13] = new ResourceRandomRectangle(1300, 300, 100, 100, 3, ResourceType.DoublePoints);
                resourcePlacement[14] = new ResourceRandomRectangle(1450, 450, 100, 100, 3, ResourceType.DoublePoints);
                resourcePlacement[15] = new ResourceRandomRectangle(1600, 600, 100, 100, 3, ResourceType.DoublePoints);
                resourcePlacement[16] = new ResourceRandomRectangle(1600, 1300, 100, 100, 3, ResourceType.DoublePoints);
                resourcePlacement[17] = new ResourceRandomRectangle(1440, 1450, 100, 100, 3, ResourceType.DoublePoints);
                resourcePlacement[18] = new ResourceRandomRectangle(1300, 1600, 100, 100, 3, ResourceType.DoublePoints);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(169,249,4);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
            		Polygon[] resourcePlacement = new Polygon[4];
		resourcePlacement[0] = new Polygon();
		resourcePlacement[0].addPoint(375, 750);
		resourcePlacement[0].addPoint(875, 250);
		resourcePlacement[0].addPoint(900, 300);
		resourcePlacement[0].addPoint(900, 800);
                resourcePlacement[0].addPoint(750, 850);
                resourcePlacement[0].addPoint(500, 850);
                resourcePlacement[0].addPoint(400, 900);
                resourcePlacement[0].addPoint(350, 900);
                resourcePlacement[1] = new Polygon();
		resourcePlacement[1].addPoint(1625, 750);
		resourcePlacement[1].addPoint(1125, 250);
		resourcePlacement[1].addPoint(1100, 300);
		resourcePlacement[1].addPoint(1100, 800);
                resourcePlacement[1].addPoint(1250, 850);
                resourcePlacement[1].addPoint(1500, 850);
                resourcePlacement[1].addPoint(1600, 900);
                resourcePlacement[1].addPoint(1650, 900);
                resourcePlacement[2] = new Polygon();
		resourcePlacement[2].addPoint(375, 1250);
		resourcePlacement[2].addPoint(875, 1750);
		resourcePlacement[2].addPoint(900, 1700);
		resourcePlacement[2].addPoint(900, 1200);
                resourcePlacement[2].addPoint(750, 1150);
                resourcePlacement[2].addPoint(500, 1150);
                resourcePlacement[2].addPoint(400, 1100);
                resourcePlacement[2].addPoint(350, 1100);
                resourcePlacement[3] = new Polygon();
		resourcePlacement[3].addPoint(1625, 1250);
		resourcePlacement[3].addPoint(1125, 1750);
		resourcePlacement[3].addPoint(1100, 1700);
		resourcePlacement[3].addPoint(1100, 1200);
                resourcePlacement[3].addPoint(1250, 1150);
                resourcePlacement[3].addPoint(1500, 1150);
                resourcePlacement[3].addPoint(1600, 1100);
                resourcePlacement[3].addPoint(1650, 1100);
                
		return resourcePlacement;
	}
}
/*
 *
 * @author Grimmy
 */
