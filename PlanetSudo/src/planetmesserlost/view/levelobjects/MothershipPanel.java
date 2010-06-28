/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.view.levelobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import planetmesserlost.levelobjects.Agent;
import planetmesserlost.levelobjects.Mothership;
import planetmesserlost.view.level.LevelPanel;

/**
 *
 * @author divine
 */
public class MothershipPanel extends AbstractLevelObjectPanel<Mothership, LevelPanel> {

	public MothershipPanel(Mothership resource, LevelPanel parentResourcePanel) {
		super(resource, parentResourcePanel);
		loadAgentPanels();
	}

	private void loadAgentPanels() {
		Iterator<Agent> agentIterator = resource.getAgends();
		while(agentIterator.hasNext()) {
			new AgentPanel(agentIterator.next(), this);
		}
	}

	public final static int MOTHERSHIP_SIZE = 100;

	@Override
	protected void paintComponent(Graphics2D g2) {
		g2.setColor(Color.YELLOW);
		boundingBox = resource.getBounds();
		paintShape(g2);
	}
}
