/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.game;

import java.beans.PropertyChangeEvent;
import logging.Logger;
import planetsudo.level.AbstractLevel;
import planetsudo.main.GUIController;

/**
 *
 * @author divine
 */
public class GameManager implements Runnable {

	public enum GameState{Configuration, Initialisation, Running, Break};
	public enum TeamType{A, B};

	private static GameManager instance;

	public Team teamA, teamB;
	public final Thread gameThread;
	public AbstractLevel level;
	public GameState gameState;
	private boolean pause, gameOver;

	public GameManager() {
		instance = this;
		Logger.info(this, "Create "+this+".");
		this.gameThread = new Thread(this, "GameThread");
		this.resetAndInit();
		this.gameThread.start();
		this.pause = false;
		this.gameOver = true;
		this.gameState = GameState.Configuration;
		new LevelReciver();
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
		while(true) {
			if(gameState == GameState.Running) {
				level.waitTillGameOver();
			}
			synchronized(this) {
				try {
					this.wait();
				} catch (InterruptedException ex) {
					Logger.error(this, "", ex);
				}
			}
		}
	}

	public void resetAndInit() {
		Logger.info(this, "Load default values.");
		teamA = null;
		teamB = null;
	}

	public boolean addTeam(Team team, TeamType type) {
		if(gameState != GameState.Configuration) {
			Logger.warn(this, "Could not add team in "+gameState.name()+" State.");
			return false;
		}
		Logger.info(this, "Add team "+team+".");
		switch(type) {
			case A:
				teamA = team;
				break;
			case B:
				teamB = team;
				break;
			default:
				Logger.warn(this, "Unknown team type!");
				return false;
		}
		


		return true;
	}

	public void setLevel(AbstractLevel level) {
		if(gameState != GameState.Configuration) {
			Logger.warn(this, "Could not set level in "+gameState.name()+" State.");
			return;
		}
		this.level = level;
		Logger.info(this, "Set "+level+" as new level.");
	}
	
	public void startGame() {
		Thread gameStartThread = new Thread("Gamestart Thread") {
			@Override
			public void run() {
				Logger.info(this, "Init game start...");
				if(gameState != GameState.Configuration) {
					Logger.error(this, "Abord gamestart because Game manager is in state "+gameState.name()+".");
					return;
				}

				setGameState(GameState.Initialisation);

				if(level == null) {
					Logger.error(this, "Abord gamestart: No level set!");
					setGameState(GameState.Configuration);
					return;
				}
				if(teamA == null || teamB == null) {
					Logger.error(this, "Abord gamestart: Not enough teams set!");
					setGameState(GameState.Configuration);
					return;
				}
				teamA.reset();
				teamB.reset();
				level.setTeamA(teamA);
				level.setTeamB(teamB);
				level.reset();
				setGameState(GameState.Running);
				new Thread(level, "Levelrunner").start();
				synchronized(this) {
					this.notify();
				}
				Logger.info(this, "Game is Running.");
			}
		};
		gameStartThread.setPriority(4);
		gameStartThread.start();
	}

	private void setGameState(GameState state) {
		GUIController.setEvent(new PropertyChangeEvent(this, GUIController.GAME_STATE_CHANGE, gameState, state));
		this.gameState = state;
		pause = state == GameState.Break;
		if(state == GameState.Running) {
			gameOver = false;
		} else if (state == GameState.Configuration) {
			gameOver = true;
		}

	}

	public void switchGameState(GameState state) {
		switch(state) {
			default:
				setGameState(state);
		}

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
