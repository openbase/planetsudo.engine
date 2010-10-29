/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.game;

import controller.ObjectFileController;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import logging.Logger;
import planetsudo.view.MainGUI;

/**
 *
 * @author divine
 */
public class LevelReciver implements Runnable {

	public final static int PORT = 8253;
	private ServerSocket welcomeSocket;

	public LevelReciver() {
		setupAndRunConnection();

	}

	private void setupAndRunConnection() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		Logger.info(this, "Setup connection...");
		ObjectInputStream in = null;
		ObjectOutputStream out = null;

		while(true) {
			try {
				welcomeSocket = new ServerSocket(PORT);
				break;
			} catch (IOException ex) {
				Logger.error(this, "Could not open Port! Try again in 10 Sec..", ex);
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException ex) {
				java.util.logging.Logger.getLogger(LevelReciver.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Logger.info(this, "Server online.");
		
		while (true) {
			try {
				Socket connectionSocket = welcomeSocket.accept();
				Logger.info(this, "Incomming team data.");
				in = new ObjectInputStream(connectionSocket.getInputStream());
				out = new ObjectOutputStream(connectionSocket.getOutputStream());

				try {
					reciveTeam(in, out);
				} catch (Exception ex) {
					Logger.error(this, "Could not transfer team!");
				}


			} catch (IOException ex) {
				Logger.error(this, "Error occured during transfer!");
			} finally {
				try {
					in.close();
					out.close();
				} catch (Exception ex) {
					Logger.error(this, "Connection Lost");
				}
			}
		}
	}

	private void reciveTeam(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
		Team team = (Team) in.readObject();
			int res = JOptionPane.showConfirmDialog(MainGUI.getInstance(),
										"Jemand möchte mit dir seine KI tauschen. Stimmst du dem zu?",
										"Team empfangen",
										JOptionPane.YES_NO_OPTION);
		Logger.debug(this, "Res is"+res);
		if(res == JOptionPane.NO_OPTION) {
			out.writeObject(new Boolean(false));
			return;
		} else {
			out.writeObject(new Boolean(true));
		}
		Team defaultTeamTmp = MainGUI.getInstance().getConfigurationPanel().getDefaultTeam();
		out.writeObject(new Boolean(defaultTeamTmp == null));
		if(defaultTeamTmp == null) {
			JOptionPane.showMessageDialog(MainGUI.getInstance(),
										"Du hast kein DefaultTeam ausgesucht! Breche Transfer ab!",
										"Cancel",
										JOptionPane.ERROR_MESSAGE);
			return;
		}
		out.writeObject(defaultTeamTmp);

		try {
			ObjectFileController<Team> fileWriter = new ObjectFileController<Team>("teams/"+team.getID()+".team");
			fileWriter.writeObject(team);
//			team.getStrategy();


//			File classFile = new File(nameAusgabedatei+".html");
//			FileWriter fw = new FileWriter(classFile);
//			BufferedWriter bw = new BufferedWriter(fw);
//			bw.write("Hallo");
//			bw.close();

		} catch (Exception ex) {
			Logger.error(this, "Could not find team folder!", ex);
			return;
		}
		MainGUI.getInstance().getConfigurationPanel().updateTeamList();
	}



}