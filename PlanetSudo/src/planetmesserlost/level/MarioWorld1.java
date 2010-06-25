/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.level;

import data.Point2D;
import java.awt.Polygon;

/**
 *
 * @author noxus
 */
public class MarioWorld1 extends Level {

	@Override
	public Polygon getLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(0, 0);
		levelBorders.addPoint(500, 400);
		levelBorders.addPoint(1000, 0);
		levelBorders.addPoint(1000, 1000);
		levelBorders.addPoint(800, 1000);
		levelBorders.addPoint(800, 700);
		levelBorders.addPoint(500, 1000);
		levelBorders.addPoint(200, 700);
		levelBorders.addPoint(200, 1000);
		levelBorders.addPoint(0, 1000);
		return levelBorders;
	}

	@Override
	public Point2D[] getHomePositions() {
		Point2D[] homePosition = new Point2D[2];
		homePosition[0] = new Point2D(200, 300);
		homePosition[1] = new Point2D(800, 300);
		return homePosition;
	}

}
