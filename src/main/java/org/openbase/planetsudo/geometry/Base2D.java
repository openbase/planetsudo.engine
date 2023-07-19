/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.geometry;

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

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class Base2D {
	private Direction2D direction;
	private Point2D point;

	public Base2D(double x, double y, int alpha) {
		this.direction = new Direction2D(alpha);
		this.point = new Point2D(x,y);
	}

	public Base2D(Point2D point, Direction2D direction) {
		this.direction = direction;
		this.point = point;
	}

	public Direction2D getDirection() {
		return direction;
	}

	public Point2D getPoint() {
		return point;
	}
    
    public void translateIntoBase(final Point2D base) {
        point.translateIntoBase(base);
    }
}
