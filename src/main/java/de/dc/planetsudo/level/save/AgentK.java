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
 * @author noxus
 */
public class AgentK extends AbstractLevel {
	
	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(0, 0);
		levelBorders.addPoint(500, 0);
		levelBorders.addPoint(500, 500);
		levelBorders.addPoint(1100, 0);
		levelBorders.addPoint(1600, 0);
		levelBorders.addPoint(500, 1000);
		levelBorders.addPoint(1600, 2000);
		levelBorders.addPoint(1100, 2000);
		levelBorders.addPoint(500, 1500);
		levelBorders.addPoint(500, 2000);
		levelBorders.addPoint(0, 2000);
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(1200, 100, Direction2D.DOWN);
		homePosition[1] = new Base2D(1200, 1900, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[10];
		resourcePlacement[0] = new ResourceRandomRectangle(500, 0, 750, 1100, 10, ResourceType.ExtraAgentFuel);
		resourcePlacement[1] = new ResourceRandomRectangle(500, 750, 750, 1100, 10, ResourceType.ExtraAgentFuel);
		resourcePlacement[2] = new ResourceRandomRectangle(0, 0, 500, 50, 20, ResourceType.ExtremPoint);
		resourcePlacement[3] = new ResourceRandomRectangle(0, 1950, 500, 50, 20, ResourceType.ExtremPoint);
		resourcePlacement[4] = new ResourceRandomRectangle(0, 0, 500, 500, 15, ResourceType.ExtraMothershipFuel);
		resourcePlacement[5] = new ResourceRandomRectangle(0, 1500, 500, 500, 15, ResourceType.ExtraMothershipFuel);
		resourcePlacement[6] = new ResourceRandomRectangle(0, 500, 500, 500, 5, ResourceType.Normal);
                resourcePlacement[7] = new ResourceRandomRectangle(0, 1000, 500, 500, 5, ResourceType.Normal);
                resourcePlacement[8] = new ResourceRandomRectangle(0, 0, 500, 500, 5, ResourceType.DoublePoints);
                resourcePlacement[9] = new ResourceRandomRectangle(0, 1500, 500, 500, 5, ResourceType.DoublePoints);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(137,67,162);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
		return null;
	}
}
/*
 *
 * @author noxus
 */
