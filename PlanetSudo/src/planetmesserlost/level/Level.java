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
import planetmesserlost.levelobjects.AbstractLevelObject;
import planetmesserlost.levelobjects.Agent;

/**
 *
 * @author divine
 */
public abstract class Level implements Runnable {

	public final static long DEFAULT_GAME_SPEED = 12/Mothership.DEFAULT_AGENT_COUNT;
	//public final static long DEFAULT_GAME_SPEED = ;


	private final String name;
	private final LinkedList<Mothership> motherships;
	private long gameSpeed;
	private Polygon levelBorderPolygon;

	public Level() {
		this.name = this.getClass().getSimpleName();
		this.levelBorderPolygon = this.getLevelBorderPolygon();
		this.motherships = new LinkedList<Mothership>();
		this.gameSpeed = DEFAULT_GAME_SPEED;
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
	
	@ Override
	public String toString() {
		return name;
	}
}
