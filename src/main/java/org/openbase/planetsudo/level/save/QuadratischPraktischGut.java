/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.level.save;

import org.openbase.util.data.Base2D;
import org.openbase.util.data.Direction2D;
import java.awt.Color;
import java.awt.Polygon;
import org.openbase.planetsudo.level.AbstractLevel;
import org.openbase.planetsudo.level.ResourcePlacement;
import org.openbase.planetsudo.level.ResourceRandomRectangle;
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType;

/**
 *
 * @author divine
 */
public class QuadratischPraktischGut extends AbstractLevel {

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
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[8];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 500, 500, 600, 20, ResourceType.Normal);
                resourcePlacement[1] = new ResourceRandomRectangle(1100, 500, 500, 600, 20, ResourceType.Normal);
		resourcePlacement[2] = new ResourceRandomRectangle(500, 0, 600, 200, 35, ResourceType.DoublePoints);
		resourcePlacement[3] = new ResourceRandomRectangle(1350, 0, 250, 250, 50, ResourceType.ExtremPoint);
		resourcePlacement[4] = new ResourceRandomRectangle(0, 1350, 250, 250, 50, ResourceType.ExtremPoint);
		resourcePlacement[5] = new ResourceRandomRectangle(500, 500, 600, 600, 10, ResourceType.ExtraAgentFuel);
		resourcePlacement[6] = new ResourceRandomRectangle(500, 500, 600, 600, 10, ResourceType.ExtraMothershipFuel);
		resourcePlacement[7] = new ResourceRandomRectangle(500, 1400, 600, 200, 35, ResourceType.DoublePoints);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(93,0,0);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		return null;
	}
}

