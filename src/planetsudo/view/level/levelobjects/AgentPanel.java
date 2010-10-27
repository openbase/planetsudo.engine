/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.view.level.levelobjects;

import data.Direction2D;
import data.Point2D;
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
	public final Color teamColor;

	//public static boolean viewFlag = true;
	public AgentPanel(Agent resource, MothershipPanel parentResourcePanel) {
		super(resource, parentResourcePanel, DrawLayer.FORGROUND, "res/img/agent1.png");
		this.teamColor = resource.getTeam().getTeamColor();
		Logger.info(this, "Create AgentPanel of "+resource);
//		if(resource.getMothership().getTeam().getID() == 0 && viewFlag) {
//			viewFlag = false;
//			MainGUI.levelView = resource.getLevelView();
//		}
	}

	private AbstractLevelObject levelObject;
	private Graphics2D g22;
	private int x, y;
	private Direction2D direction, side;
	private Point2D position;
	private int[] xPoses = new int[3], yPoses = new int[4];

	@Override
	protected void paintComponent(Graphics2D g2) {
		boundingBox = resource.getBounds();
		position = resource.getPosition();
		x = (int) position.getX();
		y = (int) position.getY();
		direction = getResource().getDirection();

//		if(MainGUI.levelView == resource.getLevelView()) {
//			resource.getLevelView().drawLevelView((int)parentResourcePanel.getBoundingBox().getX(), (int)parentResourcePanel.getBoundingBox().getY(), g2);
//		}

		// Paint Team Color
		g22 = (Graphics2D) g2.create();
			g22.setColor(teamColor);
			AffineTransform transform = new AffineTransform();
			transform = rotateTransformation(direction, image.getWidth(), image.getHeight(), getSkaleImageToBoundsTransformation());
			g22.transform(transform);
			g22.fillRect(3, 10, 19, 9);
			g2.drawRect((int)boundingBox.getCenterX()-7, (int)boundingBox.getCenterY()-7, 14, 14);
		g22.dispose();

		// Paint Laser
		levelObject = resource.isFightingWith();
		if(levelObject != null) {
			
			g22.setColor(teamColor);
			side = new Direction2D(direction.getAngle()+90);
			g2.drawLine((int) (x+(side.getVector().getX()*resource.getWidth()/2)),
						(int) (y+(side.getVector().getY()*resource.getHeight()/3)),
						(int) (levelObject.getPosition().getX()),
						(int) (levelObject.getPosition().getY()));
			side = new Direction2D(direction.getAngle()-90);
			g2.drawLine((int) (x+(side.getVector().getX()*resource.getWidth()/2)),
						(int) (y+(side.getVector().getY()*resource.getHeight()/3)),
						(int) (levelObject.getPosition().getX()),
						(int) (levelObject.getPosition().getY()));
		}

		levelObject = resource.wasHelping();
		if(levelObject != null) {
			side = new Direction2D(direction.getAngle()+90);
			xPoses[0] = (int) (x+(side.getVector().getX()*15));
			yPoses[0] = (int) (y+(side.getVector().getY()*15));
			xPoses[1] = (int) (x-(side.getVector().getX()*15));
			yPoses[1] = (int) (y-(side.getVector().getY()*15));
			xPoses[2] = (int) levelObject.getPosition().getX();
			yPoses[2] = (int) levelObject.getPosition().getY();
			g2.setColor(teamColor);
			g2.fillPolygon(xPoses,yPoses,3);
//			g2.drawPolygon((int) (resource.getPosition().getX()resource.getWidth()/2)),
//						(int) (resource.getPosition().getY()getHeight()/3)),
//						(int) (levelObject.getPosition().getX()),
//						(int) (levelObject.getPosition().getY()));
		}

		//paintShape(g2);
		paintImageRotated(direction, g2);

		//Explositon
		if(!resource.isAlive()) {
			paintExplosion(g2);
		}

		// Paint FuelBarBackground
		g2.setColor(FUEL_BACKGROUND);
		g2.fillRect((int) (x-FUEL_BAR_STATIC_WIDTH/2),
					(int) (y-FUEL_BAR_STATIC_POSITION_Y),
					(int) FUEL_BAR_STATIC_WIDTH,
					(int) FUEL_BAR_STATIC_HEIGHT);

		// Paint FuelBar
		g2.setColor(Color.GREEN);
		g2.fillRect((int) (x-FUEL_BAR_STATIC_WIDTH/2),
					(int) (y-FUEL_BAR_STATIC_POSITION_Y),
					(int) (FUEL_BAR_STATIC_WIDTH/Agent.DEFAULT_START_FUEL*resource.getFuel()),
					(int) FUEL_BAR_STATIC_HEIGHT);


		// Paint StateLable
		if(showStateLabel) {
			g2.setColor(Color.WHITE);
			g2.setFont(new Font(Font.SERIF, Font.PLAIN, 25 ));
			g2.drawString(resource.getLastAction(),x, y);
		}
	}
}
