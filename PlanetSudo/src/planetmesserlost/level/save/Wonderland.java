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
public class Wonderland extends Level {

	@Override
	public Polygon getLevelBorderPolygon() {Polygon levelBorders = new Polygon();
		levelBorders.addPoint(50, 50);
		levelBorders.addPoint(150, 50);
		levelBorders.addPoint(350, 250);
		levelBorders.addPoint(450, 150);
		levelBorders.addPoint(250, 50);
		levelBorders.addPoint(700, 50);
		levelBorders.addPoint(700, 200);
		levelBorders.addPoint(600, 200);
		levelBorders.addPoint(500, 300);
		levelBorders.addPoint(750, 300);
		levelBorders.addPoint(750, 50);
		levelBorders.addPoint(1100, 50);
		levelBorders.addPoint(1100, 350);
		levelBorders.addPoint(950, 250);
		levelBorders.addPoint(950, 150);
		levelBorders.addPoint(850, 150);
		levelBorders.addPoint(850, 250);
		levelBorders.addPoint(1100, 450);
		levelBorders.addPoint(1100, 1100);
		levelBorders.addPoint(600, 1100);
		levelBorders.addPoint(700, 850);
		levelBorders.addPoint(800, 900);
		levelBorders.addPoint(900, 800);
		levelBorders.addPoint(850, 600);
		levelBorders.addPoint(650, 600);
		levelBorders.addPoint(550, 700);
		levelBorders.addPoint(600, 800);
		levelBorders.addPoint(400, 1100);
		levelBorders.addPoint(50, 1100);
		levelBorders.addPoint(50, 700);
		levelBorders.addPoint(200, 850);
		levelBorders.addPoint(200, 950);
		levelBorders.addPoint(350, 950);
		levelBorders.addPoint(350, 600);
		levelBorders.addPoint(250, 700);
		levelBorders.addPoint(50, 500);
		levelBorders.addPoint(200, 350);
		levelBorders.addPoint(50, 200);
		return levelBorders;
	}

	@Override
	public Point2D[] getHomePositions() {
		Point2D[] homePosition = new Point2D[2];
		homePosition[0] = new Point2D(100, 900);
		homePosition[1] = new Point2D(650, 100);
		return homePosition;
	}

}
