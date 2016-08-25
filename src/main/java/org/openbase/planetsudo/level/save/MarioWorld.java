/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.level.save;

import org.openbase.util.data.Base2D;
import org.openbase.util.data.Direction2D;
import java.awt.Color;
import java.awt.Polygon;
import org.openbase.planetsudo.level.AbstractLevel;
import org.openbase.planetsudo.level.ResourcePlacement;
import org.openbase.planetsudo.level.ResourceRandomRectangle;
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType;

/**
 *
 * @author noxus
 */
public class MarioWorld extends AbstractLevel {

	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(0, 0);
		levelBorders.addPoint(500, 400);
		levelBorders.addPoint(1000, 0);
		levelBorders.addPoint(1000, 1000);
		levelBorders.addPoint(800, 1000);
		levelBorders.addPoint(800, 700);
		levelBorders.addPoint(500, 1000);
		levelBorders.addPoint(200, 700);
		levelBorders.addPoint(200, 1000);
		levelBorders.addPoint(0, 1000);
		return levelBorders;
	} 



	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(100, 950, Direction2D.DOWN);
		homePosition[1] = new Base2D(900, 950, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[4];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 0, 200, 500, 10, ResourceType.Normal);
		resourcePlacement[1] = new ResourceRandomRectangle(250, 300, 550, 550, 15, ResourceType.DoublePoints);
		resourcePlacement[2] = new ResourceRandomRectangle(200, 900, 400, 100, 15, ResourceType.ExtremPoint);
		resourcePlacement[3] = new ResourceRandomRectangle(800, 0, 200, 500, 10, ResourceType.Normal);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(174,94,94);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		return null;
	}
}
