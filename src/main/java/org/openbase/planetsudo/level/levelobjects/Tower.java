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
import org.openbase.jul.visual.swing.engine.draw2d.AbstractResourcePanel;
import org.slf4j.Logger;
import org.openbase.planetsudo.level.AbstractLevel;
import org.openbase.planetsudo.level.ResourcePlacement;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a
 */
public class Tower extends AbstractLevelObject {

    public final static int SIZE = 100;

    public enum TowerType {

        DefenceTower, ObservationTower
    };

    private static final Logger logger = LoggerFactory.getLogger(Tower.class);

    private final TowerType type;
    private final Agent commander;
    private final Object TOWER_LOCK = new Object();
    private ResourcePlacement placement;

    public Tower(final int id, final TowerType type, final AbstractLevel level, final Agent commander) {
        super(id, Tower.class.getSimpleName() + "[" + id + "]", AbstractResourcePanel.ObjectType.Static, level, commander.getPosition(), SIZE, SIZE, ObjectShape.Rec);
        this.type = type;
        this.commander = commander;
        logger.info("Create " + this);
    }

    public ResourcePlacement getPlacement() {
        return placement;
    }

    @Override
    public void reset() {
    }

    public TowerType getType() {
        return type;
    }
}
