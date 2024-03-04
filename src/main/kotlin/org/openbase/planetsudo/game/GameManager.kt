/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game

import org.openbase.jul.exception.CouldNotPerformException
import org.openbase.jul.exception.printer.ExceptionPrinter
import org.openbase.planetsudo.level.AbstractLevel
import org.openbase.planetsudo.main.GUIController
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.beans.PropertyChangeEvent
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.math.max
import kotlin.math.min

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class GameManager : Runnable {
    enum class GameState {
        Configuration, Initialisation, Running, Break
    }

    enum class TeamType {
        A, B
    }

    private val lock = ReentrantLock()
    private val condition = lock.newCondition()

    @JvmField
    var teamA: Team? = null

    @JvmField
    var teamB: Team? = null
    val gameThread: Thread
    var level: AbstractLevel? = null
        private set

    var gameState: GameState
        private set
    var isPause: Boolean
        private set
    var isGameOver: Boolean
        private set
    private var gameSpeedFactor: Double
    var gameOverSoon: Boolean

    init {
        LOGGER.info("Create $this.")
        this.gameThread = Thread(this, "GameThread")
        this.resetAndInit()
        gameThread.start()
        this.isPause = false
        this.isGameOver = true
        this.gameState = GameState.Configuration
        this.gameSpeedFactor = DEFAULT_GAME_SPEED_FACTOR.toDouble()
        this.gameOverSoon = false
    }

    override fun run() {
        while (!Thread.currentThread().isInterrupted) {
            if (gameState == GameState.Running) {
                level?.waitTillGameOver()
            }
            lock.withLock {
                condition.await()
            }
        }
    }

    fun resetAndInit() {
        LOGGER.info("Load default values.")
        teamA = null
        teamB = null
    }

    fun addTeam(teamData: TeamData?, type: TeamType?): Boolean {
        try {
            if (teamData == null) {
                LOGGER.warn("Ignore invalid team.")
                return false
            }

            if (gameState != GameState.Configuration) {
                LOGGER.warn("Could not add team in " + gameState.name + " State.")
                return false
            }
            LOGGER.info("Add team $teamData.")

            when (type) {
                TeamType.A -> teamA = Team(teamData)
                TeamType.B -> teamB = Team(teamData)
                else -> {
                    LOGGER.warn("Unknown team type!")
                    return false
                }
            }
        } catch (ex: CouldNotPerformException) {
            ExceptionPrinter.printHistory("Could not add team!", ex, LOGGER)
            return false
        }
        return true
    }

    fun setLevel(level: AbstractLevel) {
        if (gameState != GameState.Configuration) {
            LOGGER.warn("Could not set level in " + gameState.name + " State.")
            return
        }
        this.level = level
        LOGGER.info("Set $level as new level.")
    }

    fun startGame() {
        val gameStartThread: Thread = object : Thread("Gamestart Thread") {
            override fun run() {
                LOGGER.info("Init game start...")
                if (gameState != GameState.Configuration) {
                    LOGGER.error("Abord gamestart because Game manager is in state " + gameState.name + ".")
                    return
                }

                setGameState(GameState.Initialisation)

                if (level == null) {
                    LOGGER.error("Aboard game start: No level set!")
                    setGameState(GameState.Configuration)
                    return
                }
                if (teamA == null || teamB == null) {
                    LOGGER.error("Aboard game start: Not enough teams set!")
                    setGameState(GameState.Configuration)
                    return
                }
                gameOverSoon = false
                teamA!!.reset()
                teamB!!.reset()
                level?.setTeamA(teamA)
                level?.setTeamB(teamB)
                level?.reset()
                setGameState(GameState.Running)
                Thread(level, "Levelrunner").start()
                lock.withLock {
                    condition.signalAll()
                }
                LOGGER.info("Game is Running.")
            }
        }
        gameStartThread.priority = 4
        gameStartThread.isDaemon = true
        gameStartThread.start()
    }

    fun setGameOverSoon() {
        try {
            level?.setGameOverSoon()
            GameSound.EndSoon.play()
        } catch (ex: CouldNotPerformException) {
            LOGGER.warn("Could not init game over.")
        }
    }

    private fun setGameState(state: GameState) {
        GUIController.setEvent(PropertyChangeEvent(this, GUIController.GAME_STATE_CHANGE, gameState, state))
        this.gameState = state
        isPause = state == GameState.Break
        if (state == GameState.Running) {
            isGameOver = false
        } else if (state == GameState.Configuration) {
            isGameOver = true
        }
    }

    fun switchGameState(state: GameState) = setGameState(state)

    fun setGameSpeedFactor(factor: Double) {
        gameSpeedFactor = min(MAX_GAME_SPEED_FACTOR, max(factor, MIN_GAME_SPEED_FACTOR))
        level?.setGameSpeedFactor(factor)
    }

    fun getGameSpeedFactor(): Double {
        return gameSpeedFactor
    }

    fun setDefaultSpeed() {
        setGameSpeedFactor(DEFAULT_GAME_SPEED_FACTOR.toDouble())
    }

    fun isWinner(team: Team): Boolean {
        return team.finalPoints == max(teamA!!.finalPoints, teamB!!.finalPoints)
    }

    override fun toString(): String {
        return this.javaClass.simpleName
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(GameManager::class.java)

        const val DEFAULT_GAME_SPEED_FACTOR: Int = 1

        const val MIN_GAME_SPEED_FACTOR: Double = 0.01
        const val MAX_GAME_SPEED_FACTOR: Double = 100.0

        @JvmStatic
        val gameManager: GameManager = GameManager()
    }
}
