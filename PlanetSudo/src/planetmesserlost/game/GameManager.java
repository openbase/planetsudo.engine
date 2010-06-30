/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.game;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.util.Vector;
import logging.Logger;
import planetmesserlost.level.Level;
import planetmesserlost.level.SimpleWorld;
import planetmesserlost.main.GUIController;
import planetmesserlost.game.strategy.DefaultStategy;

/**
 *
 * @author divine
 */
public class GameManager implements Runnable {

	public enum GameState{Configuration, Initialisation, Running};

	private static GameManager instance;

	public final Vector teams;
	public final Thread gameThread;
	public Level level;
	public GameState gameState;

	public GameManager() {
		instance = this;
		Logger.info(this, "Create "+this+".");
		this.teams = new Vector<Team>();
		this.gameThread = new Thread(this, "GameThread");
		this.resetAndInit();
		this.gameThread.start();
	}

	public void setupTestGame() {
		setLevel(new SimpleWorld());
		addTeam(new Team(0, "Piranjas", Color.BLUE, DefaultStategy.class));
		addTeam(new Team(1, "BlackHeath", Color.MAGENTA, DefaultStategy.class));
		startGame();
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
		teams.removeAllElements();
		setGameState(GameState.Configuration);
	}

	public boolean addTeam(Team team) {
		if(gameState != GameState.Configuration) {
			Logger.warn(this, "Could not add team in "+gameState.name()+" State.");
			return false;
		}
		Logger.info(this, "Add team "+team+".");
		return teams.add(team);
	}

	public void setLevel(Level level) {
		if(gameState != GameState.Configuration) {
			Logger.warn(this, "Could not set level in "+gameState.name()+" State.");
			return;
		}
		this.level = level;
		Logger.info(this, "Set "+level+" as new level.");
	}
	
	public void startGame() {
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
		if(teams.size() < 2) {
			Logger.error(this, "Abord gamestart: Not enough teams set!");
			setGameState(GameState.Configuration);
			return;
		}

		level.setTeams(teams);
		new Thread(level, "Levelrunner").start();

		setGameState(GameState.Running);
		synchronized(this) {
			this.notify();
		}
		Logger.info(this, "Game is Running.");


	}

	public void setGameState(GameState state) {
		GUIController.setEvent(new PropertyChangeEvent(this, GUIController.GAME_STATE_CHANGE, gameState, state));
		this.gameState = state;
	}

	public static GameManager getInstance() {
		return instance;
	}

	public GameState getGameState() {
		return gameState;
	}

	public Level getLevel() {
		return level;
	}

	public Vector getTeams() {
		return teams;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
