/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.TreeMap;
import logging.Logger;
import planetmesserlost.levelobjects.AbstractLevelObject;
import planetmesserlost.levelobjects.Agent;

/**
 *
 * @author divine
 */
public class LevelView {

	public final static int RASTER_SIZE = 10;

	private final Agent agent;
	private final LevelRasterElement[] levelRepresentation;
	private final int width, height, rasterSize;

	public LevelView(Agent agent) {
		this.agent = agent;
		this.rasterSize = RASTER_SIZE;
		double widthCast = (int) agent.getMothership().getLevel().getLevelBorderPolygon().getBounds().getWidth()/rasterSize;
		double heightCast = (int) agent.getMothership().getLevel().getLevelBorderPolygon().getBounds().getHeight()/rasterSize;
		
		if(widthCast < agent.getMothership().getLevel().getLevelBorderPolygon().getBounds().getWidth()/rasterSize) {
			this.width = ((int) heightCast)+1;
		} else {
			this.width = (int) widthCast;
		}
		if(heightCast < agent.getMothership().getLevel().getLevelBorderPolygon().getBounds().getHeight()/rasterSize) {
			this.height = ((int) heightCast)+1;
		} else {
			this.height = (int) heightCast;
		}
		this.levelRepresentation = new LevelRasterElement[width*height];
		this.initLevelRepresentation();
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
		return get(((int)levelObject.getPosition().getX()-levelObject.getLevel().getX())/rasterSize, ((int)levelObject.getPosition().getY()-levelObject.getLevel().getY())/rasterSize);
	} 

	public int getAbsolutAngleToLevelObject(AbstractLevelObject levelObject) {
		return getAngle(calcLevelRasterElement(agent), calcLevelRasterElement(levelObject));
	}

	public int getDistanceToLevelObject(AbstractLevelObject levelObject) {
		return getDistance(calcLevelRasterElement(agent), calcLevelRasterElement(levelObject));
	}

	public void dijkstraTest() {
		dijkstra(levelRepresentation[0], levelRepresentation[(width*height)-1]);
	}

	private int getDistance(LevelRasterElement position, LevelRasterElement destination) {
		dijkstra(position, destination);
		return position.getDistance();
	}

	private int getAngle(LevelRasterElement position, LevelRasterElement destination) {
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

	private boolean dijkstraCalculated = false;
	private void dijkstra2(LevelRasterElement position, LevelRasterElement destination) {
		if(dijkstraCalculated) return;
		dijkstraCalculated = true;
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
		if(dijkstraCalculated) return;
		dijkstraCalculated = true;
		assert !destination.isPartOfWall();

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
		} while(element != null); // element != position &&
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

	protected Agent getAgent() {
		return agent;
	}
}
