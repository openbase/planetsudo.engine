/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.level.levelobjects;

import de.dc.planetsudo.game.Team;
import de.dc.planetsudo.level.AbstractLevel;
import de.dc.util.data.Point2D;
import de.dc.util.exceptions.CouldNotPerformException;
import de.dc.util.view.engine.draw2d.AbstractResourcePanel.ObjectType;
import javax.swing.text.Position;

/**
 *
 * @author divine
 */
public class TeamMarker extends AbstractLevelObject {

	public static final double MARKER_SIZE = 50;
	private final Object MARKER_LOCK = new Object();
	private boolean placed;

	public TeamMarker(final Team team, final AbstractLevel level) {
		super(0, team.getName() + "Marker", ObjectType.Static, level, new Point2D(), MARKER_SIZE, MARKER_SIZE, ObjectShape.Oval);
		this.placed = false;
	}

	public void place(final Point2D position) {
		synchronized (MARKER_LOCK) {
			this.position = position;
			placed = true;
		}
	}

	public void clear() {
		synchronized (MARKER_LOCK) {
			placed = false;
		}
	}

	public boolean isPlaced() {
		return placed;
	}

	public TeamMarker getMarker() throws CouldNotPerformException {
		synchronized (MARKER_LOCK) {
			if (!placed) {
				throw new CouldNotPerformException("Marker not placed!");
			}
			return this;
		}
	}

	@Override
	protected void reset() {
		clear();
	}
}
