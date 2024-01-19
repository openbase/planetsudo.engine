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
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class Kreuzung extends AbstractLevel {

	public Kreuzung() {
	}

	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(0, 0);
		levelBorders.addPoint(1000, 0);
                levelBorders.addPoint(1000, 1000);
                levelBorders.addPoint(0, 1000);

		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(500, 100, Direction2D.UP);
		homePosition[1] = new Base2D(500, 900, Direction2D.UP);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[6];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 0, 1000, 1000, 10, ResourceType.Normal);
		resourcePlacement[1] = new ResourceRandomRectangle(375, 350, 300, 10, 8, ResourceType.DoublePoints);
		resourcePlacement[2] = new ResourceRandomRectangle(375, 650, 300, 10, 8, ResourceType.DoublePoints);
		resourcePlacement[3] = new ResourceRandomRectangle(450, 450, 100, 100, 8, ResourceType.ExtremPoint);
		resourcePlacement[4] = new ResourceRandomRectangle(400, 400, 200, 200, 0, ResourceType.ExtraAgentFuel);
		resourcePlacement[5] = new ResourceRandomRectangle(400, 400, 200, 200, 0, ResourceType.ExtraMothershipFuel);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(67,167,197);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
            Polygon[] walls = new Polygon[4];
            walls[0] = new Polygon();
		walls[0].addPoint(0, 350);
		walls[0].addPoint(350, 350);           		
                walls[0].addPoint(350, 0);
                walls[0].addPoint(0, 0);
            walls[1] = new Polygon();
		walls[1].addPoint(350, 1000);
		walls[1].addPoint(350, 650);           		
                walls[1].addPoint(0, 650);
                walls[1].addPoint(0, 1000);
           walls[2] = new Polygon();
		walls[2].addPoint(650, 0);
		walls[2].addPoint(1000, 0);           		
                walls[2].addPoint(1000, 350);
                walls[2].addPoint(650, 350);
           walls[3] = new Polygon();
		walls[3].addPoint(650, 1000);
		walls[3].addPoint(650, 650);           		
                walls[3].addPoint(1000, 650);
                walls[3].addPoint(1000, 1000);
		return walls;
	}
}
