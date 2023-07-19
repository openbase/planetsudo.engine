/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * GameContext.java
 *
 * Created on Oct 10, 2010, 6:11:29 PM
 */
package org.openbase.planetsudo.view.menu;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2023 openbase.org
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
import java.util.logging.Level;
import javax.swing.ImageIcon;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.openbase.jul.visual.swing.image.ImageLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class GameContext extends javax.swing.JFrame {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static GameContext instance;

    public synchronized static void display() {
        if (instance == null) {
            java.awt.EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    new GameContext().setVisible(true);
                }
            });
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(GameContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            instance.setVisible(true);
        }
    }

    /**
     * Creates new form GameContext
     */
    public GameContext() {
        instance = this;
        initComponents();
        try {
            titleLabel.setIcon(new ImageIcon(ImageLoader.getInstance().loadImage("img/mothership.png")));
        } catch (CouldNotPerformException ex) {
            ExceptionPrinter.printHistory(new CouldNotPerformException("Could not display image", ex), logger);
        }
        setLocation(300, 300);
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Spielablauf");
        setAlwaysOnTop(true);
        setResizable(false);

        titleLabel.setText("Spielablauf");

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel2.setText("<html>Zwei unterschiedliche Agententeams erobern einen für sie unbekannten Planeten. Ihre Mutterschiffe sind bereits gelandet und sie erkundschaften die Umgebung und sammeln in Konkurrenz zum gegnerischen Team über den Planeten zufällig verstreute Ressourcen ein, die sie anschließend zum Mutterschiff zurück bringen. Ziel des Spiels ist es, mit dem im Mutterschiff vorhandenen Treibstoff so viele Ressourcen wie möglich einzusammeln und zum Mutterschiff zu befördern. Die Agenten selber verbrauchen den Treibstoff und müssen diesen Regelmäßig beim Mutterschiff wieder auftanken. Andererseits bleiben sie auf dem Spielfeld ohne Funktionalität zurück. Zum Einsammeln der Ressourcen wird eine gewisse Zeit benötigt, zudem sind Agenten, die eine Ressource zum Mutterschiff transportieren, langsamer als normal. Entdeckt ein Agenten in seiner näheren Umgebung einen feindlichen Agenten, so ist es möglich mit diesem eine kriegerische Auseinandersetzung zu führen, bis der Treibstoff des Unterlegenen leer ist. Sollte der besiegte eine Resource getragen haben lässt er diese wieder fallen. Diese ist dann wieder zum Sammeln freigegeben. Sollte ein Agent das Mutterschiff der gegnerischen Mannschaft angreifen, so verliert dieses an Schildenergie. Geht die Schildenergie zuneige (unter 50%) so beginnt der Treibstoffvorrat des angegriffenen Mutterschiffs zu sinken. Je niedriger nun der Schild fällt, desto schneller sinkt der restliche Treibstoffvorrat des Mutterschiffs. Die Agenten des angegriffenen Mutterschiffs werden darüber benachrichtigt, dass ihr Mutterschiff angegriffen wird und können nun dementsprechend handeln. Das Spiel endet wenn der gesamte Treibstoff beider Teams verbraucht ist.</html>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titleLabel)
                        .addGap(0, 790, Short.MAX_VALUE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(428, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameContext().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables

}
