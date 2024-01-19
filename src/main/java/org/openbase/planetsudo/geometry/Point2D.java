/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.geometry;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2024 openbase.org
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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 * 
 */
public class Point2D extends java.awt.geom.Point2D implements Cloneable {

	public double[] xy;

	public Point2D() {
		this(0,0);
	}

	public Point2D(final double x, final double y) {
		this.xy = new double[2];
		this.xy[0] = x;
		this.xy[1] = y;
	}

	public Point2D(final Point2D point) {
		this(point.xy[0], point.xy[1]);
	}


	@Override
	public double getX() {
		return xy[0];
	}

	@Override
	public double getY() {
		return xy[1];
	}

	public void setX(final double x) {
		this.xy[0] = x;
	}

	public void setY(final double y) {
		this.xy[1] = y;
	}

	@Override
	public void setLocation(final double x, final double y) {
		this.xy[0] = x;
		this.xy[1] = y;
	}

	@Override
	public Point2D clone() {
		return new Point2D(xy[0], xy[1]);
	}

	public void translateIntoBase(final Point2D base) {
        xy[0] -= base.getX();
		xy[1] -= base.getY();
    }
	public void translate(final Direction2D direction, final int lenght) {
		xy[0] += direction.getVector().getX()*lenght;
		xy[1] += direction.getVector().getY()*lenght;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
				// if deriving: appendSuper(super.hashCode()).
				append(xy[0]).
				append(xy[1]).
				toHashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}

		final  Point2D point = (Point2D) obj;
		// if deriving: appendSuper(super.equals(obj)).
		return new EqualsBuilder().
				append(xy[0], point.xy[0]).
				append(xy[1], point.xy[1]).
				isEquals();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()+"["+xy[0]+"|"+xy[1]+"]";
	}
}

