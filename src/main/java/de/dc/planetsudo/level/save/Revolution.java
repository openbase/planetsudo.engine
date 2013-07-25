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
public class Revolution extends AbstractLevel {

	public Revolution() {
	}

	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(0, 0);
		levelBorders.addPoint(700, 0);
		levelBorders.addPoint(700, 300);
		levelBorders.addPoint(350,300);
		levelBorders.addPoint(350, 400);
		levelBorders.addPoint(800, 400);
		levelBorders.addPoint(800, 0);
		levelBorders.addPoint(1000, 0);
		levelBorders.addPoint(1000, 600);
		levelBorders.addPoint(900, 650);
		levelBorders.addPoint(1000, 700);
		levelBorders.addPoint(1000, 1000);
		levelBorders.addPoint(300, 1000);
		levelBorders.addPoint(300, 700);
		levelBorders.addPoint(650, 700);
		levelBorders.addPoint(650, 600);
		levelBorders.addPoint(200, 600);
		levelBorders.addPoint(200, 1000);
		levelBorders.addPoint(0, 1000);
		levelBorders.addPoint(0, 400);
		levelBorders.addPoint(100, 350);
		levelBorders.addPoint(0, 300);
		levelBorders.addPoint(0, 0);
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(450, 100, Direction2D.DOWN);
		homePosition[1] = new Base2D(550, 900, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[5];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 0, 1000, 1000, 10, ResourceType.Normal);
		resourcePlacement[1] = new ResourceRandomRectangle(0, 0, 1000, 1000, 20, ResourceType.DoublePoints);
		resourcePlacement[2] = new ResourceRandomRectangle(350, 400, 300, 200, 10, ResourceType.ExtremPoint);
		resourcePlacement[3] = new ResourceRandomRectangle(350, 400, 300, 200, 10, ResourceType.ExtraAgentFuel);
		resourcePlacement[4] = new ResourceRandomRectangle(350, 400, 300, 200, 10, ResourceType.ExtraMothershipFuel);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(255, 153, 0);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		return null;
	}
}
