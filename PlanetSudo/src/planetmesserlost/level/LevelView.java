/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.level;

import data.Point2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author divine
 */
public class LevelView {
	private Level level;
	private int[][] levelRepresentation;
	private final int width, height, rasterSize;


	public LevelView(Level level) {
		this.level = level;
		this.rasterSize = 1;
		this.width  = ((int)level.getLevelBorderPolygon().getBounds().getWidth()/rasterSize);
		this.height = ((int)level.getLevelBorderPolygon().getBounds().getHeight()/rasterSize);
		this.levelRepresentation = new int[width][height];
		initLevelRepresentation();
	}

	public void initLevelRepresentation() {
		for(int xr=0;xr<width;xr++) {
			for(int yr=0;yr<height;yr++) {
				if(level.getLevelBorderPolygon().contains(new Point(xr, yr))) {
					levelRepresentation[xr][yr] = 40;
				}
				levelRepresentation[xr][yr] =  200;
			}
		}
	}


//	private int get(int x, int y) {
//		return levelRepresentation[x+y*height];
//	}
	
	private Point2D getPoint(int index) {
		int y = index / height;
		int x = index-y;
		
		return new Point2D(x,y);
	}
	
	public void drawLevelView(int x, int y, Graphics2D g2) {
		x = 0;
		y = 0;
		for(int xr=0;xr<width;xr++) {
			for(int yr=0;yr<height;yr++) {
				Rectangle2D rasterElement = new Rectangle2D.Double(x+xr*rasterSize, y+yr*rasterSize, rasterSize, rasterSize);
				if(level.getLevelBorderPolygon().contains(rasterElement)) {
					levelRepresentation[xr][yr] = 0;
				} else {
					levelRepresentation[xr][yr] =  255;
				}

				int value = levelRepresentation[xr][yr];
				g2.setColor(new Color(value,value,value));
				g2.fill(rasterElement);
				g2.setColor(Color.ORANGE);
				g2.draw(rasterElement);
			}
		}
	}

}
