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
public class Wonderland extends AbstractLevel {

	@Override
	protected Polygon loadLevelBorderPolygon() {Polygon levelBorders = new Polygon();
		levelBorders.addPoint(50, 50);
		levelBorders.addPoint(150, 50);
		levelBorders.addPoint(350, 250);
		levelBorders.addPoint(450, 150);
		levelBorders.addPoint(250, 50);
		levelBorders.addPoint(700, 50);
		levelBorders.addPoint(700, 200);
		levelBorders.addPoint(600, 200);
		levelBorders.addPoint(500, 300);
		levelBorders.addPoint(750, 300);
		levelBorders.addPoint(750, 50);
		levelBorders.addPoint(1100, 50);
		levelBorders.addPoint(1100, 350);
		levelBorders.addPoint(950, 250);
		levelBorders.addPoint(950, 150);
		levelBorders.addPoint(850, 150);
		levelBorders.addPoint(850, 250);
		levelBorders.addPoint(1100, 450);
		levelBorders.addPoint(1100, 1100);
		levelBorders.addPoint(600, 1100);
		levelBorders.addPoint(700, 850);
		levelBorders.addPoint(800, 900);
		levelBorders.addPoint(900, 800);
		levelBorders.addPoint(850, 600);
		levelBorders.addPoint(650, 600);
		levelBorders.addPoint(550, 700);
		levelBorders.addPoint(600, 800);
		levelBorders.addPoint(400, 1100);
		levelBorders.addPoint(50, 1100);
		levelBorders.addPoint(50, 700);
		levelBorders.addPoint(200, 850);
		levelBorders.addPoint(200, 950);
		levelBorders.addPoint(350, 950);
		levelBorders.addPoint(350, 600);
		levelBorders.addPoint(250, 700);
		levelBorders.addPoint(50, 500);
		levelBorders.addPoint(200, 350);
		levelBorders.addPoint(50, 200);
		return levelBorders;
	}

	@Override
	protected Point2D[] loadHomePositions() {
		Point2D[] homePosition = new Point2D[2];
		homePosition[0] = new Point2D(100, 900);
		homePosition[1] = new Point2D(650, 100);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[1];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 0, 1000, 1000, 50, ResourceType.Normal);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return Color.YELLOW;
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		return null;
	}
}
