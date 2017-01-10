package org.openbase.planetsudo.geometry;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2017 openbase.org
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
;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 * Rebuild because all functions are already supported from the Retange2D by the default swing framework
 */
public class Direction2D {

	public final static int UP = 270;
	public final static int DOWN = 90;
	public final static int LEFT = 180;
	public final static int RIGHT = 0;

	private final Point2D direction;
	private int alpha;


	public Direction2D(int alpha) {
		direction = new Point2D();
		setAngle(alpha);
	}

	public Point2D getVector() {
		return direction;
	}

	public int getAngle() {
		return alpha;
	}
	
	public void setAngle(int alpha) {
		this.alpha = alpha % 360;
		//Logger.info(this, "setAngle: "+alpha+" => "+this.alpha);
		calculateDirection();
	}
	
	private void calculateDirection() {
		double radianAlpha = Math.toRadians(alpha);
		direction.setLocation(Math.cos(radianAlpha), Math.sin(radianAlpha));
		//Logger.info(this, "CalcDirection out of alpha "+alpha+": "+direction);
	}

	public void invert() {
		setAngle(alpha+180);
	}

	public void turnTo(Point2D source, Point2D destination) {
		setAngle((int) Math.toDegrees(Math.atan2(destination.xy[1] - source.xy[1], destination.xy[0] - source.xy[0])));
		//Logger.info(this, "Base:"+source+" Dest:"+destination+" Angle:"+(int) Math.toDegrees(Math.atan2(destination.xy[1] - source.xy[1], destination.xy[0] - source.xy[0])));
	}

	public Point2D translate(Point2D point, int lenght) {
		point.translate(this, lenght);
		return point;
	}
}
