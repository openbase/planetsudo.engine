/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.level.levelobjects;

import concepts.Manageable;
import data.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import de.dc.util.logging.Logger;
import planetsudo.level.AbstractLevel;
import planetsudo.level.LevelView;

/**
 *
 * @author divine
 */
public abstract class AbstractLevelObject implements Manageable {

	public enum ObjectShape {Oval, Rec};
	public final static boolean STATIC_OBJECT = true;
	public final static boolean DYNAMIC_OBJECT = false;

	protected final int id;
	protected final String name;
	protected final AbstractLevel level;
	protected LevelView levelView;
	protected Point2D position;
	protected final ObjectShape shape;
	protected double width;
	protected double height;
	protected final PropertyChangeSupport changes;

	private boolean isStatic;

	public AbstractLevelObject(int id, String name, boolean isStatic, AbstractLevel level, Point2D position, double width, double height, ObjectShape shape) {
		this.id = id;
		this.name = name;
		this.level = level;
		this.position = position;
		this.width = width;
		this.height = height;
		this.shape = shape;
		if(!(this instanceof Resource)) {
			this.levelView = new LevelView(this);
		}
		this.isStatic = DYNAMIC_OBJECT;
		this.changes = new PropertyChangeSupport(this);
		setStatic(isStatic);
	}

	@ Override
	public int getID() {
		return id;
	}

	public AbstractLevel getLevel() {
		return level;
	}

	public LevelView getLevelView() {
		return levelView;
	}

	public String getName() {
		return name;
	}

	public Point2D getPosition() {
		return position;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean enable) {
		if(enable) {
			if(isStatic()) {
				return;
			}
			if(levelView != null) {
				levelView.updateObjectMovement();
			}
		}
		isStatic = enable;
	}

	public Rectangle2D.Double getBounds() {
		return new Rectangle2D.Double(	(int)position.getX()-(width/2),
										(int)position.getY()-(height/2),
										width,
										height);
	}

	protected abstract void reset();

	public double getHeight() {
		return height;
	}

	public ObjectShape getShape() {
		return shape;
	}

	public double getWidth() {
		return width;
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
	    changes.addPropertyChangeListener(l);
	    Logger.debug(this, "Add "+l.getClass()+" as new PropertyChangeListener.");
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
	    changes.removePropertyChangeListener(l);
	    Logger.debug(this, "Remove PropertyChangeListener "+l.getClass()+".");
	}

	@ Override
	public String toString() {
		return name;
	}
}
