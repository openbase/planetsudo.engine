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
	protected Point2D[] loadHomePositions() {
		Point2D[] homePosition = new Point2D[2];
		homePosition[0] = new Point2D(200, 300);
		homePosition[1] = new Point2D(200, 1700);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[5];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 500, 500, 1500, 30, ResourceType.Normal);
		resourcePlacement[1] = new ResourceRandomRectangle(0, 500, 1000, 1500, 20, ResourceType.DoublePoints);
		resourcePlacement[2] = new ResourceRandomRectangle(1500, 0, 2000, 2000, 6, ResourceType.ExtremPoint);
		resourcePlacement[3] = new ResourceRandomRectangle(500, 0, 2000, 2000, 10, ResourceType.ExtraAgentFuel);
		resourcePlacement[4] = new ResourceRandomRectangle(500, 0, 2000, 2000, 10, ResourceType.ExtraMothershipFuel);
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
