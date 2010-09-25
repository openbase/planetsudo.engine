/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GamePanel.java
 *
 * Created on Jun 17, 2010, 1:03:15 AM
 */

package planetmesserlost.view.game;

import planetmesserlost.game.GameManager;
import planetmesserlost.view.menu.TeamMenuPanel;

/**
 *
 * @author divine
 */
public class GamePanel extends javax.swing.JPanel {

	private boolean displayTeamPanel;

    /** Creates new form GamePanel */
    public GamePanel() {
        this.initComponents();
		this.displayTeamPanel = true;
    }

	public void updateDynamicComponents() {
		levelDisplayPanel.setLevel(GameManager.getInstance().getLevel());
		teamMenuPanel1.setTeam(GameManager.getInstance().getTeams().get(0));
		teamMenuPanel2.setTeam(GameManager.getInstance().getTeams().get(1));
		updateTeamPanelDisplayState();	
	}

	public void displayTeamPanel(boolean visible) {
		displayTeamPanel = visible;
		updateTeamPanelDisplayState();
	}
	
	public boolean isTeamPanelDisplayed() {
		return displayTeamPanel;
	}

	public void updateTeamPanelDisplayState() {
		teamMenuPanel1.setVisible(displayTeamPanel);
		teamMenuPanel2.setVisible(displayTeamPanel);
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        levelDisplayPanel = new planetmesserlost.view.level.LevelDisplayPanel();
        teamMenuPanel1 = new planetmesserlost.view.menu.TeamMenuPanel();
        teamMenuPanel2 = new planetmesserlost.view.menu.TeamMenuPanel();

        setBackground(new java.awt.Color(255, 153, 0));
        setLayout(new java.awt.BorderLayout(5, 5));
        add(levelDisplayPanel, java.awt.BorderLayout.CENTER);
        add(teamMenuPanel1, java.awt.BorderLayout.LINE_START);
        add(teamMenuPanel2, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private planetmesserlost.view.level.LevelDisplayPanel levelDisplayPanel;
    private planetmesserlost.view.menu.TeamMenuPanel teamMenuPanel1;
    private planetmesserlost.view.menu.TeamMenuPanel teamMenuPanel2;
    // End of variables declaration//GEN-END:variables

}
