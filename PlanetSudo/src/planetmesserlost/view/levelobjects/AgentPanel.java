/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.view.levelobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import logging.Logger;
import planetmesserlost.levelobjects.Agent;

/**
 *
 * @author divine
 */
public class AgentPanel extends AbstractLevelObjectPanel<Agent, MothershipPanel> {
	
	private final Color teamColor;

	public AgentPanel(Agent resource, MothershipPanel parentResourcePanel) {
		super(resource, parentResourcePanel, "res/img/agent.png");
		this.teamColor = resource.getMothership().getTeam().getTeamColor();
		Logger.info(this, "Create AgentPanel of "+resource);
	}

	@Override
	protected void paintComponent(Graphics2D g2) {
		boundingBox = resource.getBounds();
		//paintShape(g2);
		paintImageRotated(resource.getDirection(), g2);
		
		// Paint Team Color
		g2.setColor(resource.getMothership().getTeam().getTeamColor());
//		AffineTransform transform = new AffineTransform();
//		transform = rotateTransformation(resource.getDirection(), image.getWidth(), image.getHeight(), getSkaleImageToBoundsTransformation());
//		g2.transform(transform);
//		g2.fillOval(0, 0, 10, 10);
		g2.drawOval((int)boundingBox.getCenterX()-7, (int)boundingBox.getCenterY()-7, 14, 14);

		// Paint Laser
		g2.setColor(Color.RED);
		g2.drawLine((int) resource.getPosition().getX(),
					(int) resource.getPosition().getY(),
					(int) (resource.getPosition().getX()+(resource.getDirection().getDirection().getX()*resource.getWidth())),
					(int) (resource.getPosition().getY()+(resource.getDirection().getDirection().getY()*resource.getHeight())));
		
	}
}
