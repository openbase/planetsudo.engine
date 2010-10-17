/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.level.save;

import data.Base2D;
import data.Direction2D;
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
		resourcePlacement[0] = new ResourceRandomRectangle(0, 0, 1000, 1000, 50, ResourceType.Normal);
		resourcePlacement[1] = new ResourceRandomRectangle(0, 0, 1000, 1000, 25, ResourceType.DoublePoints);
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
