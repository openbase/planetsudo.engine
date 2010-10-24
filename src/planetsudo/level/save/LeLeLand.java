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
public class LeLeLand extends AbstractLevel {

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
		levelBorders.addPoint(1650, 650);
		levelBorders.addPoint(1650, 1200);
		levelBorders.addPoint(1700, 1200);
		levelBorders.addPoint(1700, 950);
		levelBorders.addPoint(1900, 950);
		levelBorders.addPoint(2000, 1000);
		levelBorders.addPoint(2000, 400);
		levelBorders.addPoint(1850, 250);
		levelBorders.addPoint(2000, 200);
		levelBorders.addPoint(2150, 250);
		levelBorders.addPoint(2150, 400);
		levelBorders.addPoint(2550, 400);
		levelBorders.addPoint(2550, 1200);
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
		homePosition[0] = new Base2D(200, 200, Direction2D.DOWN);
		homePosition[1] = new Base2D(1400, 1400, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[6];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 500, 1600, 600, 50, ResourceType.Normal);
		resourcePlacement[1] = new ResourceRandomRectangle(500, 0, 600, 1650, 25, ResourceType.DoublePoints);
		resourcePlacement[2] = new ResourceRandomRectangle(1100, 500, 500, 500, 10, ResourceType.ExtremPoint);
		resourcePlacement[3] = new ResourceRandomRectangle(0, 1100, 500, 500, 10, ResourceType.ExtremPoint);
		resourcePlacement[4] = new ResourceRandomRectangle(500, 500, 600, 600, 4, ResourceType.ExtraAgentFuel);
		resourcePlacement[5] = new ResourceRandomRectangle(500, 500, 600, 600, 4, ResourceType.ExtraMothershipFuel);
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

