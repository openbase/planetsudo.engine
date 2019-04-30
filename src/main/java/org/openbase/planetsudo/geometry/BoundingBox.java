/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.geometry;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2019 openbase.org
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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
public class BoundingBox {

	private int maxX, maxY, minX, minY;

	public BoundingBox() {
		this.maxX = Integer.MIN_VALUE;
		this.maxY = Integer.MIN_VALUE;
		this.minX = Integer.MAX_VALUE;
		this.minY = Integer.MAX_VALUE;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getMinX() {
		return minX;
	}

	public int getMinY() {
		return minY;
	}

	public int getCenterX() {
		return minX + ((maxX - minX) / 2);
	}

	public int getCenterY() {
		return minY + ((maxY - minY) / 2);
	}

	public Point getCenter() {
		return new Point(getCenterX(),getCenterY());
	}

	public int getHeight() {
		return (checkYValidation() ? maxY - minY : 0);
	}
	public int getWidth() {
		return (checkXValidation() ? maxX - minX : 0);
	}

	public void insertX(int x) {
		minX = Math.min(minX, x);
		maxX = Math.max(maxX, x);
	}

	public void insertY(int y) {
		minY = Math.min(minY, y);
		maxY = Math.max(maxY, y);
	}

	public void insertBoundingBox(BoundingBox boundingBox) {
		this.maxX = Math.max(maxX,boundingBox.getMaxX());
		this.maxY = Math.max(maxY,boundingBox.getMaxY());
		this.minX = Math.min(minX,boundingBox.getMinX());
		this.minY = Math.min(minY,boundingBox.getMinY());
	}

	public boolean checkXValidation() {
		return minX < maxX;
	}

	public boolean checkYValidation() {
		return minY < maxY;
	}

	public void paint(Graphics2D g, AffineTransform trans) {
		if(!(checkXValidation() || checkYValidation())) {
			return;
		}

		Graphics2D g2 = (Graphics2D) g.create();
		g2.transform(trans);
		g2.setColor(Color.MAGENTA);
		g2.drawRect(minX, minY, maxX - minX, maxY - minY);
		g2.setColor(Color.ORANGE);
		g2.drawLine(getCenterX(), getCenterY(), minX, minY);
		g2.drawLine(getCenterX(), getCenterY(), maxX, maxY);
		g2.drawLine(getCenterX(), getCenterY(), maxX, minY);
		g2.drawLine(getCenterX(), getCenterY(), minX, maxY);
		g2.dispose();
	}
	
	public Dimension getDimension() {
		return new Dimension(getWidth(), getHeight());
	}
}
