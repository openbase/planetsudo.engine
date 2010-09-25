/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TeamPanel.java
 *
 * Created on Jun 30, 2010, 6:13:25 PM
 */

package planetmesserlost.view.menu;

import planetmesserlost.game.Team;

/**
 *
 * @author noxus
 */
public class TeamMenuPanel extends javax.swing.JPanel {
	
	private Team team;

    /** Creates new form TeamPanel */
    public TeamMenuPanel() {
        initComponents();
    }

	public TeamMenuPanel(Team team) {
		 initComponents();
		 updateComponents();
	}
	
	private void updateComponents() {
		teamNameLabel.setText(team.getName());
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

        teamNameLabel = new javax.swing.JLabel();
        teamMothershipPanel = new javax.swing.JPanel();
        mothershipFuelProgressBar = new javax.swing.JProgressBar();
        mothershipStatusLabel = new javax.swing.JLabel();
        teamColorPanel = new javax.swing.JPanel();
        teamRessourceLabel2 = new javax.swing.JLabel();
        teamRessourceLabel = new javax.swing.JLabel();

        setOpaque(false);

        teamNameLabel.setText("Team");

        teamMothershipPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Mutterschiff"));
        teamMothershipPanel.setOpaque(false);

        mothershipFuelProgressBar.setString("Treibstoff");
        mothershipFuelProgressBar.setStringPainted(true);

        mothershipStatusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mothershipStatusLabel.setText("Status");

        javax.swing.GroupLayout teamMothershipPanelLayout = new javax.swing.GroupLayout(teamMothershipPanel);
        teamMothershipPanel.setLayout(teamMothershipPanelLayout);
        teamMothershipPanelLayout.setHorizontalGroup(
            teamMothershipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, teamMothershipPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(teamMothershipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mothershipStatusLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                    .addComponent(mothershipFuelProgressBar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                .addContainerGap())
        );
        teamMothershipPanelLayout.setVerticalGroup(
            teamMothershipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(teamMothershipPanelLayout.createSequentialGroup()
                .addComponent(mothershipFuelProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mothershipStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE))
        );

        mothershipStatusLabel.getAccessibleContext().setAccessibleParent(null);

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

        teamRessourceLabel2.setText("Gesammelte Rohstoffe:");

        teamRessourceLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        teamRessourceLabel.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(11, 11, 11)
                            .addComponent(teamColorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(teamNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(teamMothershipPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(teamRessourceLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(teamRessourceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(teamNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(teamColorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(teamMothershipPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(teamRessourceLabel2)
                    .addComponent(teamRessourceLabel))
                .addContainerGap(206, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar mothershipFuelProgressBar;
    private javax.swing.JLabel mothershipStatusLabel;
    private javax.swing.JPanel teamColorPanel;
    private javax.swing.JPanel teamMothershipPanel;
    private javax.swing.JLabel teamNameLabel;
    private javax.swing.JLabel teamRessourceLabel;
    private javax.swing.JLabel teamRessourceLabel2;
    // End of variables declaration//GEN-END:variables

}
