/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.view.levelobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import logging.Logger;
import planetmesserlost.level.levelobjects.Agent;
import planetmesserlost.view.MainGUI;

/**
 *
 * @author divine
 */
public class AgentPanel extends AbstractLevelObjectPanel<Agent, MothershipPanel> {
	
	public final static Color FUEL_BACKGROUND = new Color(255,20,20);
	public final static double FUEL_BAR_STATIC_WIDTH = 40;
	public final static double FUEL_BAR_STATIC_POSITION_Y = 32;
	public final static int FUEL_BAR_STATIC_HEIGHT = 4;

	public static boolean viewFlag = true;
	public AgentPanel(Agent resource, MothershipPanel parentResourcePanel) {
		super(resource, parentResourcePanel, "res/img/agent.png");
		Logger.info(this, "Create AgentPanel of "+resource);
//		if(resource.getMothership().getTeam().getID() == 0 && viewFlag) {
//			viewFlag = false;
//			MainGUI.levelView = resource.getLevelView();
//		}
	}

	@Override
	protected void paintComponent(Graphics2D g2) {
		boundingBox = resource.getBounds();
//		if(MainGUI.levelView == resource.getLevelView()) {
//			resource.getLevelView().drawLevelView((int)parentResourcePanel.getBoundingBox().getX(), (int)parentResourcePanel.getBoundingBox().getY(), g2);
//		}

		// Paint Team Color
		Graphics2D g22 = (Graphics2D) g2.create();
			g22.setColor(resource.getMothership().getTeam().getTeamColor());
			AffineTransform transform = new AffineTransform();
			transform = rotateTransformation(resource.getDirection(), image.getWidth(), image.getHeight(), getSkaleImageToBoundsTransformation());
			g22.transform(transform);
			g22.fillRect(3, 10, 19, 9);
			g2.drawRect((int)boundingBox.getCenterX()-7, (int)boundingBox.getCenterY()-7, 14, 14);
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
		g2.setColor(FUEL_BACKGROUND);
		g2.fillRect((int) (resource.getPosition().getX()-FUEL_BAR_STATIC_WIDTH/2),
					(int) (resource.getPosition().getY()-FUEL_BAR_STATIC_POSITION_Y),
					(int) FUEL_BAR_STATIC_WIDTH,
					(int) FUEL_BAR_STATIC_HEIGHT);

		// Paint FuelBar

		g2.setColor(Color.GREEN);
		g2.fillRect((int) (resource.getPosition().getX()-FUEL_BAR_STATIC_WIDTH/2),
					(int) (resource.getPosition().getY()-FUEL_BAR_STATIC_POSITION_Y),
					(int) (FUEL_BAR_STATIC_WIDTH/Agent.DEFAULT_START_FUEL*resource.getFuel()),
					(int) FUEL_BAR_STATIC_HEIGHT);



	}
}
