/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.game;

import logging.Logger;

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
			this.notifyAll();
		}
	}

	public void getActionPoint() {
		getActionPoint(1);
	}

	public synchronized void getActionPoint(int orderedPoints) {
		synchronized(this) {
			while(true) {
				if(points >= orderedPoints) {
					points -= orderedPoints;
					return;
				}
				try {
					wait();
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
