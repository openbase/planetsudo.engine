/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.view.level.levelobjects;

import de.dc.planetsudo.game.GameObjectImages;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import de.dc.planetsudo.level.levelobjects.Agent;
import de.dc.planetsudo.level.levelobjects.Mothership;
import de.dc.planetsudo.view.level.LevelPanel;

/**
 *
 * @author divine
 */
public class MothershipPanel extends AbstractLevelObjectPanel<Mothership, LevelPanel> {


	public MothershipPanel(Mothership resource, LevelPanel parentResourcePanel) {
		super(resource, resource.getPolygon(), GameObjectImages.Mothership.imagesURL, parentResourcePanel, DrawLayer.BACKGROUND);
		loadAgentPanels();
	}

	private void loadAgentPanels() {
		for(Agent agent : resource.getAgents()) {
			new AgentPanel(agent, this);
		}
	}

	@Override
	protected void paintComponent(Graphics2D g2, Graphics2D gl) {
		boundingBox = resource.getBounds();
		//paintShape(g2);
		g2.setColor(resource.getTeam().getTeamColor());
		g2.fillRect((int)boundingBox.getCenterX()-45, (int)boundingBox.getCenterY()-45, 90,90);
		paintImage(g2);
		if(resource.isBurning()) {
			paintExplosion(g2);
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
