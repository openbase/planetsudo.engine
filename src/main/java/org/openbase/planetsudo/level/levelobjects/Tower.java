package org.openbase.planetsudo.level.levelobjects;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2023 openbase.org
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.InvalidStateException;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.openbase.jul.schedule.GlobalCachedExecutorService;
import org.openbase.jul.visual.swing.engine.draw2d.AbstractResourcePanel;
import org.openbase.planetsudo.game.GameManager;
import org.openbase.planetsudo.game.GameSound;
import org.openbase.planetsudo.geometry.Direction2D;
import org.openbase.planetsudo.geometry.Point2D;
import org.slf4j.Logger;
import org.openbase.planetsudo.level.AbstractLevel;
import org.openbase.planetsudo.level.ResourcePlacement;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class Tower extends AbstractLevelObject implements ActionListener {
    
    public final static int SIZE = 50;
    public final static String TOWER_FUEL_STATE_CHANGE = "FuelStateChange";
    public final static String TOWER_SHIELD_STATE_CHANGE = "ShieldStateChange";
    public final static String TOWER_ERECT = "erect tower";
    public final static String TOWER_DISMANTLE = "dismantle tower";
    
    public enum TowerType {
        
        DefenceTower, ObservationTower
    };
    
    private static final Logger logger = LoggerFactory.getLogger(Tower.class);
    
    private TowerType type;
    private final Object TOWER_LOCK = new Object();
    private ResourcePlacement placement;
    private final Timer timer;
    
    private int shield;
    private int fuel;
    private final Direction2D direction;
    private boolean attacked;
    private boolean erected;
    private final Mothership mothership;
    
    public Tower(final int id, final AbstractLevel level, final Mothership mothership) {
        super(id, Tower.class.getSimpleName() + "[" + id + "]", AbstractResourcePanel.ObjectType.Static, level, mothership.getPosition(), SIZE, SIZE, ObjectShape.Rec);
        this.mothership = mothership;
        this.direction = new Direction2D(0);
        this.position = new Point2D();
        this.timer = new Timer(50, this);
        this.reset();
        logger.info("Create " + this);
        GlobalCachedExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while(!Thread.interrupted()) {
                        if (!erected) {
                            Thread.sleep(1000);
                            continue;
                        }
                        direction.setAngle(direction.getAngle() + 10);
                        Thread.sleep(100);
                    }
                } catch (Exception ex) {
                    ExceptionPrinter.printHistory(new InvalidStateException("TowerRotationThread crashed.", ex), logger);
                }
            }
        });
    }
    
    public void erect(final Tower.TowerType type, final Agent commander) throws CouldNotPerformException {
        if (!commander.isCommander()) {
            commander.kill();
            throw new CouldNotPerformException("Only the commander can erect a tower!");
        }
        if (erected) {
            throw new CouldNotPerformException("Tower is already erected!");
        }
        position.setLocation(commander.position);
        this.type = type;
        erected = true;
        changes.firePropertyChange(TOWER_ERECT, null, this);
    }
    
    public void dismantle(final Agent commander) throws CouldNotPerformException {
        if (!commander.isCommander()) {
            commander.kill();
            throw new CouldNotPerformException("Only the commander can dismantle a tower!");
        }
        if (!erected) {
            throw new CouldNotPerformException("Could not dismantle the tower! The tower was never erected!!");
        }
        erected = false;
        changes.firePropertyChange(TOWER_DISMANTLE, null, this);
    }
    
    @Override
    public void reset() {
        fuel = 100;
        erected = false;
    }
    
    public TowerType getType() {
        return type;
    }
    
    public int getShield() {
        return shield;
    }
    
    public int getFuel() {
        return fuel;
    }
    
    public boolean isAttacked() {
        return attacked;
    }
    
    public Direction2D getDirection() {
        return direction;
    }
    
    public boolean seeTower(final Agent agent) {
        if (!erected) {
            return false;
        }
        
        return getBounds().intersects(agent.getBounds());
    }
    
    public int orderFuel(int fuel, final Agent agent) {
        if (agent == null || getBounds().contains(agent.getBounds())) {
            try {
                final int oldFuel = this.fuel;
                if (fuel <= 0) { // fuel emty
                    fuel = 0;
                } else if (this.fuel < fuel) { // use last fuel
                    fuel = this.fuel;
                    this.fuel = 0;
                    synchronized (this) {
                        notifyAll();
                    }
                } else {
                    this.fuel -= fuel;
                }
                changes.firePropertyChange(TOWER_FUEL_STATE_CHANGE, oldFuel, this.fuel);
            } catch (Exception ex) {
                logger.error("Could not order fuel!", ex);
            }
        } else {
            return 0;
        }
        return fuel;
    }
    
    public boolean hasFuel() {
        return fuel > 0;
    }
    
    protected void spendFuel(final int value) {
        if (value + fuel > Mothership.TOWER_FUEL_VOLUME) {
            fuel = Mothership.TOWER_FUEL_VOLUME;
        } else {
            fuel += value;
        }
        changes.firePropertyChange(TOWER_FUEL_STATE_CHANGE, null, this.fuel);
    }
    
    public synchronized void attack() {
        logger.debug("Attack Mothership");
        if (shield > 0) {
            shield--;
            if (shield <= Mothership.BURNING_TOWER) {
                if (!timer.isRunning()) {
                    timer.start();
                    GameSound.MothershipExplosion.play();
                }
            }
            changes.firePropertyChange(TOWER_SHIELD_STATE_CHANGE, null, shield);
        }
    }
    
    public synchronized void repair() {
        if (shield < 100) {
            shield++;
            if (shield > Mothership.BURNING_TOWER && timer.isRunning()) {
                timer.stop();
            }
            changes.firePropertyChange(TOWER_SHIELD_STATE_CHANGE, null, shield);
        }
    }
    
    public boolean isBurning() {
        return shield < Mothership.BURNING_TOWER && hasFuel();
    }
    
    public int getShieldForce() {
        return shield;
    }
    
    public int getShieldPoints() {
        return shield / 2;
    }
    
    public boolean isMaxDamaged() {
        return shield == 0;
    }
    
    public boolean isDamaged() {
        return shield < 100;
    }
    
    public boolean isErected() {
        return erected;
    }

    public Mothership getMothership() {
        return mothership;
    }
    
    @Override
    public void actionPerformed(final ActionEvent ex) {
        if (!GameManager.getInstance().isPause()) {
            orderFuel(Math.max(0, Mothership.BURNING_TOWER - shield), null);
        }
    }
}
