/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.main;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import logging.Logger;
import planetmesserlost.view.MainGUI;

/**
 *
 * @author divine
 */
public class GUIController {
	public final static String GAME_STATE_CHANGE = "GameStateChange";
//	public final static String CONNECTING = "Connecting";
//	public final static String CONNECTION_IN_ACTIVITY = "ConnectionInActivity";
//	public final static String CONNECTION_OUT_ACTIVITY = "ConnectionOutActivity";
//	public final static String ADD_BASE_CUBE_ONE = "AddBaseCubeOne";
//	public final static String BASE_CUBE_ONE_INITIALIZED = "BaseCubeOneInitialized";
//	public final static String BASE_CUBE_ONE_ERROR = "BaseCubeOneError";

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
			Logger.warn(this, "Gui allready started! Ignore...");
			return;
		}
		new MainGUI(this).initialize();

		started = true;
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
	    Logger.debug(this, "Add "+l.getClass()+" as new PropertyChangeListener.");
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
	    changes.removePropertyChangeListener(l);
	    Logger.debug(this, "Remove PropertyChangeListener "+l.getClass()+".");
	}
}
