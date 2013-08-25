/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TeamPanel.java
 *
 * Created on Jun 30, 2010, 6:13:25 PM
 */

package de.dc.planetsudo.view.menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import de.dc.planetsudo.game.GameManager;
import de.dc.planetsudo.game.Team;
import de.dc.planetsudo.level.levelobjects.Mothership;

/**
 *
 * @author noxus
 */
public class TeamMenuPanel extends javax.swing.JPanel implements PropertyChangeListener, ActionListener {
	
	private Team team;
	private Timer timer;

    /** Creates new form TeamPanel */
    public TeamMenuPanel() {
        this.initComponents();
		this.mothershipFuelProgressBar.setMinimum(0);
		this.mothershipFuelProgressBar.setMaximum(Mothership.DEFAULT_START_FUEL);
		this.timer = new Timer(300, this);
		
    }
	
	private void updateComponents() {
		teamColorPanel.setBackground(team.getTeamColor());
		teamNameLabel.setText(team.getName());
		mothershipFuelProgressBar.setForeground(Color.BLACK);
		mothershipFuelProgressBar.setValue(team.getMothership().getFuel());
		teamAgentLabel.setText(team.getAgentCount()+"");
		shieldProgressBar.setValue(team.getMothership().getShieldForce());
		resourcePointsLabel.setText(team.getPoints()+"");
		updateFuelProgressBar();
		updateShieldProgressBar();

		String memberList = "<html>";
		for(String member : team.getMembers()) {
			memberList += member+"<br />";
		}
		memberList += "</html>";
		teamMemberLabel.setText(memberList);
	}

//	private int add(int a, int b) {
//		return a+b;
//	}
//
//	private int alter;
//
//	public int getAlter() {
//		return alter;
//	}
//
//	public void setAlter(int alterNeu) {
//		this.alter = alterNeu;
//	}
	
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        teamMothershipPanel = new javax.swing.JPanel();
        mothershipFuelProgressBar = new javax.swing.JProgressBar();
        shieldProgressBar = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        saveAgentsPointsNameLabel = new javax.swing.JLabel();
        mothershipShieldPointsNameLabel = new javax.swing.JLabel();
        mothershipShieldPointsLabel = new javax.swing.JLabel();
        resourcePointsLabel = new javax.swing.JLabel();
        resourcePointsNameLabel = new javax.swing.JLabel();
        teamPointsNameLabel = new javax.swing.JLabel();
        saveAgentsPointsLabel = new javax.swing.JLabel();
        teamPointsLabel = new javax.swing.JLabel();
        teamFinalStateLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        teamMemberLabel = new javax.swing.JLabel();
        teamMemberLabel2 = new javax.swing.JLabel();
        teamAgentLabel2 = new javax.swing.JLabel();
        teamAgentLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        teamColorPanel = new javax.swing.JPanel();
        teamNameLabel = new javax.swing.JLabel();

        setOpaque(false);

        teamMothershipPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Mutterschiff"));
        teamMothershipPanel.setOpaque(false);

        mothershipFuelProgressBar.setString("Treibstoff");
        mothershipFuelProgressBar.setStringPainted(true);

        shieldProgressBar.setString("Schutzschild");
        shieldProgressBar.setStringPainted(true);

