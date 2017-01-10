package org.openbase.planetsudo.view.game;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2017 openbase.org
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

import org.openbase.planetsudo.game.GameManager;
import org.openbase.planetsudo.view.level.LevelDisplayPanel.VideoThreadCommand;
import org.openbase.planetsudo.view.menu.LevelMenuPanel;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
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

	public void updateDynamicComponents() throws InterruptedException {
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
				levelMenuPanel.stopTimer();
				levelMenuPanel.reset();
				break;
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

        levelDisplayPanel = new org.openbase.planetsudo.view.level.LevelDisplayPanel();
        teamMenuPanel1 = new org.openbase.planetsudo.view.menu.TeamMenuPanel();
        teamMenuPanel2 = new org.openbase.planetsudo.view.menu.TeamMenuPanel();

        setLayout(new java.awt.BorderLayout(5, 5));
        add(levelDisplayPanel, java.awt.BorderLayout.CENTER);
        add(teamMenuPanel1, java.awt.BorderLayout.LINE_START);
        add(teamMenuPanel2, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.openbase.planetsudo.view.level.LevelDisplayPanel levelDisplayPanel;
    private org.openbase.planetsudo.view.menu.TeamMenuPanel teamMenuPanel1;
    private org.openbase.planetsudo.view.menu.TeamMenuPanel teamMenuPanel2;
    // End of variables declaration//GEN-END:variables

}
