/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.view.levelobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import logging.Logger;
import planetsudo.level.levelobjects.Resource;
import planetsudo.view.level.LevelPanel;

/**
 *
 * @author divine
 */
public class ResourcePanel extends AbstractLevelObjectPanel<Resource, LevelPanel> implements PropertyChangeListener {

	public ResourcePanel(Resource resource, LevelPanel parentResourcePanel) {
		super(resource, parentResourcePanel, "res/img/agent.png");
		Logger.info(this, "Create "+this);
		resource.addPropertyChangeListener(this);
	}

	@Override
	protected void paintComponent(Graphics2D g2) {
		//g2.setColor(Color.blue);
		g2.drawImage(image, (int)resource.getPosition().getX()-(int)resource.getWidth()/2, (int)resource.getPosition().getY()-(int)resource.getHeight()/2, parentPanel);
		paintImage(g2);
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
