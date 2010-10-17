/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.TreeMap;
import logging.Logger;
import planetsudo.level.levelobjects.AbstractLevelObject;

/**
 *
 * @author divine
 */
public class LevelView {

	public final static int RASTER_SIZE = 10;

	private final AbstractLevelObject levelObject;
	private final LevelRasterElement[] levelRepresentation;
	private final int width, height, rasterSize;

	public LevelView(AbstractLevel level) {
		this(level, null);
	}

	public LevelView(AbstractLevelObject levelObject) {
		this(levelObject.getLevel(), levelObject);
	}

	public LevelView(AbstractLevel level, AbstractLevelObject levelObject) {
		Logger.debug(this, "Create new levelview.");
		this.levelObject = levelObject;
		this.rasterSize = RASTER_SIZE;
		double widthCast = (int) level.getLevelBorderPolygon().getBounds().getWidth()/rasterSize;
		double heightCast = (int) level.getLevelBorderPolygon().getBounds().getHeight()/rasterSize;
		
		if(widthCast < level.getLevelBorderPolygon().getBounds().getWidth()/rasterSize) {
			this.width = ((int) heightCast)+1;
		} else {
			this.width = (int) widthCast;
		}
		if(heightCast < level.getLevelBorderPolygon().getBounds().getHeight()/rasterSize) {
			this.height = ((int) heightCast)+1;
		} else {
			this.height = (int) heightCast;
		}
		this.levelRepresentation = new LevelRasterElement[width*height];
		this.initLevelRepresentation();
	}

	public void initLevelRepresentation() {
		Logger.debug(this, "initLevelRepresentation");
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
		Logger.debug(this, "Draw LevelView");
		int greyValue;
		for(LevelRasterElement element : levelRepresentation) {
			if(element.isPartOfWall()) {
				g2.setColor(Color.RED);
			} else if(element.isNextToWall()) {
				g2.setColor(Color.GREEN);
			} else {
				greyValue = Math.min(255, Math.max(0, element.getDistance())/3);
				g2.setColor(new Color(greyValue, greyValue, greyValue));
			}
			g2.fill(element.getRasterLevelRectangle());
//			g2.setColor(Color.ORANGE);
//			g2.draw(element.getRasterLevelRectangle());
		}
	}

	private LevelRasterElement calcLevelRasterElement(AbstractLevelObject levelObject) {
		Logger.debug(this, "CalcLevelRasterElement");
		return get(((int)levelObject.getPosition().getX()-levelObject.getLevel().getX())/rasterSize, ((int)levelObject.getPosition().getY()-levelObject.getLevel().getY())/rasterSize);
	} 

	public int getAbsolutAngle(AbstractLevelObject levelObject) {
		Logger.debug(this, "BEGIN: GetAbsoluteAngle");
		int angle = getAngle(calcLevelRasterElement(levelObject), calcLevelRasterElement(this.levelObject));
		Logger.debug(this, "END: GetAbsoluteAngle");
		return angle;
	}

	public int getDistance(AbstractLevelObject levelObject) {
		Logger.debug(this, "GetDistance");
		return getDistance(calcLevelRasterElement(levelObject), calcLevelRasterElement(this.levelObject));
	}

	public void dijkstraTest() {
		Logger.debug(this, "dijkstraTest");
		dijkstra(levelRepresentation[0], levelRepresentation[(width*height)-1]);
	}

	private int getDistance(LevelRasterElement position, LevelRasterElement destination) {
		Logger.debug(this, "getDistance");
		dijkstra(position, destination);
		return position.getDistance();
	}

	private int getAngle(LevelRasterElement position, LevelRasterElement destination) {
		Logger.debug(this, "getAngle");
		dijkstra(position, destination);
		LevelRasterElementNeigbour neigbourNextToDestination = null;
		for(LevelRasterElementNeigbour neigbour : position.getNeigbours()) {
			if(neigbourNextToDestination == null || neigbourNextToDestination.getElement().getDistance() > neigbour.getElement().getDistance()) {
				neigbourNextToDestination = neigbour;
			}
		}
		if(neigbourNextToDestination == null) {
			Logger.error(this, "No way to destionation found!");
			return 0;
		}
		return neigbourNextToDestination.getAngle();
	}

	public void updateObjectMovement() {
		Logger.debug(this, "UpdateObjectMovement");
		dijkstra(null, calcLevelRasterElement(levelObject));
	}

	private void dijkstra2(LevelRasterElement position, LevelRasterElement destination) {
		if(levelObject.isStatic()) return;
		assert !destination.isPartOfWall();
		// Initialisation
		TreeMap<Integer, ArrayList<LevelRasterElement>> distanceQueue = new TreeMap<Integer, ArrayList<LevelRasterElement>>();
		for(LevelRasterElement element : levelRepresentation) {
			element.reset(destination);
		}
		// find shortest distance
		LevelRasterElement element = destination;
		do {
			element.setVisited(true);
			for(LevelRasterElementNeigbour neigbour : element.getNeigbours()) {
				if(!neigbour.getElement().isVisited()) {
					neigbour.getElement().setDistance(element.getDistance()+neigbour.getWeight());
					if(!distanceQueue.containsKey(neigbour.getElement().getDistance())) {
						distanceQueue.put(neigbour.getElement().getDistance(), new ArrayList<LevelRasterElement>());
					}
					if(!distanceQueue.get(neigbour.getElement().getDistance()).contains(neigbour.getElement())) {
						distanceQueue.get(neigbour.getElement().getDistance()).add(neigbour.getElement());
					}
				}
			}

			if(!distanceQueue.isEmpty()) {
				element = distanceQueue.firstEntry().getValue().remove(0);
				if(distanceQueue.firstEntry().getValue().isEmpty()) {
					distanceQueue.pollFirstEntry();
				}
			}
		} while(element != position && element != null); //
	}

	private void dijkstra(LevelRasterElement position, LevelRasterElement destination) {
		if(levelObject.isStatic()) return;
		Logger.debug(this, "BEGINN: dijkstra new calc.");
		long startTime = System.currentTimeMillis();
		// Initialisation
		PriorityQueue<LevelRasterElement> distanceQueue = new PriorityQueue<LevelRasterElement>();
		for(LevelRasterElement element : levelRepresentation) {
			element.reset(destination);
		}
		// find shortest distance
		LevelRasterElement element = destination;
		do {
			element.setVisited(true);
			for(LevelRasterElementNeigbour neigbour : element.getNeigbours()) {
				if(!neigbour.getElement().isVisited()) {
					neigbour.getElement().setDistance(element.getDistance()+neigbour.getWeight());

					if(!distanceQueue.contains(neigbour.getElement())) {
						distanceQueue.add(neigbour.getElement());
					}
				}
			}
			element = distanceQueue.poll();
		} while(element != null && element != position);
		Logger.debug(this, "END["+(System.currentTimeMillis()-startTime)+"]: dijkstra new calc.");
	}

	protected int getHeight() {
		return height;
	}

	protected LevelRasterElement[] getLevelRepresentation() {
		return levelRepresentation;
	}

	protected int getRasterSize() {
		return rasterSize;
	}

	protected int getWidth() {
		return width;
	}

	protected AbstractLevelObject getLevelObject() {
		return levelObject;
	}
}
