/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.view.levelobjects;

import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import logging.Logger;
import planetsudo.level.levelobjects.Agent;
import planetsudo.level.levelobjects.Resource;
import planetsudo.view.level.LevelPanel;

/**
 *
 * @author divine
 */
public class ResourcePanel extends AbstractLevelObjectPanel<Resource, LevelPanel> implements PropertyChangeListener {

	public ResourcePanel(Resource resource, LevelPanel parentResourcePanel) {
		super(resource, parentResourcePanel, "res/img/resource.png");
		Logger.info(this, "Create "+this);
		resource.addPropertyChangeListener(this);
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
			gg2.translate(-owner.getDirection().getDirection().getX()*owner.getWidth()*0.35, -owner.getDirection().getDirection().getY()*owner.getHeight()*0.35);
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
