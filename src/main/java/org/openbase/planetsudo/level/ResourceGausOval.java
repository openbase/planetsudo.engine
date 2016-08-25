/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.level;

import org.openbase.util.data.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import org.openbase.planetsudo.level.levelobjects.Resource;

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
			resources.add(new Resource(level.generateNewResourceID(), Resource.ResourceType.Normal, level, new Point2D(getCenterX(), getCenterY())));
		}
		return resources;
	}

	@Override
	public int getResourceCount() {
		return resourceCount;
	}

	@Override
	public Point2D calcRandomLevelPosition(AbstractLevel level) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
    
    @Override
    public void translateIntoBase(final Point2D base) {
        x -= base.getX();
		y -= base.getY();
    }
}
