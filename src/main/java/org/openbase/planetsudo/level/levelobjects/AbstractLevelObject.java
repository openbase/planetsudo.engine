package org.openbase.planetsudo.level.levelobjects;

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

import org.openbase.planetsudo.game.AbstractGameObject;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import org.slf4j.Logger;
import org.openbase.planetsudo.level.AbstractLevel;
import org.openbase.planetsudo.level.LevelView;
import java.awt.Polygon;
import org.openbase.jul.iface.Identifiable;
import org.openbase.jul.visual.swing.engine.draw2d.AbstractResourcePanel.ObjectType;
import org.openbase.planetsudo.geometry.Point2D;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public abstract class AbstractLevelObject extends AbstractGameObject implements Identifiable<Integer> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractLevelObject.class);
    
	public enum ObjectShape {

		Oval, Rec
	};
	public static final String OBJECT_TYPE_UPDATE = "ObjectTypeUpdate";
	protected final int id;
	protected final String name;
	protected final AbstractLevel level;
	protected LevelView levelView;
	protected Point2D position;
	protected final ObjectShape shape;
	protected double width;
	protected double height;
	protected final PropertyChangeSupport changes;

	public AbstractLevelObject() {
		this(0, "Level", ObjectType.Static, null, new Point2D(), 100, 100, null);
	}

	public AbstractLevelObject(int id, String name, ObjectType objectType, AbstractLevel level, Point2D position, double width, double height, ObjectShape shape) {
		this.id = id;
		this.name = name;
		this.level = level;
		this.position = position;
		this.width = width;
		this.height = height;
		this.shape = shape;
		this.changes = new PropertyChangeSupport(this);

		if (!(this instanceof Resource)) {
			this.levelView = new LevelView(this);
		}
	}

	@ Override
	public Integer getId() {
		return id;
	}

	public AbstractLevel getLevel() {
		return level;
	}

	public LevelView getLevelView() {
		return levelView;
	}

	@Override
	public String getName() {
		return name;
	}

	public Point2D getPosition() {
		return position;
	}
	
	public Rectangle2D.Double getBounds() {
		return new Rectangle2D.Double((int) position.getX() - (width / 2),
				(int) position.getY() - (height / 2),
				width,
				height);
	}

	protected abstract void reset();

	public double getHeight() {
		return height;
	}

	public Polygon getPolygon() {
		Polygon polygon = new Polygon();
		polygon.addPoint((int) (position.getX() - width / 2), (int) (position.getY() - height / 2));
		polygon.addPoint((int) (position.getX() + width / 2), (int) (position.getY() - height / 2));
		polygon.addPoint((int) (position.getX() - width / 2), (int) (position.getY() + height / 2));
		polygon.addPoint((int) (position.getX() + width / 2), (int) (position.getY() + height / 2));
		return polygon;
	}

	public ObjectShape getShape() {
		return shape;
	}

	public double getWidth() {
		return width;
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
		changes.addPropertyChangeListener(l);
		logger.debug("Add " + l.getClass() + " as new PropertyChangeListener.");
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		changes.removePropertyChangeListener(l);
		logger.debug("Remove PropertyChangeListener " + l.getClass() + ".");
	}

	@ Override
	public String toString() {
		return name;
	}
}
