/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.level.save;

import de.dc.planetsudo.level.AbstractLevel;
import de.dc.planetsudo.level.ResourcePlacement;
import de.dc.planetsudo.level.ResourceRandomRectangle;
import de.dc.planetsudo.level.levelobjects.Resource.ResourceType;
import de.dc.util.data.Base2D;
import de.dc.util.data.Direction2D;
import java.awt.Color;
import java.awt.Polygon;



/**
 *
 * @author noxus
 */
public class TwoKings extends AbstractLevel {
    
	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(0, 0);
		levelBorders.addPoint(375, 300);
		levelBorders.addPoint(750,0);
		levelBorders.addPoint(1125, 300);
		levelBorders.addPoint(1500, 0);
		levelBorders.addPoint(1500, 1500);
		levelBorders.addPoint(1125, 1200);
		levelBorders.addPoint(750, 1500);
		levelBorders.addPoint(375, 1200);
		levelBorders.addPoint(0, 1500);
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(750, 150, Direction2D.DOWN);
		homePosition[1] = new Base2D(750, 1350, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[10];
		resourcePlacement[0] = new ResourceRandomRectangle(300, 650, 200, 200, 10, ResourceType.ExtremPoint);
		resourcePlacement[1] = new ResourceRandomRectangle(1000, 650, 200, 200, 10, ResourceType.ExtremPoint);
		resourcePlacement[2] = new ResourceRandomRectangle(0, 0, 1500, 650, 5, ResourceType.ExtraAgentFuel);
		resourcePlacement[3] = new ResourceRandomRectangle(0, 850, 1500, 650, 5, ResourceType.ExtraAgentFuel);
		resourcePlacement[4] = new ResourceRandomRectangle(0, 0, 1500, 650, 5, ResourceType.ExtraMothershipFuel);
		resourcePlacement[5] = new ResourceRandomRectangle(0, 850, 1500, 650, 5, ResourceType.ExtraMothershipFuel);
		resourcePlacement[6] = new ResourceRandomRectangle(0, 0, 1500, 650, 10, ResourceType.Normal);
                resourcePlacement[7] = new ResourceRandomRectangle(0, 850, 1500, 650, 10, ResourceType.Normal);
                resourcePlacement[8] = new ResourceRandomRectangle(0, 0, 1500, 650, 5, ResourceType.DoublePoints);
                resourcePlacement[9] = new ResourceRandomRectangle(0, 850, 1500, 650, 5, ResourceType.DoublePoints);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(137,67,162);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		Polygon[] walls = new Polygon[3];
		walls[0] = new Polygon();
		walls[0].addPoint(0, 650);
		walls[0].addPoint(300, 650);
		walls[0].addPoint(300, 850);
		walls[0].addPoint(0, 850);
                walls[1] = new Polygon();
                walls[1].addPoint(500, 650);
                walls[1].addPoint(1000, 650);
                walls[1].addPoint(1000, 850);
                walls[1].addPoint(500, 850);
                walls[2] = new Polygon();
                walls[2].addPoint(1200, 650);
                walls[2].addPoint(1500, 650);
                walls[2].addPoint(1500, 850);
                walls[2].addPoint(1200, 850);
		return walls;
	}
}
/*
 *
 * @author Grimmy
 */
