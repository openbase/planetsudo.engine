/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.view.level.levelobjects;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2024 openbase.org
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
import org.openbase.planetsudo.level.levelobjects.Agent;
import org.openbase.planetsudo.level.levelobjects.Resource;
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType;
import org.openbase.planetsudo.view.level.LevelPanel;
import java.awt.Color;
import org.openbase.planetsudo.geometry.Direction2D;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class ResourcePanel extends AbstractLevelObjectPanel<Resource, LevelPanel> implements PropertyChangeListener {

    private static final Logger logger = LoggerFactory.getLogger(ResourcePanel.class);
    
	private final boolean mine;
	private final Color mineColor;

	public ResourcePanel(final Resource resource, final LevelPanel parentResourcePanel) {
		super(resource, resource.getPolygon(), getImageURI(resource.getType()), parentResourcePanel, DrawLayer.BACKGROUND);
		logger.info("Create " + this);
		resource.addPropertyChangeListener(this);
		this.mine = resource.getType() == ResourceType.Mine;
		if(mine && resource.wasPlacedByTeam() != null) {
			this.mineColor = resource.wasPlacedByTeam().getTeamColor();
		} else {
			this.mineColor = Color.BLACK;
		}
	}

	private static String getImageURI(ResourceType type) {
		return GameObjectImages.valueOf("Resource" + type.name()).imagesURL;
	}
	private Agent owner;
	private Graphics2D gg2;
	private Direction2D direction;

	@Override
	protected void paintComponent(Graphics2D g2, Graphics2D gl) {
		boundingBox = resource.getBounds();
		owner = resource.getOwner();

		if (owner == null) {
			if (mine) {
				if(animationFlag) {
					g2.setColor(mineColor);
				} else {
					g2.setColor(Color.BLACK);
				}
				g2.fillRect((int) boundingBox.getCenterX() - 5, (int) boundingBox.getCenterY() - 5, 10, 10);
			}
			paintImage(g2);
		} else {
			direction = owner.getDirection();
			gg2 = (Graphics2D) g2.create();
			gg2.translate(-direction.getVector().getX() * owner.getWidth() * 0.35, -direction.getVector().getY() * owner.getHeight() * 0.35);
			paintImageRotated(direction, gg2);
			gg2.dispose();
		}



	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(Resource.KILL_EVENT)) {
			parentResourcePanel.removeChild(this);
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
