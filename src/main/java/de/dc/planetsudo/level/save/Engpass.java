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
public class Engpass extends AbstractLevel {

	public Engpass() {
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
		homePosition[0] = new Base2D(80, 500, Direction2D.LEFT);
		homePosition[1] = new Base2D(920, 500, Direction2D.LEFT);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[5];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 0, 1000, 1000, 5, ResourceType.Normal);
		resourcePlacement[1] = new ResourceRandomRectangle(0, 0, 1000, 1000, 5, ResourceType.DoublePoints);
		resourcePlacement[2] = new ResourceRandomRectangle(400, 400, 200, 200, 10, ResourceType.ExtremPoint);
		resourcePlacement[3] = new ResourceRandomRectangle(0, 0, 1000, 1000, 10, ResourceType.ExtraAgentFuel);
		resourcePlacement[4] = new ResourceRandomRectangle(0, 0, 1000, 1000, 10, ResourceType.ExtraMothershipFuel);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(67, 167, 197);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		Polygon[] walls = new Polygon[4];
		walls[0] = new Polygon();
		walls[0].addPoint(0, 0);
		walls[0].addPoint(500, 0);
		walls[0].addPoint(500, 350);
		walls[1] = new Polygon();
		walls[1].addPoint(1000, 0);
		walls[1].addPoint(500, 0);
		walls[1].addPoint(500, 350);
		walls[2] = new Polygon();
		walls[2].addPoint(0, 1000);
		walls[2].addPoint(500, 1000);
		walls[2].addPoint(500, 650);
		walls[3] = new Polygon();
		walls[3].addPoint(1000, 1000);
		walls[3].addPoint(500, 1000);
		walls[3].addPoint(500, 650);
		return walls;
	}
}
