/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.view.game;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2023 openbase.org
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

import org.openbase.planetsudo.game.AbstractGameObject;
import java.awt.Polygon;
import org.openbase.jul.visual.swing.engine.draw2d.AbstractResourcePanel;
import org.openbase.jul.visual.swing.engine.draw2d.AbstractResourcePanel.DrawLayer;
import org.openbase.jul.visual.swing.engine.draw2d.AbstractResourcePanel.ObjectType;
import org.openbase.jul.visual.swing.engine.draw2d.ResourceDisplayPanel;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public abstract class AbstractGameObjectPanel<R extends AbstractGameObject, PRP extends AbstractGameObjectPanel> extends AbstractResourcePanel<R, AbstractGameObjectPanel, PRP> {

	public AbstractGameObjectPanel(final R resource, final Polygon placementPolygon, final ObjectType objectType, final String imageURI, final ResourceDisplayPanel parentPanel) {
		super(resource, placementPolygon, objectType, imageURI, parentPanel);
	}

	public AbstractGameObjectPanel(final R resource, final Polygon placementPolygon, final ObjectType objectType, final ResourceDisplayPanel parentPanel) {
		super(resource, placementPolygon, objectType, parentPanel);
	}

	public AbstractGameObjectPanel(final R resource, final Polygon placementPolygon, final ObjectType objectType, final String imageURI, final PRP parentResourcePanel, final DrawLayer drawLayer) {
		super(resource, placementPolygon, objectType, imageURI, parentResourcePanel, drawLayer);
	}

	public AbstractGameObjectPanel(final R resource, final Polygon placementPolygon, final ObjectType objectType, final PRP parentResourcePanel, final DrawLayer drawLayer) {
		super(resource, placementPolygon, objectType, parentResourcePanel, drawLayer);
	}
}
