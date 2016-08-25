/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.main;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import org.slf4j.Logger;
import org.openbase.planetsudo.game.GameManager;
import org.openbase.planetsudo.view.MainGUI;
import org.slf4j.LoggerFactory;

/**
 *
 * @author divine
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
