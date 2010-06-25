/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.view.levelobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import planetmesserlost.levelobjects.Agent;

/**
 *
 * @author divine
 */
public class AgentPanel extends AbstractLevelObjectPanel<Agent, MothershipPanel> {
	

	public AgentPanel(Agent resource, MothershipPanel parentResourcePanel) {
		super(resource, parentResourcePanel);
		
//		Logger.info(this, "paint agent on pos["+((int)resource.getPosition().getX()-AGENT_SIZE)+"|"+((int)resource.getPosition().getY()-AGENT_SIZE)+"]");
	}

	@Override
	protected void paintComponent(Graphics2D g2) {
		g2.setColor(Color.BLUE);
		paintShape(g2);
		g2.setColor(Color.RED);
		g2.drawLine((int) resource.getPosition().getX(),
					(int) resource.getPosition().getY(),
					(int) (resource.getPosition().getX()+(resource.getDirection().getDirection().getX()*resource.getWidth())),
					(int) (resource.getPosition().getY()+(resource.getDirection().getDirection().getY()*resource.getHeight())));
		boundingBox = resource.getBounds();
	}
}
