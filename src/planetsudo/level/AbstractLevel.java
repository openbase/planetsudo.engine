/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.level;

import data.Base2D;
import data.Point2D;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import logging.Logger;
import planetsudo.game.GameManager;
import planetsudo.level.levelobjects.Mothership;
import planetsudo.game.Team;
import planetsudo.level.levelobjects.Agent;
import planetsudo.level.levelobjects.Resource;
import planetsudo.main.GUIController;

/**
 *
 * @author divine
 */
public abstract class AbstractLevel implements Runnable {

	public final static long DEFAULT_GAME_SPEED = 12/Mothership.MAX_AGENT_COUNT; // Optimised Game Speed
	public final static String CREATE_RESOURCE = "create resource";

	private final GameManager gameManager;
	private final Polygon levelBorderPolygon;
	private final Polygon[] levelWallPolygons;
	private final Base2D[] homePositions;
	private final ResourcePlacement[] resourcePlacement;
	private final Color color;
	private final PropertyChangeSupport changes;

	private final String name;
	private final Mothership[] motherships;
	private final List<Resource> resources;
	private final long gameSpeed;
	
	private final int x, y;
	private int resourceKeyCounter;

	public AbstractLevel() {
		this.gameManager = GameManager.getInstance();
		this.changes = new PropertyChangeSupport(this);
		this.name = getClass().getSimpleName();
		this.levelBorderPolygon = loadLevelBorderPolygon();
		this.levelWallPolygons = loadLevelWallPolygons();
		this.homePositions = loadHomePositions();
		this.resourcePlacement = loadResourcePlacement();
		this.color = loadLevelColor();
		this.motherships = new Mothership[2];
		this.resources = Collections.synchronizedList(new LinkedList<Resource>());
		this.gameSpeed = DEFAULT_GAME_SPEED;
		Point2D base = updateBasePosition();
		this.x = (int) base.getX();
		this.y = (int) base.getY();
		//this.levelView = new LevelView();
	}

	@Override
	public void run() {

		Logger.info(this, "Start Level "+this);
		for(Mothership mothership : motherships) {
			mothership.startGame();
		}

		while(!isGameOver()) {
			if(!gameManager.isPause()) {
				for(Mothership mothership : motherships) {
					mothership.addActionPoint();
				}
			}
			try {
				Thread.sleep(gameSpeed);
			} catch (InterruptedException ex) {
				Logger.warn(this, "", ex);
			}
		}
		Logger.info(this, "Level Ends.");
	}

	public synchronized void reset() {
		Logger.info(this, "Load default values.");
		if(motherships[0] != null) motherships[0].reset();
		if(motherships[1] != null) motherships[1].reset();
//		motherships[0] = null;
//		motherships[1] = null;
		resources.clear();
		resourceKeyCounter = 0;
		Logger.info(this, "Place resources in "+this);
		placeResources();
		Logger.info(this, "Add "+resources.size()+" Resources");
	}

	public synchronized int generateNewResourceID() {
		return resourceKeyCounter++;
	}

	public void setTeamA(Team team) {
		motherships[0] = new Mothership(0, team, this);
	}
	
		public void setTeamB(Team team) {
		motherships[1] = new Mothership(1, team, this);
	}



	public boolean containsWall(Rectangle2D bounds) {
		Logger.debug(this, "CalcWallContain");
		if(levelWallPolygons != null) {
			for(Polygon wall : levelWallPolygons) {
				if(wall.intersects(bounds) | wall.contains(bounds)) {
					return true;
				}
			}
		}
		return !levelBorderPolygon.contains(bounds);
	}

	public boolean collisionDetected(Rectangle2D bounds) {
		return containsWall(bounds);
	}

	public void waitTillGameOver() {
		for(Mothership mothership : motherships) {
			mothership.waitTillGameEnd();
		}
	}

	public boolean isGameOver() {
		return gameManager.isGameOver();
	}


	protected abstract Polygon loadLevelBorderPolygon();
	protected abstract Polygon[] loadLevelWallPolygons();
	protected abstract Base2D[] loadHomePositions();
	protected abstract ResourcePlacement[] loadResourcePlacement();
	protected abstract Color loadLevelColor();

