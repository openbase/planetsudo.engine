/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.level.save;

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
 * @author noxus
 */
public class LuckyLoop extends AbstractLevel {
	
	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(300, 0);
		levelBorders.addPoint(450, 150);
		levelBorders.addPoint(550, 150);
		levelBorders.addPoint(650, 50);
		levelBorders.addPoint(1000, 50);
		levelBorders.addPoint(1050, 100);
		levelBorders.addPoint(1200, 100);
		levelBorders.addPoint(1300, 0);
		levelBorders.addPoint(1500, 00);
		levelBorders.addPoint(1550, 50);
		levelBorders.addPoint(1750, 50);
		levelBorders.addPoint(1800, 100);
		levelBorders.addPoint(1800, 350);
		levelBorders.addPoint(1700, 450);
		levelBorders.addPoint(1850, 600);
		levelBorders.addPoint(1700, 1000);
		levelBorders.addPoint(1850, 1150);
		levelBorders.addPoint(1850, 1450);
		levelBorders.addPoint(1550, 1450);
		levelBorders.addPoint(1400, 1300);
		levelBorders.addPoint(1300, 1300);
		levelBorders.addPoint(1200, 1400);
		levelBorders.addPoint(850, 1400);
		levelBorders.addPoint(800, 1350);
		levelBorders.addPoint(650, 1350);
		levelBorders.addPoint(550, 1450);
		levelBorders.addPoint(350, 1450);
		levelBorders.addPoint(300, 1400);
		levelBorders.addPoint(100, 1400);
		levelBorders.addPoint(50, 1350);
		levelBorders.addPoint(50, 1100);
		levelBorders.addPoint(150, 1000);
		levelBorders.addPoint(0, 850);
		levelBorders.addPoint(150, 450);
		levelBorders.addPoint(0, 300);
		levelBorders.addPoint(0, 0);

		
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(150, 150, Direction2D.DOWN);
		homePosition[1] = new Base2D(1650, 1300, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[12];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 0, 925, 1450, 10, ResourceType.Normal);
		resourcePlacement[1] = new ResourceRandomRectangle(925, 0, 925, 1450, 10, ResourceType.Normal);
		resourcePlacement[2] = new ResourceRandomRectangle(50, 1100, 300, 300, 10, ResourceType.DoublePoints);
		resourcePlacement[3] = new ResourceRandomRectangle(1500, 50, 300, 300, 10, ResourceType.DoublePoints);
		resourcePlacement[4] = new ResourceRandomRectangle(950, 350, 300, 300, 40, ResourceType.ExtremPoint);
		resourcePlacement[5] = new ResourceRandomRectangle(550, 800, 300, 300, 40, ResourceType.ExtremPoint);
		resourcePlacement[6] = new ResourceRandomRectangle(0, 0, 925, 725, 30, ResourceType.ExtraAgentFuel);
		resourcePlacement[7] = new ResourceRandomRectangle(0, 725, 925, 725, 30, ResourceType.ExtraAgentFuel);
		resourcePlacement[8] = new ResourceRandomRectangle(925, 0, 925, 725, 30, ResourceType.ExtraAgentFuel);
		resourcePlacement[9] = new ResourceRandomRectangle(925, 725, 925, 725, 30, ResourceType.ExtraAgentFuel);
		resourcePlacement[10] = new ResourceRandomRectangle(950, 350, 300, 300, 20, ResourceType.ExtraMothershipFuel);
		resourcePlacement[11] = new ResourceRandomRectangle(550, 800, 300, 300, 20, ResourceType.ExtraMothershipFuel);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(0,102,102);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		Polygon levelWalls[] = new Polygon[1];
		levelWalls[0] = new Polygon();
		levelWalls[0].addPoint(750, 300);
		levelWalls[0].addPoint(800, 250);
		levelWalls[0].addPoint(850, 250);
		levelWalls[0].addPoint(950, 300);
		levelWalls[0].addPoint(1000, 300);
		levelWalls[0].addPoint(1050, 350);
		levelWalls[0].addPoint(950, 450);
		levelWalls[0].addPoint(950, 550);
		levelWalls[0].addPoint(1050, 650);
		levelWalls[0].addPoint(1200, 650);
		levelWalls[0].addPoint(1300, 550);
		levelWalls[0].addPoint(1300, 450);
		levelWalls[0].addPoint(1200, 350);
		levelWalls[0].addPoint(1300, 250);
		levelWalls[0].addPoint(1500, 250);
		levelWalls[0].addPoint(1500, 300);
		levelWalls[0].addPoint(1400, 400);
		levelWalls[0].addPoint(1550, 550);
		levelWalls[0].addPoint(1450, 650);
		levelWalls[0].addPoint(1400, 650);
		levelWalls[0].addPoint(1300, 750);
		levelWalls[0].addPoint(1200, 750);
		levelWalls[0].addPoint(1150, 800);
		levelWalls[0].addPoint(1150, 900);
		levelWalls[0].addPoint(1100, 950);
		levelWalls[0].addPoint(1100, 1150);
		levelWalls[0].addPoint(1050, 1200);
		levelWalls[0].addPoint(1000, 1200);
		levelWalls[0].addPoint(900, 1150);
		levelWalls[0].addPoint(850, 1150);
		levelWalls[0].addPoint(800, 1100);
		levelWalls[0].addPoint(900, 1000);
		levelWalls[0].addPoint(900, 900);
		levelWalls[0].addPoint(800, 800);
		levelWalls[0].addPoint(650, 800);
		levelWalls[0].addPoint(550, 900);
		levelWalls[0].addPoint(550, 1000);
		levelWalls[0].addPoint(650, 1100);
		levelWalls[0].addPoint(550, 1200);
		levelWalls[0].addPoint(350, 1200);
		levelWalls[0].addPoint(350, 1150);
		levelWalls[0].addPoint(450, 1050);
		levelWalls[0].addPoint(300, 900);
		levelWalls[0].addPoint(400, 800);
		levelWalls[0].addPoint(450, 800);
		levelWalls[0].addPoint(550, 700);
		levelWalls[0].addPoint(650, 700);
		levelWalls[0].addPoint(700, 650);
		levelWalls[0].addPoint(700, 550);
		levelWalls[0].addPoint(750, 500);
		return levelWalls;
	}
}
/*
 *
 * @author noxus
 */
