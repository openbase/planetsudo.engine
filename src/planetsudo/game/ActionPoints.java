/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.game;

import logging.Logger;
import planetsudo.level.AbstractLevel;

/**
 *
 * @author divine
 */
public class ActionPoints {
	private int points;

	public ActionPoints() {
		this.points = 0;
	}

	public void addActionPoint() {
		synchronized(this) {
			points++;
			this.notify();
		}
	}

	public void getActionPoint() {
		getActionPoint(1);
	}

	public void getActionPoint(int orderedPoints) {
		while(true) {
			synchronized(this) {
				if(points >= orderedPoints) {
					points -= orderedPoints; // TODO ckeck if this is the right shortcut
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
