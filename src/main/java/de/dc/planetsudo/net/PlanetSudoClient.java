/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.net;

import de.citec.jps.core.JPService;
import de.dc.planetsudo.game.Team;
import de.dc.planetsudo.game.TeamData;
import de.dc.planetsudo.game.strategy.StrategyClassLoader;
import de.dc.planetsudo.main.command.SetExternalStrategyJar;
import de.dc.planetsudo.main.command.SetServer;
import de.dc.planetsudo.main.command.SetServerPort;
import de.dc.planetsudo.main.command.SetStrategySourceDirectory;
import de.dc.planetsudo.view.MainGUI;
import de.dc.util.exceptions.CouldNotPerformException;
import de.dc.util.logging.Logger;
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

/**
 *
 * @author Divine <DivineThreepwood@gmail.com>
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
			Logger.error(this, "Could not sync!", ex);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException ex) {
				Logger.error(this, "Connection Lost!");
			}
		}

		MainGUI.getInstance().getConfigurationPanel().updateTeamList();
	}

	private void connecting() {
		setConnectionState(ConnectionState.Connecting);
		try {
			Socket clientSocket = new Socket(JPService.getAttribute(SetServer.class).getValue(), JPService.getProperty(SetServerPort.class).getValue());
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
		} catch (Exception ex) {
			Logger.error(this, "Error during transfer occured!", ex);
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
		for(int i=0; i<teamCounter; i++) {
			teamData = (TeamData) in.readObject();
			Team.save(teamData);
		}
	}

	private void uploadDefaultStrategy() throws CouldNotPerformException, FileNotFoundException, IOException {
		setConnectionState(ConnectionState.UploadDefaultStrategy);
		final TeamData defaultTeamData = Team.loadDefaultTeam();
		final File sourceFile = new File(JPService.getAttribute(SetStrategySourceDirectory.class).getValue(), defaultTeamData.getStrategy()+".java");
		if(!sourceFile.exists()) {
			throw new CouldNotPerformException("File["+sourceFile.getAbsolutePath()+"] does not exist!");
		}
		out.writeUTF(sourceFile.getName());
		final byte[] fileBytes = FileUtils.readFileToByteArray(sourceFile);
		out.writeInt(fileBytes.length);
		IOUtils.write(fileBytes, out);
		out.flush();
	}

	private void downloadStrategies() throws IOException {
		setConnectionState(ConnectionState.DownloadStrategies);
		final File jarFile = JPService.getAttribute(SetExternalStrategyJar.class).getValue();
		final int fileByteLenght = in.readInt();
		final byte[] fileBytes = new byte[fileByteLenght];
		IOUtils.readFully(in, fileBytes);
		FileUtils.writeByteArrayToFile(jarFile, fileBytes);
		StrategyClassLoader.revalidate();
	}

	public void setConnectionState(final ConnectionState state) {
		if (this.state == state) {
			return;
		}
		this.state = state;
		Logger.info(this, "ConnectionState: "+state.name());
		change.firePropertyChange(CONNECTION_STATE_UPDATE, null, state);
	}

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		change.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		change.removePropertyChangeListener(listener);
	}
}