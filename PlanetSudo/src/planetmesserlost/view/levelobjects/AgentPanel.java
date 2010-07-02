/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.view.levelobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import logging.Logger;
import planetmesserlost.levelobjects.Agent;

/**
 *
 * @author divine
 */
public class AgentPanel extends AbstractLevelObjectPanel<Agent, MothershipPanel> {
	
	private final Color teamColor;
	public final static Color FUEL_BACKGROUND = new Color(255,20,20);

	public AgentPanel(Agent resource, MothershipPanel parentResourcePanel) {
		super(resource, parentResourcePanel, "res/img/agent.png");
		this.teamColor = resource.getMothership().getTeam().getTeamColor();
		Logger.info(this, "Create AgentPanel of "+resource);
	}

	@Override
	protected void paintComponent(Graphics2D g2) {
		boundingBox = resource.getBounds();

		// Paint Team Color
		Graphics2D g22 = (Graphics2D) g2.create();
			g22.setColor(resource.getMothership().getTeam().getTeamColor());
			AffineTransform transform = new AffineTransform();
			transform = rotateTransformation(resource.getDirection(), image.getWidth(), image.getHeight(), getSkaleImageToBoundsTransformation());
			g22.transform(transform);
			g22.fillRect(3, 10, 19, 9);
			//g2.drawRect((int)boundingBox.getCenterX()-7, (int)boundingBox.getCenterY()-7, 14, 14);
		g22.dispose();

		//paintShape(g2);
		paintImageRotated(resource.getDirection(), g2);
		

		// Paint Laser
		g2.setColor(Color.RED);
		g2.drawLine((int) resource.getPosition().getX(),
					(int) resource.getPosition().getY(),
					(int) (resource.getPosition().getX()+(resource.getDirection().getDirection().getX()*resource.getWidth())),
					(int) (resource.getPosition().getY()+(resource.getDirection().getDirection().getY()*resource.getHeight())));


	


		// Paint FuelBarBackground
		Graphics2D g3 = (Graphics2D) g2.create();
		g3.setColor(FUEL_BACKGROUND);
		g3.fillRect((int) resource.getPosition().getX()-20,
					(int) resource.getPosition().getY()-32,
		//			(int) resource.getFuel()/10*4,
					40,
					4);

		// Paint FuelBar
		Graphics2D g4 = (Graphics2D) g2.create();
		g4.setColor(Color.GREEN);
		g4.fillRect((int) resource.getPosition().getX()-20,
					(int) resource.getPosition().getY()-32,
		//			(int) resource.getFuel()/10*4,
					26,
					4);
		
	}
}
