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

/**
 *
 * @author divine
 */
public class SimpleFight extends AbstractLevel {

	public SimpleFight() {
	}

	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(0, 0);
		levelBorders.addPoint(1000, 0);
		levelBorders.addPoint(1000, 1000);
		levelBorders.addPoint(0, 1000);
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(200, 500, Direction2D.DOWN);
		homePosition[1] = new Base2D(800, 500, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[0];
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
