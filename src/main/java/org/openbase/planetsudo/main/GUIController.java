/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.main;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2017 openbase.org
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import org.slf4j.Logger;
import org.openbase.planetsudo.game.GameManager;
import org.openbase.planetsudo.view.MainGUI;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class GUIController {
	public final static String GAME_STATE_CHANGE = "GameStateChange";
	public final static String LOADING_STATE_CHANGE = "GameLoadingState";
	public final static String LOADING_STEP = "GameLoadingStep";

    private final Logger logger = LoggerFactory.getLogger(getClass());

	private PropertyChangeSupport changes;
	private static GUIController instance;
	private boolean started;

	public GUIController() {
		instance = this;
		this.changes = new PropertyChangeSupport(this);
		this.started = false;
	}

	public synchronized void startGUI() {
		if(started) {
			logger.warn("Gui allready started! Ignore...");
			return;
		}
		new MainGUI(this).initialize();

		started = true;
		changes.firePropertyChange(new PropertyChangeEvent(this, GUIController.GAME_STATE_CHANGE, null, GameManager.getInstance().getGameState()));
	}

	public PropertyChangeSupport getPropertyChangeSupport() {
		return changes;
	}

	public static void setEvent(PropertyChangeEvent event) {
		if(instance != null && instance.changes != null) {
			instance.changes.firePropertyChange(event);
		}
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
	    changes.addPropertyChangeListener(l);
	    logger.debug("Add "+l.getClass()+" as new PropertyChangeListener.");
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
	    changes.removePropertyChangeListener(l);
	    logger.debug("Remove PropertyChangeListener "+l.getClass()+".");
	}
}
