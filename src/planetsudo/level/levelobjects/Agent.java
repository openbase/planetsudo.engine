/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.level.levelobjects;

import data.Direction2D;
import data.Point2D;
import exceptions.NotValidException;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Constructor;
import java.util.logging.Level;
import logging.Logger;
import math.RandomGenerator;
import planetsudo.game.ActionPoints;
import planetsudo.game.Team;
import planetsudo.game.strategy.AbstractStrategy;

/**
 *
 * @author divineactionPoints.getActionPoint();
 */
public class Agent extends AbstractLevelObject {

	public final static int DEFAULT_START_FUEL = 2000;
	public final static int AGENT_SIZE = 50;
	public final static int AGENT_VIEW_DISTANCE = AGENT_SIZE;
	public final static int DEFAULT_AGENT_SPEED = 5;

	protected final Mothership mothership;
	
	protected final ActionPoints actionPoints;
	protected Direction2D direction;
	protected int fuel;
	private boolean alive;
	private Resource resource;
	private String lastAction;
	private AbstractLevelObject adversaryObject;

	public Agent(String name, Mothership mothership) {
		super(mothership.registerAgent(), name, DYNAMIC_OBJECT, mothership.getLevel(), mothership.getAgentHomePosition(), AGENT_SIZE, AGENT_SIZE, ObjectShape.Oval);
		Logger.info(this, "Create "+this);
		this.lastAction = "Init";
		this.mothership = mothership;
		this.actionPoints = new ActionPoints(this);
		this.alive = true;
		if(id == -1) { // check if valid
			kill();
			return;
		}
		reset();
	}

	public Direction2D getDirection() {
		return direction;
	}

	public String getLastAction() {
		return lastAction+" ["+actionPoints.getActionPoints()+"]";
	}

	public void setLastAction(String lastAction) {
		this.lastAction = lastAction;
	}

	@ Override
	protected void reset() {
		fuel = DEFAULT_START_FUEL;
		position = mothership.getAgentHomePosition();
		try {
			direction = new Direction2D(RandomGenerator.getRandom(1, 360));
		} catch (NotValidException ex) {
			java.util.logging.Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
		}
		alive = true;
	}

	public Rectangle2D.Double getViewBounds() {
		Point2D point = direction.translate(new Point2D(position), AGENT_VIEW_DISTANCE);
		return new Rectangle2D.Double(point.getX()-AGENT_VIEW_DISTANCE, point.getY()-AGENT_VIEW_DISTANCE, AGENT_VIEW_DISTANCE*2, AGENT_VIEW_DISTANCE*2);
	}

	public ActionPoints getActionPoints() {
		return actionPoints;
	}

	public int getFuel() {
		return fuel;
	}

	public Team getTeam() {
		return mothership.getTeam();
	}

	public boolean hasFuel() {
		return fuel > 0;
	}

	private int calcSpeed() {
		return isCarringResource() ? DEFAULT_AGENT_SPEED/2 : DEFAULT_AGENT_SPEED;
	}


	private boolean useFuel() {
		return useFuel(1) == 1;
	}

	private void disable() {
		if(isCarringResource()) {
			resource.release();
		}
		adversaryObject = null;
	}

	private synchronized int useFuel(int value) {
		if(fuel == 0) {
			disable();
			return 0;
		} else if(fuel < value) {
			value = fuel;
			fuel = 0;
			disable();
			return value;
		} else {
			fuel -= value;
			return value;
		}
	}

	public boolean isDisabled() {
		return !isAlive() || !hasFuel();
	}

	public boolean isCarringResource() {
		return resource != null;
	}

	public Mothership getMothership() {
		return mothership;
	}

	public void releaseResource() {
		resource.release();
	}

	public Resource getResource() {
		Resource tmpResource = resource;
		resource = null;
		return tmpResource;
	}

	protected void kill() {
		Logger.info(this, "Kill "+name);
		mothership.removeAgent(this);
		alive = false;
	}

	public boolean isAlive() {
		return alive;
	}

	public boolean collisionDetected() {
		return level.collisionDetected(getFutureBounds());
	}

	public Rectangle2D getFutureBounds() {
		Point2D futurePosition = direction.translate(position.clone(), calcSpeed());
		return new Rectangle2D.Double(	(int)futurePosition.getX()-(width/2),
					(int)futurePosition.getY()-(height/2),
					width,
					height);
	}

	protected void startGame() {
		Logger.info(this, "startAgend"+this);
		// ############## Load strategy #########################
		Constructor<? extends AbstractStrategy> constructor;
		try {
			constructor = mothership.getTeam().getStrategy().getConstructor(this.getClass());
		} catch (Exception ex) {
			Logger.error(this, "Could not load strategy!", ex);
			kill();
			return;
		}
		AbstractStrategy strategy;
		try {
			strategy = constructor.newInstance(this);
		} catch (Exception ex) {
			Logger.error(this, "Could not load strategy!", ex);
			kill();
			return;
		}
		new Thread(strategy, name+"Thread").start();
		// ######################################################
	}

