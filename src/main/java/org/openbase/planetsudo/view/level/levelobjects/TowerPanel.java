package org.openbase.planetsudo.view.level.levelobjects;

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
import java.awt.Color;
import org.openbase.planetsudo.game.GameObjectImages;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.slf4j.Logger;
import java.awt.event.MouseEvent;
import org.openbase.planetsudo.view.level.LevelPanel;
import org.openbase.planetsudo.geometry.Direction2D;
import org.openbase.planetsudo.level.levelobjects.Tower;
import org.openbase.planetsudo.level.levelobjects.Tower.TowerType;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class TowerPanel extends AbstractLevelObjectPanel<Tower, LevelPanel> implements PropertyChangeListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TowerPanel.class);

    private TowerTopPanel towerTopPanel;
    private final Direction2D towerDirection2D;
    private final Color teamColor;

    public TowerPanel(final Tower tower, final LevelPanel parentPanel) {
        super(tower, tower.getPolygon(), getImageURI(tower.getType()), parentPanel, DrawLayer.BACKGROUND);
        LOGGER.info("Create " + this);
        tower.addPropertyChangeListener(this);
        this.towerDirection2D = new Direction2D(0);
        this.teamColor = tower.getMothership().getTeam().getTeamColor();
    }

    private static String getImageURI(TowerType type) {
        return GameObjectImages.Tower.imagesURL;
    }
    private Graphics2D gg2;
    private Direction2D direction;

    @Override
    protected void paintComponent(Graphics2D g2, Graphics2D gl) {
        // only paint if erected
        if (!resource.isErected()) {
            return;
        }

        boundingBox = resource.getBounds();
        direction = resource.getDirection();

        gg2 = (Graphics2D) g2.create();
        gg2.setColor(teamColor);
        gg2.fillOval((int) boundingBox.getX(), (int) boundingBox.getY(), (int) boundingBox.getWidth(), (int) boundingBox.getHeight());
        paintImageRotated(towerDirection2D, gg2);
        gg2.dispose();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(Tower.TOWER_ERECT)) {
            if (((Tower) evt.getNewValue()).equals(resource)) {
                if (towerTopPanel == null) {
                    towerTopPanel = new TowerTopPanel(resource, this);
                }
            }
        } else if (evt.getPropertyName().equals(Tower.TOWER_DISMANTLE)) {
            if (((Tower) evt.getNewValue()).equals(resource)) {
                if (towerTopPanel != null) {
                    removeChild(towerTopPanel);
                    towerTopPanel = null;
                }
            }
        }
    }

    @Override
    public boolean isFocusable() {
        return false;
    }

    @Override
    protected void notifyMouseEntered() {
    }

    @Override
    protected void notifyMouseClicked(MouseEvent evt) {
    }
}
