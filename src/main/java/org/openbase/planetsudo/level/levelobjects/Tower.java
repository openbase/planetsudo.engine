package org.openbase.planetsudo.level.levelobjects;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2016 openbase.org
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
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.InstantiationException;
import org.openbase.jul.iface.Shutdownable;
import org.openbase.jul.visual.swing.engine.draw2d.AbstractResourcePanel;
import org.openbase.planetsudo.geometry.Direction2D;
import org.openbase.planetsudo.geometry.Point2D;
import org.slf4j.Logger;
import org.openbase.planetsudo.level.AbstractLevel;
import org.openbase.planetsudo.level.ResourcePlacement;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class Tower extends AbstractLevelObject implements Shutdownable {

    public final static int SIZE = 100;
    public final static String REMOVE_TOWER = "remove tower";

    public enum TowerType {

        DefenceTower, ObservationTower
    };

    private static final Logger logger = LoggerFactory.getLogger(Tower.class);

    private final TowerType type;
    private final Agent commander;
    private final Object TOWER_LOCK = new Object();
    private ResourcePlacement placement;
    
    private int shield;
    private int fuel;
    private final Direction2D direction;
    private boolean attacked;
    private boolean placed;

    public Tower(final int id, final TowerType type, final AbstractLevel level, final Agent commander) throws InstantiationException {
        super(id, Tower.class.getSimpleName() + "[" + id + "]", AbstractResourcePanel.ObjectType.Static, level, commander.getPosition(), SIZE, SIZE, ObjectShape.Rec);
        try {
            this.type = type;
            if (!commander.isCommander()) {
                commander.kill();
                throw new CouldNotPerformException("Only the commander can place a tower!");
            }
            this.commander = commander;
            this.position = new Point2D(commander.getPosition());
            this.direction = commander.getDirection();
            this.placed = true;
            this.reset();
            logger.info("Create " + this);
        } catch (CouldNotPerformException ex) {
            throw new org.openbase.jul.exception.InstantiationException(this, ex);
        }
    }

    public ResourcePlacement getPlacement() {
        return placement;
    }

    public Agent getCommander() {
        return commander;
    }

    @Override
    public void reset() {
        fuel = 1000;
        placed = true;
    }

    public TowerType getType() {
        return type;
    }

    public int getShield() {
        return shield;
    }

    public int getFuel() {
        return fuel;
    }

    public boolean isAttacked() {
        return attacked;
    }

    public Direction2D getDirection() {
        return direction;
    }
    
    public boolean seeTower(final Agent agent) {
		if(!placed) {
			return false;
		}

		return getBounds().intersects(agent.getBounds());
	}
    
    @Override
    public void shutdown() {
        changes.firePropertyChange(REMOVE_TOWER, null, this);
    }
}
