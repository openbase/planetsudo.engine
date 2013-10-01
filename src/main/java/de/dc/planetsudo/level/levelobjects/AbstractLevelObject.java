/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.level.levelobjects;

import de.dc.planetsudo.game.AbstractGameObject;
import de.dc.util.data.Point2D;
import de.dc.util.interfaces.Manageable;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import de.dc.util.logging.Logger;
import de.dc.planetsudo.level.AbstractLevel;
import de.dc.planetsudo.level.LevelView;
import de.dc.util.view.engine.draw2d.AbstractResourcePanel.ObjectType;
import java.awt.Polygon;

/**
 *
 * @author divine
 */
public abstract class AbstractLevelObject extends AbstractGameObject implements Manageable {

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
	private ObjectType objectType;

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
		this.objectType = ObjectType.Dynamic;

		if (!(this instanceof Resource)) {
			this.levelView = new LevelView(this);
		}
		this.setObjectType(objectType);
	}

	@ Override
	public int getId() {
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
	
	public boolean isObjectType(ObjectType type) {
		return objectType == type;
	}

	public ObjectType getObjectType() {
		return objectType;
	}

	public final void setObjectType(ObjectType objectType) {
		if (this.objectType == objectType) {
			return;
		}
		if (levelView != null) {
			levelView.updateObjectMovement();
		}
		this.objectType = objectType;
		changes.firePropertyChange(OBJECT_TYPE_UPDATE, null, objectType);
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
		Logger.debug(this, "Add " + l.getClass() + " as new PropertyChangeListener.");
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		changes.removePropertyChangeListener(l);
		Logger.debug(this, "Remove PropertyChangeListener " + l.getClass() + ".");
	}

	@ Override
	public String toString() {
		return name;
	}
}
