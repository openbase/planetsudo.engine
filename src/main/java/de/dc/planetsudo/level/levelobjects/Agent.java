/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.level.levelobjects;

import de.dc.util.data.Direction2D;
import de.dc.util.data.Point2D;
import de.dc.util.exceptions.NotValidException;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Constructor;
import java.util.logging.Level;
import de.dc.util.logging.Logger;
import de.dc.util.math.RandomGenerator;
import de.dc.planetsudo.game.ActionPoints;
import de.dc.planetsudo.game.GameSound;
import de.dc.planetsudo.game.Team;
import de.dc.planetsudo.game.strategy.AbstractStrategy;
import de.dc.planetsudo.level.levelobjects.Resource.ResourceType;
import de.dc.util.exceptions.CouldNotPerformException;
import de.dc.util.view.engine.draw2d.AbstractResourcePanel.ObjectType;

/**
 *
 * @author divineactionPoints.getActionPoint();
 */
public class Agent extends AbstractLevelObject implements AgentInterface {

//	public final static int DEFAULT_START_FUEL = 2000;
	//public final static int DEFAULT_START_FUEL = 1000;
	public final static int AGENT_SIZE = 50;
	public final static int AGENT_VIEW_DISTANCE = AGENT_SIZE;
	public final static int DEFAULT_AGENT_SPEED = 6;
	private final int fuelVolume;
	protected final Mothership mothership;
	private final ActionPoints actionPoints;
	protected Direction2D direction;
	protected int fuel;
	private boolean alive;
	private boolean attacked;
	private Resource resource;
	private boolean hasMine;
	private boolean needSupport;
	private boolean commanderFlag;
	private String lastAction;
	private AbstractLevelObject adversaryObject;
	private boolean gameOverSoon;

	public Agent(final String name, final boolean commanderFlag, final int fuelVolume, final Mothership mothership) {
		super(mothership.registerAgent(), name, ObjectType.Dynamic, mothership.getLevel(), mothership.getAgentHomePosition(), AGENT_SIZE, AGENT_SIZE, ObjectShape.Oval);
		Logger.info(this, "Create " + this);
		this.lastAction = "Init";
		this.fuelVolume = fuelVolume;
		this.commanderFlag = commanderFlag;
		this.mothership = mothership;
		this.actionPoints = new ActionPoints(this);
		this.alive = true;
		this.attacked = false;
		this.gameOverSoon = false;
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
		return lastAction + " [" + actionPoints.getActionPoints() + "]";
	}

	public void setLastAction(String lastAction) {
		this.lastAction = lastAction;
	}

	protected void spendFuel(int value) {
		fuel = Math.min(fuelVolume, fuel + value);
	}

