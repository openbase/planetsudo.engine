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
 * @author divine
 */
public class SimpleWorld extends AbstractLevel {

	public SimpleWorld() {
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
	protected Point2D[] loadHomePositions() {
		Point2D[] homePosition = new Point2D[2];
		homePosition[0] = new Point2D(200, 500);
		homePosition[1] = new Point2D(800, 500);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[5];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 0, 1000, 1000, 50, ResourceType.Normal);
		resourcePlacement[1] = new ResourceRandomRectangle(0, 0, 1000, 1000, 20, ResourceType.DoublePoints);
		resourcePlacement[2] = new ResourceRandomRectangle(400, 800, 200, 200, 10, ResourceType.ExtremPoint);
		resourcePlacement[3] = new ResourceRandomRectangle(0, 0, 1000, 1000, 10, ResourceType.ExtraAgentFuel);
		resourcePlacement[4] = new ResourceRandomRectangle(400, 0, 200, 200, 10, ResourceType.ExtraMothershipFuel);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(255, 153, 0);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		Polygon[] walls = new Polygon[1];
		walls[0] = new Polygon();
		walls[0].addPoint(400, 400);
		walls[0].addPoint(600, 400);
		walls[0].addPoint(600, 600);
		walls[0].addPoint(400, 600);
		return walls;
	}
}
