/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.game;

import java.awt.Color;
import de.dc.planetsudo.game.strategy.AbstractStrategy;
import de.dc.planetsudo.game.strategy.StrategyClassLoader;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import de.dc.util.logging.Logger;
import de.dc.planetsudo.level.levelobjects.Mothership;
import de.dc.planetsudo.main.command.SetTeamPathCommand;
import de.dc.util.controller.ObjectFileController;
import de.dc.util.exceptions.ConstructionException;
import de.dc.util.exceptions.CouldNotPerformException;
import de.unibi.agai.clparser.CLParser;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;

/**
 *
 * @author divine
 */
public class Team {

	public static final String DEFAULT_ID = "default";

	public final static String POINT_STATE_CHANGE = "PointStateChange";
	private final String name;
	private final Color teamColor;
	private Mothership mothership;
	private Class<? extends AbstractStrategy> strategy;
	private int points;
	private ArrayList<String> members;
	protected final PropertyChangeSupport changes;

	public Team(TeamData data) throws ConstructionException {
		try {
			this.name = data.getName();
			this.teamColor = data.getTeamColor();
			this.strategy = StrategyClassLoader.loadStrategy(data.getStrategy());
			this.points = 0;
			this.changes = new PropertyChangeSupport(this);
			this.members = new ArrayList<String>();
			this.members.addAll(members);
		} catch (CouldNotPerformException ex) {
			throw new ConstructionException(this, ex);
		}
	}

	private int loadAgentCount() {
		int counter = 0;
		try {
			counter = strategy.newInstance().getAgentCount();
		} catch (InstantiationException ex) {
			Logger.error(this, "Could not create strategy instance!", ex);
		} catch (IllegalAccessException ex) {
			Logger.error(this, "Could not access strategy file!", ex);
		}
		if (counter > Mothership.MAX_AGENT_COUNT || counter <= 0) {
			Logger.info(this, "Bad agent count! Corrected to " + Mothership.MAX_AGENT_COUNT);
			counter = Mothership.MAX_AGENT_COUNT;
		}
		return counter;
	}

	public void reset() {
		points = 0;
	}

	public String getName() {
		return name;
	}

	public int getPoints() {
		return points;
	}

	public int getAgentCount() {
		return loadAgentCount();
	}

	public void addPoint() {
		addPoints(1);
	}

	public void addPoints(int points) {
		this.points += points;
		changes.firePropertyChange(POINT_STATE_CHANGE, null, this.points);
	}

	public Mothership getMothership() {
		return mothership;
	}

	public void setMothership(Mothership mothership) {
		this.mothership = mothership;
	}

	public Class<? extends AbstractStrategy> getStrategy() {
		return strategy;
	}

	@ Override
	public String toString() {
		return name;
	}

	public Color getTeamColor() {
		return teamColor;
	}

	public ArrayList<String> getMembers() {
		return members;
	}

	public int getFinalPoints() {
		return mothership.getAgentsAtHomePosition() + mothership.getShieldPoints() + points;
	}

	public static void saveDefaultTeam(final TeamData data) throws CouldNotPerformException {
		save(data, DEFAULT_ID);
	}

	public static void save(final TeamData data) throws CouldNotPerformException {
		save(data, data.getName());
	}

	public static void save(final TeamData data, final String teamName) throws CouldNotPerformException {
		try {
			final ObjectFileController<TeamData> fileWriter = new ObjectFileController<TeamData>(CLParser.getAttribute(SetTeamPathCommand.class).getValue().getAbsolutePath() + "/" + teamName + ".team");
			fileWriter.writeObject(data);
		} catch (Exception ex) {
			throw new CouldNotPerformException("Could not save TeamData[" + data.getName() + "] as default team!", ex);
		}
	}

	public static TeamData loadDefaultTeam() throws CouldNotPerformException {
		return load(DEFAULT_ID);
	}

	public static TeamData load(final String teamName) throws CouldNotPerformException {
		try {
			final ObjectFileController<TeamData> fileWriter = new ObjectFileController<TeamData>(CLParser.getAttribute(SetTeamPathCommand.class).getValue().getAbsolutePath() + "/" + teamName + ".team");
			return fileWriter.readObject();
		} catch (Exception ex) {
			throw new CouldNotPerformException("Could not load TeamData[" + teamName + "]!", ex);
		}
	}

	public static List<TeamData> loadAll() throws CouldNotPerformException {

		final List<TeamData> teamList = new ArrayList<TeamData>();

		final File teamFolder = CLParser.getAttribute(SetTeamPathCommand.class).getValue();
		if (!teamFolder.exists()) {
			throw new CouldNotPerformException("Could not find team folder! ");
		}
		
		final String[] teamClassNameList = teamFolder.list();
		if (teamClassNameList == null) {
			throw new CouldNotPerformException("Team folder is empty!!");
		}

		for (String teamClassName : teamClassNameList) {
			if(teamClassName.startsWith(DEFAULT_ID)) {
				continue;
			}
			if (teamClassName.endsWith(".team")) {
				String teamStringID = teamClassName.replace(".team", "");

				try {
					final ObjectFileController<TeamData> teamDataController = new ObjectFileController<TeamData>(CLParser.getAttribute(SetTeamPathCommand.class).getValue() + "/" + teamStringID + ".team");
					teamList.add(teamDataController.readObject());
				} catch (Exception ex) {
					Logger.warn(Team.class, "Could not load team " + teamClassName + "!", ex);
					continue;
				}
			}
		}
		return teamList;
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
		changes.addPropertyChangeListener(l);
		Logger.debug(this, "Add " + l.getClass() + " as new PropertyChangeListener.");
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		changes.removePropertyChangeListener(l);
		Logger.debug(this, "Remove PropertyChangeListener " + l.getClass() + ".");
	}
}
