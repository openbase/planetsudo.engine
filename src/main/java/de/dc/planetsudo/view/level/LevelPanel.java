/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.view.level;

import de.dc.planetsudo.game.GameObjectImages;
import de.dc.util.view.engine.draw2d.ResourceDisplayPanel;
import java.beans.PropertyChangeEvent;
import de.dc.planetsudo.view.level.levelobjects.MothershipPanel;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import de.dc.planetsudo.level.levelobjects.Mothership;
import de.dc.planetsudo.level.AbstractLevel;
import de.dc.planetsudo.level.levelobjects.Resource;
import de.dc.planetsudo.view.game.AbstractGameObjectPanel;
import de.dc.planetsudo.view.level.levelobjects.ResourcePanel;

/**
 *
 * @author divine
 */
public class LevelPanel extends AbstractGameObjectPanel<AbstractLevel, LevelPanel> implements PropertyChangeListener {

	private final boolean hasInternalWalls;
	private boolean enabledLevelObjects;

	public LevelPanel(AbstractLevel resource, ResourceDisplayPanel parentPanel) {
		super(resource, resource.getLevelBorderPolygon(), ObjectType.Dynamic, GameObjectImages.Default.imagesURL, parentPanel);
		hasInternalWalls = resource.getLevelWallPolygons() != null;
//		boundingBox = resource.getLevelBorderPolygon().getBounds2D();
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
		Iterator<Resource> resourceIterator = resource.getResources();
		while(resourceIterator.hasNext()) {
			new ResourcePanel(resourceIterator.next(), this);
		}
	}

	private void loadMothershipPanels() {
		Mothership[] mothershipArray = resource.getMotherships();
		for(Mothership mothership : mothershipArray) {
			new MothershipPanel(mothership, this);
		}
	}

	@Override
	protected void paintComponent(Graphics2D g2, Graphics2D gl) {
		g2.fill(resource.getLevelBorderPolygon());
//		g2.fill(tranformedPlacement);
		if(hasInternalWalls) {
			g2.setColor(resource.getColor());
			for(Polygon wall : resource.getLevelWallPolygons()) {
				g2.fill(wall);
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(AbstractLevel.CREATE_RESOURCE) && enabledLevelObjects) {
			new ResourcePanel((Resource) evt.getNewValue(), this);
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