	@ Override
	protected void reset() {
		fuel = fuelVolume;
		gameOverSoon = false;
		position = mothership.getAgentHomePosition();
		hasMine = mothership.orderMine();
		try {
			direction = new Direction2D(RandomGenerator.getRandom(1, 360));
		} catch (NotValidException ex) {
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

	public int getFuel() {
		return fuel;
	}

	public int getFuelInPercent() {
		return (fuel * 100) / fuelVolume;
	}

	public Team getTeam() {
		return mothership.getTeam();
	}

	public boolean hasFuel() {
		return fuel > 0;
	}

	public boolean hasMine() {
		return hasMine;
	}

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

	public boolean isDisabled() {
		return !isAlive() || !hasFuel();
	}

	public boolean isCarringResource(final ResourceType type) {
		if (!isCarringResource()) {
			return resource.getType().equals(type);
		}
		return false;
	}

	public boolean isCarringResource() {
		return resource != null;
	}

	public Mothership getMothership() {
		return mothership;
	}

	public void releaseResource() {
		if (isCarringResource()) {
			resource.release();
			resource = null;
		}
	}

	protected Resource getResource() {
		Resource tmp2Resource = resource;
		resource = null;
		return tmp2Resource;
	}

	public void kill() {
		Logger.info(this, "Kill " + name);
		mothership.removeAgent(this);
		alive = false;
		fuel = 0;
		if (needSupport) {
			mothership.cancelSupport(this);
		}
		GameSound.AgentExplosion.play();
	}

	public boolean isAlive() {
		return alive;
	}

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
		Logger.info(this, "startAgend" + this);
		// ############## Load strategy #########################
		Constructor<? extends AbstractStrategy> constructor;
		try {
			constructor = mothership.getTeam().getStrategy().getConstructor(AgentInterface.class);
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
		new Thread(strategy, name + "Thread").start();
		// ######################################################
	}

	public void go() {
		if (isCarringResource()) {
			actionPoints.getActionPoint(8);
		} else {
			actionPoints.getActionPoint(4);
		}

		if (!useFuel()) {
			return;
		}

		position.translate(direction, calcSpeed());
		if (level.collisionDetected(getBounds())) { // Is collied with wall?
			kill();
		}
	}

	public void turnAround() {
		actionPoints.getActionPoint();
		if (useFuel()) {
			direction.invert();
		}
	}

	public void goLeft(final int beta) {
		go();
		if (hasFuel()) {
			direction.setAngle(direction.getAngle() - beta);
		}

	}

	public void goRight(final int beta) {
		go();
		if (hasFuel()) {
			direction.setAngle(direction.getAngle() + beta);
		}
	}

	public void turnLeft(final int beta) {
		actionPoints.getActionPoint();
		if (useFuel()) {
			direction.setAngle(direction.getAngle() - beta);
		}
	}

	public void turnRight(final int beta) {
		actionPoints.getActionPoint();
		if (useFuel()) {
			direction.setAngle(direction.getAngle() + beta);
		}
	}

	public void turnRandom() {
		turnRandom(360);
	}

	public void turnRandom(int opening) {
		actionPoints.getActionPoint();
		try {
			if (useFuel()) {
				final int randomAngle = RandomGenerator.getRandom(0, opening) - (opening / 2);
				direction.setAngle(direction.getAngle() + randomAngle);
			}
		} catch (NotValidException ex) {
			Logger.error(this, "Could not turn random.", ex);
		}
	}

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
			Logger.error(this, "Could not refill fuel! Percent value[" + percent + "] is not in bounds! Valuerange 0-100");
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
	public void goToResource() {
		actionPoints.getActionPoint();
		if (useFuel()) {
			final Resource resourceToGo = level.getCloseResource(this);
			if (resourceToGo != null) {
				direction.turnTo(position, resourceToGo.position);
				position.translate(direction, calcSpeed());
			}
		}
	}

	@Override
	public void deliverResourceToMothership() {
		actionPoints.getActionPoint(10);
		try {
			if (isCarringResource() && useFuel()) {
				mothership.passResource(this);
				GameSound.DeliverResource.play();
			}
		} catch (NotValidException ex) {
			Logger.warn(this, "Could not deliver resource to Mothership!", ex);
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
				releaseResource();
				final Resource resourceToCollect = level.getTouchableResource(this);
				if (resourceToCollect != null && resourceToCollect.setBusy(getTeam())) {
					direction.turnTo(position, resourceToCollect.position);
					actionPoints.getActionPoint(resourceToCollect.getCapturingActionPoints());
					carryResource(resourceToCollect);
				}
			}
		} catch (NotValidException ex) {
			Logger.warn(this, "Could not pickup resource!", ex);
		}
	}

	private void carryResource(final Resource resource) throws NotValidException {
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
					catchedfuel = (adversaryAgent.useFuel((Mothership.AGENT_FUEL_VOLUME / 500) * 2) / 2);
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
		if (useFuel()) {
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
			Logger.warn(this, "Could not goToSuppordAgent!", ex);
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
		if (needSupport) {
			mothership.cancelSupport(this);
		}
	}

	@Override
	public void deployMarker() {
		mothership.placeMarker(position.clone());
	}

	public void goToMarker() {
		try {
			go();
			direction.setAngle(mothership.getMarker().getLevelView().getAbsolutAngle(this));
		} catch (CouldNotPerformException ex) {
			Logger.warn(this, "Could not goToMarker!", ex);
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
	public int getTeamPoints() {
		return mothership.getTeam().getPoints();
	}

	@Override
	public boolean isGameOverSoon() {
		return gameOverSoon;
	}

	protected void setGameOverSoon() {
		this.gameOverSoon = true;
		Logger.info(this, "Game over soon!");
	}
}
