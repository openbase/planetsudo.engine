/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.level;

import de.dc.planetsudo.game.AbstractGameObject;
import de.dc.util.data.Base2D;
import de.dc.util.data.Point2D;
import de.dc.util.logging.Logger;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import de.dc.planetsudo.game.GameManager;
import de.dc.planetsudo.game.Team;
import de.dc.planetsudo.level.levelobjects.Agent;
import de.dc.planetsudo.level.levelobjects.Mothership;
import de.dc.planetsudo.level.levelobjects.Resource;
import de.dc.planetsudo.main.GUIController;
import de.dc.util.sync.SyncObject;
import java.util.ArrayList;

/**
 *
 * @author divine
 */
public abstract class AbstractLevel extends AbstractGameObject implements Runnable {

    public final static long DEFAULT_AP_SPEED = 12 / Mothership.MAX_AGENT_COUNT; // Optimised Game Speed
    public final static String CREATE_RESOURCE = "create resource";
    public final static String GAME_SPEED_CHANGED = "SpeedChanged";
    private final Object RESOURCES_LOCK = new SyncObject("ResourcesLock");
    private final GameManager gameManager;
    private Point2D base;
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
//        this.base = calculateBasePosition(levelWallPolygons, base);

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
        //this.levelView = new LevelView();
    }

    @Override
    public void run() {

        Logger.info(this, "Start Level " + this);
        try {
            for (Mothership mothership : motherships) {
                mothership.startGame();
//				gameManager.setGameState(GameManager.GameState.Configuration);
            }
        } catch (Exception ex) {
            Logger.warn(this, "Could not start Level!", ex);
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
                Logger.warn(this, "", ex);
            }
        }
        Logger.info(this, "Level Ends.");
    }

    private int mineCounter = 0;

    public synchronized void reset() {

        if (teams[0] != null && teams[1] != null) {
            mineCounter = (Math.min(teams[0].getAgentCount(), teams[1].getAgentCount()) - 1);
        } else {
            mineCounter = 0;
        }

        Logger.info(this, "Load default values.");
        if (motherships[0] != null) {
            motherships[0].reset();
        }
        if (motherships[1] != null) {
            motherships[1].reset();
        }
//		motherships[0] = null;
//		motherships[1] = null;
        synchronized (RESOURCES_LOCK) {
            resources.clear();
        }
        resourceKeyCounter = 0;
        Logger.info(this, "Place resources in " + this);
        placeResources();
        Logger.info(this, "Add " + resources.size() + " Resources");
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
        Logger.debug(this, "CalcWallContain");
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
            return new ArrayList<Resource>(resources);
        }
    }

//    private static Point2D calculateBasePosition(final Polygon outerWalls, final Polygon[] walls, final Point2D existingBase) {
////        ;
////        ;
////        int x = (int) existingBase.getX();
////        int y = (int) existingBase.getY();
////        for (int i = 0; i < polygon.npoints; i++) {
////            x = Math.min(x, polygon.xpoints[i]);
////            y = Math.min(y, polygon.ypoints[i]);
////        }
//        Rectangle2D bounds;
//        for(Polygon polygon : polygons) {
//             bounds= polygon.getBounds2D();
//        }
//        
//        bounds.add(existingBase);
//        return new Point2D(bounds.getMinX(), bounds.getMinY());
//    }
    
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
        Logger.info(this, "Game speed " + speed + " -> " + gameSpeed);
        this.gameSpeed = Math.min(100, Math.max(speed, 1));
        changes.firePropertyChange(GAME_SPEED_CHANGED, null, gameSpeed);
    }

    public String getName() {
        return name;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
        Logger.debug(this, "Add " + l.getClass() + " as new PropertyChangeListener.");
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
        Logger.debug(this, "Remove PropertyChangeListener " + l.getClass() + ".");
    }

    public int getGameSpeed() {
        return gameSpeed;
    }

    public void setGameOverSoon() {
        motherships[0].setGameOverSoon();
        motherships[1].setGameOverSoon();
    }
}
