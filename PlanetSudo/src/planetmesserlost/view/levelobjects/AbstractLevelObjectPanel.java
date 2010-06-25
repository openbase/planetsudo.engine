/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.view.levelobjects;

import java.awt.Graphics2D;
import planetmesserlost.levelobjects.AbstractLevelObject;
import view.components.draw.AbstractResourcePanel;

/**
 *
 * @author divine
 */
public abstract class AbstractLevelObjectPanel<R extends AbstractLevelObject, PRP extends AbstractResourcePanel> extends AbstractResourcePanel<R, PRP> {

	public AbstractLevelObjectPanel(R resource, PRP parentResourcePanel) {
		super(resource, parentResourcePanel);
	}

	protected void paintShape(Graphics2D g2) {
		switch(resource.getShape()) {
			case Oval:
				g2.fillOval((int)resource.getPosition().getX()-((int)resource.getWidth()/2), (int)resource.getPosition().getY()-((int)resource.getHeight()/2), (int)resource.getWidth(), (int)resource.getHeight());
				break;
			case Rec:
				g2.fillRect((int)resource.getPosition().getX()-((int)resource.getWidth()/2), (int)resource.getPosition().getY()-((int)resource.getHeight()/2), (int)resource.getWidth(), (int)resource.getHeight());
				break;
		}
	}
}
