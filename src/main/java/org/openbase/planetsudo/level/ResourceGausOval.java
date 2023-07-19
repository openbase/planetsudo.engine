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

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import org.openbase.planetsudo.geometry.Point2D;
import org.openbase.planetsudo.level.levelobjects.Resource;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
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
