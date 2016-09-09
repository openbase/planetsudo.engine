package org.openbase.planetsudo.view.level.levelobjects;

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

    private static final Logger logger = LoggerFactory.getLogger(TowerPanel.class);
    

    public TowerPanel(final Tower tower, final LevelPanel parentPanel) {
        super(tower, tower.getPolygon(), getImageURI(tower.getType()), parentPanel, DrawLayer.BACKGROUND);
        logger.info("Create " + this);
        tower.addPropertyChangeListener(this);
    }

    private static String getImageURI(TowerType type) {
        return GameObjectImages.Tower.imagesURL;
    }
    private Graphics2D gg2;
    private Direction2D direction;

    @Override
    protected void paintComponent(Graphics2D g2, Graphics2D gl) {
        // only paint if erected
        if(!resource.isErected()) {
            return;
        }
        
        boundingBox = resource.getBounds();
        direction = resource.getDirection();
        gg2 = (Graphics2D) g2.create();
        paintImageRotated(direction, gg2);
        gg2.dispose();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
//        if (evt.getPropertyName().equals(Tower.REMOVE_TOWER)) {
//            if (((Tower) evt.getNewValue()).equals(resource)) {
//                parentResourcePanel.removeChild(this);
//            }
//        }
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
