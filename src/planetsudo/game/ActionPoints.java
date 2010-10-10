/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.game;

import logging.Logger;
import planetsudo.level.levelobjects.Agent;

/**
 *
 * @author divine
 */
public class ActionPoints {
	private int points;
	private Agent agent;

	public ActionPoints(Agent agent) {
		this.points = 0;
		this.agent = agent;
	}

	public void addActionPoint() {
		if(!agent.isDisabled()) {
			synchronized(this) {
				points++;
				this.notify();
			}
		}
	}

	public void getActionPoint() {
		getActionPoint(1);
	}

	public void getActionPoint(int orderedPoints) {
		while(true) {
			synchronized(this) {
				if(points >= orderedPoints) {
					points -= orderedPoints;
					return;
				}
				try {
					this.wait();
				} catch (InterruptedException ex) {
					Logger.warn(this, "", ex);
				}
			}
		}
	}

	public int getActionPoints() {
		return points;
	}
}
