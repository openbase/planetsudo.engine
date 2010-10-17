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
		homePosition[0] = new Base2D(200, 300, Direction2D.DOWN);
		homePosition[1] = new Base2D(200, 1200, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[1];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 0, 100, 100, 3, ResourceType.Normal);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return Color.PINK;
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
