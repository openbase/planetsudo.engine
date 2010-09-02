/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.view.levelobjects;

import java.awt.Graphics2D;
import java.util.Iterator;
import planetmesserlost.level.levelobjects.Agent;
import planetmesserlost.level.levelobjects.Mothership;
import planetmesserlost.view.MainGUI;
import planetmesserlost.view.level.LevelPanel;

/**
 *
 * @author divine
 */
public class MothershipPanel extends AbstractLevelObjectPanel<Mothership, LevelPanel> {


	public MothershipPanel(Mothership resource, LevelPanel parentResourcePanel) {
		super(resource, parentResourcePanel, "res/img/mothership2.png");
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
		boundingBox = resource.getBounds();
		//paintShape(g2);
		g2.setColor(resource.getTeam().getTeamColor());
		g2.fillRect((int)boundingBox.getCenterX()-45, (int)boundingBox.getCenterY()-45, 90,90);
		paintImage(g2);
	}
}
