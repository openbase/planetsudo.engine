/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.geometry;

/**
 *
 * @author divine
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
