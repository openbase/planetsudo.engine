/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.view.levelobjects;

import data.Direction2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import logging.Logger;
import planetsudo.level.levelobjects.AbstractLevelObject;
import planetsudo.level.levelobjects.Agent;

/**
 *
 * @author divine
 */
public class AgentPanel extends AbstractLevelObjectPanel<Agent, MothershipPanel> {
	
	public final static Color FUEL_BACKGROUND = new Color(255,20,20);
	public final static double FUEL_BAR_STATIC_WIDTH = 40;
	public final static double FUEL_BAR_STATIC_POSITION_Y = 32;
	public final static int FUEL_BAR_STATIC_HEIGHT = 4;

	public static boolean showStateLabel = false;

	//public static boolean viewFlag = true;
	public AgentPanel(Agent resource, MothershipPanel parentResourcePanel) {
		super(resource, parentResourcePanel, "res/img/agent.png");
		Logger.info(this, "Create AgentPanel of "+resource);
//		if(resource.getMothership().getTeam().getID() == 0 && viewFlag) {
//			viewFlag = false;
//			MainGUI.levelView = resource.getLevelView();
//		}
	}

	private AbstractLevelObject levelObject;

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

		// Paint Laser
		levelObject = resource.isFightingWith();
		if(levelObject != null) {
			Direction2D side;
			g22.setColor(resource.getMothership().getTeam().getTeamColor());
			side = new Direction2D(resource.getDirection().getAngle()+90);
			g2.drawLine((int) (resource.getPosition().getX()+(side.getVector().getX()*resource.getWidth()/2)),
						(int) (resource.getPosition().getY()+(side.getVector().getY()*resource.getHeight()/3)),
						(int) (levelObject.getPosition().getX()),
						(int) (levelObject.getPosition().getY()));
			side = new Direction2D(resource.getDirection().getAngle()-90);
			g2.drawLine((int) (resource.getPosition().getX()+(side.getVector().getX()*resource.getWidth()/2)),
						(int) (resource.getPosition().getY()+(side.getVector().getY()*resource.getHeight()/3)),
						(int) (levelObject.getPosition().getX()),
						(int) (levelObject.getPosition().getY()));
		}

		//paintShape(g2);
		paintImageRotated(resource.getDirection(), g2);

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

		// Paint StateLable
		if(showStateLabel) {
			g2.setColor(Color.WHITE);
			g2.setFont(new Font(Font.SERIF, Font.PLAIN, 25 ));
			g2.drawString(resource.getLastAction(),
					(int) (resource.getPosition().getX()),
					(int) (resource.getPosition().getY()));
		}


	}
}
