/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.level;

import data.Point2D;
import exceptions.NotValidException;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import logging.Logger;
import math.RandomGenerator;
import planetmesserlost.levelobjects.Resource;

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
			try {
				Point2D resourcePosition = new Point2D(RandomGenerator.getRandom((int) getX(), (int) getWidth()), RandomGenerator.getRandom((int) getY(), (int) getHeight()));
				resources.add(new Resource(level.generateNewResourceID(), Resource.ResourceType.NORMAL, level, resourcePosition));
			} catch (NotValidException ex) {
				Logger.error(this, "Could not place resource!", ex);
			}
		}
		return resources;
	}
}
