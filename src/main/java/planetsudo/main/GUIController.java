/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.main;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import de.dc.util.logging.Logger;
import planetsudo.game.GameManager;
import planetsudo.view.MainGUI;

/**
 *
 * @author divine
 */
public class GUIController {
	public final static String GAME_STATE_CHANGE = "GameStateChange";
	public final static String LOADING_STATE_CHANGE = "GameLoadingState";
	public final static String LOADING_STEP = "GameLoadingStep";


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
	    Logger.debug(this, "Add "+l.getClass()+" as new PropertyChangeListener.");
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
	    changes.removePropertyChangeListener(l);
	    Logger.debug(this, "Remove PropertyChangeListener "+l.getClass()+".");
	}
}
