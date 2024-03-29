/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * CreateTeamFrame.java
 *
 * Created on Oct 28, 2010, 9:50:26 AM
 */
package org.openbase.planetsudo.view.configuration

import org.openbase.jul.exception.CouldNotPerformException
import org.openbase.jul.visual.swing.component.ColorChooserFrame
import org.openbase.planetsudo.game.Team.Companion.save
import org.openbase.planetsudo.game.TeamData
import org.openbase.planetsudo.game.strategy.AbstractStrategy
import org.openbase.planetsudo.game.strategy.StrategyClassLoader.loadStrategy
import org.openbase.planetsudo.view.MainGUI
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Color
import java.awt.EventQueue
import java.awt.GridBagConstraints
import java.awt.event.ActionEvent
import javax.swing.*

/**
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class CreateTeamFrame : JFrame() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private fun createTeam() {
        var strategy: Class<out AbstractStrategy<*>?>? = null
        val members: MutableList<String> = ArrayList()
        strategyTextField!!.foreground = Color.BLACK
        createButton!!.foreground = Color.BLACK

        val name = nameTextField!!.text

        if (colorChooserButton!!.text != "") {
            colorChooserButton!!.foreground = Color.RED
            colorChooserButton!!.text = "No Teamcolor selected!"
            logger.error("Color not selected!")
            return
        }
        val color = colorChooserButton!!.background

        // 			logger.info("try to load "+ AbstractStrategy.class.getPackage().getName()+"."+strategyTextField.getText());
// 			strategy = (Class<AbstractStrategy>) getClass().getClassLoader().loadClass(AbstractStrategy.class.getPackage().getName()+"."+strategyTextField.getText());
// 		} catch (ClassNotFoundException ex) {
// 			strategyTextField.setForeground(Color.RED);
// 			logger.error("Could not find strategy!", ex);
// 			return;
// 		}
        try {
            strategy = loadStrategy(strategyTextField!!.text)
        } catch (ex: CouldNotPerformException) {
            strategyTextField!!.foreground = Color.RED
            logger.warn("Could not load strategy!", ex)
            return
        }
        members.add(member1TextField!!.text)
        members.add(member2TextField!!.text)

        val teamData = TeamData(name, color, strategy.simpleName, members)

        try {
            save(teamData)
        } catch (ex: CouldNotPerformException) {
            logger.error("Could not find team folder!", ex)
            createButton!!.foreground = Color.RED
            return
        }
        MainGUI.instance!!.configurationPanel!!.apply {
            updateTeamList(teamData)
            setDefaultTeamCandidate(teamData)
        }
        isVisible = false
        reset()
    }

    private fun reset() {
        nameTextField!!.text = ""
        strategyTextField!!.text = ""
        member1TextField!!.text = ""
        member2TextField!!.text = ""
        colorChooserButton!!.background = Color(220, 220, 220)
        colorChooserButton!!.text = "Klicken um Farbe zu wählen"
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private fun initComponents() {
        var gridBagConstraints: GridBagConstraints

        createButton = JButton()
        jPanel1 = JPanel()
        member2TextField = JTextField()
        jLabel7 = JLabel()
        jLabel5 = JLabel()
        member1TextField = JTextField()
        jLabel8 = JLabel()
        jLabel4 = JLabel()
        strategyTextField = JTextField()
        nameTextField = JTextField()
        jLabel2 = JLabel()
        colorChooserButton = JButton()

        defaultCloseOperation = DISPOSE_ON_CLOSE
        title = "Erstelle Team"
        isAlwaysOnTop = true
        isResizable = false

        createButton!!.text = "Erstellen"
        createButton!!.addActionListener { evt -> createButtonActionPerformed(evt) }

        jPanel1!!.border = BorderFactory.createTitledBorder("Team Angaben")
        jPanel1!!.layout = null
        jPanel1!!.add(member2TextField)
        member2TextField!!.setBounds(10, 180, 347, 27)

        jLabel7!!.text = "Entwickler"
        jPanel1!!.add(jLabel7)
        jLabel7!!.setBounds(370, 180, 76, 29)

        jLabel5!!.text = "Entwickler"
        jPanel1!!.add(jLabel5)
        jLabel5!!.setBounds(370, 140, 76, 27)
        jPanel1!!.add(member1TextField)
        member1TextField!!.setBounds(10, 140, 347, 27)

        jLabel8!!.text = "Farbe"
        jPanel1!!.add(jLabel8)
        jLabel8!!.setBounds(370, 100, 76, 29)

        jLabel4!!.text = "Strategie"
        jPanel1!!.add(jLabel4)
        jLabel4!!.setBounds(370, 60, 76, 27)
        jPanel1!!.add(strategyTextField)
        strategyTextField!!.setBounds(10, 60, 347, 27)

        nameTextField!!.addActionListener { evt -> nameTextFieldActionPerformed(evt) }
        jPanel1!!.add(nameTextField)
        nameTextField!!.setBounds(10, 20, 347, 27)

        jLabel2!!.text = "Name"
        jPanel1!!.add(jLabel2)
        jLabel2!!.setBounds(370, 20, 40, 27)

        colorChooserButton!!.text = "Klicken um Farbe zu wählen"
        colorChooserButton!!.addActionListener { evt -> colorChooserButtonActionPerformed(evt) }
        jPanel1!!.add(colorChooserButton)
        colorChooserButton!!.setBounds(10, 100, 347, 29)

        val layout = GroupLayout(contentPane)
        contentPane.layout = layout
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(createButton)
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 473, GroupLayout.PREFERRED_SIZE),
                        )
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE.toInt()),
                ),
        )
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(createButton)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE.toInt()),
                ),
        )

        pack()
    } // </editor-fold>//GEN-END:initComponents

    private fun createButtonActionPerformed(evt: ActionEvent) { // GEN-FIRST:event_createButtonActionPerformed
        createTeam()
    } // GEN-LAST:event_createButtonActionPerformed

    private fun colorChooserButtonActionPerformed(evt: ActionEvent) { // GEN-FIRST:event_colorChooserButtonActionPerformed
        val worker: SwingWorker<*, *> = object : SwingWorker<Any?, Any?>() {
            @Throws(Exception::class)
            override fun doInBackground(): Any? {
                val color = ColorChooserFrame.getColor()
                colorChooserButton!!.background = color
                colorChooserButton!!.text = ""
                return null
            }
        }
        worker.execute()
    } // GEN-LAST:event_colorChooserButtonActionPerformed

    private fun nameTextFieldActionPerformed(evt: ActionEvent) { // GEN-FIRST:event_nameTextFieldActionPerformed
        // TODO add your handling code here:
    } // GEN-LAST:event_nameTextFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private var colorChooserButton: JButton? = null
    private var createButton: JButton? = null
    private var jLabel2: JLabel? = null
    private var jLabel4: JLabel? = null
    private var jLabel5: JLabel? = null
    private var jLabel7: JLabel? = null
    private var jLabel8: JLabel? = null
    private var jPanel1: JPanel? = null
    private var member1TextField: JTextField? = null
    private var member2TextField: JTextField? = null
    private var nameTextField: JTextField? = null
    private var strategyTextField: JTextField? = null // End of variables declaration//GEN-END:variables

    /**
     * Creates new form CreateTeamFrame
     */
    init {
        instance = this
        initComponents()
        setLocation(300, 300)
    }

    companion object {
        private var instance: CreateTeamFrame? = null

        @JvmStatic
        @Synchronized
        fun display() {
            if (instance == null) {
                EventQueue.invokeLater { CreateTeamFrame().isVisible = true }
                Thread.sleep(500)
            } else {
                instance!!.reset()
                instance!!.isVisible = true
            }
        }
    }
}
