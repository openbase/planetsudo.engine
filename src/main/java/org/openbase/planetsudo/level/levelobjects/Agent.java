package org.openbase.planetsudo.level.levelobjects;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2016 openbase.org
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
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.InvalidStateException;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.openbase.jul.visual.swing.engine.draw2d.AbstractResourcePanel.ObjectType;
import org.slf4j.Logger;
import org.openbase.planetsudo.game.ActionPoints;
import org.openbase.planetsudo.game.GameSound;
import org.openbase.planetsudo.game.SwatTeam;
import static org.openbase.planetsudo.game.SwatTeam.ALL;
import static org.openbase.planetsudo.game.SwatTeam.COMMANDER;
import org.openbase.planetsudo.game.Team;
import org.openbase.planetsudo.game.strategy.AbstractStrategy;
import org.openbase.planetsudo.geometry.Direction2D;
import org.openbase.planetsudo.geometry.Point2D;
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType;
import org.openbase.planetsudo.util.RandomGenerator;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class Agent extends AbstractLevelObject implements AgentInterface {

//	public final static int DEFAULT_START_FUEL = 2000;
    //public final static int DEFAULT_START_FUEL = 1000;
    public final static int AGENT_SIZE = 50;
    public final static int AGENT_VIEW_DISTANCE = AGENT_SIZE;
    public final static int DEFAULT_AGENT_SPEED = 6;

    private static final Logger LOGGER = LoggerFactory.getLogger(Agent.class);

    private final int fuelVolume;
    protected final Mothership mothership;
    private final ActionPoints actionPoints;
    protected Direction2D direction;
    protected int fuel;
    private boolean alive;
    private boolean attacked;
    private Resource resource;
    private boolean hasMine;
    private boolean hasTower;
    private boolean needSupport;
    private final boolean commanderFlag;
    private String lastAction;
    private AbstractLevelObject adversaryObject;
    private boolean gameOverSoon;
    private final Set<SwatTeam> swatTeamSet;

    public Agent(final String name, final boolean commanderFlag, final int fuelVolume, final Mothership mothership) {
        super(mothership.registerAgent(), name, ObjectType.Dynamic, mothership.getLevel(), mothership.getAgentHomePosition(), AGENT_SIZE, AGENT_SIZE, ObjectShape.Oval);
        LOGGER.info("Create " + this);
        this.lastAction = "Init";
        this.fuelVolume = fuelVolume;
        this.commanderFlag = commanderFlag;
        this.mothership = mothership;
        this.actionPoints = new ActionPoints(this);
        this.alive = true;
        this.attacked = false;
        this.gameOverSoon = false;
        this.swatTeamSet = new TreeSet<>();
        if (commanderFlag) {
            swatTeamSet.add(SwatTeam.COMMANDER);
        }

        if (id == -1) { // check if valid
            kill();
            return;
        }
        reset();
    }

    public int getFuelVolume() {
        return fuelVolume;
    }

    public int getAngle() {
        return direction.getAngle();
    }

    public Direction2D getDirection() {
        return direction;
    }

    public String getLastAction() {
        return lastAction;
    }

    public String getDescription() {
        return lastAction + " AP[" + actionPoints.getActionPoints() + "] " + SwatTeam.toString(swatTeamSet);
    }

    public void setLastAction(String lastAction) {
        this.lastAction = lastAction;
    }

    protected void spendFuel(int value) {
        fuel = Math.min(fuelVolume, fuel + value);
    }

    @Override
    protected void reset() {
        fuel = fuelVolume;
        gameOverSoon = false;
        position = mothership.getAgentHomePosition();
        hasMine = mothership.orderMine();
        hasTower = isCommander();
        try {
            direction = new Direction2D(RandomGenerator.getRandom(1, 360));
        } catch (InvalidStateException ex) {
            java.util.logging.Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }
        alive = true;
    }

    public Rectangle2D.Double getViewBounds() {
        Point2D point = direction.translate(new Point2D(position), AGENT_VIEW_DISTANCE);
        return new Rectangle2D.Double(point.getX() - AGENT_VIEW_DISTANCE, point.getY() - AGENT_VIEW_DISTANCE, AGENT_VIEW_DISTANCE * 2, AGENT_VIEW_DISTANCE * 2);
    }

    @Override
    public int getActionPoints() {
        return actionPoints.getActionPoints();
    }

    protected void addActionPoint() {
        actionPoints.addActionPoint();
    }

    @Override
    public int getFuel() {
        return fuel;
    }

    @Override
    public int getFuelInPercent() {
        return (fuel * 100) / fuelVolume;
    }

    public Team getTeam() {
        return mothership.getTeam();
    }

    @Override
    public boolean hasFuel() {
        return fuel > 0;
    }

    @Override
    public boolean hasMine() {
        return hasMine;
    }

    @Override
    public boolean hasTower() {
        return hasTower;
    }

    @Override
    public synchronized void deployMine() {
        actionPoints.getActionPoint(50);
        if (useFuel(5) == 5 || hasMine) {
            Resource newMine = new Resource(level.generateNewResourceID(), level, this);
            level.addResource(newMine);
            hasMine = false;
            GameSound.DeployMine.play();
        }
    }

    private int calcSpeed() {
        return isCarringResource() ? DEFAULT_AGENT_SPEED / 2 : DEFAULT_AGENT_SPEED;
    }

    private boolean useFuel() {
        return useFuel(1) == 1;
    }

    private void disable() {
        releaseResource();
        adversaryObject = null;
        isHelping = false;
    }

    private synchronized int useFuel(int value) {
        actionPoints.getActionPoint(1);
        if (fuel == 0) {
            disable();
            return 0;
        } else if (fuel < value) {
            value = fuel;
            fuel = 0;
            disable();
            return value;
        } else {
            fuel -= value;
            return value;
        }
    }

    @Override
    public boolean isDisabled() {
        return !isAlive() || !hasFuel();
    }

    @Override
    public boolean isCarringResource(final ResourceType type) {
        if (isCarringResource()) {
            return resource.getType().equals(type);
        }
        return false;
    }

    @Override
    public boolean isCarringResource() {
        return resource != null;
    }

    public Mothership getMothership() {
        return mothership;
    }

    @Override
    public void releaseResource() {
        if (isCarringResource() || resource != null) {
            resource.release();
            resource = null;
        }
    }

    protected Resource getResource() {
        Resource tmp2Resource = resource;
        resource = null;
        return tmp2Resource;
    }

    @Override
    public void kill() {
        LOGGER.info("Kill " + name);
        mothership.removeAgent(this);
        alive = false;
        fuel = 0;
        if (needSupport) {
            mothership.cancelSupport(this);
        }
        GameSound.AgentExplosion.play();
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public boolean isCollisionDetected() {
        return level.collisionDetected(getFutureBounds());
    }

    public Rectangle2D getFutureBounds() {
        Point2D futurePosition = direction.translate(position.clone(), calcSpeed());
        return new Rectangle2D.Double((int) futurePosition.getX() - (width / 2),
                (int) futurePosition.getY() - (height / 2),
                width,
                height);
    }

    protected void startGame() {
        LOGGER.info("startAgent" + this);
        // ############## Load strategy #########################
        Constructor<? extends AbstractStrategy> constructor;
        try {
            constructor = mothership.getTeam().getStrategy().getConstructor(AgentInterface.class);
        } catch (Exception ex) {
            LOGGER.error("Could not load strategy!", ex);
            kill();
            return;
        }
        AbstractStrategy strategy;
        try {
            strategy = constructor.newInstance(this);
        } catch (Exception ex) {
            LOGGER.error("Could not load strategy!", ex);
            kill();
            return;
        }
        new Thread(strategy, name + "Thread").start();
        // ######################################################
    }

    @Override
    public void go() {
        if (isCarringResource()) {
            actionPoints.getActionPoint(6);
        } else {
            actionPoints.getActionPoint(3);
        }

        if (!useFuel()) {
            return;
        }

        position.translate(direction, calcSpeed());
        if (level.collisionDetected(getBounds())) { // Is collied with wall?
            kill();
        }
    }

    @Override
    public void turnAround() {
        actionPoints.getActionPoint();
        if (useFuel()) {
            direction.invert();
        }
    }

    @Override
    public void goLeft(final int beta) {
        go();
        if (hasFuel()) {
            direction.setAngle(direction.getAngle() - beta);
        }

    }

    @Override
    public void goRight(final int beta) {
        go();
        if (hasFuel()) {
            direction.setAngle(direction.getAngle() + beta);
        }
    }

    @Override
    public void turnLeft(final int beta) {
        actionPoints.getActionPoint();
        if (useFuel()) {
            direction.setAngle(direction.getAngle() - beta);
        }
    }

    @Override
    public void turnRight(final int beta) {
        actionPoints.getActionPoint();
        if (useFuel()) {
            direction.setAngle(direction.getAngle() + beta);
        }
    }

    @Override
    public void turnRandom() {
        turnRandom(360);
    }

    @Override
    public void turnRandom(int opening) {
        actionPoints.getActionPoint();
        try {
            if (useFuel()) {
                final int randomAngle = RandomGenerator.getRandom(0, opening) - (opening / 2);
                direction.setAngle(direction.getAngle() + randomAngle);
            }
        } catch (InvalidStateException ex) {
            LOGGER.error("Could not turn random.", ex);
        }
    }

    @Override
    public void goToMothership() {
        actionPoints.getActionPoint();
        go();
        if (hasFuel()) {
            direction.setAngle(mothership.getLevelView().getAbsolutAngle(this));
        }
    }

    @Override
    public boolean isAtMothership() {
        return mothership.getBounds().contains(getBounds());
    }

    @Override
    public void orderFuel(final int percent) {
        actionPoints.getActionPoint(20);
        if (percent < 0 || percent > 100) {
            LOGGER.error("Could not refill fuel! Percent value[" + percent + "] is not in bounds! Valuerange 0-100");
            return;
        }

        for (int toOrder = ((fuelVolume * percent) / 100) - fuel; toOrder > 0; toOrder--) {
            fuel += mothership.orderFuel(1, this);
            actionPoints.getActionPoint(2);
        }
        GameSound.RechargeFuel.play();
    }

    @Override
    public boolean seeResource() {
        return level.getCloseResource(this) != null;
    }
    
    @Override
    public boolean seeResource(final ResourceType resourceType) {
        return level.getCloseResource(this, resourceType) != null;
    }

    @Override
    public void goToResource() {
        actionPoints.getActionPoint();
        if (useFuel()) {
            final Resource resourceToGo = level.getCloseResource(this);
            if (resourceToGo != null) {
                go();
                direction.turnTo(position, resourceToGo.position);
            }
        }
    }
    
    @Override
    public void goToResource(final ResourceType resourceType) {
        actionPoints.getActionPoint();
        if (useFuel()) {
            final Resource resourceToGo = level.getCloseResource(this, resourceType);
            if (resourceToGo != null) {
                go();
                direction.turnTo(position, resourceToGo.position);
            }
        }
    }

    @Override
    public void deliverResourceToMothership() {
        actionPoints.getActionPoint(10);
        try {
            if (isCarringResource() && useFuel() && isAtMothership()) {
                mothership.passResource(this);
                GameSound.DeliverResource.play();
            }
        } catch (CouldNotPerformException ex) {
            LOGGER.warn("Could not deliver resource to Mothership!", ex);
        }
    }

    @Override
    public boolean isTouchingResource(Resource.ResourceType type) {
        return getResourceType() == type;
    }

    @Override
    public void searchResources() {
        final int startAngle = direction.getAngle();
        for (int i = 0; i < 18; i++) {
            actionPoints.getActionPoint(10);
            if (seeResource()) {
                return;
            }
            turnLeft(20);
        }
    }

    @Override
    public Resource.ResourceType getResourceType() {
        final Resource tmpResource = level.getTouchableResource(this);
        if (tmpResource == null) {
            return Resource.ResourceType.Unknown;
        }

        if (tmpResource.getType() == ResourceType.Mine && tmpResource.wasPlacedByTeam() != getTeam()) {
            return ResourceType.ExtremPoint;
        }
        return tmpResource.getType();
    }

    @Override
    public boolean isTouchingResource() {
        return level.getTouchableResource(this) != null;
    }

    @Override
    public void pickupResource() {
        actionPoints.getActionPoint(10);
        try {
            if (useFuel()) {

                final Resource resourceToCollect = level.getTouchableResource(this);
                if (resourceToCollect != null && resourceToCollect.getType() != ResourceType.ExtraAgentFuel) {
                    releaseResource();
                }
                if (resourceToCollect != null && resourceToCollect.setBusy(getTeam())) {
                    direction.turnTo(position, resourceToCollect.position);
                    actionPoints.getActionPoint(resourceToCollect.getCapturingActionPoints());
                    carryResource(resourceToCollect);
                }
            }
        } catch (InvalidStateException ex) {
            LOGGER.warn("Could not pickup resource!", ex);
        }
    }

    private void carryResource(final Resource resource) throws InvalidStateException {
        if (resource != null) {
            if (resource.capture(this)) {
                this.resource = resource;
            }
        }
    }

    @Override
    public boolean seeAdversaryAgent() {
        return level.getAdversaryAgent(this) != null;
    }

    @Override
    public boolean seeAdversaryMothership() {
        return level.getAdversaryMothership(this) != null;
    }

    @Override
    public boolean isUnderAttack() {
        final boolean oldattackedValue;
        oldattackedValue = attacked;
        attacked = false;
        return oldattackedValue;
    }
    private int catchedfuel;

    @Override
    public void fightWithAdversaryAgent() {
        actionPoints.getActionPoint();
        if (useFuel()) {
            final Agent adversaryAgent = level.getAdversaryAgent(this);
            adversaryObject = adversaryAgent;
            if (adversaryAgent != null) {
                adversaryAgent.attacked = true;
                actionPoints.getActionPoint(20);
                direction.turnTo(position, adversaryAgent.position);
                if (adversaryAgent.hasFuel()) {
                    catchedfuel = (adversaryAgent.useFuel((Mothership.AGENT_FUEL_VOLUME / 500) * 2) / 3);
                    fuel = Math.min(fuelVolume, fuel + catchedfuel);
                }
                GameSound.Laser.play();
            }
        }
    }

    @Override
    public void fightWithAdversaryMothership() {
        actionPoints.getActionPoint();
        if (useFuel()) {
            Mothership adversaryMothership = level.getAdversaryMothership(this);
            adversaryObject = adversaryMothership;
            if (adversaryMothership != null) {
                actionPoints.getActionPoint(30);
                direction.turnTo(position, adversaryMothership.position);
                adversaryMothership.attack();
                GameSound.Laser.play();
            }
        }
    }

    @Override
    public boolean isFighting() {
        return adversaryObject != null;
    }

    public AbstractLevelObject isFightingWith() {
        AbstractLevelObject adversary = adversaryObject;
        adversaryObject = null;
        return adversary;
    }

    @Override
    public boolean seeLostTeamAgent() {
        return level.getLostTeamAgent(this) != null;
    }
    private boolean isHelping = false;
    private AbstractLevelObject helpLevelObject = null;

    @Override
    public void spendTeamAgentFuel(int value) {
        if (useFuel()) {
            Agent teamAgent = level.getLostTeamAgent(this);
            if (teamAgent != null) {
                helpLevelObject = teamAgent;
                isHelping = true;
                direction.turnTo(position, teamAgent.position);
                actionPoints.getActionPoint(value * 2);
                teamAgent.fuel += useFuel(value);
            }
            GameSound.SpendFuel.play();
        }
        isHelping = false;
    }
    private AbstractLevelObject helpLevelObjectOld;

    public AbstractLevelObject wasHelping() {
        if (!isHelping) {
            helpLevelObjectOld = helpLevelObject;
            helpLevelObject = null;
            return helpLevelObjectOld;
        }
        return helpLevelObject;
    }

    @Override
    public void repairMothership() {
        actionPoints.getActionPoint(30);
        if (useFuel() && isAtMothership()) {
            mothership.repair();
        }
    }

    @Override
    public void orderSupport() {
        actionPoints.getActionPoint(5);
        if (!needSupport) {
            mothership.callForSupport(this);
            GameSound.CallForSupport.play();
        }
    }

    @Override
    public void goToSupportAgent() {
        try {
            Agent agentToSupport = mothership.getAgentToSupport(this);
            if (agentToSupport != this) {
                go();
                direction.setAngle(agentToSupport.getLevelView().getAbsolutAngle(this));
            } else {
                throw new CouldNotPerformException("Could not support himself!");
            }
        } catch (CouldNotPerformException ex) {
            ExceptionPrinter.printHistory(new CouldNotPerformException("Could not goToSuppordAgent!", ex), LOGGER);
        }
    }

    @Override
    public boolean isSupportOrdered() {
        return needSupport;
    }

    protected void setNeedSupport(final boolean needSupport) {
        this.needSupport = needSupport;
    }

    @Override
    public void cancelSupport() {
        actionPoints.getActionPoint(5);
        if (needSupport) {
            mothership.cancelSupport(this);
        }
    }

    @Override
    public void deployMarker() {
        actionPoints.getActionPoint(5);
        if (useFuel()) {
            mothership.placeMarker(position.clone());
        }
    }

    public void goToMarker() {
        try {
            go();
            direction.setAngle(mothership.getMarker().getLevelView().getAbsolutAngle(this));
        } catch (CouldNotPerformException ex) {
            LOGGER.warn("Could not goToMarker!", ex);
        }
    }

    @Override
    public boolean isCommander() {
        return commanderFlag;
    }

    @Override
    public boolean seeMarker() {
        return mothership.getTeamMarker().seeMarker(this);
    }

    @Override
    public boolean seeTower() {
        return mothership.getTower().seeTower(this);
    }

    @Override
    public int getTeamPoints() {
        return mothership.getTeam().getPoints();
    }

    @Override
    public boolean isGameOverSoon() {
        return gameOverSoon;
    }

    public void joinSwatTeam(final SwatTeam... swatTeams) {
        List<SwatTeam> swatTeamList = Arrays.asList(swatTeams);
        if (!Collections.disjoint(swatTeamList, SwatTeam.NEGATED_TEAM_LIST)) {
            LOGGER.error("It's not allowed to join any negated swat teams!");
            kill();
            return;
        }
        if (swatTeamList.contains(COMMANDER)) {
            LOGGER.error("It's not allowed to setup the commander manually!");
            kill();
            return;
        }
        if (swatTeamList.contains(ALL)) {
            LOGGER.error("ALL is not a vaild swat team!");
            kill();
            return;
        }
        swatTeamSet.addAll(swatTeamList);
    }

    @Override
    public boolean isMemberOfSwatTeam(final SwatTeam... swatTeams) {
        return isMemberOfSwatTeam(Arrays.asList(swatTeams));
    }

    @Override
    public boolean isMemberOfSwatTeam(final Collection<SwatTeam> swatTeams) {

        // Check if agent was excluded.
        for (SwatTeam swat : swatTeams) {
            if (swat.negative) {
                if (swatTeamSet.contains(swat.opposition)) {
                    return false;
                }
            }
        }

        // Check if wildcard was set
        if (swatTeams.contains(ALL)) {
            return true;
        }

        // Check if agent is included
        return !Collections.disjoint(swatTeams, swatTeamSet);
    }

    protected void setGameOverSoon() {
        this.gameOverSoon = true;
        LOGGER.info("Game over soon!");
    }

    @Override
    public void erectTower(final Tower.TowerType type) {
        try {
            actionPoints.getActionPoint(1500);
            useFuel(50);
            if (!isCommander()) {
                LOGGER.warn("Only the commander is able to erect a tower, deployment failed!");
                return;
            }
            mothership.getTower().erect(type, this);
            hasTower = false;
        } catch (CouldNotPerformException ex) {
            kill();
            ExceptionPrinter.printHistory(new CouldNotPerformException("Could not erect Tower!", ex), LOGGER);
        }
    }

    @Override
    public void dismantleTower() {
        try {
            actionPoints.getActionPoint(500);
            useFuel(50);
            if (!isCommander()) {
                LOGGER.warn("Only the commander is able to dismantle a tower, deployment failed!");
                return;
            }
            
            if(!mothership.getTower().seeTower(this)) {
                LOGGER.warn("The commander is not close enough to dismantle the tower!");
                return;
            }
            mothership.getTower().dismantle(this);
            hasTower = true;
        } catch (CouldNotPerformException ex) {
            kill();
            ExceptionPrinter.printHistory(new CouldNotPerformException("Could not dismantle Tower!", ex), LOGGER);
        }
    }
}
