/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.level;

import data.Point2D;
import exceptions.NotValidException;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import logging.Logger;
import math.RandomGenerator;
import planetsudo.level.levelobjects.Resource;
import planetsudo.level.levelobjects.Resource.ResourceType;
import planetsudo.main.GUIController;

/**
 *
 * @author divine
 */
public class ResourceRandomRectangle extends Rectangle2D.Double implements ResourcePlacement {
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
//			} catch (NotValidException ex) {
//				Logger.error(this, "Could not generate resource position.", ex);
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
			} catch (NotValidException ex) {
				Logger.warn(this, "Could not place Resource["+type+"]! Bad resoure bounds!", ex);
			}
			if(!level.containsWall(new Rectangle(resourceXPos-Resource.RESOURCE_SIZE, resourceYPos-Resource.RESOURCE_SIZE, Resource.RESOURCE_SIZE*2, Resource.RESOURCE_SIZE*2))) {
				break;
			} else {
				tries++;
				if(tries > 1000) {
					Logger.warn(this, "Could not place Resource["+type+"]! Bad map design!");
				}
			}
		}
		return new Point2D(resourceXPos, resourceYPos);
	}

	@Override
	public int getResourceCount() {
		return resourceCount;
	}
}
