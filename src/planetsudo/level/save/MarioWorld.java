/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.level.save;

import data.Point2D;
import java.awt.Color;
import java.awt.Polygon;
import planetsudo.level.AbstractLevel;
import planetsudo.level.ResourcePlacement;
import planetsudo.level.ResourceRandomRectangle;
import planetsudo.level.levelobjects.Resource.ResourceType;

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
	protected Point2D[] loadHomePositions() {
		Point2D[] homePosition = new Point2D[2];
		homePosition[0] = new Point2D(100, 950);
		homePosition[1] = new Point2D(900, 950);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[1];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 0, 100, 100, 20, ResourceType.Normal);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return Color.RED;
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		return null;
	}
}
