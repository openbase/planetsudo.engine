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
public class Engpass extends AbstractLevel {

	public Engpass() {
	}

	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(0, 0);
                levelBorders.addPoint(500, 400);
                levelBorders.addPoint(1000, 0);
                levelBorders.addPoint(1000, 1000);
                levelBorders.addPoint(500, 600);
                levelBorders.addPoint(0, 1000);
                     
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(80, 500, Direction2D.LEFT);
		homePosition[1] = new Base2D(920, 500, Direction2D.LEFT);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[4];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 0, 1000, 1000, 5, ResourceType.Normal);
		resourcePlacement[1] = new ResourceRandomRectangle(0, 0, 450, 450, 5, ResourceType.DoublePoints);
		resourcePlacement[2] = new ResourceRandomRectangle(550, 550, 1000, 1000, 5, ResourceType.DoublePoints);
		resourcePlacement[3] = new ResourceRandomRectangle(495, 300, 5, 400, 20, ResourceType.ExtremPoint);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(67,167,197);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
            Polygon[] walls = new Polygon[0];
		return walls;
	}
}
