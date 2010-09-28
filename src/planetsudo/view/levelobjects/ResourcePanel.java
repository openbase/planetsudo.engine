/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.view.levelobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import logging.Logger;
import planetsudo.level.levelobjects.Resource;
import planetsudo.view.level.LevelPanel;

/**
 *
 * @author divine
 */
public class ResourcePanel extends AbstractLevelObjectPanel<Resource, LevelPanel>{

	public ResourcePanel(Resource resource, LevelPanel parentResourcePanel) {
		super(resource, parentResourcePanel, "res/img/agent.png");
		Logger.info(this, "Create "+this);
	}

	@Override
	protected void paintComponent(Graphics2D g2) {
		switch(resource.getType()) {
			case NORMAL:
				g2.setColor(Color.blue);
				//g2.fillOval(50, 50, 100, 100);
				g2.fillOval((int)resource.getPosition().getX(), (int)resource.getPosition().getY(), (int)resource.getWidth(), (int)resource.getHeight());
		}
	}

}
