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
import de.dc.planetsudo.view.MainGUI;
import de.dc.planetsudo.view.level.LevelPanel;

/**
 *
 * @author divine
 */
public class ResourcePanel extends AbstractLevelObjectPanel<Resource, LevelPanel> implements PropertyChangeListener {

	public ResourcePanel(Resource resource, LevelPanel parentResourcePanel) {
		super(resource, resource.getPolygon(), getImageURI(resource.getType()), parentResourcePanel, DrawLayer.BACKGROUND);
		Logger.info(this, "Create " + this);
		resource.addPropertyChangeListener(this);
	}

	private static String getImageURI(ResourceType type) {
		return GameObjectImages.valueOf("Resource" + type.name()).imagesURL;
	}
	private Agent owner;
	private Graphics2D gg2;

	@Override
	protected void paintComponent(Graphics2D g2, Graphics2D gl) {
		boundingBox = resource.getBounds();
		owner = resource.getOwner();
		if (owner == null) {
			paintImage(g2);
		} else {
			gg2 = (Graphics2D) g2.create();
			gg2.translate(-owner.getDirection().getVector().getX() * owner.getWidth() * 0.35, -owner.getDirection().getVector().getY() * owner.getHeight() * 0.35);
			paintImageRotated(owner.getDirection(), gg2);
			gg2.dispose();
		}

		//g2.fillOval((int)resource.getPosition().getX()-(int)resource.getWidth()/2, (int)resource.getPosition().getY()-(int)resource.getHeight()/2, (int)resource.getWidth(), (int)resource.getHeight());
//				if(resource.isOwned()) {
//					resource.getOwner().getDirection(
//					//					(int) (resource.getPosition().getX()+(resource.getDirection().getDirection().getX()*resource.getWidth())),
////					(int) (resource.getPosition().getY()+(resource.getDirection().getDirection().getY()*resource.getHeight())));
//
//				} else {
//
//				}

//		if (resource != null && resource.getLevelView() != null && MainGUI.levelView == resource.getLevelView()) {
//			resource.getLevelView().drawLevelView((int) parentResourcePanel.getBoundingBox().getX(), (int) parentResourcePanel.getBoundingBox().getY(), g2);
//		}
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
