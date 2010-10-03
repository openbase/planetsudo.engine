/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.level.save;

import data.Point2D;
import java.awt.Polygon;
import planetsudo.level.AbstractLevel;
import planetsudo.level.ResourceGausOval;
import planetsudo.level.ResourcePlacement;
import planetsudo.level.ResourceRandomRectangle;

/**
 *
 * @author divine
 */
public class SimpleWorld extends AbstractLevel {

	public SimpleWorld() {
	}

	@Override
	public Polygon getLevelBorderPolygon() {
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
	public Point2D[] getHomePositions() {
		Point2D[] homePosition = new Point2D[2];
		homePosition[0] = new Point2D(200, 200);
		homePosition[1] = new Point2D(800, 200);
		return homePosition;
	}

	@Override
	public ResourcePlacement[] getResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[1];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 0, 1000, 1000, 50);
		return resourcePlacement;
	}
}
