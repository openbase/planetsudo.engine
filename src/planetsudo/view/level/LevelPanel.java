/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.view.level;

import java.beans.PropertyChangeEvent;
import planetsudo.view.level.levelobjects.MothershipPanel;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import planetsudo.level.levelobjects.Mothership;
import planetsudo.level.AbstractLevel;
import planetsudo.level.levelobjects.Resource;
import planetsudo.view.level.levelobjects.ResourcePanel;
import view.components.draw.AbstractResourcePanel;
import view.components.draw.ResourceDisplayPanel;

/**
 *
 * @author divine
 */
public class LevelPanel extends AbstractResourcePanel<AbstractLevel, LevelPanel> implements PropertyChangeListener {

	private final boolean hasInternalWalls;

	public LevelPanel(AbstractLevel resource, ResourceDisplayPanel parentPanel) {
		super(resource, parentPanel);
		hasInternalWalls = resource.getLevelWallPolygons() != null;
		boundingBox = resource.getLevelBorderPolygon().getBounds2D();
		updateBounds();
		parentPanel.setDoubleBuffered(true);
		resource.addPropertyChangeListener(this);
	}

	public void loadLevelObjects() {
		loadResourcePanels();
		loadMothershipPanels();
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
	protected void paintComponent(Graphics2D g2) {
		g2.fill(resource.getLevelBorderPolygon());
		if(hasInternalWalls) {
			g2.setColor(resource.getColor());
			for(Polygon wall : resource.getLevelWallPolygons()) {
				g2.fill(wall);
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(AbstractLevel.CREATE_RESOURCE)) {
			new ResourcePanel((Resource) evt.getNewValue(), this);
		}
	}
}
