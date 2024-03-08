/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * LevelMenuPanel.java
 *
 * Created on Oct 4, 2010, 3:05:09 PM
 */
package org.openbase.planetsudo.view.menu

import org.openbase.planetsudo.level.AbstractLevel
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class LevelMenuPanel : JPanel(), ActionListener {
    private var minutes: Int
    private var seconds: Int
    private val timer: Timer
    private var levelName: String

    fun setLevel(level: AbstractLevel) {
        levelName = level.name
        level.addPropertyChangeListener {
            if (it.propertyName == AbstractLevel.GAME_SPEED_FACTOR_CHANGED) {
                timer.isRunning.let { running ->
                    timer.delay = (1000.0 * (1 / (it.newValue as Double))).toInt()
                    if (running) timer.restart()
                }
            }
        }
        updateTitle()
    }

    private var text: String? = null
    private fun updateTitle() {
        text = "$levelName ["
        if (minutes <= 9) {
            text += "0"
        }
        text += "$minutes:"
        if (seconds <= 9) {
            text += "0"
        }
        text += "$seconds]"
        nameAndTimeLabel!!.text = text
    }

    fun startTimer() {
        if (!timer.isRunning) timer.start()
    }

    fun stopTimer() {
        if (timer.isRunning) {
            timer.stop()
        }
    }

    fun reset() {
        stopTimer()
        seconds = 0
        minutes = 0
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private fun initComponents() {
        nameAndTimeLabel = JLabel()

        isOpaque = false

        nameAndTimeLabel!!.horizontalAlignment = SwingConstants.CENTER
        nameAndTimeLabel!!.text = "Name"

        val layout = GroupLayout(this)
        this.layout = layout
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(nameAndTimeLabel, GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE.toInt())
                        .addContainerGap(),
                ),
        )
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    GroupLayout.Alignment.TRAILING,
                    layout.createSequentialGroup()
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE.toInt())
                        .addComponent(nameAndTimeLabel),
                ),
        )
    } // </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private var nameAndTimeLabel: JLabel? = null

    /** Creates new form LevelMenuPanel  */
    init {
        initComponents()
        this.minutes = 0
        this.seconds = 0
        this.levelName = "Level"
        this.timer = Timer(1000, this)
    }

    // End of variables declaration//GEN-END:variables
    override fun actionPerformed(ex: ActionEvent) {
        if (seconds < 59) {
            seconds++
        } else {
            minutes++
            seconds = 0
        }
        updateTitle()
    }
}
