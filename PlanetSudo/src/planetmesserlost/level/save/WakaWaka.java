/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.level.save;

import data.Point2D;
import java.awt.Polygon;
import planetmesserlost.level.AbstractLevel;
import planetmesserlost.level.ResourcePlacement;
import planetmesserlost.level.ResourceRandomRectangle;

/**
 *
 * @author lena
 */
public class WakaWaka extends AbstractLevel{
@Override
	public Polygon getLevelBorderPolygon() {
		Polygon levelBorders = new Polygon();
		levelBorders.addPoint(50, 50);
		levelBorders.addPoint(350, 50);
	//	levelBorders.addPoint(350, 250);
	//	levelBorders.addPoint(450, 350);
	//	levelBorders.addPoint(700, 350);
	//	levelBorders.addPoint(700, 200);
	//	levelBorders.addPoint(600, 300);
	//	levelBorders.addPoint(500, 300);
	//	levelBorders.addPoint(450, 200);
		levelBorders.addPoint(450, 50);
		levelBorders.addPoint(800, 50);
		levelBorders.addPoint(800, 100);
		levelBorders.addPoint(850, 100);
		levelBorders.addPoint(850, 150);
		levelBorders.addPoint(900, 150);
		levelBorders.addPoint(900, 100);
		levelBorders.addPoint(950, 100);
		levelBorders.addPoint(950, 50);
		levelBorders.addPoint(1250, 50);
		levelBorders.addPoint(1250, 250);
		levelBorders.addPoint(1150, 300);
		levelBorders.addPoint(1050, 250);
		levelBorders.addPoint(950, 250);
		levelBorders.addPoint(900, 350);
		levelBorders.addPoint(900, 450);
		levelBorders.addPoint(1000, 450);
		levelBorders.addPoint(1000, 450);
		levelBorders.addPoint(1000, 450);
		levelBorders.addPoint(950, 400);
		levelBorders.addPoint(950, 350);
		levelBorders.addPoint(1000, 300);
		levelBorders.addPoint(1050, 300);
		levelBorders.addPoint(1150, 350);
		levelBorders.addPoint(1150, 400);
		levelBorders.addPoint(1250, 500);
		levelBorders.addPoint(1250, 550);
		levelBorders.addPoint(1200, 600);
		levelBorders.addPoint(1150, 600);
		levelBorders.addPoint(1150, 550);
		levelBorders.addPoint(1100, 550);
		levelBorders.addPoint(1100, 650);
		levelBorders.addPoint(1200, 650);
		levelBorders.addPoint(1300, 600);
		levelBorders.addPoint(1300, 500);
		levelBorders.addPoint(1250, 400);
		levelBorders.addPoint(1350, 300);
		levelBorders.addPoint(1500, 300);
		levelBorders.addPoint(1500, 600);
		levelBorders.addPoint(1450, 600);
		levelBorders.addPoint(1450, 650);
		levelBorders.addPoint(1400, 650);
		levelBorders.addPoint(1400, 700);
		levelBorders.addPoint(1450, 700);
		levelBorders.addPoint(1450, 750);
		levelBorders.addPoint(1500, 750);
		levelBorders.addPoint(1500, 1100);
	//	levelBorders.addPoint(1350, 1100);
	//	levelBorders.addPoint(1250, 1000);
	//	levelBorders.addPoint(1250, 950);
	//	levelBorders.addPoint(1350, 850);
	//	levelBorders.addPoint(1200, 850);
	//	levelBorders.addPoint(1200, 1100);
	//	levelBorders.addPoint(1300, 1200);
		levelBorders.addPoint(1500, 1200);
		levelBorders.addPoint(1500, 1500);
		levelBorders.addPoint(1200, 1500);
	//	levelBorders.addPoint(1200, 1300);
	//	levelBorders.addPoint(1100, 1200);
	//	levelBorders.addPoint(800, 1200);
	//	levelBorders.addPoint(800, 1300);
	//	levelBorders.addPoint(950, 1250);
	//	levelBorders.addPoint(1000, 1250);
	//	levelBorders.addPoint(1100, 1350);
		levelBorders.addPoint(1100, 1500);
		levelBorders.addPoint(750, 1500);
		levelBorders.addPoint(650, 1400);
		levelBorders.addPoint(550, 1500);
		levelBorders.addPoint(150, 1500);
		levelBorders.addPoint(250, 1400);
		levelBorders.addPoint(300, 1450);
		levelBorders.addPoint(400, 1350);
		levelBorders.addPoint(350, 1300);
		levelBorders.addPoint(450, 1200);
		levelBorders.addPoint(550, 1300);
		levelBorders.addPoint(550, 1050);
		levelBorders.addPoint(600, 1000);
		levelBorders.addPoint(850, 1000);
		levelBorders.addPoint(850, 850);
		levelBorders.addPoint(800, 850);
		levelBorders.addPoint(750, 950);
		levelBorders.addPoint(600, 800);
		levelBorders.addPoint(700, 750);
		levelBorders.addPoint(700, 700);
		levelBorders.addPoint(550, 700);
		levelBorders.addPoint(550, 950);
		levelBorders.addPoint(500, 1000);
		levelBorders.addPoint(250, 1000);
		levelBorders.addPoint(350, 1100);
		levelBorders.addPoint(250, 1200);
		levelBorders.addPoint(200, 1150);
		levelBorders.addPoint(100, 1250);
		levelBorders.addPoint(150, 1300);
		levelBorders.addPoint(50, 1400);
		levelBorders.addPoint(50, 1000);
		levelBorders.addPoint(150, 900);
		levelBorders.addPoint(50, 800);
		levelBorders.addPoint(50, 450);
	//	levelBorders.addPoint(200, 450);
	//	levelBorders.addPoint(300, 550);
	//	levelBorders.addPoint(300, 600);
	//	levelBorders.addPoint(200, 700);
	//	levelBorders.addPoint(350, 700);
	//	levelBorders.addPoint(350, 450);
	//	levelBorders.addPoint(250, 350);
		levelBorders.addPoint(50, 350);
		levelBorders.addPoint(50, 50);
		return levelBorders;
	}

	@Override
	public Point2D[] getHomePositions() {
		Point2D[] homePosition = new Point2D[2];
		homePosition[0] = new Point2D(100, 100);
		homePosition[1] = new Point2D(1450, 1450);
		return homePosition;
	}

	@Override
	public ResourcePlacement[] getResourcePlacement() {
		ResourcePlacement[] resourcePlacement = new ResourcePlacement[1];
		resourcePlacement[0] = new ResourceRandomRectangle(0, 0, 1000, 1000, 5);
		return resourcePlacement;
	}

}