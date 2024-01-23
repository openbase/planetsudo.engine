/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.main

import org.openbase.planetsudo.game.GameManager.Companion.gameManager
import org.openbase.planetsudo.view.MainGUI
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

/**
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class GUIController {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    val propertyChangeSupport: PropertyChangeSupport?
    private var started = false

    init {
        instance = this
        this.propertyChangeSupport = PropertyChangeSupport(this)
    }

    @Synchronized
    fun startGUI() {
        if (started) {
            logger.warn("Gui already started! Ignore...")
            return
        }
        MainGUI(this).initialize()

        started = true
        propertyChangeSupport!!.firePropertyChange(
            PropertyChangeEvent(
                this,
                GAME_STATE_CHANGE,
                null,
                gameManager.gameState
            )
        )
    }

    fun addPropertyChangeListener(l: PropertyChangeListener) {
        propertyChangeSupport!!.addPropertyChangeListener(l)
        logger.debug("Add " + l.javaClass + " as new PropertyChangeListener.")
    }

    fun removePropertyChangeListener(l: PropertyChangeListener) {
        propertyChangeSupport!!.removePropertyChangeListener(l)
        logger.debug("Remove PropertyChangeListener " + l.javaClass + ".")
    }

    companion object {
        const val GAME_STATE_CHANGE: String = "GameStateChange"
        const val LOADING_STATE_CHANGE: String = "GameLoadingState"
        const val LOADING_STEP: String = "GameLoadingStep"

        private var instance: GUIController? = null
        fun setEvent(event: PropertyChangeEvent?) {
            if (instance != null && instance!!.propertyChangeSupport != null) {
                instance!!.propertyChangeSupport!!.firePropertyChange(event)
            }
        }
    }
}
