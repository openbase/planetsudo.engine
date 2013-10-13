/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.view.level.levelobjects;

import de.dc.planetsudo.game.GameObjectImages;
import de.dc.planetsudo.level.levelobjects.Agent;
import de.dc.planetsudo.level.levelobjects.Resource;
import de.dc.planetsudo.level.levelobjects.TeamMarker;
import de.dc.planetsudo.view.level.LevelPanel;
import de.dc.util.logging.Logger;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author divine
 */
public class TeamMarkerPanel extends AbstractLevelObjectPanel<TeamMarker, LevelPanel> implements PropertyChangeListener {

	public TeamMarkerPanel(TeamMarker resource, LevelPanel parentResourcePanel) {
		super(resource, resource.getPolygon(), null, parentResourcePanel, DrawLayer.BACKGROUND);
		Logger.info(this, "Create " + this);
		resource.addPropertyChangeListener(this);
	}
	private Graphics2D gg2;
	private int animationCounter = 0;

//	private static final stroke = new Stroke.

	@Override
	protected void paintComponent(Graphics2D g2, Graphics2D gl) {
		if (resource.isPlaced()) {
			boundingBox = resource.getBounds();
			g2.setStroke(new BasicStroke(5));
			switch (animationCounter) {
				case 0:
					g2.setColor(resource.getTeam().getTeamColor());
					g2.fillOval((int) boundingBox.getCenterX() - 4, (int) boundingBox.getCenterY() - 4, (int) 8, (int) 8);
					g2.setColor(Color.WHITE);
					break;
				case 1:
					g2.setColor(resource.getTeam().getTeamColor());
					g2.drawOval((int) boundingBox.getCenterX() - 8, (int) boundingBox.getCenterY() - 8, 16, 16);
					g2.setColor(Color.WHITE);
					g2.fillOval((int) boundingBox.getCenterX() - 4, (int) boundingBox.getCenterY() - 4, (int) 8, (int) 8);
					//g2.fillOval((int) boundingBox.getCenterX() - 16, (int) boundingBox.getCenterY() - 16, 32, 32);
					break;
				case 2:
					g2.setColor(resource.getTeam().getTeamColor());
					g2.drawOval((int) boundingBox.getCenterX() - 16, (int) boundingBox.getCenterY() - 16, 32, 32);
					g2.fillOval((int) boundingBox.getCenterX() - 4, (int) boundingBox.getCenterY() - 4, (int) 8, (int) 8);
					g2.setColor(Color.WHITE);
					g2.drawOval((int) boundingBox.getCenterX() - 8, (int) boundingBox.getCenterY() - 8, 16, 16);
					break;
			}

			//g2.drawOval((int) boundingBox.getX(), (int) boundingBox.getY(), (int) boundingBox.getWidth(), (int) boundingBox.getHeight());
			animationCounter++;
			if (animationCounter > 2) {
				animationCounter = 0;
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(Resource.KILL_EVENT)) {
			parentResourcePanel.removeChild(this);
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