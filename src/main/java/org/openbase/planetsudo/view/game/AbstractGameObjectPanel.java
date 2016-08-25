/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.view.game;

import org.openbase.planetsudo.game.AbstractGameObject;
import org.openbase.util.view.engine.draw2d.AbstractResourcePanel;
import org.openbase.util.view.engine.draw2d.AbstractResourcePanel.ObjectType;
import org.openbase.util.view.engine.draw2d.ResourceDisplayPanel;
import java.awt.Polygon;

/**
 *
 * @author divine
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
