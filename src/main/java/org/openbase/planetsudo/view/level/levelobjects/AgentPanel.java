/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.view.level.levelobjects;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2017 openbase.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
import org.openbase.planetsudo.game.GameObjectImages;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import org.slf4j.Logger;
import java.awt.event.MouseEvent;
import org.openbase.planetsudo.level.levelobjects.AbstractLevelObject;
import org.openbase.planetsudo.level.levelobjects.Agent;
import org.openbase.planetsudo.view.MainGUI;
import java.awt.Polygon;
import org.openbase.planetsudo.geometry.Direction2D;
import org.openbase.planetsudo.geometry.Point2D;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class AgentPanel extends AbstractLevelObjectPanel<Agent, MothershipPanel> {

    public final static Color FUEL_BACKGROUND = new Color(255, 20, 20);
    public final static Color COLOR_DISABLED = new Color(154, 154, 154);
    public final static double FUEL_BAR_STATIC_WIDTH = 40;
    public final static double FUEL_BAR_STATIC_POSITION_Y = 32;
    public final static int FUEL_BAR_STATIC_HEIGHT = 4;
    public static boolean showStateLabel = false;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public final Color teamColor;

    public static GameObjectImages getAgentImage(final Agent agent) {
        if (agent.isCommander()) {
            return GameObjectImages.AgentCommander;
        } else {
            return GameObjectImages.Agent;
        }
    }

    //public static boolean viewFlag = true;
    public AgentPanel(final Agent resource, final MothershipPanel parentResourcePanel) {
        super(resource, resource.getPolygon(), getAgentImage(resource).imagesURL, parentResourcePanel, DrawLayer.FORGROUND); //TODO Check Polygon
        this.teamColor = resource.getTeam().getTeamColor();
        logger.info("Create AgentPanel of " + resource);
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
        TEAM_COLOR_POLYGON.addPoint(relative(7), relative(36));
        TEAM_COLOR_POLYGON.addPoint(relative(17), relative(36));
        TEAM_COLOR_POLYGON.addPoint(relative(19), relative(50));
        TEAM_COLOR_POLYGON.addPoint(relative(50), relative(12));
        TEAM_COLOR_POLYGON.addPoint(relative(80), relative(50));
        TEAM_COLOR_POLYGON.addPoint(relative(82), relative(36));
        TEAM_COLOR_POLYGON.addPoint(relative(92), relative(36));
        TEAM_COLOR_POLYGON.addPoint(relative(92), relative(82));
        TEAM_COLOR_POLYGON.addPoint(relative(83), relative(92));
        TEAM_COLOR_POLYGON.addPoint(relative(50), relative(71));
        TEAM_COLOR_POLYGON.addPoint(relative(16), relative(91));
        TEAM_COLOR_POLYGON.addPoint(relative(7), relative(82));
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
        int trans_x = x + 60;
        int trans_y = y + 60;
        direction = getResource().getDirection();

        if (showStateLabel) {
            gl.setColor(Color.WHITE);
            gl.drawLine((int) boundingBox.getMaxX(), (int) boundingBox.getMaxY(), trans_x, trans_y);
        }

//		if(MainGUI.levelView == resource.getLevelView()) {
//			resource.getLevelView().drawLevelView((int)parentResourcePanel.getBoundingBox().getX(), (int)parentResourcePanel.getBoundingBox().getY(), g2);
//		}
        // Paint Team Color
        g22 = (Graphics2D) g2.create();
        g22.setColor(teamColor);
        g22.transform(getBoundsTransformation(direction));
        //g22.fillRect(3, 10, 19, 9);
        g22.fill(TEAM_COLOR_POLYGON);
        if (resource.isSupportOrdered() && animationFlag) {
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
                (int) (FUEL_BAR_STATIC_WIDTH / resource.getFuelVolume() * resource.getFuel()),
                (int) FUEL_BAR_STATIC_HEIGHT);

        // Paint StateLable
        if (showStateLabel) {
            final String description = resource.getDescription();
            gl.setFont(new Font(Font.SERIF, Font.BOLD, 25));
            gl.setColor(teamColor);
            gl.fillRoundRect(trans_x - 10, trans_y - 30, 20 + gl.getFontMetrics().stringWidth(description), 40, 50, 50);
            gl.setColor(Color.BLACK);

            gl.drawRoundRect(trans_x - 10, trans_y - 30, 20 + gl.getFontMetrics().stringWidth(description), 40, 50, 50);
            gl.drawString(description, trans_x, trans_y);

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
