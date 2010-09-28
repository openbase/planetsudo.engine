/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.view.level;

import planetsudo.view.levelobjects.MothershipPanel;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import planetsudo.level.levelobjects.Mothership;
import planetsudo.level.AbstractLevel;
import planetsudo.level.levelobjects.Resource;
import planetsudo.view.levelobjects.ResourcePanel;
import view.components.draw.AbstractResourcePanel;
import view.components.draw.ResourceDisplayPanel;

/**
 *
 * @author divine
 */
public class LevelPanel extends AbstractResourcePanel<AbstractLevel, LevelPanel> implements ActionListener {


	public LevelPanel(AbstractLevel resource, ResourceDisplayPanel parentPanel) {
		super(resource, parentPanel);
		boundingBox = resource.getLevelBorderPolygon().getBounds2D();
		updateBounds();
		loadResourcePanels();
		loadMothershipPanels();
		parentPanel.setDoubleBuffered(true);
	}

	private void loadResourcePanels() {
		Iterator<Resource> resourceIterator = resource.getResources();
		while(resourceIterator.hasNext()) {
			new ResourcePanel(resourceIterator.next(), this);
		}
	}

	private void loadMothershipPanels() {
		Iterator<Mothership> mothershipIterator = resource.getMotherships();
		while(mothershipIterator.hasNext()) {
			new MothershipPanel(mothershipIterator.next(), this);
		}
	}

	@Override
	protected void paintComponent(Graphics2D g2) {
		g2.fill(resource.getLevelBorderPolygon());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		parentPanel.repaint();
	}
}
