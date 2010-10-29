/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.view.level.levelobjects;

import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import logging.Logger;
import planetsudo.level.levelobjects.Agent;
import planetsudo.level.levelobjects.Resource;
import planetsudo.level.levelobjects.Resource.ResourceType;
import planetsudo.view.level.LevelPanel;

/**
 *
 * @author divine
 */
public class ResourcePanel extends AbstractLevelObjectPanel<Resource, LevelPanel> implements PropertyChangeListener {

	public ResourcePanel(Resource resource, LevelPanel parentResourcePanel) {
		super(resource, parentResourcePanel, DrawLayer.BACKGROUND, getImageURI(resource.getType()));
		Logger.info(this, "Create "+this);
		resource.addPropertyChangeListener(this);
	}

	private static String getImageURI(ResourceType type) {
		switch(type) {
			case Normal:
				return "res/img/resource1.png";
			case DoublePoints:
				return "res/img/resource3.png";
			case ExtremPoint:
				return "res/img/resource6.png";
			case ExtraAgentFuel:
				return "res/img/resource5.png";
			case ExtraMothershipFuel:
				return "res/img/resource7.png";
			case Bomb:
				return "res/img/resource4.png";
			default:
				return null;
		}
	}

	private Agent owner;
	private Graphics2D gg2;
	@Override
	protected void paintComponent(Graphics2D g2) {
		boundingBox = resource.getBounds();
		owner = resource.getOwner();
		if(owner == null) {
			paintImage(g2);
		} else {
			gg2 = (Graphics2D) g2.create();
			gg2.translate(-owner.getDirection().getVector().getX()*owner.getWidth()*0.35, -owner.getDirection().getVector().getY()*owner.getHeight()*0.35);
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

		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(Resource.KILL_EVENT)) {
			parentResourcePanel.removeChild(this);
		} 
	}
}
