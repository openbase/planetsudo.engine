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
import org.openbase.planetsudo.level.levelobjects.Agent;
import org.openbase.planetsudo.level.levelobjects.Resource;
import org.openbase.planetsudo.level.levelobjects.TeamMarker;
import org.openbase.planetsudo.view.level.LevelPanel;
import org.slf4j.Logger;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class TeamMarkerPanel extends AbstractLevelObjectPanel<TeamMarker, LevelPanel> implements PropertyChangeListener {

    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
	public TeamMarkerPanel(TeamMarker resource, LevelPanel parentResourcePanel) {
		super(resource, resource.getPolygon(), null, parentResourcePanel, DrawLayer.BACKGROUND);
		logger.info("Create " + this);
		resource.addPropertyChangeListener(this);
	}
	private Graphics2D gg2;
	private int animationCounter = 0;

//	private static final stroke = new Stroke.

	@Override
	protected void paintComponent(Graphics2D g2, Graphics2D gl) {
		if (resource.isPlaced()) {
			boundingBox = resource.getBounds();
			g2.setStroke(new BasicStroke(5));
			switch (animationCounter) {
				case 0:
					g2.setColor(resource.team.teamColor);
					g2.fillOval((int) boundingBox.getCenterX() - 4, (int) boundingBox.getCenterY() - 4, (int) 8, (int) 8);
					g2.setColor(Color.WHITE);
					break;
				case 1:
					g2.setColor(resource.team.teamColor);
					g2.drawOval((int) boundingBox.getCenterX() - 8, (int) boundingBox.getCenterY() - 8, 16, 16);
					g2.setColor(Color.WHITE);
					g2.fillOval((int) boundingBox.getCenterX() - 4, (int) boundingBox.getCenterY() - 4, (int) 8, (int) 8);
					//g2.fillOval((int) boundingBox.getCenterX() - 16, (int) boundingBox.getCenterY() - 16, 32, 32);
					break;
				case 2:
					g2.setColor(resource.team.teamColor);
					g2.drawOval((int) boundingBox.getCenterX() - 16, (int) boundingBox.getCenterY() - 16, 32, 32);
					g2.fillOval((int) boundingBox.getCenterX() - 4, (int) boundingBox.getCenterY() - 4, (int) 8, (int) 8);
					g2.setColor(Color.WHITE);
					g2.drawOval((int) boundingBox.getCenterX() - 8, (int) boundingBox.getCenterY() - 8, 16, 16);
					break;
			}

			//g2.drawOval((int) boundingBox.getX(), (int) boundingBox.getY(), (int) boundingBox.getWidth(), (int) boundingBox.getHeight());
			animationCounter++;
			if (animationCounter > 2) {
				animationCounter = 0;
			}
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
