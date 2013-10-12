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
public class AtomumLight extends AbstractLevel {
	
	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(1000, 0);
		levelBorders.addPoint(2000, 1000);
		levelBorders.addPoint(1000, 2000);
		levelBorders.addPoint(0, 1000);
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(150, 1000, Direction2D.DOWN);
		homePosition[1] = new Base2D(1850, 1000, Direction2D.DOWN);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[19];
		resourcePlacement[0] = new ResourceRandomRectangle(800, 800, 400, 400, 15, ResourceType.ExtraAgentFuel);
		resourcePlacement[1] = new ResourceRandomRectangle(800, 0, 300, 150, 50, ResourceType.ExtremPoint);
		resourcePlacement[2] = new ResourceRandomRectangle(800, 1850, 300, 150, 50, ResourceType.ExtremPoint);
		resourcePlacement[3] = new ResourceRandomRectangle(900, 1200, 300, 250, 15, ResourceType.ExtraMothershipFuel);
		resourcePlacement[4] = new ResourceRandomRectangle(900, 550, 300, 250, 15, ResourceType.ExtraMothershipFuel);
		resourcePlacement[5] = new ResourceRandomRectangle(500, 850, 250, 300, 5, ResourceType.Normal);
                resourcePlacement[6] = new ResourceRandomRectangle(1250, 850, 250, 300, 5, ResourceType.Normal);
                resourcePlacement[7] = new ResourceRandomRectangle(550, 450, 100, 100, 3, ResourceType.DoublePoints);
                resourcePlacement[8] = new ResourceRandomRectangle(700, 300, 100, 100, 3, ResourceType.DoublePoints);
                resourcePlacement[9] = new ResourceRandomRectangle(400, 600, 100, 100, 3, ResourceType.DoublePoints);
                resourcePlacement[10] = new ResourceRandomRectangle(300, 1300, 100, 100, 3, ResourceType.DoublePoints);
                resourcePlacement[11] = new ResourceRandomRectangle(450, 1450, 100, 100, 3, ResourceType.DoublePoints);
                resourcePlacement[12] = new ResourceRandomRectangle(600, 1600, 100, 100, 3, ResourceType.DoublePoints);
                resourcePlacement[13] = new ResourceRandomRectangle(1300, 300, 100, 100, 3, ResourceType.DoublePoints);
                resourcePlacement[14] = new ResourceRandomRectangle(1450, 450, 100, 100, 3, ResourceType.DoublePoints);
                resourcePlacement[15] = new ResourceRandomRectangle(1600, 600, 100, 100, 3, ResourceType.DoublePoints);
                resourcePlacement[16] = new ResourceRandomRectangle(1600, 1300, 100, 100, 3, ResourceType.DoublePoints);
                resourcePlacement[17] = new ResourceRandomRectangle(1440, 1450, 100, 100, 3, ResourceType.DoublePoints);
                resourcePlacement[18] = new ResourceRandomRectangle(1300, 1600, 100, 100, 3, ResourceType.DoublePoints);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(169,249,4);
	}

	@Override
	protected Polygon[] loadLevelWallPolygons() {
            		Polygon[] resourcePlacement = new Polygon[4];
		resourcePlacement[0] = new Polygon();
		resourcePlacement[0].addPoint(375, 750);
		resourcePlacement[0].addPoint(875, 250);
		resourcePlacement[0].addPoint(900, 300);
		resourcePlacement[0].addPoint(900, 800);
                resourcePlacement[0].addPoint(750, 850);
                resourcePlacement[0].addPoint(500, 850);
                resourcePlacement[0].addPoint(400, 900);
                resourcePlacement[0].addPoint(350, 900);
                resourcePlacement[1] = new Polygon();
		resourcePlacement[1].addPoint(1625, 750);
		resourcePlacement[1].addPoint(1125, 250);
		resourcePlacement[1].addPoint(1100, 300);
		resourcePlacement[1].addPoint(1100, 800);
                resourcePlacement[1].addPoint(1250, 850);
                resourcePlacement[1].addPoint(1500, 850);
                resourcePlacement[1].addPoint(1600, 900);
                resourcePlacement[1].addPoint(1650, 900);
                resourcePlacement[2] = new Polygon();
		resourcePlacement[2].addPoint(375, 1250);
		resourcePlacement[2].addPoint(875, 1750);
		resourcePlacement[2].addPoint(900, 1700);
		resourcePlacement[2].addPoint(900, 1200);
                resourcePlacement[2].addPoint(750, 1150);
                resourcePlacement[2].addPoint(500, 1150);
                resourcePlacement[2].addPoint(400, 1100);
                resourcePlacement[2].addPoint(350, 1100);
                resourcePlacement[3] = new Polygon();
		resourcePlacement[3].addPoint(1625, 1250);
		resourcePlacement[3].addPoint(1125, 1750);
		resourcePlacement[3].addPoint(1100, 1700);
		resourcePlacement[3].addPoint(1100, 1200);
                resourcePlacement[3].addPoint(1250, 1150);
                resourcePlacement[3].addPoint(1500, 1150);
                resourcePlacement[3].addPoint(1600, 1100);
                resourcePlacement[3].addPoint(1650, 1100);
                
		return resourcePlacement;
	}
}
/*
 *
 * @author Grimmy
 */
