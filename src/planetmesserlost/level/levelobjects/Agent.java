/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.level.levelobjects;

import data.Direction2D;
import data.Point2D;
import exceptions.NotValidException;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Constructor;
import java.util.logging.Level;
import logging.Logger;
import math.RandomGenerator;
import planetmesserlost.game.ActionPoints;
import planetmesserlost.game.strategy.AbstractStrategy;
import planetmesserlost.level.LevelView;

/**
 *
 * @author divine
 */
public class Agent extends AbstractLevelObject {

	public final static int DEFAULT_START_FUEL = 1000;
	public final static int AGENT_SIZE = 50;
	protected final Mothership mothership;
	
	protected final ActionPoints actionPoints;
	protected Direction2D direction;
	protected int fuel;
	private boolean disabled;

	public Agent(String name, Mothership mothership) {
		super(mothership.registerAgent(), name, DYNAMIC_OBJECT, mothership.getLevel(), mothership.getAgentHomePosition(), AGENT_SIZE, AGENT_SIZE, ObjectShape.Oval);
		Logger.info(this, "Create "+this);
		this.mothership = mothership;
		this.actionPoints = new ActionPoints();
		if(id == -1) { // check if valid
			kill();
			return;
		}
		reset();
	}

	public Direction2D getDirection() {
		return direction;
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
		disabled = false;
	}

	public ActionPoints getActionPoints() {
		return actionPoints;
	}

	public int getFuel() {
		return fuel;
	}

	public boolean hasFuel() {
		return fuel > 0;
	}

	private boolean useFuel() {
		if(fuel == 0) {
			disabled = true;
			return false;
		}
		fuel--;
		return true;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public Mothership getMothership() {
		return mothership;
	}

	protected void kill() {
		Logger.info(this, "Kill "+name);
		mothership.removeAgent(this);
	}

	public boolean collisionDetected() {
		return level.collisionDetected(this);
	}

	public Rectangle2D getFutureBounds() {
		Point2D futurePosition = direction.translate(position.clone());
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
		if(useFuel()) {
			actionPoints.getActionPoint();
			position.translate(direction);
		}
	}

	public void turnAround() {
		if(useFuel()) {
			actionPoints.getActionPoint();
			direction.invert();
		}
	}

	public void turnLeft(int beta) {
		if(useFuel()) {
			actionPoints.getActionPoint();
			direction.setAngle(direction.getAngle()-beta);
		}
	}

	public void turnRight(int beta) {
		if(useFuel()) {
			actionPoints.getActionPoint();
			direction.setAngle(direction.getAngle()+beta);
		}
	}

	public void turnRandom() {
		try {
			if(useFuel()) {
				actionPoints.getActionPoint();
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
		if(percent < 0 || percent > 100) {
			Logger.error(this, "Could not refill fuel! Percent value["+percent+"] is not in bounds! Valuerange 0-100");
			return;
		}
		int toOrder = ((DEFAULT_START_FUEL*percent)/100) - fuel;

		fuel += mothership.orderFuel(toOrder);
	}

	public boolean closeToResource() {
		 return level.isCloseTo(this) != null;
	}
}
