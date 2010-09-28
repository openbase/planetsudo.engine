/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.level;

import data.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import planetsudo.level.levelobjects.Resource;

/**
 *
 * @author divine
 */
public class ResourceGausOval extends Rectangle2D.Double implements ResourcePlacement {

	private int resourceCount;
	public ResourceGausOval(int x, int y, int width, int height, int resourceCount) {
		super((int)x, (int)y, (int)width, (int)height);
		this.resourceCount = resourceCount;
	}

	@Override
	public ArrayList<Resource> getResources(AbstractLevel level) {
		ArrayList<Resource> resources = new ArrayList<Resource>();
		for(int i=0; i<resourceCount;i++) {
			resources.add(new Resource(level.generateNewResourceID(), Resource.ResourceType.NORMAL, level, new Point2D(getCenterX(), getCenterY())));
		}
		return resources;
	}
}
