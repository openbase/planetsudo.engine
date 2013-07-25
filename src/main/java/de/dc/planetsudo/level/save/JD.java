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
public class JD extends AbstractLevel {

	public JD() {
	}

	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(100, 650);
		levelBorders.addPoint(650, 650);
		levelBorders.addPoint(650, 100);
		levelBorders.addPoint(1350, 100);
		levelBorders.addPoint(1350, 650);
		levelBorders.addPoint(1900, 650);
		levelBorders.addPoint(1900, 1350);
		levelBorders.addPoint(1350, 1350);
		levelBorders.addPoint(1350, 1900);
		levelBorders.addPoint(650, 1900);
		levelBorders.addPoint(650, 1350);
		levelBorders.addPoint(100, 1350);
		levelBorders.addPoint(100, 650);
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(850, 850, Direction2D.DOWN);
		homePosition[1] = new Base2D(1150, 1150, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[5];
		resourcePlacement[0] = new ResourceRandomRectangle(100, 100, 1900, 1900, 10, ResourceType.Normal);
		resourcePlacement[1] = new ResourceRandomRectangle(100, 100, 1900, 1900, 20, ResourceType.DoublePoints);
		resourcePlacement[2] = new ResourceRandomRectangle(100, 100, 1900, 1900, 10, ResourceType.ExtremPoint);
		resourcePlacement[3] = new ResourceRandomRectangle(100, 100, 1900, 1900, 10, ResourceType.ExtraAgentFuel);
		resourcePlacement[4] = new ResourceRandomRectangle(100, 100, 1900, 1900, 10, ResourceType.ExtraMothershipFuel);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(150, 150, 150);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		Polygon[] resourcePlacement = new Polygon[1];
		resourcePlacement[0] = new Polygon();
		resourcePlacement[0].addPoint(300, 950);
		resourcePlacement[0].addPoint(400, 1050);
		resourcePlacement[0].addPoint(600, 850);
		resourcePlacement[0].addPoint(700, 950);
		resourcePlacement[0].addPoint(950, 950);
		resourcePlacement[0].addPoint(950, 700);
		resourcePlacement[0].addPoint(1050, 600);
		resourcePlacement[0].addPoint(850, 400);
		resourcePlacement[0].addPoint(950, 300);
		resourcePlacement[0].addPoint(1050, 300);
		resourcePlacement[0].addPoint(950, 400);
		resourcePlacement[0].addPoint(1150, 600);
		resourcePlacement[0].addPoint(1050, 700);
		resourcePlacement[0].addPoint(1050, 950);
		resourcePlacement[0].addPoint(1300, 950);
		resourcePlacement[0].addPoint(1400, 1050);
		resourcePlacement[0].addPoint(1500, 950);
		resourcePlacement[0].addPoint(1600, 850);
		resourcePlacement[0].addPoint(1700, 950);
		resourcePlacement[0].addPoint(1700, 1050);
		resourcePlacement[0].addPoint(1600, 950);
		resourcePlacement[0].addPoint(1400, 1150);
		resourcePlacement[0].addPoint(1300, 1050);
		resourcePlacement[0].addPoint(1050, 1050);
		resourcePlacement[0].addPoint(1050, 1300);
		resourcePlacement[0].addPoint(950, 1400);
		resourcePlacement[0].addPoint(1150, 1600);
		resourcePlacement[0].addPoint(1050, 1700);
		resourcePlacement[0].addPoint(950, 1700);
		resourcePlacement[0].addPoint(1050, 1600);
		resourcePlacement[0].addPoint(850, 1400);
		resourcePlacement[0].addPoint(950, 1300);
		resourcePlacement[0].addPoint(950, 1050);
		resourcePlacement[0].addPoint(700, 1050);
		resourcePlacement[0].addPoint(600, 950);
		resourcePlacement[0].addPoint(400, 1150);
		resourcePlacement[0].addPoint(300, 1050);
		resourcePlacement[0].addPoint(300, 950);
		return resourcePlacement;
	}
}
