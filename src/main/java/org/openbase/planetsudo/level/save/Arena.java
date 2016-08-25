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
public class Arena extends AbstractLevel {

	public Arena() {
	}

	@Override
	protected Polygon loadLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
                levelBorders.addPoint(0, 0);
                levelBorders.addPoint(0, 200);              
                levelBorders.addPoint(400, 200);
                levelBorders.addPoint(400, 0);              
                levelBorders.addPoint(600, 0);
                levelBorders.addPoint(600, 200);            
                levelBorders.addPoint(1000, 200);                
                levelBorders.addPoint(1000, 0);                          
                levelBorders.addPoint(1000, 800);
                levelBorders.addPoint(600, 800);              
                levelBorders.addPoint(600, 1000);
                levelBorders.addPoint(1000, 1000);             
                levelBorders.addPoint(0, 1000);
                levelBorders.addPoint(400, 1000); 
                levelBorders.addPoint(400, 800);
                levelBorders.addPoint(0, 800);
                
		return levelBorders;
	}

	@Override
	protected Base2D[] loadHomePositions() {
		Base2D[] homePosition = new Base2D[2];
		homePosition[0] = new Base2D(500, 100, Direction2D.UP);
		homePosition[1] = new Base2D(500, 900, Direction2D.UP);
		return homePosition;
	}

	@Override
	protected ResourcePlacement[] loadResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[6];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 0, 1000, 1000, 10, ResourceType.Normal);
		resourcePlacement[1] = new ResourceRandomRectangle(300, 325, 400, 50, 10, ResourceType.DoublePoints);
                resourcePlacement[2] = new ResourceRandomRectangle(300, 675, 400, 50, 10, ResourceType.DoublePoints);
		resourcePlacement[3] = new ResourceRandomRectangle(400, 480, 200, 40, 10, ResourceType.ExtremPoint);
		resourcePlacement[4] = new ResourceRandomRectangle(0, 0, 1000, 1000, 5, ResourceType.ExtraAgentFuel);
		resourcePlacement[5] = new ResourceRandomRectangle(0, 0, 1000, 1000, 5, ResourceType.ExtraMothershipFuel);
		return resourcePlacement;
	}

	@Override
	protected Color loadLevelColor() {
		return new Color(67,167,197);
	}

	@Override
    protected Polygon[] loadLevelWallPolygons() {
        Polygon[] walls = new Polygon[0];
        return null;
	}
}