	public void goStraightAhead() {
		actionPoints.getActionPoint(4);
		if(useFuel()) {
			position.translate(direction, calcSpeed());
			if(level.collisionDetected(getBounds())) { // Is collied with wall?
				kill();
			}
		}
	}

	public void turnAround() {
		actionPoints.getActionPoint();
		if(useFuel()) {
			direction.invert();
		}
	}

	public void turnLeft(int beta) {
		actionPoints.getActionPoint();
		if(useFuel()) {
			direction.setAngle(direction.getAngle()-beta);
		}
	}

	public void turnRight(int beta) {
		actionPoints.getActionPoint();
		if(useFuel()) {
			direction.setAngle(direction.getAngle()+beta);
		}
	}

	public void turnRandom() {
		actionPoints.getActionPoint();
		try {
			if(useFuel()) {
				direction.setAngle(RandomGenerator.getRandom(1, 360));
			}
		} catch (NotValidException ex) {
			Logger.error(this, "Could not turn random.", ex);
		}
	}

	public void moveOneStepInTheMothershipDirection() {
		direction.setAngle(mothership.getLevelView().getAbsolutAngle(this));
		goStraightAhead();
	}

	public boolean isAtMothership() {
		return mothership.getBounds().contains(getBounds());
	}

	public void orderFuel(int percent) {
//		if(actionPoints.getActionPoints() > 20) {
				actionPoints.getActionPoint(20);
//		}
		if(percent < 0 || percent > 100) {
			Logger.error(this, "Could not refill fuel! Percent value["+percent+"] is not in bounds! Valuerange 0-100");
			return;
		}

		for(int toOrder = ((DEFAULT_START_FUEL*percent)/100) - fuel; toOrder>0;toOrder--) {
			fuel += mothership.orderFuel(1);
			actionPoints.getActionPoint(2);
		}
	}

	public boolean seeResource() {
		return level.getCloseResource(this) != null;
	}

	public void goToResource() {
		actionPoints.getActionPoint();
		if(useFuel()) {
			Resource resourceToGo = level.getCloseResource(this);
			if(resourceToGo  != null) {
				direction.turnTo(position, resourceToGo.position);
				position.translate(direction, calcSpeed());
			}
		}
	}

	public void deliverResourceToMothership() {
		actionPoints.getActionPoint(10);
		if(isCarringResource()) {
			mothership.passResource(this);
		}
	}

	public boolean toucheResource() {
		return level.getTouchableResource(this) != null;
	}

	public void pickupResource() {
		actionPoints.getActionPoint(10);
		Resource resourceToCollect = level.getTouchableResource(this);
		if(resourceToCollect != null && resourceToCollect.setBusy(getTeam())) {
			direction.turnTo(position, resourceToCollect.position);
			actionPoints.getActionPoint(resourceToCollect.getCapturingActionPoints());
			this.carryResource(resourceToCollect);
		}
	}

	private void carryResource(Resource resource) {
		if(resource != null) {
			if(resource.capture(this)) {
				this.resource = resource;
			}
		}
	}

	public boolean seeAdversaryAgent() {
		return level.getAdversaryAgent(this) != null;
	}

	public boolean seeAdversaryMothership() {
		return level.getAdversaryMothership(this) != null;
	}

	private int catchedfuel;
	public void fightWithAdversaryAgent() {
		actionPoints.getActionPoint();
		if(useFuel()) {
			Agent adversaryAgent = level.getAdversaryAgent(this);
			adversaryObject = adversaryAgent;
			if(adversaryAgent != null) {
				actionPoints.getActionPoint(20);
				direction.turnTo(position, adversaryAgent.position);
				if(adversaryAgent.hasFuel()) {
					catchedfuel = (adversaryAgent.useFuel((DEFAULT_START_FUEL/100)*2)/2);
					if(fuel+catchedfuel <= DEFAULT_START_FUEL) {
					fuel += catchedfuel;
					}
				} 
			}
		}
	}

	public void fightWithAdversaryMothership() {
		actionPoints.getActionPoint();
		if(useFuel()) {
			Mothership adversaryMothership = level.getAdversaryMothership(this);
			adversaryObject = adversaryMothership;
			if(adversaryMothership != null) {
				actionPoints.getActionPoint(10);
				direction.turnTo(position, adversaryMothership.position);
				adversaryMothership.attack();
			}
		}
	}

	public boolean isFighting() {
		return adversaryObject != null;
	}

	public AbstractLevelObject isFightingWith() {
		AbstractLevelObject adversary = adversaryObject;
		adversaryObject = null;
		return adversary;
	}

	public boolean seeLostTeamAgent() {
		return level.getLostTeamAgent(this) != null;
	}

	public void spendFuelTeamAgend(int value) {
		actionPoints.getActionPoint(20);
		if(useFuel()) {
			Agent teamAgent = level.getLostTeamAgent(this);
			if(teamAgent  != null) {
				direction.turnTo(position, teamAgent.position);
				actionPoints.getActionPoint(value);
				teamAgent.fuel += useFuel(value);
			}
		}
	}

	public void repaireMothership() {
		actionPoints.getActionPoint(50);
		if(useFuel()) {
			mothership.repaire();
		}
	}
}
