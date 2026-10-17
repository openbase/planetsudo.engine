package org.openbase.planetsudo.game.strategy

import java.awt.KeyboardFocusManager
import java.awt.Window
import java.awt.event.KeyEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean

internal object ManualControlInput {
    private val pressedKeys = ConcurrentHashMap.newKeySet<Int>()
    private val dispatcherInstalled = AtomicBoolean(false)

    fun install(window: Window) {
        if (dispatcherInstalled.compareAndSet(false, true)) {
            KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher { event ->
                when (event.id) {
                    KeyEvent.KEY_PRESSED -> pressedKeys.add(event.keyCode)
                    KeyEvent.KEY_RELEASED -> pressedKeys.remove(event.keyCode)
                }
                false
            }
        }

        window.addWindowFocusListener(
            object : WindowAdapter() {
                override fun windowLostFocus(event: WindowEvent) {
                    pressedKeys.clear()
                }
            },
        )
    }

    fun isPressed(keyCode: Int): Boolean = keyCode in pressedKeys

    internal fun keyPressed(keyCode: Int) {
        pressedKeys.add(keyCode)
    }

    internal fun keyReleased(keyCode: Int) {
        pressedKeys.remove(keyCode)
    }

    internal fun clear() {
        pressedKeys.clear()
    }
}

/**
 * Returns whether the given AWT key is currently held down.
 *
 * The desktop GUI tracks key presses and releases; outside the GUI this trigger remains false.
 */
fun AbstractStrategy<*>.manualControl(keyCode: Int): Boolean = ManualControlInput.isPressed(keyCode)