        javax.swing.GroupLayout teamMothershipPanelLayout = new javax.swing.GroupLayout(teamMothershipPanel);
        teamMothershipPanel.setLayout(teamMothershipPanelLayout);
        teamMothershipPanelLayout.setHorizontalGroup(
            teamMothershipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(teamMothershipPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(teamMothershipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mothershipFuelProgressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                    .addComponent(shieldProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
                .addContainerGap())
        );
        teamMothershipPanelLayout.setVerticalGroup(
            teamMothershipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(teamMothershipPanelLayout.createSequentialGroup()
                .addComponent(mothershipFuelProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(shieldProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Punkte"));
        jPanel1.setOpaque(false);

        saveAgentsPointsNameLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        saveAgentsPointsNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        saveAgentsPointsNameLabel.setText("Agenten Gesichert:");

        mothershipShieldPointsNameLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        mothershipShieldPointsNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        mothershipShieldPointsNameLabel.setText("Verteidigung:");

        mothershipShieldPointsLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        mothershipShieldPointsLabel.setText("0");

        resourcePointsLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        resourcePointsLabel.setText("0");

        resourcePointsNameLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        resourcePointsNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        resourcePointsNameLabel.setText("Resourcen:");

        teamPointsNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        teamPointsNameLabel.setText("Gesamt Punktzahl:");

        saveAgentsPointsLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        saveAgentsPointsLabel.setText("0");

        teamPointsLabel.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(teamPointsNameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(saveAgentsPointsNameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mothershipShieldPointsNameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resourcePointsNameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(teamPointsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mothershipShieldPointsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                    .addComponent(resourcePointsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(saveAgentsPointsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resourcePointsNameLabel)
                    .addComponent(resourcePointsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mothershipShieldPointsNameLabel)
                    .addComponent(mothershipShieldPointsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveAgentsPointsNameLabel)
                    .addComponent(saveAgentsPointsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(teamPointsNameLabel)
                    .addComponent(teamPointsLabel))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        teamFinalStateLabel.setFont(new java.awt.Font("Dialog", 1, 30));
        teamFinalStateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        teamFinalStateLabel.setText("Gewinner");

        jPanel2.setOpaque(false);

        teamMemberLabel.setText("Member Member");
        teamMemberLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        teamMemberLabel2.setText("Entwickler:");

        teamAgentLabel2.setText("Agenten:");

        teamAgentLabel.setFont(new java.awt.Font("Dialog", 1, 14));
        teamAgentLabel.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(teamMemberLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(teamAgentLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(teamAgentLabel)
                    .addComponent(teamMemberLabel)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(teamAgentLabel2)
                    .addComponent(teamAgentLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(teamMemberLabel2)
                    .addComponent(teamMemberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3.setOpaque(false);

        teamColorPanel.setBackground(new java.awt.Color(51, 102, 255));
        teamColorPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        teamColorPanel.setPreferredSize(new java.awt.Dimension(16, 16));

        javax.swing.GroupLayout teamColorPanelLayout = new javax.swing.GroupLayout(teamColorPanel);
        teamColorPanel.setLayout(teamColorPanelLayout);
        teamColorPanelLayout.setHorizontalGroup(
            teamColorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 12, Short.MAX_VALUE)
        );
        teamColorPanelLayout.setVerticalGroup(
            teamColorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 12, Short.MAX_VALUE)
        );

        teamNameLabel.setText("Team");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(teamColorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(teamNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(teamColorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(teamNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(teamMothershipPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(teamFinalStateLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(teamMothershipPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(teamFinalStateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	
	public void setTeam(Team team) {
		if(team != null) {
			team.removePropertyChangeListener(this);
			team.getMothership().removePropertyChangeListener(this);
		}
		this.team = team;
		team.addPropertyChangeListener(this);
		team.getMothership().addPropertyChangeListener(this);
		updateComponents();
		timer.stop();
		teamFinalStateLabel.setText("");
		teamFinalStateLabel.setEnabled(false);
		teamFinalStateLabel.setText("");
		saveAgentsPointsLabel.setText("-");
		mothershipShieldPointsLabel.setText("-");
	}


	public void displayEndCalculation() {
		Thread t = new Thread() {
			@Override
			public void run() {
				animateEndCalculation();
			}
		};
		t.setPriority(Thread.MIN_PRIORITY);
		t.start();
	}

	public final static int BLINK_ANIMATION = 100;
	public final static int COUNT_ANIMATION = 150;
	private void animateEndCalculation() {
		int tmpValue;
		try {
			mothershipShieldPointsLabel.setEnabled(false);
			mothershipShieldPointsNameLabel.setEnabled(false);
			Thread.sleep(BLINK_ANIMATION);
			mothershipShieldPointsLabel.setEnabled(true);
			mothershipShieldPointsNameLabel.setEnabled(true);
			tmpValue = team.getMothership().getShieldPoints();
			for(int i=0; i<=tmpValue;i++) {
				mothershipShieldPointsLabel.setText(i + "");
				Thread.sleep(COUNT_ANIMATION);
			}
			mothershipShieldPointsLabel.setEnabled(false);
			mothershipShieldPointsNameLabel.setEnabled(false);
			Thread.sleep(BLINK_ANIMATION);
			mothershipShieldPointsLabel.setEnabled(true);
			mothershipShieldPointsNameLabel.setEnabled(true);
			Thread.sleep(BLINK_ANIMATION);
			mothershipShieldPointsLabel.setEnabled(false);
			mothershipShieldPointsNameLabel.setEnabled(false);
			Thread.sleep(BLINK_ANIMATION);
			mothershipShieldPointsLabel.setEnabled(true);
			mothershipShieldPointsNameLabel.setEnabled(true);
			Thread.sleep(BLINK_ANIMATION);


			saveAgentsPointsLabel.setEnabled(false);
			saveAgentsPointsNameLabel.setEnabled(false);
			Thread.sleep(BLINK_ANIMATION);
			saveAgentsPointsLabel.setEnabled(true);
			saveAgentsPointsNameLabel.setEnabled(true);
			tmpValue = team.getMothership().getAgentsAtHomePosition();
			for(int i=0; i<=tmpValue;i++) {
				saveAgentsPointsLabel.setText(i + "");
				Thread.sleep(COUNT_ANIMATION);
			}
			saveAgentsPointsLabel.setEnabled(false);
			saveAgentsPointsNameLabel.setEnabled(false);
			Thread.sleep(BLINK_ANIMATION);
			saveAgentsPointsLabel.setEnabled(true);
			saveAgentsPointsNameLabel.setEnabled(true);
			Thread.sleep(BLINK_ANIMATION);
			saveAgentsPointsLabel.setEnabled(false);
			saveAgentsPointsNameLabel.setEnabled(false);
			Thread.sleep(BLINK_ANIMATION);
			saveAgentsPointsLabel.setEnabled(true);
			saveAgentsPointsNameLabel.setEnabled(true);
			Thread.sleep(BLINK_ANIMATION);


			teamPointsLabel.setEnabled(false);
			teamPointsNameLabel.setEnabled(false);
			Thread.sleep(BLINK_ANIMATION);
			teamPointsLabel.setEnabled(true);
			teamPointsNameLabel.setEnabled(true);
			tmpValue = team.getFinalPoints();
			for(int i=0; i<=tmpValue;i++) {
				teamPointsLabel.setText(i + "");
				Thread.sleep(COUNT_ANIMATION);
			}
			teamPointsLabel.setEnabled(false);
			teamPointsNameLabel.setEnabled(false);
			Thread.sleep(BLINK_ANIMATION);
			teamPointsLabel.setEnabled(true);
			teamPointsNameLabel.setEnabled(true);
			Thread.sleep(BLINK_ANIMATION);
			teamPointsLabel.setEnabled(false);
			teamPointsNameLabel.setEnabled(false);
			Thread.sleep(BLINK_ANIMATION);
			teamPointsLabel.setEnabled(true);
			teamPointsNameLabel.setEnabled(true);
			Thread.sleep(BLINK_ANIMATION);

			
			if(GameManager.getInstance().isWinner(team)) {
				Thread.sleep(BLINK_ANIMATION);
				teamFinalStateLabel.setText("Gewinner");
				teamFinalStateLabel.setForeground(Color.GREEN);
				Thread.sleep(BLINK_ANIMATION);
				teamFinalStateLabel.setEnabled(true);
				Thread.sleep(BLINK_ANIMATION);
				teamFinalStateLabel.setEnabled(false);
				Thread.sleep(BLINK_ANIMATION);
				teamFinalStateLabel.setEnabled(true);
				Thread.sleep(BLINK_ANIMATION);
				teamFinalStateLabel.setEnabled(false);
				Thread.sleep(BLINK_ANIMATION);
				teamFinalStateLabel.setEnabled(true);
				Thread.sleep(BLINK_ANIMATION);
				teamFinalStateLabel.setEnabled(false);
				Thread.sleep(BLINK_ANIMATION);
				teamFinalStateLabel.setEnabled(true);
				Thread.sleep(BLINK_ANIMATION);
				teamFinalStateLabel.setEnabled(false);
				Thread.sleep(BLINK_ANIMATION);
				teamFinalStateLabel.setEnabled(true);
			} else {
				teamFinalStateLabel.setText("Verlierer");
				Thread.sleep(BLINK_ANIMATION);
				teamFinalStateLabel.setForeground(Color.RED);
				teamFinalStateLabel.setEnabled(true);
			}


		} catch (InterruptedException ex) {
				Logger.getLogger(TeamMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JProgressBar mothershipFuelProgressBar;
    private javax.swing.JLabel mothershipShieldPointsLabel;
    private javax.swing.JLabel mothershipShieldPointsNameLabel;
    private javax.swing.JLabel resourcePointsLabel;
    private javax.swing.JLabel resourcePointsNameLabel;
    private javax.swing.JLabel saveAgentsPointsLabel;
    private javax.swing.JLabel saveAgentsPointsNameLabel;
    private javax.swing.JProgressBar shieldProgressBar;
    private javax.swing.JLabel teamAgentLabel;
    private javax.swing.JLabel teamAgentLabel2;
    private javax.swing.JPanel teamColorPanel;
    private javax.swing.JLabel teamFinalStateLabel;
    private javax.swing.JLabel teamMemberLabel;
    private javax.swing.JLabel teamMemberLabel2;
    private javax.swing.JPanel teamMothershipPanel;
    private javax.swing.JLabel teamNameLabel;
    private javax.swing.JLabel teamPointsLabel;
    private javax.swing.JLabel teamPointsNameLabel;
    // End of variables declaration//GEN-END:variables

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(Mothership.FUEL_STATE_CHANGE)) {
			mothershipFuelProgressBar.setValue((Integer) evt.getNewValue());
			updateFuelProgressBar();
		} else if(evt.getPropertyName().equals(Mothership.SHIELD_STATE_CHANGE)) {
			shieldProgressBar.setValue((Integer) evt.getNewValue());
			updateShieldProgressBar();
		} else if(evt.getPropertyName().equals(Team.POINT_STATE_CHANGE)) {
			resourcePointsLabel.setText(evt.getNewValue().toString());
		}
	}
	
	private void updateFuelProgressBar() {

		mothershipFuelProgressBar.setString("Treibstoff " + (int) (mothershipFuelProgressBar.getPercentComplete() * 100) + "%");
			if(mothershipFuelProgressBar.getPercentComplete() < 0.25) {
				timer.start();
			}
		int green, red;
		if (mothershipFuelProgressBar.getPercentComplete() >= 0.5) {
			red = (int) (255 - (255 * (mothershipFuelProgressBar.getPercentComplete() - 0.5) * 2));
			green = 255;
		} else {
			red = 255;
			green = (int) (255 * (mothershipFuelProgressBar.getPercentComplete()) * 2);
		}
		mothershipFuelProgressBar.setForeground(new Color(red, green, 0));
	}

	private void updateShieldProgressBar() {

		shieldProgressBar.setString("Schutzschild " + (int) (shieldProgressBar.getPercentComplete() * 100) + "%");
			if(shieldProgressBar.getPercentComplete() < Mothership.BURNING_MOTHERSHIP/100) {
				timer.start();
			}
		int blue, red;
		if (shieldProgressBar.getPercentComplete() >= 0.5) {
			red = (int) (255 - (255 * (shieldProgressBar.getPercentComplete() - 0.5) * 2));
			blue = 255;
		} else {
			red = 255;
			blue = (int) (255 * (shieldProgressBar.getPercentComplete()) * 2);
		}
		shieldProgressBar.setForeground(new Color(red, 0, blue));
	}

	private boolean blink;

	@Override
	public void actionPerformed(ActionEvent e) {
		if(shieldProgressBar.getPercentComplete() < Mothership.BURNING_MOTHERSHIP/100) {
			if(blink) {
				shieldProgressBar.setForeground(Color.BLACK);
			} else {
				updateShieldProgressBar();
			}
		}

		if(mothershipFuelProgressBar.getPercentComplete() < 0.25) {
			if(blink) {
				mothershipFuelProgressBar.setForeground(Color.BLACK);
			} else {
				updateFuelProgressBar();
			}
		}
		blink = !blink;

		if(mothershipFuelProgressBar.getPercentComplete() > 0.25 && shieldProgressBar.getPercentComplete() > Mothership.BURNING_MOTHERSHIP/100) {
			timer.stop();
			updateShieldProgressBar();
			updateFuelProgressBar();
		}
	}
}