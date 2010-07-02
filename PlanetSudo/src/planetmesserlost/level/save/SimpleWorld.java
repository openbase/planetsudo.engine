/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.level.save;

import data.Point2D;
import java.awt.Polygon;
import planetmesserlost.level.Level;

/**
 *
 * @author divine
 */
public class SimpleWorld extends Level {

	public SimpleWorld() {
	}

	@Override
	public Polygon getLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(0, 0);
		levelBorders.addPoint(0, 800);
		levelBorders.addPoint(200, 1000);
		levelBorders.addPoint(800, 1000);
		levelBorders.addPoint(1000, 800);
		levelBorders.addPoint(1000, 0);
		levelBorders.addPoint(600, 0);
		levelBorders.addPoint(600, 500);
		levelBorders.addPoint(400, 500);
		levelBorders.addPoint(400, 0);
		return levelBorders;
	}

	@Override
	public Point2D[] getHomePositions() {
		Point2D[] homePosition = new Point2D[2];
		homePosition[0] = new Point2D(200, 200);
		homePosition[1] = new Point2D(700, 700);
		return homePosition;
	}

}
