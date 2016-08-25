/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game;

import org.slf4j.Logger;
import org.openbase.planetsudo.level.levelobjects.Agent;
import org.slf4j.LoggerFactory;

/**
 *
 * @author divine
 */
public class ActionPoints {

    private static final Logger logger = LoggerFactory.getLogger(ActionPoints.class);
    
	private int points;
	private final Agent agent;

	public ActionPoints(Agent agent) {
		this.points = 0;
		this.agent = agent;
	}

	public void addActionPoint() {
		if (agent.isDisabled() && !agent.isAtMothership()) {
			return;
		}
		synchronized (this) {
			points++;
			this.notify();
		}
	}

	public void getActionPoint() {
		getActionPoint(1);
	}

	public void getActionPoint(int orderedPoints) {
		while (true) {
			synchronized (this) {
				if (points >= orderedPoints) {
					points -= orderedPoints;
					return;
				}
				try {
					this.wait();
				} catch (InterruptedException ex) {
					logger.warn("", ex);
				}
			}
		}
	}

	public int getActionPoints() {
		return points;
	}
}
