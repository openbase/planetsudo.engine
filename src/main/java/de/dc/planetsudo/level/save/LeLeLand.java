/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.level.save;

import de.dc.util.data.Base2D;
import de.dc.util.data.Direction2D;
import java.awt.Color;
import java.awt.Polygon;
import de.dc.planetsudo.level.AbstractLevel;
import de.dc.planetsudo.level.ResourcePlacement;
import de.dc.planetsudo.level.ResourceRandomRectangle;
import de.dc.planetsudo.level.levelobjects.Resource.ResourceType;

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
		levelBorders.addPoint(2000, 1200);
		levelBorders.addPoint(1950, 1250);
		levelBorders.addPoint(2050, 1350);
		levelBorders.addPoint(2250, 1350);
		levelBorders.addPoint(2250, 1400);
		levelBorders.addPoint(2450, 1400);
		levelBorders.addPoint(1450, 1750);
		levelBorders.addPoint(2100, 1250);
		levelBorders.addPoint(2100, 1550);
		levelBorders.addPoint(2100, 1500);
		levelBorders.addPoint(1700, 1500);
		levelBorders.addPoint(1400, 1200);
		levelBorders.addPoint(1000, 1200);
		levelBorders.addPoint(1000, 650);
		levelBorders.addPoint(950, 600);
		levelBorders.addPoint(950, 900);
		levelBorders.addPoint(700, 900);
		levelBorders.addPoint(650, 850);
		levelBorders.addPoint(1600, 1450);
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

