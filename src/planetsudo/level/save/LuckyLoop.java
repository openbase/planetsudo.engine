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
public class LuckyLoop extends AbstractLevel {
	
	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(300, 0);
		levelBorders.addPoint(450, 150);
		levelBorders.addPoint(450, 300);
		levelBorders.addPoint(550, 150);
		levelBorders.addPoint(650, 50);
		levelBorders.addPoint(1000, 50);
		levelBorders.addPoint(1050, 100);
		levelBorders.addPoint(1200, 0);
		levelBorders.addPoint(1500, 0);
		levelBorders.addPoint(1550, 50);
		levelBorders.addPoint(1750, 50);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);
		levelBorders.addPoint(00, 00);

		
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(200, 300, Direction2D.DOWN);
		homePosition[1] = new Base2D(200, 1700, Direction2D.DOWN);
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
