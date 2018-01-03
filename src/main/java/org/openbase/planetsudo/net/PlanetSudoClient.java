/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.net;

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

import org.openbase.jps.core.JPService;
import org.openbase.planetsudo.game.Team;
import org.openbase.planetsudo.game.TeamData;
import org.openbase.planetsudo.game.strategy.StrategyClassLoader;
import org.openbase.planetsudo.jp.JPExternalStrategyJar;
import org.openbase.planetsudo.jp.JPServerHostname;
import org.openbase.planetsudo.jp.JPServerPort;
import org.openbase.planetsudo.jp.JPStrategySourceDirectory;
import org.openbase.planetsudo.view.MainGUI;
import org.slf4j.Logger;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.SwingWorker;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openbase.jps.exception.JPNotAvailableException;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Divine Threepwood
 */
public class PlanetSudoClient {

    public static final String CONNECTION_STATE_UPDATE = "ConnectionStateUpdate";

    public enum ConnectionState {

        UploadDefaultTeam("Sende Team..."),
        DownloadTeams("Empfange Teams..."),
        UploadDefaultStrategy("Sende Strategie..."),
        DownloadStrategies("Empfange Strategien..."),
        Connecting("Verbindung wird hergestellt..."),
        SyncSuccessful("Erfolgreich Synchronisiert!"),
        ConnectionError("Verbindungsfehler!");

        private String description;

        private ConnectionState(final String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static PlanetSudoClient instance;
    private final PropertyChangeSupport change;
    private ConnectionState state;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;

    private PlanetSudoClient() {
        this.change = new PropertyChangeSupport(this);
    }

    public synchronized static PlanetSudoClient getInstance() {
        if (instance == null) {
            instance = new PlanetSudoClient();
        }
        return instance;
    }

    public void runSync() {
        final SwingWorker worker = new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {
                sync();
                return null;
            }
        };
        worker.execute();
    }

    private synchronized void sync() {
        try {
            connecting();
            transferData();
        } catch (Exception ex) {
            setConnectionState(ConnectionState.ConnectionError);
            logger.error("Could not sync!", ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                logger.error("Connection Lost!");
            }
        }

        MainGUI.getInstance().getConfigurationPanel().updateTeamList();
    }

    private void connecting() {
        setConnectionState(ConnectionState.Connecting);
        try {
            Socket clientSocket = new Socket(JPService.getProperty(JPServerHostname.class).getValue(), JPService.getProperty(JPServerPort.class).getValue());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        } catch (Exception ex) {
            ExceptionPrinter.printHistory(new CouldNotPerformException("Error during transfer occured!", ex), logger);
        }
    }

    private void transferData() throws CouldNotPerformException, IOException, ClassNotFoundException {
        uploadDefaultTeam();
        uploadDefaultStrategy();
        downloadTeams();
        downloadStrategies();
        setConnectionState(ConnectionState.SyncSuccessful);
    }

    private void uploadDefaultTeam() throws CouldNotPerformException, IOException {
        setConnectionState(ConnectionState.UploadDefaultTeam);
        out.writeObject(Team.loadDefaultTeam());
    }

    private void downloadTeams() throws IOException, ClassNotFoundException, CouldNotPerformException {
        setConnectionState(ConnectionState.DownloadTeams);
        final int teamCounter = in.readInt();
        TeamData teamData;
        for (int i = 0; i < teamCounter; i++) {
            teamData = (TeamData) in.readObject();
            Team.save(teamData);
        }
    }

    private void uploadDefaultStrategy() throws CouldNotPerformException, FileNotFoundException, IOException {
        try {
            setConnectionState(ConnectionState.UploadDefaultStrategy);
            final TeamData defaultTeamData = Team.loadDefaultTeam();
            final File sourceFile = new File(JPService.getProperty(JPStrategySourceDirectory.class).getValue(), defaultTeamData.getStrategy() + ".java");
            if (!sourceFile.exists()) {
                throw new CouldNotPerformException("File[" + sourceFile.getAbsolutePath() + "] does not exist!");
            }
            out.writeUTF(sourceFile.getName());
            final byte[] fileBytes = FileUtils.readFileToByteArray(sourceFile);
            out.writeInt(fileBytes.length);
            IOUtils.write(fileBytes, out);
            out.flush();
        } catch (CouldNotPerformException | JPNotAvailableException ex) {
            throw new CouldNotPerformException("Could not upload default strategy!", ex);
        }
    }

    private void downloadStrategies() throws CouldNotPerformException {
        try {
            setConnectionState(ConnectionState.DownloadStrategies);
            final File jarFile = JPService.getProperty(JPExternalStrategyJar.class).getValue();
            final int fileByteLenght = in.readInt();
            final byte[] fileBytes = new byte[fileByteLenght];
            IOUtils.readFully(in, fileBytes);
            FileUtils.writeByteArrayToFile(jarFile, fileBytes);
            StrategyClassLoader.revalidate();
        } catch (JPNotAvailableException | IOException ex) {
            throw new CouldNotPerformException("Could not download stradegies!", ex);
        }
    }

    public void setConnectionState(final ConnectionState state) {
        if (this.state == state) {
            return;
        }
        this.state = state;
        logger.info("ConnectionState: " + state.name());
        change.firePropertyChange(CONNECTION_STATE_UPDATE, null, state);
    }

    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        change.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        change.removePropertyChangeListener(listener);
    }
}
