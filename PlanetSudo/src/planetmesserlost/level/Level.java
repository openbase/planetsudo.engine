/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.level;

import data.Point2D;
import java.awt.Polygon;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import logging.Logger;
import planetmesserlost.levelobjects.Mothership;
import planetmesserlost.game.Team;
import planetmesserlost.levelobjects.Agent;

/**
 *
 * @author divine
 */
public abstract class Level implements Runnable {

	public final static long DEFAULT_GAME_SPEED = 12/Mothership.DEFAULT_AGENT_COUNT; // Optimised Game Speed

	private final String name;
	private final LinkedList<Mothership> motherships;
	private final long gameSpeed;
	private final Polygon levelBorderPolygon;
	private final int x, y;

	public Level() {
		this.name = this.getClass().getSimpleName();
		this.levelBorderPolygon = this.getLevelBorderPolygon();
		this.motherships = new LinkedList<Mothership>();
		this.gameSpeed = DEFAULT_GAME_SPEED;
		Point2D base = updateBasePosition();
		this.x = (int) base.getX();
		this.y = (int) base.getY();
	}

	@Override
	public void run() {
		Logger.info(this, "Start Level "+this);
		for(Mothership mothership : motherships) {
			mothership.startGame();
		}

		while(!isGameOver()) {
			for(Mothership mothership : motherships) {
				mothership.addActionPoint();
			}
			try {
				Thread.sleep(gameSpeed);
			} catch (InterruptedException ex) {
				Logger.warn(this, "", ex);
			}
		}
	}

	public void reset() {
		Logger.info(this, "Load default values.");
		for(Mothership mothership : motherships) {
			mothership.reset();
			motherships.remove(mothership);
		}
	}

	public void setTeams(Collection<Team> teams) {
		reset();
		for(Team team : teams) {
			motherships.add(new Mothership(motherships.size(), team, this));
		}
	}

	public boolean collisionDetected(Agent agent) {
		return !levelBorderPolygon.contains(agent.getFutureBounds());
	}

	public void waitTillGameOver() {
		for(Mothership mothership : motherships) {
			mothership.waitTillFuelRunsOut();
		}
	}

	public boolean isGameOver() {
		for(Mothership mothership : motherships) {
			if(mothership.hasFuel()) {
				return false;
			}
		}
		return true;
	}

	public abstract Polygon getLevelBorderPolygon();
	public abstract Point2D[] getHomePositions();

	public Point2D getMothershipHomePosition(int mothershipID) {
		return getHomePositions()[mothershipID];
	}

	public Iterator<Mothership> getMotherships() {
		return motherships.iterator();
	}

	private Point2D updateBasePosition() {
		int x = Integer.MAX_VALUE;
		int y = Integer.MAX_VALUE;
		for(int i=0; i<levelBorderPolygon.npoints; i++) {
			if(levelBorderPolygon.xpoints[i] < x) {
				x = levelBorderPolygon.xpoints[i];
			}
			if(levelBorderPolygon.ypoints[i] < y) {
				y = levelBorderPolygon.xpoints[i];
			}
		}
		return new Point2D(x, y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	@ Override
	public String toString() {
		return name;
	}
}
