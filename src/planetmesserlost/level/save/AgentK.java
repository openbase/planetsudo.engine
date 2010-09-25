/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.level.save;


import data.Point2D;
import java.awt.Polygon;
import planetmesserlost.level.AbstractLevel;
import planetmesserlost.level.ResourceGausOval;
import planetmesserlost.level.ResourcePlacement;
import planetmesserlost.level.ResourceRandomRectangle;

/**
 *
 * @author noxus
 */
public class AgentK extends AbstractLevel {
	
		@Override
	public Polygon getLevelBorderPolygon() {
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
	public Point2D[] getHomePositions() {
		Point2D[] homePosition = new Point2D[2];
		homePosition[0] = new Point2D(200, 300);
		homePosition[1] = new Point2D(200, 1200);
		return homePosition;
	}

	@Override
	public ResourcePlacement[] getResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[1];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 0, 100, 100, 3);
		return resourcePlacement;
	}
}
/*
 *
 * @author noxus
 */
