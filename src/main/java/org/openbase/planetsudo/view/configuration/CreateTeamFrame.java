/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CreateTeamFrame.java
 *
 * Created on Oct 28, 2010, 9:50:26 AM
 */
package org.openbase.planetsudo.view.configuration;

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

import java.awt.Color;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.openbase.planetsudo.game.Team;
import org.openbase.planetsudo.game.TeamData;
import org.openbase.planetsudo.game.strategy.AbstractStrategy;
import org.openbase.planetsudo.game.strategy.StrategyClassLoader;
import org.openbase.planetsudo.view.MainGUI;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;
import org.openbase.jul.visual.swing.component.ColorChooserFrame;
import org.slf4j.LoggerFactory;

/**
 *
 * @author divine
 */
public class CreateTeamFrame extends javax.swing.JFrame {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
	private static CreateTeamFrame instance;

	public synchronized static void display() {
		if (instance == null) {
			java.awt.EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					new CreateTeamFrame().setVisible(true);
				}
			});
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
				java.util.logging.Logger.getLogger(CreateTeamFrame.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			instance.reset();
			instance.setVisible(true);
		}
	}

	/**
	 * Creates new form CreateTeamFrame
	 */
	public CreateTeamFrame() {
		instance = this;
		initComponents();
		setLocation(300, 300);
	}

	private void createTeam() {
		String name;
		Color color;
		Class<? extends AbstractStrategy> strategy = null;
		List<String> members = new ArrayList<String>();
		strategyTextField.setForeground(Color.BLACK);
		createButton.setForeground(Color.BLACK);

		name = nameTextField.getText();

		if (!colorChooserButton.getText().equals("")) {
			colorChooserButton.setForeground(Color.RED);
			colorChooserButton.setText("No Teamcolor selected!");
			logger.error("Color not selected!");
			return;
		}
		color = colorChooserButton.getBackground();

//			logger.info("try to load "+ AbstractStrategy.class.getPackage().getName()+"."+strategyTextField.getText());
//			strategy = (Class<AbstractStrategy>) getClass().getClassLoader().loadClass(AbstractStrategy.class.getPackage().getName()+"."+strategyTextField.getText());
//		} catch (ClassNotFoundException ex) {
//			strategyTextField.setForeground(Color.RED);
//			logger.error("Could not find strategy!", ex);
//			return;
//		}



		try {
			strategy = StrategyClassLoader.loadStrategy(strategyTextField.getText());
		} catch (Exception ex) {
			strategyTextField.setForeground(Color.RED);
			logger.warn("Could not load strategy!", ex);
			return;
		}
		members.add(member1TextField.getText());
		members.add(member2TextField.getText());

		final TeamData teamData = new TeamData(name, color, strategy.getSimpleName(), members);
		
		try {
			Team.save(teamData);
		} catch (Exception ex) {
			logger.error("Could not find team folder!", ex);
			createButton.setForeground(Color.RED);
			return;
		}
		MainGUI.getInstance().getConfigurationPanel().updateTeamList();
		setVisible(false);
		reset();
	}

	private void reset() {
		nameTextField.setText("");
		strategyTextField.setText("");
		member1TextField.setText("");
		member2TextField.setText("");
		colorChooserButton.setBackground(new Color(220,220,220));
		colorChooserButton.setText("Klicken um Farbe zu wählen");
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        createButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        member2TextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        member1TextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        strategyTextField = new javax.swing.JTextField();
        nameTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        colorChooserButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Erstelle Team");
        setAlwaysOnTop(true);
        setResizable(false);

        createButton.setText("Erstellen");
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Team Angaben"));
        jPanel1.setLayout(null);
        jPanel1.add(member2TextField);
        member2TextField.setBounds(10, 180, 347, 27);

        jLabel7.setText("Entwickler");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(370, 180, 76, 29);

        jLabel5.setText("Entwickler");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(370, 140, 76, 27);
        jPanel1.add(member1TextField);
        member1TextField.setBounds(10, 140, 347, 27);

        jLabel8.setText("Farbe");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(370, 100, 76, 29);

        jLabel4.setText("Strategie");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(370, 60, 76, 27);
        jPanel1.add(strategyTextField);
        strategyTextField.setBounds(10, 60, 347, 27);

        nameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTextFieldActionPerformed(evt);
            }
        });
        jPanel1.add(nameTextField);
        nameTextField.setBounds(10, 20, 347, 27);

        jLabel2.setText("Name");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(370, 20, 40, 27);

        colorChooserButton.setText("Klicken um Farbe zu wählen");
        colorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorChooserButtonActionPerformed(evt);
            }
        });
        jPanel1.add(colorChooserButton);
        colorChooserButton.setBounds(10, 100, 347, 29);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(createButton)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(createButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed
		createTeam();
	}//GEN-LAST:event_createButtonActionPerformed

    private void colorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorChooserButtonActionPerformed
        final SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                Color color = ColorChooserFrame.getColor();
                colorChooserButton.setBackground(color);
                colorChooserButton.setText("");
                return null;
            }
        };
        worker.execute();
    }//GEN-LAST:event_colorChooserButtonActionPerformed

    private void nameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameTextFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton colorChooserButton;
    private javax.swing.JButton createButton;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField member1TextField;
    private javax.swing.JTextField member2TextField;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JTextField strategyTextField;
    // End of variables declaration//GEN-END:variables
}
