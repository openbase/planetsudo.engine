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
 * @author noxus
 */
public class AgentK extends AbstractLevel {
	
	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(0, 0);
		levelBorders.addPoint(500, 0);
		levelBorders.addPoint(500, 500);
		levelBorders.addPoint(1100, 0);
		levelBorders.addPoint(1600, 0);
		levelBorders.addPoint(500, 1000);
		levelBorders.addPoint(1600, 2000);
		levelBorders.addPoint(1100, 2000);
		levelBorders.addPoint(500, 1500);
		levelBorders.addPoint(500, 2000);
		levelBorders.addPoint(0, 2000);
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(1200, 100, Direction2D.DOWN);
		homePosition[1] = new Base2D(1200, 1900, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[10];
		resourcePlacement[0] = new ResourceRandomRectangle(500, 0, 750, 1100, 10, ResourceType.ExtraAgentFuel);
		resourcePlacement[1] = new ResourceRandomRectangle(500, 750, 750, 1100, 10, ResourceType.ExtraAgentFuel);
		resourcePlacement[2] = new ResourceRandomRectangle(0, 0, 500, 50, 20, ResourceType.ExtremPoint);
		resourcePlacement[3] = new ResourceRandomRectangle(0, 1950, 500, 50, 20, ResourceType.ExtremPoint);
		resourcePlacement[4] = new ResourceRandomRectangle(0, 0, 500, 500, 15, ResourceType.ExtraMothershipFuel);
		resourcePlacement[5] = new ResourceRandomRectangle(0, 1500, 500, 500, 15, ResourceType.ExtraMothershipFuel);
		resourcePlacement[6] = new ResourceRandomRectangle(0, 500, 500, 500, 5, ResourceType.Normal);
                resourcePlacement[7] = new ResourceRandomRectangle(0, 1000, 500, 500, 5, ResourceType.Normal);
                resourcePlacement[8] = new ResourceRandomRectangle(0, 0, 500, 500, 5, ResourceType.DoublePoints);
                resourcePlacement[9] = new ResourceRandomRectangle(0, 1500, 500, 500, 5, ResourceType.DoublePoints);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(137,67,162);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		return null;
	}
}
/*
 *
 * @author noxus
 */
