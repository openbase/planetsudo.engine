package org.openbase.planetsudo.game.strategy

import com.studiohartman.jamepad.ControllerManager
import com.studiohartman.jamepad.ControllerState
import org.slf4j.LoggerFactory
import java.awt.KeyboardFocusManager
import java.awt.Window
import java.awt.event.KeyEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean

enum class ManualPlayer {
    PLAYER_1,
    PLAYER_2,
}

enum class ManualAction {
    GO_FORWARD,
    TURN_LEFT,
    TURN_RIGHT,
    SHIFT,
    FIGHT_ENEMY,
    SET_MARKER,
    PLACE_MINE,
    PRIMARY_ACTION,
}

data class ManualGamepadDevice(val id: String, val name: String)

internal object ManualControlInput {
    private val pressedKeys = ConcurrentHashMap.newKeySet<Int>()
    private val gamepadActions = ConcurrentHashMap<ManualPlayer, Set<ManualAction>>()
    private val dispatcherInstalled = AtomicBoolean(false)
    private val gamepadPollingStarted = AtomicBoolean(false)
    private val refreshRequested = AtomicBoolean(false)

    @Volatile
    private var gamepads: List<ManualGamepadDevice> = emptyList()

    @Volatile
    private var gamepadStatus = "Gamepad-Erkennung wird gestartet."
    private val selectedGamepads = ConcurrentHashMap<ManualPlayer, String>()
    private val logger = LoggerFactory.getLogger(ManualControlInput::class.java)

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

    fun isPressed(player: ManualPlayer, action: ManualAction): Boolean =
        action in gamepadActions[player].orEmpty()

    fun availableGamepads(): List<ManualGamepadDevice> = gamepads

    fun gamepadStatus(): String = gamepadStatus

    fun startGamepadPolling() {
        if (!gamepadPollingStarted.compareAndSet(false, true)) return

        val pollingThread =
            Thread(::pollGamepads, "Gamepad input poller").apply {
                isDaemon = true
                start()
            }
        Runtime.getRuntime().addShutdownHook(
            Thread(
                {
                    pollingThread.interrupt()
                    pollingThread.join(1000)
                },
                "Gamepad input shutdown",
            ),
        )
    }

    fun refreshGamepads() {
        if (!gamepadPollingStarted.get()) {
            startGamepadPolling()
            return
        }
        refreshRequested.set(true)
    }

    fun selectedGamepad(player: ManualPlayer): String? = selectedGamepads[player]

    fun selectGamepad(player: ManualPlayer, deviceId: String?) {
        if (deviceId == null) {
            selectedGamepads.remove(player)
        } else {
            selectedGamepads[player] = deviceId
        }
    }

    internal fun updateGamepads(devices: List<ManualGamepadDevice>) {
        gamepads = devices
    }

    private fun pollGamepads() {
        var manager: ControllerManager? = null
        var initialized = false
        try {
            manager = ControllerManager()
            manager.initSDLGamepad()
            initialized = true

            while (!Thread.currentThread().isInterrupted) {
                if (refreshRequested.getAndSet(false)) {
                    manager.update()
                }
                val connectedGamepads = mutableListOf<ManualGamepadDevice>()
                val currentActions = ManualPlayer.entries.associateWith { mutableSetOf<ManualAction>() }
                for (controllerIndex in 0 until MAX_GAMEPADS) {
                    val state = manager.getState(controllerIndex)
                    if (!state.isConnected) continue

                    connectedGamepads.add(
                        ManualGamepadDevice(
                            id = controllerIndex.toString(),
                            name = "${state.controllerType} (Gamepad ${controllerIndex + 1})",
                        ),
                    )
                    readPlayerInput(controllerIndex, state, currentActions)
                }
                updateGamepads(connectedGamepads)
                currentActions.forEach { (player, actions) -> setGamepadActions(player, actions) }
                gamepadStatus =
                    if (connectedGamepads.isEmpty()) {
                        "Keine Gamepads gefunden."
                    } else {
                        "${connectedGamepads.size} Gamepad(s) gefunden."
                    }

                try {
                    Thread.sleep(POLL_INTERVAL_MS)
                } catch (ex: InterruptedException) {
                    Thread.currentThread().interrupt()
                }
            }
        } catch (ex: LinkageError) {
            gamepadStatus = "Gamepad-Bibliothek konnte nicht geladen werden: ${ex.message}"
            logger.error("Could not load native gamepad library.", ex)
        } catch (ex: Exception) {
            gamepadStatus = "Gamepad-Erkennung fehlgeschlagen: ${ex.message}"
            logger.error("Could not initialize gamepad input.", ex)
        } finally {
            gamepads = emptyList()
            gamepadActions.clear()
            if (initialized) {
                try {
                    manager?.quitSDLGamepad()
                } catch (ex: LinkageError) {
                    logger.error("Could not shut down native gamepad library.", ex)
                } catch (ex: Exception) {
                    logger.error("Could not shut down gamepad input.", ex)
                }
            }
            gamepadPollingStarted.set(false)
        }
    }

    private fun readPlayerInput(
        controllerIndex: Int,
        state: ControllerState,
        actionsByPlayer: Map<ManualPlayer, MutableSet<ManualAction>>,
    ) {
        val player = selectedGamepads.entries.firstOrNull { it.value == controllerIndex.toString() }?.key ?: return
        val actions = actionsByPlayer[player] ?: return
        if (state.leftTrigger > 0) actions.add(ManualAction.GO_FORWARD)
        if (state.leftStickX < -0.1) actions.add(ManualAction.TURN_LEFT)
        if (state.leftStickX > 0.1) actions.add(ManualAction.TURN_RIGHT)
        if (state.leftStickClick) actions.add(ManualAction.SHIFT)
        if (state.rightTrigger > 0) actions.add(ManualAction.FIGHT_ENEMY)
        if (state.y) actions.add(ManualAction.SET_MARKER)
        if (state.rb) actions.add(ManualAction.PLACE_MINE)
        if (state.a) actions.add(ManualAction.PRIMARY_ACTION)
    }

    internal fun keyPressed(keyCode: Int) {
        pressedKeys.add(keyCode)
    }

    internal fun keyReleased(keyCode: Int) {
        pressedKeys.remove(keyCode)
    }

    internal fun clear() {
        pressedKeys.clear()
        gamepadActions.clear()
    }

    internal fun setGamepadActions(player: ManualPlayer, actions: Set<ManualAction>) {
        gamepadActions[player] = actions
    }

    private const val MAX_GAMEPADS = 4
    private const val POLL_INTERVAL_MS = 20L
}

/**
 * Returns whether the given AWT key is currently held down.
 *
 * The desktop GUI tracks key presses and releases; outside the GUI this trigger remains false.
 */
fun AbstractStrategy<*>.manualControl(keyCode: Int): Boolean = ManualControlInput.isPressed(keyCode)

/**
 * Returns whether the given action is active on the gamepad assigned to this local player.
 */
fun AbstractStrategy<*>.manualControl(player: ManualPlayer, action: ManualAction): Boolean =
    ManualControlInput.isPressed(player, action)
