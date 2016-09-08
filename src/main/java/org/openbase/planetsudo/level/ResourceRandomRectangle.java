/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.level;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2016 openbase.org
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

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import org.openbase.jul.exception.InvalidStateException;
import org.openbase.planetsudo.geometry.Point2D;
import org.slf4j.Logger;
import org.openbase.planetsudo.level.levelobjects.Resource;
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType;
import org.openbase.planetsudo.main.GUIController;
import org.openbase.planetsudo.util.RandomGenerator;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class ResourceRandomRectangle extends Rectangle2D.Double implements ResourcePlacement {
    
    private static final Logger logger = LoggerFactory.getLogger(ResourceRandomRectangle.class);
    
	private int resourceCount;
	private ResourceType type;
	public ResourceRandomRectangle(int x, int y, int width, int height, int resourceCount, ResourceType type) {
		super((int)x, (int)y, (int)width, (int)height);
		this.resourceCount = resourceCount;
		this.type = type;
	}

	@Override
	public ArrayList<Resource> getResources(AbstractLevel level) {
		ArrayList<Resource> resources = new ArrayList<Resource>();
		for(int i=0; i<resourceCount;i++) {
			GUIController.setEvent(new PropertyChangeEvent(this, GUIController.LOADING_STEP, null, -1));
			//Point2D resourcePosition = new Point2D(RandomGenerator.getRandom((int) getX(), (int) getWidth()), RandomGenerator.getRandom((int) getY(), (int) getHeight()));
			resources.add(new Resource(level.generateNewResourceID(), type, level, this));
		}
		return resources;
	}

	@Override
	public Point2D calcRandomLevelPosition(AbstractLevel level) {
//		double widthCast = (int) level.getLevelBorderPolygon().getBounds().getWidth()/LevelView.RASTER_SIZE;
//		double heightCast = (int) level.getLevelBorderPolygon().getBounds().getHeight()/LevelView.RASTER_SIZE;
//		double width, height;
//
//
//		if(widthCast < level.getLevelBorderPolygon().getBounds().getWidth()/LevelView.RASTER_SIZE) {
//			width = ((int) heightCast)+1;
//		} else {
//			width = (int) widthCast;
//		}
//		if(heightCast < level.getLevelBorderPolygon().getBounds().getHeight()/LevelView.RASTER_SIZE) {
//			height = ((int) heightCast)+1;
//		} else {
//			height = (int) heightCast;
//		}

//		int index = 0;
//
//		while(true) {
//			try {
//				index = RandomGenerator.getRandom(0, width * height);
//			} catch (InvalidStateException ex) {
//				logger.error("Could not generate resource position.", ex);
//			}
//
//			int x = index % width;
//			int y = index / width;
//
//			int xLevelPosition = (int) level.getX()+(x*LevelView.RASTER_SIZE+LevelView.RASTER_SIZE/2);
//			int yLevelPosition = (int) level.getY()+(y*LevelView.RASTER_SIZE+LevelView.RASTER_SIZE/2);
//
//			Rectangle2D rasterLevelRectangle = new Rectangle2D.Double(xLevelPosition-LevelView.RASTER_SIZE/2, yLevelPosition-LevelView.RASTER_SIZE/2, LevelView.RASTER_SIZE, LevelView.RASTER_SIZE);
//			Rectangle2D agentBoundsRectangle = new Rectangle2D.Double(xLevelPosition-Agent.AGENT_SIZE, yLevelPosition-Agent.AGENT_SIZE, Agent.AGENT_SIZE*2, Agent.AGENT_SIZE*2);
//
//			boolean partOfWall = level.containsWall(rasterLevelRectangle);
//			boolean nextToWall = level.containsWall(agentBoundsRectangle);
//
//			if(!(partOfWall || nextToWall)) {
//				return new Point2D(xLevelPosition, yLevelPosition);
//			}
//		}
		int resourceXPos = 0, resourceYPos = 0;
		int tries = 0;
		while(true) {
			try {
				resourceXPos = RandomGenerator.getRandom((int) getMinX(), (int) getMaxX());
				resourceYPos = RandomGenerator.getRandom((int) getMinY(), (int) getMaxY());
			} catch (InvalidStateException ex) {
				logger.warn("Could not place Resource["+type+"]! Bad resoure bounds!", ex);
				break;
			}
			if(!level.containsWall(new Rectangle(resourceXPos-Resource.RESOURCE_SIZE, resourceYPos-Resource.RESOURCE_SIZE, Resource.RESOURCE_SIZE*2, Resource.RESOURCE_SIZE*2))) {
				break;
			} else {
				tries++;
				if(tries > 1000) {
					logger.warn("Could not place Resource["+type+"]! Bad map design!");
					break;
				}
			}
		}
		return new Point2D(resourceXPos, resourceYPos);
	}
    
    @Override
    public void translateIntoBase(final Point2D base) {
        x -= base.getX();
		y -= base.getY();
    }
    
	@Override
	public int getResourceCount() {
		return resourceCount;
	}
}
