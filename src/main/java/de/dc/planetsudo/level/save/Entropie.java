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
public class Entropie extends AbstractLevel {

    public Entropie() {
    }

    @Override
    protected Polygon loadLevelBorderPolygon() {
        Polygon levelBorders = new Polygon();
        levelBorders.addPoint(0, 0);
        levelBorders.addPoint(0, 400);
        levelBorders.addPoint(450, 400);
        levelBorders.addPoint(450, 600);
        levelBorders.addPoint(150, 600);
        levelBorders.addPoint(150, 800);
        levelBorders.addPoint(450, 800);
        levelBorders.addPoint(450, 1000);
        levelBorders.addPoint(0, 1000);
        levelBorders.addPoint(0, 1400);
        levelBorders.addPoint(900, 1400);
        levelBorders.addPoint(900, 0);
        return levelBorders;
    }

    @Override
    protected Base2D[] loadHomePositions() {
        Base2D[] homePosition = new Base2D[2];
        homePosition[0] = new Base2D(100, 200, Direction2D.DOWN);
        homePosition[1] = new Base2D(100, 1200, Direction2D.DOWN);
        return homePosition;
    }

    @Override
    protected ResourcePlacement[] loadResourcePlacement() {
        ResourcePlacement[] resourcePlacement = new ResourcePlacement[5];
        resourcePlacement[0] = new ResourceRandomRectangle(450, 0, 450, 1400, 10, ResourceType.Normal);
        resourcePlacement[1] = new ResourceRandomRectangle(450, 0, 450, 1400, 20, ResourceType.DoublePoints);
        resourcePlacement[2] = new ResourceRandomRectangle(150, 600, 300, 200, 50, ResourceType.ExtremPoint);
        resourcePlacement[3] = new ResourceRandomRectangle(800, 0, 100, 1400, 20, ResourceType.ExtraAgentFuel);
        resourcePlacement[4] = new ResourceRandomRectangle(150, 600, 300, 200, 10, ResourceType.ExtraMothershipFuel);
        return resourcePlacement;
    }

    @Override
    protected Color loadLevelColor() {
        return new Color(177, 44, 140);
    }

    @Override
    protected Polygon[] loadLevelWallPolygons() {
        Polygon[] walls = new Polygon[0];
//        walls[0] = new Polygon();
//        walls[0].addPoint(400, 400);
//        walls[0].addPoint(600, 400);
//        walls[0].addPoint(600, 600);
//        walls[0].addPoint(400, 600);
        return null;
    }
}
