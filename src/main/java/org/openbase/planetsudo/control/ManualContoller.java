package org.openbase.planetsudo.control;

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
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.openbase.jul.schedule.GlobalExecutionService;
import org.openbase.planetsudo.level.levelobjects.Agent;
import org.openbase.planetsudo.level.levelobjects.Mothership;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class ManualContoller {

    public static boolean manual = true;
    private static Agent commanderA, commanderB;
    private static boolean active = false;

    private static int speed = 0;

    static {
        activateShortKey();
    }

    public static void init(final Agent commanderAA, final Agent commanderBB) {
        commanderA = commanderAA;
        commanderB = commanderBB;

        GlobalExecutionService.execute(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    try {
                        if (speed > 0) {
                            for (int i = 0; i < speed; i++) {
                                if (commanderA != null) {
                                    commanderA.go();
                                }
                            }
                        }

                        Thread.sleep(100);
                    } catch (Exception ex) {
                        ExceptionPrinter.printHistory(ex, System.out);
                    }
                }
            }
        }
        );

    }

    public static final void activateShortKey() {

        if (active) {
            return;
        }

        active = true;
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(final KeyEvent evt) {
                if (!manual && commanderA != null && commanderB != null) {
                    return false;
                }
                switch (evt.getKeyCode()) {
                    case KeyEvent.VK_UP:

                        speed++;
                        return true;
                    case KeyEvent.VK_LEFT:
                        commanderA.turnLeft(10);
                        return true;
                    case KeyEvent.VK_RIGHT:
                        commanderA.turnRight(10);
                        return true;
                    case KeyEvent.VK_DOWN:
                        speed = 0;
                        return true;
                    case KeyEvent.VK_A:
                        commanderA.orderSupport();
                        return true;
                    case KeyEvent.VK_CONTROL:
                        commanderA.fightWithAdversaryAgent();
                        return true;
                    case KeyEvent.VK_ALT:
                        if (commanderA.isTouchingResource() && !commanderA.isCarringResource()) {
                            commanderA.pickupResource();
                            return true;
                        }

                        if (commanderA.isCarringResource() && commanderA.isAtMothership()) {
                            commanderA.deliverResourceToMothership();
                            return true;
                        }
                        if (commanderA.isAtMothership()) {
                            commanderA.orderFuel(100);
                            return true;
                        }
                        return true;
                    case KeyEvent.VK_M:
                        commanderA.deployMarker();
                        return true;
                    case KeyEvent.VK_X:
                        commanderA.deployMine();
                        return true;
                }
                return false;
            }
        });
    }
}
