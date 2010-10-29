/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.view.level.levelobjects;

import java.awt.Graphics2D;
import planetsudo.level.levelobjects.Agent;
import planetsudo.level.levelobjects.Mothership;
import planetsudo.view.level.LevelPanel;

/**
 *
 * @author divine
 */
public class MothershipPanel extends AbstractLevelObjectPanel<Mothership, LevelPanel> {


	public MothershipPanel(Mothership resource, LevelPanel parentResourcePanel) {
		super(resource, parentResourcePanel, DrawLayer.BACKGROUND, "res/img/mothership2.png");
		loadAgentPanels();
	}

	private void loadAgentPanels() {
		for(Agent agent : resource.getAgents()) {
			new AgentPanel(agent, this);
		}
	}

	@Override
	protected void paintComponent(Graphics2D g2) {
		boundingBox = resource.getBounds();
		//paintShape(g2);
		g2.setColor(resource.getTeam().getTeamColor());
		g2.fillRect((int)boundingBox.getCenterX()-45, (int)boundingBox.getCenterY()-45, 90,90);
		paintImage(g2);
		if(resource.isBurning()) {
			paintExplosion(g2);
		}
	}
}