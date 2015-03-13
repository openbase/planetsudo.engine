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
public class Offensive extends AbstractLevel {

	public Offensive() {
	}

	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(350, 600 );
		levelBorders.addPoint(650, 600);
		levelBorders.addPoint(650, 850);
		levelBorders.addPoint(750, 850);
		levelBorders.addPoint(750, 350);
		levelBorders.addPoint(900, 350);
		levelBorders.addPoint(900, 600);
		levelBorders.addPoint(1150, 600);
		levelBorders.addPoint(1150, 300);
		levelBorders.addPoint(1450, 300);
		levelBorders.addPoint(1450, 600);
		levelBorders.addPoint(1700, 600);
		levelBorders.addPoint(1700, 350);
		levelBorders.addPoint(1850, 350);
		levelBorders.addPoint(1850, 850);
		levelBorders.addPoint(1950, 850);
		levelBorders.addPoint(1950, 600);
		levelBorders.addPoint(2250, 600);
		levelBorders.addPoint(2250, 1200);
		levelBorders.addPoint(1850, 1200);
		levelBorders.addPoint(1850, 1450);
		levelBorders.addPoint(1700, 1450);
		levelBorders.addPoint(1700, 1200);
		levelBorders.addPoint(1450, 1200);
		levelBorders.addPoint(1450, 1500);
		levelBorders.addPoint(1150, 1500);
		levelBorders.addPoint(1150, 1200);
		levelBorders.addPoint(900, 1200);
		levelBorders.addPoint(900, 1450);
		levelBorders.addPoint(750, 1450);
		levelBorders.addPoint(750, 1200);
		levelBorders.addPoint(350, 1200);
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(1000, 900, Direction2D.DOWN);
		homePosition[1] = new Base2D(1600, 900, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[12];
		resourcePlacement[0] = new ResourceRandomRectangle(1700, 1200, 150, 150, 5, ResourceType.Normal);
		resourcePlacement[1] = new ResourceRandomRectangle(750, 1200, 150, 150, 5, ResourceType.Normal);
		resourcePlacement[2] = new ResourceRandomRectangle(750, 450, 150, 150, 5, ResourceType.Normal);
		resourcePlacement[3] = new ResourceRandomRectangle(1700, 450, 150, 150, 5, ResourceType.Normal);
		resourcePlacement[4] = new ResourceRandomRectangle(750, 350, 150, 100, 3, ResourceType.DoublePoints);
		resourcePlacement[5] = new ResourceRandomRectangle(1700, 350, 150, 100, 3, ResourceType.DoublePoints);
		resourcePlacement[6] = new ResourceRandomRectangle(750, 1350, 150, 100, 3, ResourceType.DoublePoints);
		resourcePlacement[7] = new ResourceRandomRectangle(1700, 1350, 150, 100, 3, ResourceType.DoublePoints);
		resourcePlacement[8] = new ResourceRandomRectangle(1150, 300, 300, 150, 8, ResourceType.ExtremPoint);
		resourcePlacement[9] = new ResourceRandomRectangle(1150, 1350, 300, 150, 8, ResourceType.ExtremPoint);
		resourcePlacement[10] = new ResourceRandomRectangle(1150, 800, 300, 200, 3, ResourceType.ExtraAgentFuel);
		resourcePlacement[11] = new ResourceRandomRectangle(1150, 800, 300, 200, 2, ResourceType.ExtraMothershipFuel);
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
