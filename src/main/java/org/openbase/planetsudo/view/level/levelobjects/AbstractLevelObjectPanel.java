/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.view.level.levelobjects;

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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.util.logging.Level;
import org.openbase.planetsudo.level.levelobjects.AbstractLevelObject;
import org.openbase.planetsudo.view.game.AbstractGameObjectPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import org.openbase.jul.exception.InvalidStateException;
import org.openbase.jul.visual.swing.engine.draw2d.ResourceDisplayPanel;
import org.openbase.planetsudo.geometry.Direction2D;
import org.openbase.planetsudo.util.RandomGenerator;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public abstract class AbstractLevelObjectPanel<R extends AbstractLevelObject, PRP extends AbstractGameObjectPanel> extends AbstractGameObjectPanel<R, PRP> {

	protected static boolean animationFlag;
	static {
		new Timer(200, new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				animationFlag = !animationFlag;
			}
		}).start();
	}

	public AbstractLevelObjectPanel(final R resource, final Polygon placementPolygon, final String imageURI, final PRP parentResourcePanel, final DrawLayer drawLayer) {
		super(resource, placementPolygon, ObjectType.Static, imageURI, parentResourcePanel, drawLayer);
//		resource.addPropertyChangeListener(new PropertyChangeListener() {
//			@Override
//			public void propertyChange(final PropertyChangeEvent evt) {
//				if (evt.getPropertyName().equals(AbstractLevelObject.OBJECT_TYPE_UPDATE)) {
//					setObjectType(resource.getObjectType());
//				}
//			}
//		});
	}

	public AbstractLevelObjectPanel(final R resource, final Polygon placementPolygon, final String imageURI, final ResourceDisplayPanel parentPanel) {
		super(resource, placementPolygon, ObjectType.Dynamic, imageURI, parentPanel);
//		resource.addPropertyChangeListener(new PropertyChangeListener() {
//			@Override
//			public void propertyChange(final PropertyChangeEvent evt) {
//				if (evt.getPropertyName().equals(AbstractLevelObject.OBJECT_TYPE_UPDATE)) {
//					setObjectType(resource.getObjectType());
//				}
//			}
//		});
	}

	protected void paintShape(final Graphics2D g2) {
		switch (resource.getShape()) {
			case Oval:
				g2.fillOval((int) resource.getPosition().getX() - ((int) resource.getWidth() / 2), (int) resource.getPosition().getY() - ((int) resource.getHeight() / 2), (int) resource.getWidth(), (int) resource.getHeight());
				break;
			case Rec:
				g2.fillRect((int) resource.getPosition().getX() - ((int) resource.getWidth() / 2), (int) resource.getPosition().getY() - ((int) resource.getHeight() / 2), (int) resource.getWidth(), (int) resource.getHeight());
				break;
		}
	}

	protected void paintImageRotated(Direction2D direction, Graphics2D g2) {
		AffineTransform transformation = getSkaleImageToBoundsTransformation();
		transformation = rotateTransformation(direction, image.getWidth(), image.getHeight(), transformation);
		g2.drawImage(image, transformation, parentPanel);
	}
	private Dimension2D dimension2D = new Dimension();

	public AffineTransform rotateTransformation(Direction2D direction, double width, double height, AffineTransform affineTransform) {
		dimension2D.setSize(width, height);
		return getRotationTransformation(direction, dimension2D, affineTransform);
	}

	public AffineTransform getRotationTransformation(Direction2D direction, Dimension2D dimension, AffineTransform affineTransform) {
		affineTransform.rotate(Math.toRadians(direction.getAngle() + 90),
				dimension.getWidth() / 2.0,
				dimension.getHeight() / 2.0);
		return affineTransform;
	}

	public AffineTransform getBoundsTransformation(final Direction2D direction) {
		return rotateTransformation(direction, boundingBox.getWidth(), boundingBox.getHeight(), getBoundsTransformation());
	}

	public AffineTransform getBoundsTransformation() {
		return new AffineTransform(
				1, 0,
				0, 1,
				boundingBox.getX(), boundingBox.getY());
	}

	public AffineTransform getSkaleImageToBoundsTransformation() {
		assert boundingBox != null;
		assert image != null;
		return new AffineTransform(
				boundingBox.getWidth() / image.getWidth(), 0,
				0, boundingBox.getHeight() / image.getHeight(),
				boundingBox.getX(), boundingBox.getY());
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + resource.getId() + "]";
	}
	private int xa1 = (int) resource.getPosition().getX();
	private int xa2 = (int) resource.getPosition().getX();
	private int xa3 = (int) resource.getPosition().getX();
	private int ya1 = (int) resource.getPosition().getY();
	private int ya2 = (int) resource.getPosition().getY();
	private int ya3 = (int) resource.getPosition().getY();
	private int xb1 = (int) resource.getPosition().getX();
	private int xb2 = (int) resource.getPosition().getX();
	private int xb3 = (int) resource.getPosition().getX();
	private int yb1 = (int) resource.getPosition().getY();
	private int yb2 = (int) resource.getPosition().getY();
	private int yb3 = (int) resource.getPosition().getY();

	protected void paintExplosion(Graphics2D g22) {
		try {
			xa3 = xa2;
			ya3 = ya2;
			xa2 = xa1;
			ya2 = ya1;
			xa1 = xb3;
			ya1 = yb3;
			xb3 = xb2;
			yb3 = yb2;
			xb2 = xb1;
			yb2 = yb1;
			xb1 = RandomGenerator.getRandom((int) boundingBox.getMinX(), (int) boundingBox.getMaxX());
			yb1 = RandomGenerator.getRandom((int) boundingBox.getMinY(), (int) boundingBox.getMaxY());

			g22.setColor(Color.YELLOW);
			g22.fillOval(xa3 - 9, ya3 - 9, 18, 18);
			g22.setColor(Color.ORANGE);
			g22.fillOval(xa1 - 6, ya1 - 6, 12, 12);
			g22.fillOval(xa2 - 6, ya2 - 6, 12, 12);
			g22.fillOval(xa3 - 6, ya3 - 6, 12, 12);
			g22.setColor(Color.RED);
			//g22.drawOval(xa3-3, ya3-3, 6, 6);
			g22.fillOval(xa2 - 3, ya2 - 3, 6, 6);
			g22.fillOval(xa1 - 3, ya1 - 3, 6, 6);

			g22.setColor(Color.YELLOW);
			g22.fillOval(xb3 - 9, yb3 - 9, 18, 18);
			g22.setColor(Color.ORANGE);
			g22.fillOval(xb1 - 6, yb1 - 6, 12, 12);
			g22.fillOval(xb2 - 6, yb2 - 6, 12, 12);
			g22.fillOval(xb3 - 6, yb3 - 6, 12, 12);
			g22.setColor(Color.RED);
			//g22.drawOval(xb3-3, yb3-3, 6, 6);
			g22.fillOval(xb2 - 3, yb2 - 3, 6, 6);
			g22.fillOval(xb1 - 3, yb1 - 3, 6, 6);

		} catch (InvalidStateException ex) {
			java.util.logging.Logger.getLogger(AbstractLevelObjectPanel.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
