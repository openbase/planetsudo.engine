/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AINetworkTransferMenu.java
 *
 * Created on Oct 28, 2010, 3:04:15 AM
 */

package planetsudo.view.menu;

import configuration.parameter.CommandParameterParser;
import controller.ObjectFileController;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import logging.Logger;
import planetsudo.game.LevelReciver;
import planetsudo.game.Team;
import planetsudo.main.clc.SetTeamPathCommand;
import planetsudo.view.MainGUI;

/**
 *
 * @author divine
 */
public class AINetworkTransferMenu extends javax.swing.JFrame {

	private static AINetworkTransferMenu instance;

	public synchronized static void display() {
		if (instance == null) {
			java.awt.EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					new AINetworkTransferMenu().setVisible(true);
				}
			});
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
				java.util.logging.Logger.getLogger(AINetworkTransferMenu.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			instance.setVisible(true);
		}
	}


    /** Creates new form AINetworkTransferMenu */
    public AINetworkTransferMenu() {
		instance = this;
        initComponents();
		setLocation(300, 300);
		updateTeamList();
    }

	public void connectToServer() {
		sendButton.setEnabled(false);
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		try {
			Socket clientSocket = new Socket(hostTextField.getText(), LevelReciver.PORT);
			stateLabel.setText("Verbunden mit "+hostTextField.getText());
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
			sendTeam(out, in , clientSocket);
		} catch (Exception ex) {
			sendButton.setEnabled(true);
			Logger.error(this, "Error during transfer occured!", ex);
			stateLabel.setText("Übertragungsfehler!");
		} finally {
			try {
				out.close();
				in.close();
			} catch (IOException ex) {
				Logger.error(this, "Connection Lost!");
			}
			sendButton.setEnabled(true);
		}
		sendButton.setEnabled(true);
	}

	public void updateTeamList() {

		teamComboBox.removeAllItems();

		File teamFolder = new File(CommandParameterParser.getAttribute(SetTeamPathCommand.class).getValue());
		if(!teamFolder.exists()) {
			Logger.error(this, "Could not find team folder! ");
			return;
		}
		String[] teamClassNameList = teamFolder.list();
		if(teamClassNameList == null) {
			Logger.error(this, "Team folder is empty!!");
			return;
		}
		Team tmpTeam;
		for (String teamClassName : teamClassNameList) {
			if (teamClassName.endsWith(".team")) {
				String teamStringID = teamClassName.replace(".team", "");

				try {
					ObjectFileController<Team> readerController = new ObjectFileController<Team>(CommandParameterParser.getAttribute(SetTeamPathCommand.class).getValue()+ teamStringID + ".team");

					tmpTeam  = readerController.readObject();
					teamComboBox.addItem(tmpTeam);
				} catch (Exception ex) {
					Logger.error(this, "Could not find team "+teamClassName+"!", ex);
					continue;
				}
			}
		}
	}



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        teamComboBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        hostTextField = new javax.swing.JTextField();
        stateLabel = new javax.swing.JLabel();
        sendButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Netzübertragung");
        setAlwaysOnTop(true);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Team und KI übers Netzwerk verschicken"));

        jLabel4.setText("Team");

        teamComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Empfänger:");

        hostTextField.setText("Host / IP");
        hostTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hostTextFieldActionPerformed(evt);
            }
        });

        stateLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        stateLabel.setText("Not Connected");

        sendButton.setText("Senden");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Status:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(hostTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendButton))
                    .addComponent(teamComboBox, 0, 301, Short.MAX_VALUE)
                    .addComponent(stateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(teamComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(hostTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stateLabel)
                    .addComponent(jLabel3))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void hostTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hostTextFieldActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_hostTextFieldActionPerformed

	private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
		stateLabel.setText("Verbinde mit "+hostTextField.getText());
		Thread t = new Thread() {
			@Override
			public void run() {
				connectToServer();
			}
		};
		t.start();
		

	}//GEN-LAST:event_sendButtonActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AINetworkTransferMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField hostTextField;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton sendButton;
    private javax.swing.JLabel stateLabel;
    private javax.swing.JComboBox teamComboBox;
    // End of variables declaration//GEN-END:variables

	private void sendTeam(ObjectOutputStream out, ObjectInputStream in, Socket clientSocket) throws IOException, ClassNotFoundException {
		stateLabel.setText("Übertrage Team");
		Team teamToSend = (Team) teamComboBox.getSelectedItem();
		URL strategyURL =  teamToSend.getStrategy().getResource(teamToSend.getStrategy().getSimpleName()+".class");
		try {
			File strategyFile = new File(strategyURL.toURI());
			out.writeObject(strategyFile);
		} catch (URISyntaxException ex) {
			java.util.logging.Logger.getLogger(AINetworkTransferMenu.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			Logger.info(this, "########### ClassLocation:" + strategyURL.toURI().toString());
		} catch (URISyntaxException ex) {
			java.util.logging.Logger.getLogger(AINetworkTransferMenu.class.getName()).log(Level.SEVERE, null, ex);
		}
		//out.writeObject(new File(teamToSend.getStrategy().teamToSend)
		out.writeObject(teamToSend);
		if(((Boolean) in.readObject())) {
			stateLabel.setText("Connecting");
		} else {
			stateLabel.setText("Empfang verweigert!");
			clientSocket.close();
			sendButton.setEnabled(true);
			return;
		}
		if(!((Boolean) in.readObject())) {
			stateLabel.setText("Empfange Team");
		} else {
			stateLabel.setText("Gegenstelle nicht bereit!");
			clientSocket.close();
			sendButton.setEnabled(true);
			return;
		}
		File strategyFileSend = (File) in.readObject();




//		FileWriter fstream = new FileWriter(strategyFileSend.getPath()+strategyFileSend.getName());
//        BufferedWriter outfile = new BufferedWriter(fstream);
//			outfile.writewrite(strategyFileSend);
		 //Close the output stream
    out.close();




		//getClass().getClassLoader().getSystemClassLoader().
		Team team = (Team) in.readObject();
		Logger.info(this, "Incomming OtherTeam");

		try {
			ObjectFileController<Team> fileWriter = new ObjectFileController<Team>(CommandParameterParser.getAttribute(SetTeamPathCommand.class).getValue()+team.getID()+".team");
			fileWriter.writeObject(team);
		} catch (Exception ex) {
			stateLabel.setText("Konnte Team nicht speichern!");
			Logger.error(this, "Could not find team folder!", ex);
			return;
		}
		MainGUI.getInstance().getConfigurationPanel().updateTeamList();
		clientSocket.close();
		stateLabel.setText("Übertragung erfolgreich,");
	}

}
