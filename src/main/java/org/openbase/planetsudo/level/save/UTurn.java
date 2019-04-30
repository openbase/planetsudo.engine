/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.level.save;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2019 openbase.org
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
public class UTurn extends AbstractLevel {

	public UTurn() {
	}

	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(0, 0);
		levelBorders.addPoint(0, 800);
		levelBorders.addPoint(200, 1000);
		levelBorders.addPoint(800, 1000);
		levelBorders.addPoint(1000, 800);
		levelBorders.addPoint(1000, 0);
		levelBorders.addPoint(600, 0);
		levelBorders.addPoint(600, 500);
		levelBorders.addPoint(400, 500);
		levelBorders.addPoint(400, 0);
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(200, 200, Direction2D.DOWN);
		homePosition[1] = new Base2D(800, 200, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[5];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 0, 1000, 1000, 5, ResourceType.Normal);
		resourcePlacement[1] = new ResourceRandomRectangle(0, 0, 1000, 1000, 5, ResourceType.DoublePoints);
		resourcePlacement[2] = new ResourceRandomRectangle(0, 0, 1000, 1000, 10, ResourceType.ExtremPoint);
		resourcePlacement[3] = new ResourceRandomRectangle(0, 0, 1000, 1000, 10, ResourceType.ExtraAgentFuel);
		resourcePlacement[4] = new ResourceRandomRectangle(0, 0, 1000, 1000, 10, ResourceType.ExtraMothershipFuel);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(67,167,197);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		return null;
	}
}
