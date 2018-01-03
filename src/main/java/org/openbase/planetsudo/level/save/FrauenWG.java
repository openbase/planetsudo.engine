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
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class FrauenWG extends AbstractLevel {

	public FrauenWG() {
	}

	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(100, 0);
		levelBorders.addPoint(450, 0);
		levelBorders.addPoint(450, 200);
		levelBorders.addPoint(500, 200);
		levelBorders.addPoint(500, 250);
		levelBorders.addPoint(850, 250);
		levelBorders.addPoint(1150, 550);
		levelBorders.addPoint(1450, 550);
		levelBorders.addPoint(1450, 1100);
		levelBorders.addPoint(1500, 1150);
		levelBorders.addPoint(1500, 850);
		levelBorders.addPoint(1850, 850);
		levelBorders.addPoint(1900, 900);
		levelBorders.addPoint(1900, 300);
		levelBorders.addPoint(1750, 150);
		levelBorders.addPoint(1900, 0);
		levelBorders.addPoint(2050, 150);
		levelBorders.addPoint(2050, 300);
		levelBorders.addPoint(2450, 300);
		levelBorders.addPoint(2450, 1100);
		levelBorders.addPoint(1900, 1100);
		levelBorders.addPoint(1850, 1150);
		levelBorders.addPoint(1950, 1250);
		levelBorders.addPoint(2150, 1250);
		levelBorders.addPoint(2200, 1300);
		levelBorders.addPoint(2350, 1300);
		levelBorders.addPoint(2350, 1650);
		levelBorders.addPoint(2000, 1650);
		levelBorders.addPoint(2000, 1450);
		levelBorders.addPoint(1950, 1450);
		levelBorders.addPoint(1950, 1400);
		levelBorders.addPoint(1500, 1400);
		levelBorders.addPoint(1200, 1100);
		levelBorders.addPoint(900, 1100);
		levelBorders.addPoint(900, 550);
		levelBorders.addPoint(850, 500);
		levelBorders.addPoint(850, 800);
		levelBorders.addPoint(600, 800);
		levelBorders.addPoint(550, 750);
		levelBorders.addPoint(550, 1350);
		levelBorders.addPoint(700, 1500);
		levelBorders.addPoint(550, 1650);
		levelBorders.addPoint(400, 1500);
		levelBorders.addPoint(400, 1350);
		levelBorders.addPoint(0, 1350);
		levelBorders.addPoint(0, 550);
		levelBorders.addPoint(550, 550);
		levelBorders.addPoint(600, 500);
		levelBorders.addPoint(500, 400);
		levelBorders.addPoint(300, 400);
		levelBorders.addPoint(300, 350);
		levelBorders.addPoint(100, 350);
		levelBorders.addPoint(100, 0);
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(750, 700, Direction2D.DOWN);
		homePosition[1] = new Base2D(1600, 950, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[8];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 550, 550, 800, 30, ResourceType.Normal);
		resourcePlacement[1] = new ResourceRandomRectangle(1900, 300, 500, 800, 30, ResourceType.Normal);
		resourcePlacement[2] = new ResourceRandomRectangle(900, 550, 550, 550, 15, ResourceType.ExtraMothershipFuel);
		resourcePlacement[3] = new ResourceRandomRectangle(100, 0, 350, 350, 50, ResourceType.DoublePoints);
		resourcePlacement[4] = new ResourceRandomRectangle(2000, 1300, 350, 450, 50, ResourceType.DoublePoints);
		resourcePlacement[5] = new ResourceRandomRectangle(400, 1350, 300, 300, 50, ResourceType.ExtremPoint);
		resourcePlacement[6] = new ResourceRandomRectangle(1750, 0, 300, 300, 50, ResourceType.ExtremPoint);
                resourcePlacement[7] = new ResourceRandomRectangle(900, 550, 550, 550, 30, ResourceType.ExtraAgentFuel);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(0, 0, 0);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		return null;
	}
}
