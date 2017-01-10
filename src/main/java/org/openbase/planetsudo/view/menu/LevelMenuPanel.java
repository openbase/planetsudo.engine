/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LevelMenuPanel.java
 *
 * Created on Oct 4, 2010, 3:05:09 PM
 */

package org.openbase.planetsudo.view.menu;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2016 openbase.org
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import org.openbase.planetsudo.level.AbstractLevel;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class LevelMenuPanel extends javax.swing.JPanel implements ActionListener {

	private int minutes, secunds;
	private Timer timer;
	private String levelName;

    /** Creates new form LevelMenuPanel */
    public LevelMenuPanel() {
        initComponents();
		this.minutes = 0;
		this.secunds = 0;
		this.levelName = "Level";
		this.timer = new Timer(1000, this);
    }

	public void setLevel(AbstractLevel level) {
		levelName = level.getName();
		updateTitle();
	}

	private String text;
	private void updateTitle() {
		text = levelName+" [";
		if(minutes < 9) {
			text += "0";
		}
		text += minutes+":";
		if(secunds < 9) {
			text += "0";
		}
		text += secunds+"]";
		nameAndTimeLabel.setText(text);
	}

	public void startTimer() {
		if(!timer.isRunning())
		timer.start();
	}

	public void stopTimer() {
		if(timer.isRunning()) {
			timer.stop();
		}
	}

	public void reset() {
		stopTimer();
		secunds = 0;
		minutes = 0;
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameAndTimeLabel = new javax.swing.JLabel();

        setOpaque(false);

        nameAndTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nameAndTimeLabel.setText("Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nameAndTimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nameAndTimeLabel))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel nameAndTimeLabel;
    // End of variables declaration//GEN-END:variables

	@Override
	public void actionPerformed(ActionEvent e) {
		if(secunds >= 60) {
			minutes++;
			secunds = 0;
		} else {
			secunds++;
		}
		updateTitle();
	}
}