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
public class OffensiveBackdoor extends AbstractLevel {

	public OffensiveBackdoor() {
	}

	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(350, 600 );
		levelBorders.addPoint(650, 600);
		levelBorders.addPoint(650, 850);
		levelBorders.addPoint(750, 850);
		levelBorders.addPoint(750, 350);
		levelBorders.addPoint(900, 350);
		levelBorders.addPoint(900, 600);
		levelBorders.addPoint(1150, 600);
		levelBorders.addPoint(1150, 300);
		levelBorders.addPoint(1450, 300);
		levelBorders.addPoint(1450, 600);
		levelBorders.addPoint(1700, 600);
		levelBorders.addPoint(1700, 350);
		levelBorders.addPoint(1850, 350);
		levelBorders.addPoint(1850, 850);
		levelBorders.addPoint(1950, 850);
		levelBorders.addPoint(1950, 600);
		levelBorders.addPoint(2250, 600);
		levelBorders.addPoint(2250, 650);
		levelBorders.addPoint(2550, 650);
		levelBorders.addPoint(2550, 1800);
		levelBorders.addPoint(50, 1800);
		levelBorders.addPoint(50, 650);
		levelBorders.addPoint(350, 650);
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(500, 700, Direction2D.DOWN);
		homePosition[1] = new Base2D(2100, 700, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[15];
		resourcePlacement[0] = new ResourceRandomRectangle(1700, 1200, 150, 150, 5, ResourceType.Normal);
		resourcePlacement[1] = new ResourceRandomRectangle(750, 1200, 150, 150, 5, ResourceType.Normal);
		resourcePlacement[2] = new ResourceRandomRectangle(750, 450, 150, 150, 5, ResourceType.Normal);
		resourcePlacement[3] = new ResourceRandomRectangle(1700, 450, 150, 150, 5, ResourceType.Normal);
		resourcePlacement[4] = new ResourceRandomRectangle(750, 350, 150, 100, 3, ResourceType.DoublePoints);
		resourcePlacement[5] = new ResourceRandomRectangle(1700, 350, 150, 100, 3, ResourceType.DoublePoints);
		resourcePlacement[6] = new ResourceRandomRectangle(750, 1350, 150, 100, 3, ResourceType.DoublePoints);
		resourcePlacement[7] = new ResourceRandomRectangle(1700, 1350, 150, 100, 3, ResourceType.DoublePoints);
		resourcePlacement[8] = new ResourceRandomRectangle(1150, 300, 300, 150, 8, ResourceType.ExtremPoint);
		resourcePlacement[9] = new ResourceRandomRectangle(1150, 1350, 300, 150, 8, ResourceType.ExtremPoint);
		resourcePlacement[10] = new ResourceRandomRectangle(1150, 800, 300, 200, 3, ResourceType.ExtraAgentFuel);
		resourcePlacement[11] = new ResourceRandomRectangle(1150, 800, 300, 200, 2, ResourceType.ExtraMothershipFuel);
		resourcePlacement[12] = new ResourceRandomRectangle(900, 1650, 800, 100, 7, ResourceType.ExtraAgentFuel);
		resourcePlacement[13] = new ResourceRandomRectangle(50, 1650, 200, 150, 3, ResourceType.ExtremPoint);
		resourcePlacement[14] = new ResourceRandomRectangle(2350, 1650, 200, 150, 3, ResourceType.ExtremPoint);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(255, 153, 0);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
        Polygon[] polygon = new Polygon[1];
        polygon[0] = new Polygon();
        polygon[0].addPoint(350, 800);
        polygon[0].addPoint(350, 1200);
        polygon[0].addPoint(750, 1200);
        polygon[0].addPoint(750, 1450);
        polygon[0].addPoint(900, 1450);
        polygon[0].addPoint(900, 1200);
        polygon[0].addPoint(1150, 1200);
        polygon[0].addPoint(1150, 1500);
        polygon[0].addPoint(1450, 1500);
        polygon[0].addPoint(1450, 1200);
        polygon[0].addPoint(1700, 1200);
        polygon[0].addPoint(1700, 1450);
        polygon[0].addPoint(1850, 1450);
        polygon[0].addPoint(1850, 1200);
        polygon[0].addPoint(2250, 1200);
        polygon[0].addPoint(2250, 800);
        polygon[0].addPoint(2350, 800);
        polygon[0].addPoint(2350, 1650);
        polygon[0].addPoint(250, 1650);
        polygon[0].addPoint(250, 800);
        polygon[0].addPoint(350, 800);
		return polygon;
	}
}
