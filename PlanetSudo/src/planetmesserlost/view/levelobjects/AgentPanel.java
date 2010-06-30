/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.view.levelobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import logging.Logger;
import planetmesserlost.levelobjects.Agent;

/**
 *
 * @author divine
 */
public class AgentPanel extends AbstractLevelObjectPanel<Agent, MothershipPanel> {
	
	private final Color teamColor;

	public AgentPanel(Agent resource, MothershipPanel parentResourcePanel) {
		super(resource, parentResourcePanel);
		this.teamColor = resource.getMothership().getTeam().getTeamColor();
		Logger.info(this, "Create AgentPanel of "+resource);
	}

	@Override
	protected void paintComponent(Graphics2D g2) {
		g2.setColor(teamColor);
		paintShape(g2);
		g2.setColor(Color.RED);
		g2.drawLine((int) resource.getPosition().getX(),
					(int) resource.getPosition().getY(),
					(int) (resource.getPosition().getX()+(resource.getDirection().getDirection().getX()*resource.getWidth())),
					(int) (resource.getPosition().getY()+(resource.getDirection().getDirection().getY()*resource.getHeight())));
		boundingBox = resource.getBounds();
	}
}
