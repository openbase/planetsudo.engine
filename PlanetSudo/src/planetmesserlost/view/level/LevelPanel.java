/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.view.level;

import planetmesserlost.view.levelobjects.MothershipPanel;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.Timer;
import planetmesserlost.levelobjects.Mothership;
import planetmesserlost.level.Level;
import planetmesserlost.level.LevelView;
import view.components.draw.AbstractResourcePanel;
import view.components.draw.ResourceDisplayPanel;

/**
 *
 * @author divine
 */
public class LevelPanel extends AbstractResourcePanel<Level, LevelPanel> implements ActionListener {


	public LevelPanel(Level resource, ResourceDisplayPanel parentPanel) {
		super(resource, parentPanel);
		boundingBox = resource.getLevelBorderPolygon().getBounds2D();
		updateBounds();
		loadMothershipPanels();
		//new Timer(50, this).start();
		parentPanel.setDoubleBuffered(true);
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
		//g2.setColor(Color.BLACK);
		//g2.fill(resource.getLevelBorderPolygon());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		parentPanel.repaint();
	}
}
