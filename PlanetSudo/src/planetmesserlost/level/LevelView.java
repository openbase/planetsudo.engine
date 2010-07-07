/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.PriorityQueue;
import javax.lang.model.element.Element;
import planetmesserlost.levelobjects.Mothership;

/**
 *
 * @author divine
 */
public class LevelView {

	public final static int RASTER_SIZE = 100;

	private Level level;
	private Mothership mothership;
	private LevelRasterElement[] levelRepresentation;
	private final int width, height, rasterSize;

//	0 1 2
//	3 4 5
//	6 7 8
//		
//	0 1 2 3 4 5 6 7 8


	public LevelView(Mothership mothership) {
		this.mothership = mothership;
		this.level = mothership.getLevel();
		this.rasterSize = RASTER_SIZE;
		this.width  = ((int)level.getLevelBorderPolygon().getBounds().getWidth()/rasterSize);
		this.height = ((int)level.getLevelBorderPolygon().getBounds().getHeight()/rasterSize);
		this.levelRepresentation = new LevelRasterElement[width*height];
		this.initLevelRepresentation();
		dijkstra(levelRepresentation[0], levelRepresentation[5]);
	}

	public void initLevelRepresentation() {
		for(int i=0;i<levelRepresentation.length;i++) {
			levelRepresentation[i] = new LevelRasterElement(i, this);
		}
		
		for(LevelRasterElement element : levelRepresentation) {
			element.calculateNeigbours();
		}
	}

	protected LevelRasterElement get(int x, int y) {
		return levelRepresentation[x+y*width];
	}

	public void drawLevelView(int x, int y, Graphics2D g2) {
		int greyValue;
		for(LevelRasterElement element : levelRepresentation) {
			if(element.isPartOfWall()) {
				g2.setColor(Color.RED);
			} else {
				greyValue = Math.min(255, Math.max(0, element.getDistance()));
				g2.setColor(new Color(greyValue, greyValue, greyValue));
			}
			g2.fill(element.getRasterLevelRectangle());
//			g2.setColor(Color.ORANGE);
//			g2.draw(element.getRasterLevelRectangle());
		}
	}

	private void dijkstra(LevelRasterElement source, LevelRasterElement destination) {
		
		// Initialisation
		PriorityQueue<LevelRasterElement> distanceQueue = new PriorityQueue<LevelRasterElement>();
		for(LevelRasterElement element : levelRepresentation) {
				element.reset(source);
		}

		// find shortest distance
		LevelRasterElement element = source;
		do {
			element.setVisited(true);
			for(LevelRasterElementNeigbour neigbour : element.getNeigbours()) {
				if(neigbour.getElement().isVisited()) {
					continue;
				}
				neigbour.getElement().setDistance(element.getDistance()+neigbour.getWeight());
				distanceQueue.add(neigbour.getElement());
			}
			element = distanceQueue.poll();

		} while(element != null);

		
	}

	protected int getHeight() {
		return height;
	}

	protected Level getLevel() {
		return level;
	}

	protected LevelRasterElement[] getLevelRepresentation() {
		return levelRepresentation;
	}

	protected Mothership getMothership() {
		return mothership;
	}

	protected int getRasterSize() {
		return rasterSize;
	}

	protected int getWidth() {
		return width;
	}


}
