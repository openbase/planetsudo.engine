/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CreateTeamFrame.java
 *
 * Created on Oct 28, 2010, 9:50:26 AM
 */

package planetsudo.view.configuration;

import controller.ObjectFileController;
import java.awt.Color;
import java.util.Collection;
import java.util.Vector;
import logging.Logger;
import planetsudo.game.Team;
import planetsudo.game.strategy.AbstractStrategy;
import planetsudo.view.MainGUI;

/**
 *
 * @author divine
 */
public class CreateTeamFrame extends javax.swing.JFrame {

    /** Creates new form CreateTeamFrame */
    public CreateTeamFrame() {
        initComponents();
		setLocation(300, 300);
    }

	private void createTeam() {
		int id = 0;
		String name;
		Color color;
		Class<AbstractStrategy> strategy = null;
		Collection<String> members = new Vector<String>();
		idTextField.setForeground(Color.BLACK);
		strategyTextField.setForeground(Color.BLACK);
		createButton.setForeground(Color.BLACK);

		try {
			id = Integer.parseInt(idTextField.getText());
		} catch (NumberFormatException ex) {
			idTextField.setForeground(Color.RED);
			Logger.error(this, "Could not parse team id!");
			return;
		}
		name = nameTextField.getText();
		color = colorChooser.getColor();

		try {
			Logger.info(this, "try to load "+ AbstractStrategy.class.getPackage().getName()+"."+strategyTextField.getText());
			strategy = (Class<AbstractStrategy>) getClass().getClassLoader().loadClass(AbstractStrategy.class.getPackage().getName()+"."+strategyTextField.getText());
		} catch (ClassNotFoundException ex) {
			strategyTextField.setForeground(Color.RED);
			Logger.error(this, "Could not find strategy!", ex);
			return;
		}


		members.add(member1TextField.getText());
		members.add(member2TextField.getText());


		Team team = new Team(id, name, color, strategy, members);
		try {
			ObjectFileController<Team> fileWriter = new ObjectFileController<Team>("teams/"+id+".team");
			fileWriter.writeObject(team);
		} catch (Exception ex) {
			Logger.error(this, "Could not find team folder!", ex);
			createButton.setForeground(Color.RED);
			return;
		}
		MainGUI.getInstance().getConfigurationPanel().updateTeamList();
		setVisible(false);
		reset();

	}

	private void reset() {
		idTextField.setText("");
		nameTextField.setText("");
		strategyTextField.setText("");
		member1TextField.setText("");
		member2TextField.setText("");
		

	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        idTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        strategyTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        member1TextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        member2TextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        createButton = new javax.swing.JButton();
        colorChooser = new javax.swing.JColorChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Erstelle Team");
        setAlwaysOnTop(true);
        setResizable(false);

        jLabel1.setText("ID");

        idTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idTextFieldActionPerformed(evt);
            }
        });

        jLabel2.setText("Name");

        nameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTextFieldActionPerformed(evt);
            }
        });

        jLabel4.setText("Strategie");

        jLabel5.setText("Entwickler");

        jLabel6.setText("Erstelle neues Team");

        jLabel7.setText("Entwickler");

        createButton.setText("Erstellen");
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addComponent(idTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                    .addComponent(member2TextField, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                    .addComponent(member1TextField, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                    .addComponent(strategyTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                    .addComponent(createButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7))
                        .addGap(49, 49, 49)
                        .addComponent(colorChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(colorChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)))
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(strategyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(member1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(member2TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(createButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed
		createTeam();
	}//GEN-LAST:event_createButtonActionPerformed

	private void idTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idTextFieldActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_idTextFieldActionPerformed

	private void nameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameTextFieldActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_nameTextFieldActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreateTeamFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JColorChooser colorChooser;
    private javax.swing.JButton createButton;
    private javax.swing.JTextField idTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField member1TextField;
    private javax.swing.JTextField member2TextField;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JTextField strategyTextField;
    // End of variables declaration//GEN-END:variables

}