	public Polygon getLevelBorderPolygon() {
		return levelBorderPolygon;
	}

	public Polygon[] getLevelWallPolygons() {
		return levelWallPolygons;
	}

	public Base2D[] getHomePositions() {
		return homePositions;
	}
	public ResourcePlacement[] getResourcePlacement() {
		return resourcePlacement;
	}
	public Color getColor() {
		return color;
	}

	public Base2D getMothershipBase(int mothershipID) {
		return getHomePositions()[mothershipID];
	}

	public Mothership[] getMotherships() {
		return motherships;
	}

	public Iterator<Resource> getResources() {
		return resources.iterator();
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

	private void placeResources() {
		synchronized(resources) {
			ResourcePlacement[] resourcePlacements = getResourcePlacement();

			int resourceCounter = 0;
			for(ResourcePlacement placement : resourcePlacements) {
				resourceCounter += placement.getResourceCount();
			}
			GUIController.setEvent(new PropertyChangeEvent(this, GUIController.LOADING_STATE_CHANGE, resourceCounter, "Lade Resourcen"));
			
			for(ResourcePlacement placement : resourcePlacements) {
				resources.addAll(placement.getResources(this));
			}
		}
	}

	public void removeResource(Resource resource) {
		synchronized(resources) {
			resources.remove(resource);
			if(resource.getType() == Resource.ResourceType.Normal) {
				spornNewResource(resource.getPlacement());
			}
		}
	}

	private void spornNewResource(ResourcePlacement placement) {
		Resource newResource = new Resource(generateNewResourceID(), Resource.ResourceType.Normal, this, placement);
		resources.add(newResource);
		changes.firePropertyChange(CREATE_RESOURCE, null, newResource);

	}

	public void addResource(Resource resource) {
		synchronized(resources) {
			resources.add(resource);
		}
	}

	/**
	 * WARNING: method returns null in case of no close resource.
	 * @param agent
	 * @return
	 */
	public Resource getCloseResource(Agent agent) {
		synchronized(resources) {
			for(Resource resource : resources) {
				if(resource.getBounds().intersects(agent.getViewBounds()) &&
						(resource.getOwner() == null || resource.getOwner().getTeam() != agent.getTeam())) {
					return resource;
				}
			}
		}
		return null;
	}

	/**
	 * WARNING: method returns null in case of no touchable resource.
	 * @param agent
	 * @return
	 */
	public synchronized Resource getTouchableResource(Agent agent) {
		synchronized(resources) {
			for(Resource resource : resources) {
				if(resource.getBounds().intersects(agent.getBounds()) &&
						(resource.getOwner() == null || resource.getOwner().getTeam() != agent.getTeam())) {
					return resource;
				}
			}
		}
		return null;
	}

	public Mothership getAdversaryMothership(Agent agent) {
		for(Mothership mothership : motherships) {
			if((mothership.getTeam() != agent.getTeam()) &&
					!mothership.isMaxDamaged() &&
					mothership.getBounds().intersects(agent.getViewBounds())) {
				return mothership;
			}
		}
		return null;
	}

	public Agent getAdversaryAgent(Agent agent) {
		for(Mothership mothership : motherships) {
			if(mothership.getTeam() != agent.getTeam()) {
				for(Agent adversaryAgent : mothership.getAgends()) {
					if(adversaryAgent.hasFuel() && adversaryAgent.getBounds().intersects(agent.getViewBounds())) {
						return adversaryAgent;
					}
				}
			}
		}
		return null;
	}

	public Agent getLostTeamAgent(Agent agent) {
		for(Mothership mothership : motherships) {
			if(mothership.getTeam() == agent.getTeam()) {
				for(Agent teamAgent : mothership.getAgends()) {
					if(!teamAgent.hasFuel() && teamAgent.getBounds().intersects(agent.getViewBounds())) {
						return teamAgent;
					}
				}
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
	    changes.addPropertyChangeListener(l);
	    Logger.debug(this, "Add "+l.getClass()+" as new PropertyChangeListener.");
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
	    changes.removePropertyChangeListener(l);
	    Logger.debug(this, "Remove PropertyChangeListener "+l.getClass()+".");
	}
}
