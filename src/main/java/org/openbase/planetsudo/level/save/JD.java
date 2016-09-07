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
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a
 */
public class JD extends AbstractLevel {

	public JD() {
	}

	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(0, 550);
		levelBorders.addPoint(550, 550);
		levelBorders.addPoint(550, 0);
		levelBorders.addPoint(1250, 0);
		levelBorders.addPoint(1250, 550);
		levelBorders.addPoint(1800, 550);
		levelBorders.addPoint(1800, 1250);
		levelBorders.addPoint(1250, 1250);
		levelBorders.addPoint(1250, 1800);
		levelBorders.addPoint(550, 1800);
		levelBorders.addPoint(550, 1250);
		levelBorders.addPoint(0, 1250);
		levelBorders.addPoint(0, 550);
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(750, 750, Direction2D.DOWN);
		homePosition[1] = new Base2D(1050, 1050, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[5];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 0, 1800, 1800, 10, ResourceType.Normal);
		resourcePlacement[1] = new ResourceRandomRectangle(0, 0, 1800, 1800, 20, ResourceType.DoublePoints);
		resourcePlacement[2] = new ResourceRandomRectangle(0, 0, 1800, 1800, 10, ResourceType.ExtremPoint);
		resourcePlacement[3] = new ResourceRandomRectangle(0, 0, 1800, 1800, 10, ResourceType.ExtraAgentFuel);
		resourcePlacement[4] = new ResourceRandomRectangle(0, 0, 1800, 1800, 10, ResourceType.ExtraMothershipFuel);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(150, 150, 150);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		Polygon[] resourcePlacement = new Polygon[1];
		resourcePlacement[0] = new Polygon();
		resourcePlacement[0].addPoint(200, 850);
		resourcePlacement[0].addPoint(300, 950);
		resourcePlacement[0].addPoint(500, 750);
		resourcePlacement[0].addPoint(600, 850);
		resourcePlacement[0].addPoint(850, 850);
		resourcePlacement[0].addPoint(850, 600);
		resourcePlacement[0].addPoint(950, 500);
		resourcePlacement[0].addPoint(750, 300);
		resourcePlacement[0].addPoint(850, 200);
		resourcePlacement[0].addPoint(950, 200);
		resourcePlacement[0].addPoint(850, 300);
		resourcePlacement[0].addPoint(1050, 500);
		resourcePlacement[0].addPoint(950, 600);
		resourcePlacement[0].addPoint(950, 850);
		resourcePlacement[0].addPoint(1200, 850);
		resourcePlacement[0].addPoint(1300, 950);
		resourcePlacement[0].addPoint(1400, 850);
		resourcePlacement[0].addPoint(1500, 750);
		resourcePlacement[0].addPoint(1600, 850);
		resourcePlacement[0].addPoint(1600, 950);
		resourcePlacement[0].addPoint(1500, 850);
		resourcePlacement[0].addPoint(1300, 1050);
		resourcePlacement[0].addPoint(1200, 950);
		resourcePlacement[0].addPoint(950, 950);
		resourcePlacement[0].addPoint(950, 1200);
		resourcePlacement[0].addPoint(850, 1300);
		resourcePlacement[0].addPoint(1050, 1500);
		resourcePlacement[0].addPoint(950, 1600);
		resourcePlacement[0].addPoint(850, 1600);
		resourcePlacement[0].addPoint(950, 1500);
		resourcePlacement[0].addPoint(750, 1300);
		resourcePlacement[0].addPoint(850, 1200);
		resourcePlacement[0].addPoint(850, 950);
		resourcePlacement[0].addPoint(600, 950);
		resourcePlacement[0].addPoint(500, 850);
		resourcePlacement[0].addPoint(300, 1050);
		resourcePlacement[0].addPoint(200, 950);
		resourcePlacement[0].addPoint(200, 850);
		return resourcePlacement;
	}
}
