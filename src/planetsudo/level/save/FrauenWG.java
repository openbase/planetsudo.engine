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
public class FrauenWG extends AbstractLevel {

	public FrauenWG() {
	}

	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(200, 100);
		levelBorders.addPoint(550, 100);
		levelBorders.addPoint(550, 300);
		levelBorders.addPoint(600, 300);
		levelBorders.addPoint(600, 350);
		levelBorders.addPoint(950, 350);
		levelBorders.addPoint(1250, 650);
		levelBorders.addPoint(1550, 650);
		levelBorders.addPoint(1550, 1200);
		levelBorders.addPoint(1600, 1250);
		levelBorders.addPoint(1600, 950);
		levelBorders.addPoint(1950, 950);
		levelBorders.addPoint(2000, 1000);
		levelBorders.addPoint(2000, 400);
		levelBorders.addPoint(1850, 250);
		levelBorders.addPoint(2000, 100);
		levelBorders.addPoint(2150, 250);
		levelBorders.addPoint(2150, 400);
		levelBorders.addPoint(2550, 400);
		levelBorders.addPoint(2550, 1200);
		levelBorders.addPoint(2000, 1200);
		levelBorders.addPoint(1950, 1250);
		levelBorders.addPoint(2050, 1350);
		levelBorders.addPoint(2250, 1350);
		levelBorders.addPoint(2300, 1400);
		levelBorders.addPoint(2450, 1400);
		levelBorders.addPoint(2450, 1750);
		levelBorders.addPoint(2100, 1750);
		levelBorders.addPoint(2100, 1550);
		levelBorders.addPoint(2050, 1550);
		levelBorders.addPoint(2050, 1500);
		levelBorders.addPoint(1600, 1500);
		levelBorders.addPoint(1300, 1200);
		levelBorders.addPoint(1000, 1200);
		levelBorders.addPoint(1000, 650);
		levelBorders.addPoint(950, 600);
		levelBorders.addPoint(950, 900);
		levelBorders.addPoint(700, 900);
		levelBorders.addPoint(650, 850);
		levelBorders.addPoint(650, 1450);
		levelBorders.addPoint(800, 1600);
		levelBorders.addPoint(650, 1750);
		levelBorders.addPoint(500, 1600);
		levelBorders.addPoint(500, 1450);
		levelBorders.addPoint(100, 1450);
		levelBorders.addPoint(100, 650);
		levelBorders.addPoint(650, 650);
		levelBorders.addPoint(700, 600);
		levelBorders.addPoint(600, 500);
		levelBorders.addPoint(400, 500);
		levelBorders.addPoint(400, 450);
		levelBorders.addPoint(200, 450);
		levelBorders.addPoint(200, 100);

		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(350, 250, Direction2D.DOWN);
		homePosition[1] = new Base2D(2300, 1600, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[7];
		resourcePlacement[0] = new ResourceRandomRectangle(100, 100, 2550, 1750, 100, ResourceType.Normal);
		resourcePlacement[1] = new ResourceRandomRectangle(100, 100, 2550, 1750, 40, ResourceType.DoublePoints);
		resourcePlacement[2] = new ResourceRandomRectangle(1000, 650, 1650, 1200, 20, ResourceType.ExtremPoint);
		resourcePlacement[3] = new ResourceRandomRectangle(500, 1450, 800, 1750, 10, ResourceType.ExtraAgentFuel);
		resourcePlacement[4] = new ResourceRandomRectangle(1850, 100, 2150, 400, 10, ResourceType.ExtraAgentFuel);
		resourcePlacement[5] = new ResourceRandomRectangle(100, 650, 650, 1450, 25, ResourceType.ExtraMothershipFuel);
		resourcePlacement[6] = new ResourceRandomRectangle(2000, 400, 2550, 1200, 25, ResourceType.ExtraMothershipFuel);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(0, 0, 0);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		return null;
	}
}
