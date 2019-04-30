package org.openbase.planetsudo.level;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2019 openbase.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
import org.openbase.planetsudo.game.AbstractGameObject;
import org.slf4j.Logger;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import org.openbase.planetsudo.game.GameManager;
import org.openbase.planetsudo.game.Team;
import org.openbase.planetsudo.level.levelobjects.Agent;
import org.openbase.planetsudo.level.levelobjects.Mothership;
import org.openbase.planetsudo.level.levelobjects.Resource;
import org.openbase.planetsudo.main.GUIController;
import java.util.ArrayList;
import org.openbase.jul.schedule.SyncObject;
import org.openbase.planetsudo.geometry.Base2D;
import org.openbase.planetsudo.geometry.Point2D;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public abstract class AbstractLevel extends AbstractGameObject implements Runnable {

    public final static long DEFAULT_AP_SPEED = 12 / Mothership.MAX_AGENT_COUNT; // Optimised Game Speed
    public final static String CREATE_RESOURCE = "create resource";
    public final static String GAME_SPEED_CHANGED = "SpeedChanged";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Object RESOURCES_LOCK = new SyncObject("ResourcesLock");
    private final Object TOWER_LOCK = new SyncObject("TowerLock");
    private final GameManager gameManager;
    private final Point2D base;
    private final Polygon levelBorderPolygon;
    private final Polygon[] levelWallPolygons;
    private final Base2D[] homePositions;
    private final ResourcePlacement[] resourcePlacement;
    private final Color color;
    private final PropertyChangeSupport changes;
    private final String name;
    private final Mothership[] motherships;
    private final List<Resource> resources;
    private final long apSpeed;
    private int gameSpeed;
    private final int x, y;
    private int resourceKeyCounter;
    private final Team[] teams;

    public AbstractLevel() {
        this.gameManager = GameManager.getInstance();
        this.teams = new Team[2];
        this.changes = new PropertyChangeSupport(this);
        this.name = getClass().getSimpleName();

        // Load level
        this.levelBorderPolygon = loadLevelBorderPolygon();
        this.levelWallPolygons = loadLevelWallPolygons();
        this.homePositions = loadHomePositions();
        this.resourcePlacement = loadResourcePlacement();

        // Base transformation
        this.base = calculateBasePosition(levelBorderPolygon);

        levelBorderPolygon.translate((int) -base.getX(), (int) -base.getY());
        if (levelWallPolygons != null) {
            for (Polygon p : levelWallPolygons) {
                p.translate((int) -base.getX(), (int) -base.getY());
            }
        }
        for (Base2D homepos : homePositions) {
            homepos.translateIntoBase(base);
        }

        for (ResourcePlacement placement : resourcePlacement) {
            placement.translateIntoBase(base);
        }

        this.color = loadLevelColor();
        this.motherships = new Mothership[2];
        this.resources = new ArrayList<Resource>();
        this.apSpeed = DEFAULT_AP_SPEED;
        this.gameSpeed = 50;

        final Rectangle2D bounds = levelBorderPolygon.getBounds2D();
        this.x = (int) bounds.getX();
        this.y = (int) bounds.getY();
    }

    @Override
    public void run() {

        logger.info("Start Level " + this);
        try {
            for (Mothership mothership : motherships) {
                mothership.startGame();
            }
        } catch (Exception ex) {
            logger.warn("Could not start Level!", ex);
            return;
        }

        while (!isGameOver()) {
            if (!gameManager.isPause()) {
                for (Mothership mothership : motherships) {
                    mothership.addActionPoint();
                }
            }
            try {
                Thread.sleep(apSpeed);
            } catch (InterruptedException ex) {
                logger.warn("", ex);
            }
        }
        logger.info("Level Ends.");
    }

    private int mineCounter = 0;

    public synchronized void reset() {

        if (teams[0] != null && teams[1] != null) {
            mineCounter = (Math.min(teams[0].getAgentCount(), teams[1].getAgentCount()) - 1);
        } else {
            mineCounter = 0;
        }

        logger.info("Load default values.");
        if (motherships[0] != null) {
            motherships[0].reset();
        }
        if (motherships[1] != null) {
            motherships[1].reset();
        }
        
        synchronized (RESOURCES_LOCK) {
            resources.clear();
        }
        resourceKeyCounter = 0;
        logger.info("Place resources in " + this);
        placeResources();
        logger.info("Add " + resources.size() + " Resources");
    }

    public synchronized int generateNewResourceID() {
        return resourceKeyCounter++;
    }

    public void setTeamA(Team team) {
        teams[0] = team;
        motherships[0] = new Mothership(0, team, this);
    }

    public void setTeamB(Team team) {
        teams[1] = team;
        motherships[1] = new Mothership(1, team, this);
    }

    public int getMineCounter() {
        return mineCounter;
    }

    public boolean containsWall(Rectangle2D bounds) {
        logger.debug("CalcWallContain");
        if (levelWallPolygons != null) {
            for (Polygon wall : levelWallPolygons) {
                if (wall.intersects(bounds) | wall.contains(bounds)) {
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
        for (Mothership mothership : motherships) {
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

    public List<Resource> getResources() {
        synchronized (RESOURCES_LOCK) {
            return new ArrayList<>(resources);
        }
    }
    
    private static Point2D calculateBasePosition(final Polygon polygon) {
        Rectangle2D bounds = polygon.getBounds2D();
        return new Point2D(bounds.getMinX(), bounds.getMinY());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return name;
    }

    private void placeResources() {
        synchronized (RESOURCES_LOCK) {
            ResourcePlacement[] resourcePlacements = getResourcePlacement();

            int resourceCounter = 0;
            for (ResourcePlacement placement : resourcePlacements) {
                resourceCounter += placement.getResourceCount();
            }
            GUIController.setEvent(new PropertyChangeEvent(this, GUIController.LOADING_STATE_CHANGE, resourceCounter, "Lade Resourcen"));

            for (ResourcePlacement placement : resourcePlacements) {
                resources.addAll(placement.getResources(this));
            }
        }
    }
    
    public void removeResource(Resource resource) {
        synchronized (RESOURCES_LOCK) {
            resources.remove(resource);
        }
        if (resource.getType() == Resource.ResourceType.Normal) {
            spornNewResource(resource.getPlacement());
        }
    }

    private void spornNewResource(ResourcePlacement placement) {
        Resource newResource = new Resource(generateNewResourceID(), Resource.ResourceType.Normal, this, placement);
        addResource(newResource);
    }

    public void addResource(Resource resource) {
        synchronized (RESOURCES_LOCK) {
            resources.add(resource);
        }
        changes.firePropertyChange(CREATE_RESOURCE, null, resource);
    }

    /**
     * WARNING: method returns null in case of no close resource.
     *
     * @param agent
     * @param resourceType
     * @return
     */
    public Resource getCloseResource(Agent agent, final Resource.ResourceType resourceType) {
        synchronized (RESOURCES_LOCK) {
            for (Resource resource : resources) {
                if (!resource.isUsed()
                        && resource.getType().equals(resourceType)
                        && (!resource.isOwned() || resource.getOwner().getTeam() != agent.getTeam())
                        && resource.isSaveFor(agent)
                        && resource.getBounds().intersects(agent.getViewBounds())) {
                    return resource;
                }
            }
        }
        return null;
    }
    
    /**
     * WARNING: method returns null in case of no close resource.
     *
     * @param agent
     * @return
     */
    public Resource getCloseResource(Agent agent) {
        synchronized (RESOURCES_LOCK) {
            for (Resource resource : resources) {
                if (!resource.isUsed()
                        && (!resource.isOwned() || resource.getOwner().getTeam() != agent.getTeam())
                        && resource.isSaveFor(agent)
                        && resource.getBounds().intersects(agent.getViewBounds())) {
                    return resource;
                }
            }
        }
        return null;
    }

    /**
     * WARNING: method returns null in case of no touchable resource.
     *
     * @param agent
     * @return
     */
    public Resource getTouchableResource(Agent agent) {
        synchronized (RESOURCES_LOCK) {
            for (Resource resource : resources) {
                if (!resource.isUsed() && resource.getBounds().intersects(agent.getBounds())
                        && (resource.getOwner() == null || resource.getOwner().getTeam() != agent.getTeam())) {
                    return resource;
                }
            }
        }
        return null;
    }

    public Mothership getAdversaryMothership(Agent agent) {
        for (Mothership mothership : motherships) {
            if ((mothership.getTeam() != agent.getTeam())
                    && !mothership.isMaxDamaged()
                    && mothership.getBounds().intersects(agent.getViewBounds())) {
                return mothership;
            }
        }
        return null;
    }

    public Agent getAdversaryAgent(Agent agent) {
        for (Mothership mothership : motherships) {
            if (mothership.getTeam() != agent.getTeam()) {
                for (Agent adversaryAgent : mothership.getAgents()) {
                    if (adversaryAgent.hasFuel() && adversaryAgent.getBounds().intersects(agent.getViewBounds())) {
                        return adversaryAgent;
                    }
                }
            }
        }
        return null;
    }

    public Agent getLostTeamAgent(Agent agent) {
        for (Mothership mothership : motherships) {
            if (mothership.getTeam() == agent.getTeam()) {
                for (Agent teamAgent : mothership.getAgents()) {
                    if (!teamAgent.hasFuel() && teamAgent.getBounds().intersects(agent.getViewBounds())) {
                        return teamAgent;
                    }
                }
            }
        }
        return null;
    }

    public void setGameSpeed(int speed) {
        if (gameSpeed == speed) {
            return;
        }
        logger.info("Game speed " + speed + " -> " + gameSpeed);
        this.gameSpeed = Math.min(100, Math.max(speed, 1));
        changes.firePropertyChange(GAME_SPEED_CHANGED, null, gameSpeed);
    }

    @Override
    public String getName() {
        return name;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
        logger.debug("Add " + l.getClass() + " as new PropertyChangeListener.");
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
        logger.debug("Remove PropertyChangeListener " + l.getClass() + ".");
    }

    public int getGameSpeed() {
        return gameSpeed;
    }
    
    public void setGameOverSoon() {
        motherships[0].setGameOverSoon();
        motherships[1].setGameOverSoon();
    }
}
