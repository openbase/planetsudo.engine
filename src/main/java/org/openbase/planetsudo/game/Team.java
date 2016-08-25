/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game;

import org.openbase.jps.core.JPService;
import java.awt.Color;
import org.openbase.planetsudo.game.strategy.AbstractStrategy;
import org.openbase.planetsudo.game.strategy.StrategyClassLoader;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.openbase.planetsudo.level.levelobjects.Mothership;
import org.openbase.planetsudo.jp.JPTeamPath;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.openbase.jps.exception.JPNotAvailableException;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.openbase.jul.processing.JSonObjectFileProcessor;
import org.openbase.planetsudo.deprication.ObjectFileController;
import org.slf4j.LoggerFactory;

/**
 *
 * @author divine
 */
public class Team {

    public static final String DEFAULT_ID = "default";

    public final static String POINT_STATE_CHANGE = "PointStateChange";

    private static final Logger logger = LoggerFactory.getLogger(Team.class);

    private final String name;
    private final Color teamColor;
    private Mothership mothership;
    private Class<? extends AbstractStrategy> strategy;
    private int points;
    private ArrayList<String> members;
    protected final PropertyChangeSupport changes;
    private static final JSonObjectFileProcessor<TeamData> TEAM_DATA_PROCESSOR = new JSonObjectFileProcessor<>(TeamData.class);

    public Team(TeamData data) throws org.openbase.jul.exception.InstantiationException {
        try {
            this.name = data.getName();
            this.teamColor = data.getTeamColor();
            this.strategy = StrategyClassLoader.loadStrategy(data.getStrategy());
            this.points = 0;
            this.changes = new PropertyChangeSupport(this);
            this.members = new ArrayList<>();
            this.members.addAll(data.getMembers());
        } catch (CouldNotPerformException ex) {
            throw new org.openbase.jul.exception.InstantiationException(this, ex);
        }
    }

    private int loadAgentCount() {
        int counter = 0;
        try {
            counter = strategy.newInstance().getAgentCount();
        } catch (InstantiationException ex) {
            logger.error("Could not create strategy instance!", ex);
        } catch (IllegalAccessException ex) {
            logger.error("Could not access strategy file!", ex);
        }
        if (counter > Mothership.MAX_AGENT_COUNT || counter <= 0) {
            logger.info("Bad agent count! Corrected to " + Mothership.MAX_AGENT_COUNT);
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

    @Override
    public String toString() {
        return name;
    }

    public Color getTeamColor() {
        return teamColor;
    }

    public List<String> getMembers() {
        return members;
    }

    public int getFinalPoints() {
        return mothership.getAgentsAtHomePoints() + mothership.getShieldPoints() + points;
    }

    public static void saveDefaultTeam(final TeamData data, final boolean force) throws CouldNotPerformException {
        save(data, DEFAULT_ID, force);
    }

    public static void saveDefaultTeam(final TeamData data) throws CouldNotPerformException {
        saveDefaultTeam(data, false);
    }

    public static void save(final TeamData data) throws CouldNotPerformException {
        save(data, data.getName(), true);
    }

    public static void save(final TeamData data, final String teamName, final boolean force) throws CouldNotPerformException {
        try {
            final String fileURL = JPService.getProperty(JPTeamPath.class).getValue().getAbsolutePath() + "/" + teamName + ".team";

            if (!force && new File(fileURL).exists()) {
                throw new CouldNotPerformException("File already exist!");
            }
            TEAM_DATA_PROCESSOR.serialize(data, new File(fileURL));
//            final ObjectFileController<TeamData> fileWriter = new ObjectFileController<TeamData>(fileURL);
//            fileWriter.writeObject(data);
        } catch (JPNotAvailableException | CouldNotPerformException ex) {
            throw new CouldNotPerformException("Could not save TeamData[" + data.getName() + "] as default team!", ex);
        }
    }

    public static void resetDefaultTeam() {
        try {
            new File(JPService.getProperty(JPTeamPath.class).getValue().getAbsolutePath() + "/" + DEFAULT_ID + ".team").delete();
        } catch (JPNotAvailableException ex) {
            ExceptionPrinter.printHistory(new CouldNotPerformException("Could not reset default team!", ex), logger);
        }
    }

    public static TeamData loadDefaultTeam() throws CouldNotPerformException {
        return load(DEFAULT_ID);
    }

    public static TeamData load(final String teamName) throws CouldNotPerformException {
        try {
            return TEAM_DATA_PROCESSOR.deserialize(new File(JPService.getProperty(JPTeamPath.class).getValue().getAbsolutePath() + "/" + teamName + ".team"));
        } catch (CouldNotPerformException | JPNotAvailableException ex) {
            logger.warn("Could not load team " + teamName + "! Try again with outdated deserializer.", ex);
            try {
                final ObjectFileController<TeamData> fileWriter = new ObjectFileController<>(JPService.getProperty(JPTeamPath.class).getValue().getAbsolutePath() + "/" + teamName + ".team");
                return fileWriter.readObject();
            } catch (IOException | ClassNotFoundException | JPNotAvailableException exx) {
                throw new CouldNotPerformException("Could not load TeamData[" + teamName + "]!", exx);
            }
        }
    }

    public static List<TeamData> loadAll() throws CouldNotPerformException {
        try {

            final List<TeamData> teamList = new ArrayList<>();

            final File teamFolder;
            teamFolder = JPService.getProperty(JPTeamPath.class).getValue();
            if (!teamFolder.exists()) {
                throw new CouldNotPerformException("Could not find team folder! ");
            }

            final String[] teamClassNameList = teamFolder.list();
            if (teamClassNameList == null) {
                throw new CouldNotPerformException("Team folder is empty!!");
            }

            for (String teamClassName : teamClassNameList) {
                if (teamClassName.startsWith(DEFAULT_ID)) {
                    continue;
                }
                if (teamClassName.endsWith(".team")) {
                    String teamStringID = teamClassName.replace(".team", "");
                    try {
                        teamList.add(load(teamStringID));
                    } catch (Exception ex) {
                        logger.warn("Could not load team " + teamClassName + "!", ex);
                    }
                }
            }
            return teamList;
        } catch (JPNotAvailableException | CouldNotPerformException ex) {
            throw new CouldNotPerformException("Could not load all teams.", ex);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
        logger.debug("Add " + l.getClass() + " as new PropertyChangeListener.");
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
        logger.debug("Remove PropertyChangeListener " + l.getClass() + ".");
    }
}
