/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.geometry;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 *
 * @author divine
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

