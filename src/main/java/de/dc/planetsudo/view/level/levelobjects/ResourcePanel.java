/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.view.level.levelobjects;

import de.dc.planetsudo.game.GameObjectImages;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import de.dc.util.logging.Logger;
import java.awt.event.MouseEvent;
import de.dc.planetsudo.level.levelobjects.Agent;
import de.dc.planetsudo.level.levelobjects.Resource;
import de.dc.planetsudo.level.levelobjects.Resource.ResourceType;
import de.dc.planetsudo.view.level.LevelPanel;
import de.dc.util.data.Direction2D;
import java.awt.Color;

/**
 *
 * @author divine
 */
public class ResourcePanel extends AbstractLevelObjectPanel<Resource, LevelPanel> implements PropertyChangeListener {

	private final boolean mine;
	private final Color mineColor;

	public ResourcePanel(final Resource resource, final LevelPanel parentResourcePanel) {
		super(resource, resource.getPolygon(), getImageURI(resource.getType()), parentResourcePanel, DrawLayer.BACKGROUND);
		Logger.info(this, "Create " + this);
		resource.addPropertyChangeListener(this);
		this.mine = resource.getType() == ResourceType.Mine;
		if(mine && resource.wasPlacedByTeam() != null) {
			this.mineColor = resource.wasPlacedByTeam().getTeamColor();
		} else {
			this.mineColor = Color.BLACK;
		}
	}

	private static String getImageURI(ResourceType type) {
		return GameObjectImages.valueOf("Resource" + type.name()).imagesURL;
	}
	private Agent owner;
	private Graphics2D gg2;
	private Direction2D direction;

	@Override
	protected void paintComponent(Graphics2D g2, Graphics2D gl) {
		boundingBox = resource.getBounds();
		owner = resource.getOwner();

		if (owner == null) {
			if (mine) {
				if(animationFlag) {
					g2.setColor(mineColor);
				} else {
					g2.setColor(Color.BLACK);
				}
				g2.fillRect((int) boundingBox.getCenterX() - 5, (int) boundingBox.getCenterY() - 5, 10, 10);
			}
			paintImage(g2);
		} else {
			direction = owner.getDirection();
			gg2 = (Graphics2D) g2.create();
			gg2.translate(-direction.getVector().getX() * owner.getWidth() * 0.35, -direction.getVector().getY() * owner.getHeight() * 0.35);
			paintImageRotated(direction, gg2);
			gg2.dispose();
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
