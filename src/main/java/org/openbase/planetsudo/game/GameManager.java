/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2018 openbase.org
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
import org.slf4j.Logger;
import org.openbase.planetsudo.level.AbstractLevel;
import org.openbase.planetsudo.main.GUIController;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class GameManager implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);
    
	public static final int DEFAULT_GAME_SPEED = 50;

	public enum GameState {

		Configuration, Initialisation, Running, Break
	};

	public enum TeamType {

		A, B
	};
	private static GameManager instance;
	public Team teamA, teamB;
	public final Thread gameThread;
	public AbstractLevel level;
	public GameState gameState;
	private boolean pause, gameOver;
	private int gameSpeed;
	public boolean gameOverSoon;

	public GameManager() {
		instance = this;
		LOGGER.info("Create " + this + ".");
		this.gameThread = new Thread(this, "GameThread");
		this.resetAndInit();
		this.gameThread.start();
		this.pause = false;
		this.gameOver = true;
		this.gameState = GameState.Configuration;
		this.gameSpeed = 50;
		this.gameOverSoon = false;
	}

	public void setupTestGame() {
//		LevelLoader.getInstance();
//		ArrayList<String> team0 = new ArrayList<String>(), team1 = new ArrayList<String>();
//		team0.add("OptimusPrime");
//		team0.add("Divine");
//		team1.add("Noxus");
//		addTeam(new Team(0, "Piranjas", Color.BLUE, MarianStrategy.class, team0), TeamType.A);
//		//addTeam(new Team(1, "BlackHeath", Color.MAGENTA, DefaultStategy.class, 10, team1));
//		addTeam(new Team(2, "Legions of Iron", new Color(255,100,100), MarcosStrategy.class, team1), TeamType.B);
//		startGame();
	}

	@Override
	public void run() {
		while (true) {
			if (gameState == GameState.Running) {
				level.waitTillGameOver();
			}
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException ex) {
					LOGGER.error("", ex);
				}
			}
		}
	}

	public void resetAndInit() {
		LOGGER.info("Load default values.");
		teamA = null;
		teamB = null;
	}

	public boolean addTeam(TeamData teamData, TeamType type) {
		try {
			if (teamData == null) {
				LOGGER.warn("Ignore invalid team.");
				return false;
			}

			if (gameState != GameState.Configuration) {
				LOGGER.warn("Could not add team in " + gameState.name() + " State.");
				return false;
			}
			LOGGER.info("Add team " + teamData + ".");

			switch (type) {
				case A:
					teamA = new Team(teamData);
					break;
				case B:
					teamB = new Team(teamData);
					break;
				default:
					LOGGER.warn("Unknown team type!");
					return false;
			}

		} catch (Exception ex) {
			LOGGER.warn("Could not add team!", ex);
			return false;
		}
		return true;
	}

	public void setLevel(final AbstractLevel level) {
		if (gameState != GameState.Configuration) {
			LOGGER.warn("Could not set level in " + gameState.name() + " State.");
			return;
		}
		this.level = level;
		LOGGER.info("Set " + level + " as new level.");
	}

	public void startGame() {
		Thread gameStartThread = new Thread("Gamestart Thread") {
			@Override
			public void run() {
				LOGGER.info("Init game start...");
				if (gameState != GameState.Configuration) {
					LOGGER.error("Abord gamestart because Game manager is in state " + gameState.name() + ".");
					return;
				}

				setGameState(GameState.Initialisation);

				if (level == null) {
					LOGGER.error("Abord gamestart: No level set!");
					setGameState(GameState.Configuration);
					return;
				}
				if (teamA == null || teamB == null) {
					LOGGER.error("Abord gamestart: Not enough teams set!");
					setGameState(GameState.Configuration);
					return;
				}
				gameOverSoon = false;
				teamA.reset();
				teamB.reset();
				level.setTeamA(teamA);
				level.setTeamB(teamB);
				level.reset();
				setGameState(GameState.Running);
				new Thread(level, "Levelrunner").start();
				synchronized (this) {
					this.notify();
				}
				LOGGER.info("Game is Running.");
			}
		};
		gameStartThread.setPriority(4);
		gameStartThread.start();
	}

	public void setGameOverSoon() {
		try {
			level.setGameOverSoon();
			GameSound.EndSoon.play();
		} catch (Exception ex) {
			LOGGER.warn("Could not init game over.");
		}
	}

	private void setGameState(GameState state) {
		GUIController.setEvent(new PropertyChangeEvent(this, GUIController.GAME_STATE_CHANGE, gameState, state));
		this.gameState = state;
		pause = state == GameState.Break;
		if (state == GameState.Running) {
			gameOver = false;
		} else if (state == GameState.Configuration) {
			gameOver = true;

		}

	}

	public void switchGameState(GameState state) {
		switch (state) {
			default:
				setGameState(state);
		}
	}

	public void setGameSpeed(int speed) {
		this.gameSpeed = speed;
		if (level != null) {
			this.level.setGameSpeed(speed);
		}
	}

	public int getGameSpeed() {
		return gameSpeed;
	}

	public boolean isPause() {
		return pause;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public static GameManager getInstance() {
		return instance;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setDefaultSpeed() {
		setGameSpeed(DEFAULT_GAME_SPEED);
	}

	public AbstractLevel getLevel() {
		return level;
	}

	public Team getTeamA() {
		return teamA;
	}

	public Team getTeamB() {
		return teamB;
	}

	public boolean isWinner(Team team) {
		return team.getFinalPoints() == Math.max(teamA.getFinalPoints(), teamB.getFinalPoints());
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
