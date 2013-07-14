/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.level.save;

import data.Base2D;
import data.Direction2D;
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
public class QuadratischPraktischBoese extends AbstractLevel {

	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(0, 0);
		levelBorders.addPoint(500, 0);
		levelBorders.addPoint(500, 300);
		levelBorders.addPoint(800, 0);
		levelBorders.addPoint(1100, 300);
		levelBorders.addPoint(1100, 0);
		levelBorders.addPoint(1600, 0);
		levelBorders.addPoint(1600, 500);
		levelBorders.addPoint(1300, 500);
		levelBorders.addPoint(1600, 800);
		levelBorders.addPoint(1300, 1100);
		levelBorders.addPoint(1600, 1100);
		levelBorders.addPoint(1600, 1600);
		levelBorders.addPoint(1100, 1600);
		levelBorders.addPoint(1100, 1300);
		levelBorders.addPoint(800, 1600);
		levelBorders.addPoint(500, 1300);
		levelBorders.addPoint(500, 1600);
		levelBorders.addPoint(0, 1600);
		levelBorders.addPoint(0, 1100);
		levelBorders.addPoint(300, 1100);
		levelBorders.addPoint(0, 800);
		levelBorders.addPoint(300, 500);
		levelBorders.addPoint(0, 500);

		return levelBorders;
	}



	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(200, 200, Direction2D.DOWN);
		homePosition[1] = new Base2D(1400, 1400, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[7];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 500, 1600, 600, 50, ResourceType.Normal);
		resourcePlacement[1] = new ResourceRandomRectangle(500, 0, 600, 1650, 25, ResourceType.DoublePoints);
		resourcePlacement[2] = new ResourceRandomRectangle(1100, 0, 500, 500, 10, ResourceType.ExtremPoint);
		resourcePlacement[3] = new ResourceRandomRectangle(0, 1100, 500, 500, 10, ResourceType.ExtremPoint);
		resourcePlacement[4] = new ResourceRandomRectangle(500, 500, 600, 600, 4, ResourceType.ExtraAgentFuel);
		resourcePlacement[5] = new ResourceRandomRectangle(500, 500, 600, 600, 4, ResourceType.ExtraMothershipFuel);
		resourcePlacement[6] = new ResourceRandomRectangle(500, 500, 600, 600, 4, ResourceType.Bomb);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(93,0,0);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		Polygon[] resourcePlacement = new Polygon[1];
		resourcePlacement[0] = new Polygon();
		resourcePlacement[0].addPoint(500, 1000);
		resourcePlacement[0].addPoint(1000, 500);
		resourcePlacement[0].addPoint(1100, 600);
		resourcePlacement[0].addPoint(600, 1100);
		return resourcePlacement;
	}
}

