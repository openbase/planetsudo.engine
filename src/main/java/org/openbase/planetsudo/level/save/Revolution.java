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
public class Revolution extends AbstractLevel {

	public Revolution() {
	}

	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(0, 0);
		levelBorders.addPoint(700, 0);
		levelBorders.addPoint(700, 300);
		levelBorders.addPoint(350,300);
		levelBorders.addPoint(350, 400);
		levelBorders.addPoint(800, 400);
		levelBorders.addPoint(800, 0);
		levelBorders.addPoint(1000, 0);
		levelBorders.addPoint(1000, 600);
		levelBorders.addPoint(900, 650);
		levelBorders.addPoint(1000, 700);
		levelBorders.addPoint(1000, 1000);
		levelBorders.addPoint(300, 1000);
		levelBorders.addPoint(300, 700);
		levelBorders.addPoint(650, 700);
		levelBorders.addPoint(650, 600);
		levelBorders.addPoint(200, 600);
		levelBorders.addPoint(200, 1000);
		levelBorders.addPoint(0, 1000);
		levelBorders.addPoint(0, 400);
		levelBorders.addPoint(100, 350);
		levelBorders.addPoint(0, 300);
		levelBorders.addPoint(0, 0);
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(450, 100, Direction2D.DOWN);
		homePosition[1] = new Base2D(550, 900, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[5];
		resourcePlacement[0] = new ResourceRandomRectangle(800, 0, 200, 200, 20, ResourceType.DoublePoints);
                resourcePlacement[1] = new ResourceRandomRectangle(0, 800, 200, 200, 20, ResourceType.DoublePoints);
		resourcePlacement[2] = new ResourceRandomRectangle(350, 450, 300, 100, 20, ResourceType.ExtremPoint);
		resourcePlacement[3] = new ResourceRandomRectangle(350, 450, 300, 100, 10, ResourceType.ExtraAgentFuel);
		resourcePlacement[4] = new ResourceRandomRectangle(350, 450, 300, 100, 10, ResourceType.ExtraMothershipFuel);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(255, 153, 0);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		return null;
	}
}
