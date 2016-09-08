/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.view.level;

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
import java.beans.PropertyChangeEvent;
import org.openbase.planetsudo.view.level.levelobjects.MothershipPanel;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import org.openbase.planetsudo.level.levelobjects.Mothership;
import org.openbase.planetsudo.level.AbstractLevel;
import org.openbase.planetsudo.level.levelobjects.Resource;
import org.openbase.planetsudo.view.game.AbstractGameObjectPanel;
import org.openbase.planetsudo.view.level.levelobjects.ResourcePanel;
import java.awt.Polygon;
import org.openbase.jul.visual.swing.engine.draw2d.AbstractResourcePanel.ObjectType;
import org.openbase.jul.visual.swing.engine.draw2d.ResourceDisplayPanel;
import org.openbase.planetsudo.level.levelobjects.Tower;
import org.openbase.planetsudo.view.level.levelobjects.TowerPanel;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class LevelPanel extends AbstractGameObjectPanel<AbstractLevel, LevelPanel> implements PropertyChangeListener {

    private final boolean hasInternalWalls;
    private boolean enabledLevelObjects;

    public LevelPanel(AbstractLevel resource, ResourceDisplayPanel parentPanel) {
        super(resource, resource.getLevelBorderPolygon(), ObjectType.Dynamic, GameObjectImages.Default.imagesURL, parentPanel);
        hasInternalWalls = resource.getLevelWallPolygons() != null;
        boundingBox = resource.getLevelBorderPolygon().getBounds2D();

        updateBounds();
        parentPanel.setDoubleBuffered(true);
        resource.addPropertyChangeListener(this);
        enabledLevelObjects = false;
    }

    public void loadLevelObjects() {
        enabledLevelObjects = true;
        loadResourcePanels();
        loadMothershipPanels();
        updateBounds();
    }

    private void loadResourcePanels() {
        for (Resource res : resource.getResources()) {
            new ResourcePanel(res, this);
        }
    }

    private void loadMothershipPanels() {
        for (Mothership mothership : resource.getMotherships()) {
            new MothershipPanel(mothership, this);
        }
    }

    @Override
    protected void paintComponent(Graphics2D g2, Graphics2D gl) {
        g2.fill(resource.getLevelBorderPolygon());
//		g2.fill(tranformedPlacement);
        if (hasInternalWalls) {
            g2.setColor(resource.getColor());
            for (Polygon wall : resource.getLevelWallPolygons()) {
                g2.fill(wall);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(AbstractLevel.CREATE_RESOURCE) && enabledLevelObjects) {
            new ResourcePanel((Resource) evt.getNewValue(), this);
        } else if (evt.getPropertyName().equals(AbstractLevel.CREATE_TOWER) && enabledLevelObjects) {
            new TowerPanel((Tower) evt.getNewValue(), this);
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
