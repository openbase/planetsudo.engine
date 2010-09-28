/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.level;

import data.Point2D;
import exceptions.NotValidException;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import logging.Logger;
import math.RandomGenerator;
import planetsudo.level.levelobjects.Agent;
import planetsudo.level.levelobjects.Resource;

/**
 *
 * @author divine
 */
public class ResourceRandomRectangle extends Rectangle2D.Double implements ResourcePlacement {
	private int resourceCount;
	public ResourceRandomRectangle(int x, int y, int width, int height, int resourceCount) {
		super((int)x, (int)y, (int)width, (int)height);
		this.resourceCount = resourceCount;
	}

	@Override
	public ArrayList<Resource> getResources(AbstractLevel level) {
		ArrayList<Resource> resources = new ArrayList<Resource>();
		for(int i=0; i<resourceCount;i++) {
			Point2D resourcePosition = calcRandomLevelPosition(level);
			//Point2D resourcePosition = new Point2D(RandomGenerator.getRandom((int) getX(), (int) getWidth()), RandomGenerator.getRandom((int) getY(), (int) getHeight()));
			resources.add(new Resource(level.generateNewResourceID(), Resource.ResourceType.NORMAL, level, resourcePosition));
		}
		return resources;
	}

	public Point2D calcRandomLevelPosition(AbstractLevel level) {
		double widthCast = (int) level.getLevelBorderPolygon().getBounds().getWidth()/LevelView.RASTER_SIZE;
		double heightCast = (int) level.getLevelBorderPolygon().getBounds().getHeight()/LevelView.RASTER_SIZE;
		int width, height;


		if(widthCast < level.getLevelBorderPolygon().getBounds().getWidth()/LevelView.RASTER_SIZE) {
			width = ((int) heightCast)+1;
		} else {
			width = (int) widthCast;
		}
		if(heightCast < level.getLevelBorderPolygon().getBounds().getHeight()/LevelView.RASTER_SIZE) {
			height = ((int) heightCast)+1;
		} else {
			height = (int) heightCast;
		}

		int index = 0;

		while(true) {
			try {
				index = RandomGenerator.getRandom(0, width * height);
			} catch (NotValidException ex) {
				Logger.error(this, "Could not generate resource position.", ex);
			}

			int x = index % width;
			int y = index / width;

			int xLevelPosition = (int) level.getX()+(x*LevelView.RASTER_SIZE+LevelView.RASTER_SIZE/2);
			int yLevelPosition = (int) level.getY()+(y*LevelView.RASTER_SIZE+LevelView.RASTER_SIZE/2);

			Rectangle2D rasterLevelRectangle = new Rectangle2D.Double(xLevelPosition-LevelView.RASTER_SIZE/2, yLevelPosition-LevelView.RASTER_SIZE/2, LevelView.RASTER_SIZE, LevelView.RASTER_SIZE);
			Rectangle2D agentBoundsRectangle = new Rectangle2D.Double(xLevelPosition-Agent.AGENT_SIZE, yLevelPosition-Agent.AGENT_SIZE, Agent.AGENT_SIZE*2, Agent.AGENT_SIZE*2);

			boolean partOfWall = !level.getLevelBorderPolygon().contains(rasterLevelRectangle);
			boolean nextToWall = !level.getLevelBorderPolygon().contains(agentBoundsRectangle);

			if(!(partOfWall || nextToWall)) {
				return new Point2D(xLevelPosition, yLevelPosition);
			}
		}
	}
}
