/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GamePanel.java
 *
 * Created on Jun 17, 2010, 1:03:15 AM
 */

package planetsudo.view.game;

import planetsudo.game.GameManager;
import planetsudo.view.level.LevelDisplayPanel.VideoThreadCommand;
import planetsudo.view.menu.LevelMenuPanel;

/**
 *
 * @author divine
 */
public class GamePanel extends javax.swing.JPanel {

	private boolean displayTeamPanel;
	private LevelMenuPanel levelMenuPanel;

    /** Creates new form GamePanel */
    public GamePanel() {
        this.initComponents();
		this.levelMenuPanel = new LevelMenuPanel();
		this.add(levelMenuPanel, java.awt.BorderLayout.NORTH);
		this.displayTeamPanel = true;
    }

	public void updateDynamicComponents() {
		levelDisplayPanel.setLevel(GameManager.getInstance().getLevel());
		levelDisplayPanel.displayLevelObjects();
		teamMenuPanel1.setTeam(GameManager.getInstance().getTeamA());
		teamMenuPanel2.setTeam(GameManager.getInstance().getTeamB());
		levelMenuPanel.setLevel(GameManager.getInstance().getLevel());
		setBackground(GameManager.getInstance().getLevel().getColor());
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

	public void displayEndCalculation() {
		teamMenuPanel1.displayEndCalculation();
		teamMenuPanel2.displayEndCalculation();
	}

	public void setVideoThreadCommand(VideoThreadCommand command) {
		levelDisplayPanel.setVideoThreadCommand(command);
		switch(command) {
			case Start:
			case Resume:
				levelMenuPanel.startTimer();
				break;
			case Stop:
				levelMenuPanel.reset();
			case Pause:
				levelMenuPanel.stopTimer();
				break;
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

        levelDisplayPanel = new planetsudo.view.level.LevelDisplayPanel();
        teamMenuPanel1 = new planetsudo.view.menu.TeamMenuPanel();
        teamMenuPanel2 = new planetsudo.view.menu.TeamMenuPanel();

        setLayout(new java.awt.BorderLayout(5, 5));
        add(levelDisplayPanel, java.awt.BorderLayout.CENTER);
        add(teamMenuPanel1, java.awt.BorderLayout.LINE_START);
        add(teamMenuPanel2, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private planetsudo.view.level.LevelDisplayPanel levelDisplayPanel;
    private planetsudo.view.menu.TeamMenuPanel teamMenuPanel1;
    private planetsudo.view.menu.TeamMenuPanel teamMenuPanel2;
    // End of variables declaration//GEN-END:variables

}
