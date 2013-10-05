/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.view.level.levelobjects;

import de.dc.planetsudo.game.GameObjectImages;
import de.dc.util.data.Direction2D;
import de.dc.util.data.Point2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import de.dc.util.logging.Logger;
import java.awt.event.MouseEvent;
import de.dc.planetsudo.level.levelobjects.AbstractLevelObject;
import de.dc.planetsudo.level.levelobjects.Agent;
import de.dc.planetsudo.view.MainGUI;
import java.awt.Polygon;

/**
 *
 * @author divine
 */
public class AgentPanel extends AbstractLevelObjectPanel<Agent, MothershipPanel> {

	public final static Color FUEL_BACKGROUND = new Color(255, 20, 20);
	public final static Color COLOR_DISABLED = new Color(154, 154, 154);
	public final static double FUEL_BAR_STATIC_WIDTH = 40;
	public final static double FUEL_BAR_STATIC_POSITION_Y = 32;
	public final static int FUEL_BAR_STATIC_HEIGHT = 4;
	public static boolean showStateLabel = false;
	public final Color teamColor;

	//public static boolean viewFlag = true;
	public AgentPanel(Agent resource, MothershipPanel parentResourcePanel) {
		super(resource, resource.getPolygon(), GameObjectImages.Agent.imagesURL, parentResourcePanel, DrawLayer.FORGROUND); //TODO Check Polygon
		this.teamColor = resource.getTeam().getTeamColor();
		Logger.info(this, "Create AgentPanel of " + resource);
//		if(resource.getMothership().getTeam().getId() == 0) {
		MainGUI.levelView = resource.getLevelView();
//		}
	}
	private AbstractLevelObject levelObject;
	private Graphics2D g22;
	private int x, y;
	private Direction2D direction, side;
	private Point2D position;
	private int[] xPoses = new int[3], yPoses = new int[4];

	public static final Polygon TEAM_COLOR_POLYGON = new Polygon();
	
	static {
		TEAM_COLOR_POLYGON.addPoint(relative(8), relative(36));
		TEAM_COLOR_POLYGON.addPoint(relative(16), relative(38));
		TEAM_COLOR_POLYGON.addPoint(relative(20), relative(44));
		TEAM_COLOR_POLYGON.addPoint(relative(78), relative(44));
		TEAM_COLOR_POLYGON.addPoint(relative(82), relative(38));
		TEAM_COLOR_POLYGON.addPoint(relative(90), relative(36));
		TEAM_COLOR_POLYGON.addPoint(relative(90), relative(66));
		TEAM_COLOR_POLYGON.addPoint(relative(76), relative(82));
		TEAM_COLOR_POLYGON.addPoint(relative(50), relative(70));
		TEAM_COLOR_POLYGON.addPoint(relative(48), relative(70));
		TEAM_COLOR_POLYGON.addPoint(relative(22), relative(82));
		TEAM_COLOR_POLYGON.addPoint(relative(8), relative(66));
	}
	
	private static int relative(double per) {
		return (int) ((Agent.AGENT_SIZE * per) / 100);
	}
	
	@Override
	protected void paintComponent(Graphics2D g2, Graphics2D gl) {
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
			g22.transform(getBoundsTransformation(direction));
			//g22.fillRect(3, 10, 19, 9);
			g22.fill(TEAM_COLOR_POLYGON);
			if(resource.needSupport() && animationFlag) {
//				if(((System.currentTimeMillis()/500)%500)>5) {
					g22.setColor(COLOR_DISABLED);
//				}
			}
				g22.fillOval(relative(33), relative(33), relative(35), relative(35)); // Oval zeichnen
		g22.dispose();

		// Paint Laser
		levelObject = resource.isFightingWith();
		if (levelObject != null) {

			g2.setColor(teamColor);
			side = new Direction2D(direction.getAngle() + 90);
			g2.drawLine((int) (x + (side.getVector().getX() * resource.getWidth() / 2)),
					(int) (y + (side.getVector().getY() * resource.getHeight() / 3)),
					(int) (levelObject.getPosition().getX()),
					(int) (levelObject.getPosition().getY()));
			side = new Direction2D(direction.getAngle() - 90);
			g2.drawLine((int) (x + (side.getVector().getX() * resource.getWidth() / 2)),
					(int) (y + (side.getVector().getY() * resource.getHeight() / 3)),
					(int) (levelObject.getPosition().getX()),
					(int) (levelObject.getPosition().getY()));
		}

		levelObject = resource.wasHelping();
		if (levelObject != null) {
			side = new Direction2D(direction.getAngle() + 90);
			xPoses[0] = (int) (x + (side.getVector().getX() * 15));
			yPoses[0] = (int) (y + (side.getVector().getY() * 15));
			xPoses[1] = (int) (x - (side.getVector().getX() * 15));
			yPoses[1] = (int) (y - (side.getVector().getY() * 15));
			xPoses[2] = (int) levelObject.getPosition().getX();
			yPoses[2] = (int) levelObject.getPosition().getY();
			g2.setColor(teamColor);
			g2.fillPolygon(xPoses, yPoses, 3);
//			g2.drawPolygon((int) (resource.getPosition().getX()resource.getWidth()/2)),
//						(int) (resource.getPosition().getY()getHeight()/3)),
//						(int) (levelObject.getPosition().getX()),
//						(int) (levelObject.getPosition().getY()));
		}

		//paintShape(g2);
		paintImageRotated(direction, g2);

		//Explositon
		if (!resource.isAlive()) {
			paintExplosion(g2);
		}

		// Paint FuelBarBackground
		gl.setColor(FUEL_BACKGROUND);
		gl.fillRect((int) (x - FUEL_BAR_STATIC_WIDTH / 2),
				(int) (y - FUEL_BAR_STATIC_POSITION_Y),
				(int) FUEL_BAR_STATIC_WIDTH,
				(int) FUEL_BAR_STATIC_HEIGHT);

		// Paint FuelBar
		gl.setColor(Color.GREEN);
		gl.fillRect((int) (x - FUEL_BAR_STATIC_WIDTH / 2),
				(int) (y - FUEL_BAR_STATIC_POSITION_Y),
				(int) (FUEL_BAR_STATIC_WIDTH / Agent.DEFAULT_START_FUEL * resource.getFuel()),
				(int) FUEL_BAR_STATIC_HEIGHT);


		// Paint StateLable
		if (showStateLabel) {
			gl.setColor(Color.WHITE);
			gl.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
			gl.drawString(resource.getLastAction(), x, y);
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
