/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.level;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2023 openbase.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.openbase.planetsudo.level.levelobjects.AbstractLevelObject;
import java.awt.geom.Rectangle2D;
import org.openbase.planetsudo.geometry.Point2D;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class LevelView {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
	public final static int RASTER_SIZE = 10;
	private final AbstractLevelObject levelObject;
	private final LevelRasterElement[] levelRepresentation;
	private final int x_base, y_base, width, height, rasterSize;
	private Point2D lastPosition;

	public LevelView(AbstractLevel level) {
		this(level, null);
	}

	public LevelView(AbstractLevelObject levelObject) {
		this(levelObject.getLevel(), levelObject);
	}

	public LevelView(AbstractLevel level, AbstractLevelObject levelObject) {
		logger.debug("Create new levelview.");
		this.levelObject = levelObject;
		this.rasterSize = RASTER_SIZE;
		final Rectangle2D bounds = level.getLevelBorderPolygon().getBounds2D();

		double widthCast = (int) (bounds.getWidth() / rasterSize);
		double heightCast = (int) bounds.getHeight() / rasterSize;

		this.x_base = (int) bounds.getX();
		this.y_base = (int) bounds.getY();

		if (widthCast < bounds.getWidth() / rasterSize) {
			this.width = ((int) widthCast) + 1;
		} else {
			this.width = (int) widthCast;
		}
		if (heightCast < bounds.getHeight() / rasterSize) {
			this.height = ((int) heightCast) + 1;
		} else {
			this.height = (int) heightCast;
		}
		this.levelRepresentation = new LevelRasterElement[width * height];
		this.initLevelRepresentation();
		this.updateObjectMovement(true);
	}

	private void initLevelRepresentation() {
		logger.debug("initLevelRepresentation");
		for (int i = 0; i < levelRepresentation.length; i++) {
			levelRepresentation[i] = new LevelRasterElement(i, this);
		}

		for (LevelRasterElement element : levelRepresentation) {
			element.calculateNeigbours();
		}
	}

	protected LevelRasterElement get(int x, int y) {
		try {
			return levelRepresentation[x + y * width];
		} catch (Exception ex) {
			logger.error("x_base[" + x_base + "] y_base[" + y_base + "] x[" + x + "] y[" + y + "] width[" + width + "] bounds[" + levelRepresentation.length + "]");
			throw new RuntimeException(ex);
		}
	}

	public void drawLevelView(int x, int y, Graphics2D g2) {
		logger.info("Draw LevelView");
		int greyValue;
		for (LevelRasterElement element : levelRepresentation) {
			if (element.isPartOfWall()) {
				g2.setColor(Color.RED);
			} else if (element.isNextToWall()) {
				g2.setColor(Color.GREEN);
			} else {
				greyValue = Math.min(255, Math.max(0, element.getDistance()) / 3);
				g2.setColor(new Color(greyValue, greyValue, greyValue));
			}
			g2.fill(element.getRasterLevelRectangle());
//			g2.setColor(Color.ORANGE);
//			g2.draw(element.getRasterLevelRectangle());
		}
	}

	private LevelRasterElement calcLevelRasterElement(final AbstractLevelObject levelObject) {
		logger.debug("CalcLevelRasterElement");
		return get(((int) levelObject.getPosition().getX() - levelObject.getLevel().getX()) / rasterSize, ((int) levelObject.getPosition().getY() - levelObject.getLevel().getY()) / rasterSize);
	}

	public int getAbsolutAngle(final AbstractLevelObject levelObject) {
//		logger.info("BEGIN: GetAbsoluteAngle");
		final int angle = getAngle(calcLevelRasterElement(levelObject), calcLevelRasterElement(this.levelObject));
//		logger.info("END: GetAbsoluteAngle: "+angle);
		assert angle != -1;
		return angle;
	}

	public int getDistance(final AbstractLevelObject levelObject) {
//		logger.debug("GetDistance");
		return getDistance(calcLevelRasterElement(levelObject), calcLevelRasterElement(this.levelObject));
	}

	public void dijkstraTest() {
//		logger.debug("dijkstraTest");
		dijkstra(levelRepresentation[0], levelRepresentation[(width * height) - 1]);
	}

	private int getDistance(LevelRasterElement position, LevelRasterElement destination) {
//		logger.debug("getDistance");
		dijkstra(position, destination);
		return position.getDistance();
	}

	private int getAngle(LevelRasterElement position, LevelRasterElement destination) {
//		logger.debug("getAngle");
		dijkstra(position, destination);
		LevelRasterElementNeigbour neigbourNextToDestination = null;
		for (LevelRasterElementNeigbour neigbour : position.getNeigbours()) {
			if (neigbourNextToDestination == null || neigbourNextToDestination.getElement().getDistance() > neigbour.getElement().getDistance()) {
				neigbourNextToDestination = neigbour;
			}
		}
		if (neigbourNextToDestination == null) {
			logger.error("No way to destionation found!");
			return 0;
		}
		return neigbourNextToDestination.getAngle();
	}

	public final void updateObjectMovement(final boolean force) {
		logger.debug("UpdateObjectMovement");
		dijkstra(null, calcLevelRasterElement(levelObject), force);
	}

	public final void updateObjectMovement() {
		updateObjectMovement(false);
	}

	private void dijkstra2(LevelRasterElement position, LevelRasterElement destination, final boolean force) {
		if (!force && stablePosition()) {
			logger.debug("Skip dijkstra.");
			return;
		}
		assert !destination.isPartOfWall();
		// Initialisation
		TreeMap<Integer, ArrayList<LevelRasterElement>> distanceQueue = new TreeMap<Integer, ArrayList<LevelRasterElement>>();
		for (LevelRasterElement element : levelRepresentation) {
			element.reset(destination);
		}
		// find shortest distance
		LevelRasterElement element = destination;
		do {
			element.setVisited(true);
			for (LevelRasterElementNeigbour neigbour : element.getNeigbours()) {
				if (!neigbour.getElement().isVisited()) {
					neigbour.getElement().setDistance(element.getDistance() + neigbour.getWeight());
					if (!distanceQueue.containsKey(neigbour.getElement().getDistance())) {
						distanceQueue.put(neigbour.getElement().getDistance(), new ArrayList<LevelRasterElement>());
					}
					if (!distanceQueue.get(neigbour.getElement().getDistance()).contains(neigbour.getElement())) {
						distanceQueue.get(neigbour.getElement().getDistance()).add(neigbour.getElement());
					}
				}
			}

			if (!distanceQueue.isEmpty()) {
				element = distanceQueue.firstEntry().getValue().remove(0);
				if (distanceQueue.firstEntry().getValue().isEmpty()) {
					distanceQueue.pollFirstEntry();
				}
			}
		} while (element != position && element != null); //
	}

	private boolean stablePosition() {
		return levelObject.getPosition().equals(lastPosition);
	}

	private void dijkstra(LevelRasterElement position, LevelRasterElement destination) {
		dijkstra(position, destination, false);
	}

	private synchronized void dijkstra(LevelRasterElement position, LevelRasterElement destination, boolean force) {
		if (!force && stablePosition()) {
			logger.debug("Skip dijkstra.");
			return;
		}
		this.lastPosition = levelObject.getPosition();
//		logger.debug("BEGINN: dijkstra new calc.");
//		long startTime = System.currentTimeMillis();
		// Initialisation
		final PriorityQueue<LevelRasterElement> distanceQueue = new PriorityQueue<LevelRasterElement>();
		for (LevelRasterElement element : levelRepresentation) {
			element.reset(destination);
		}
		// find shortest distance
		LevelRasterElement element = destination;
		do {
			element.setVisited(true);
			for (LevelRasterElementNeigbour neigbour : element.getNeigbours()) {
				if (!neigbour.getElement().isVisited()) {
					neigbour.getElement().setDistance(element.getDistance() + neigbour.getWeight());

					if (!distanceQueue.contains(neigbour.getElement())) {
						distanceQueue.add(neigbour.getElement());
					}
				}
			}
			element = distanceQueue.poll();
		//} while (element != null && element != position);
			} while (element != null);
//		logger.debug("END["+(System.currentTimeMillis()-startTime)+"]: dijkstra new calc.");
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
