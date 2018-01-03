/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.level.save;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2018 openbase.org
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
 * @author lena
 */
public class WakaWaka extends AbstractLevel{
@Override
	public Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(50, 50);
		levelBorders.addPoint(350, 50);
		levelBorders.addPoint(350, 250); //
		levelBorders.addPoint(450, 350);
		levelBorders.addPoint(700, 350);
		levelBorders.addPoint(700, 200);
		levelBorders.addPoint(600, 300);
		levelBorders.addPoint(550, 300);
		levelBorders.addPoint(450, 200); //
		levelBorders.addPoint(450, 50);
		levelBorders.addPoint(800, 50);
		levelBorders.addPoint(800, 100);
		levelBorders.addPoint(850, 100);
		levelBorders.addPoint(850, 150);
		levelBorders.addPoint(900, 150);
		levelBorders.addPoint(900, 100);
		levelBorders.addPoint(950, 100);
		levelBorders.addPoint(950, 50);
//		levelBorders.addPoint(1250, 50);
//		levelBorders.addPoint(1250, 250);
//		levelBorders.addPoint(1150, 300);
//		levelBorders.addPoint(1050, 250);
//		levelBorders.addPoint(950, 250);
//		levelBorders.addPoint(900, 350);
//		levelBorders.addPoint(900, 450);
//		levelBorders.addPoint(1000, 450);
//		levelBorders.addPoint(1000, 450);
//		levelBorders.addPoint(1000, 450);
//		levelBorders.addPoint(950, 400);
//		levelBorders.addPoint(950, 350);
//		levelBorders.addPoint(1000, 300);
//		levelBorders.addPoint(1050, 300);
//		levelBorders.addPoint(1150, 350);
//		levelBorders.addPoint(1150, 400);
//		levelBorders.addPoint(1250, 500);
//		levelBorders.addPoint(1250, 550);
//		levelBorders.addPoint(1200, 600);
//		levelBorders.addPoint(1150, 600);
//		levelBorders.addPoint(1150, 550);
//		levelBorders.addPoint(1100, 550);
//		levelBorders.addPoint(1100, 650);
//		levelBorders.addPoint(1200, 650);
//		levelBorders.addPoint(1300, 600);
//		levelBorders.addPoint(1300, 500);
//		levelBorders.addPoint(1250, 400);
//		levelBorders.addPoint(1350, 300);
//		levelBorders.addPoint(1500, 300);
		levelBorders.addPoint(1500, 50);
		levelBorders.addPoint(1500, 600);
		levelBorders.addPoint(1450, 600);
		levelBorders.addPoint(1450, 650);
		levelBorders.addPoint(1400, 650);
		levelBorders.addPoint(1400, 700);
		levelBorders.addPoint(1450, 700);
		levelBorders.addPoint(1450, 750);
		levelBorders.addPoint(1500, 750);
		levelBorders.addPoint(1500, 1100);
		levelBorders.addPoint(1350, 1100); //
		levelBorders.addPoint(1250, 1000);
		levelBorders.addPoint(1250, 950);
		levelBorders.addPoint(1350, 850);
		levelBorders.addPoint(1200, 850);
		levelBorders.addPoint(1200, 1100);
		levelBorders.addPoint(1300, 1200); //
		levelBorders.addPoint(1500, 1200);
		levelBorders.addPoint(1500, 1500);
		levelBorders.addPoint(1200, 1500);
	//	levelBorders.addPoint(1200, 1300);
	//	levelBorders.addPoint(1100, 1200);
	//	levelBorders.addPoint(800, 1200);
	//	levelBorders.addPoint(800, 1300);
	//	levelBorders.addPoint(950, 1250);
	//	levelBorders.addPoint(1000, 1250);
	//	levelBorders.addPoint(1100, 1350);
		levelBorders.addPoint(1100, 1500);
		levelBorders.addPoint(750, 1500);
		levelBorders.addPoint(650, 1400);
		levelBorders.addPoint(550, 1500);
		levelBorders.addPoint(150, 1500);
		levelBorders.addPoint(250, 1400);
		levelBorders.addPoint(300, 1450);
		levelBorders.addPoint(400, 1350);
		levelBorders.addPoint(350, 1300);
		levelBorders.addPoint(450, 1200);
		levelBorders.addPoint(550, 1300);
		levelBorders.addPoint(550, 1050);
		levelBorders.addPoint(600, 1000);
		levelBorders.addPoint(850, 1000);
		levelBorders.addPoint(850, 850);
		levelBorders.addPoint(800, 850);
		levelBorders.addPoint(750, 950);
		levelBorders.addPoint(600, 800);
		levelBorders.addPoint(700, 750);
		levelBorders.addPoint(700, 700);
		levelBorders.addPoint(550, 700);
		levelBorders.addPoint(550, 950);
		levelBorders.addPoint(500, 1000);
		levelBorders.addPoint(250, 1000);
		levelBorders.addPoint(350, 1100);
		levelBorders.addPoint(250, 1200);
		levelBorders.addPoint(200, 1150);
		levelBorders.addPoint(100, 1250);
		levelBorders.addPoint(150, 1300);
		levelBorders.addPoint(50, 1400);
		levelBorders.addPoint(50, 1000);
		levelBorders.addPoint(150, 900);
		levelBorders.addPoint(50, 800);
		levelBorders.addPoint(50, 450);
	//	levelBorders.addPoint(200, 450);
	//	levelBorders.addPoint(300, 550);
	//	levelBorders.addPoint(300, 600);
	//	levelBorders.addPoint(200, 700);
	//	levelBorders.addPoint(350, 700);
	//	levelBorders.addPoint(350, 450);
	//	levelBorders.addPoint(250, 350);
		levelBorders.addPoint(50, 350);
		levelBorders.addPoint(50, 50);
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(100, 100, Direction2D.DOWN);
		homePosition[1] = new Base2D(1450, 1450, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[5];
		resourcePlacement[0] = new ResourceRandomRectangle(50, 50, 1500, 1500, 50, ResourceType.Normal);
		resourcePlacement[1] = new ResourceRandomRectangle(450, 50, 1500, 1100, 25, ResourceType.DoublePoints);
		resourcePlacement[2] = new ResourceRandomRectangle(1000, 50, 1500, 550, 10, ResourceType.ExtremPoint);
		resourcePlacement[3] = new ResourceRandomRectangle(50, 50, 1500, 1100, 20, ResourceType.ExtraAgentFuel);
		resourcePlacement[4] = new ResourceRandomRectangle(50, 50, 1500, 1100, 10, ResourceType.ExtraMothershipFuel);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(10,125,0);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		return null;
	}
}
