/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.view.loading

import org.openbase.jul.visual.swing.animation.LoadingAnimation
import org.openbase.planetsudo.game.GameManager.Companion.gameManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.JPanel
import javax.swing.border.BevelBorder

/**
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class LevelLoadingPanel : JPanel() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private var titlePanel: LevelTitleLoadingPanel? = null
    private var mainPanel: JPanel? = null
    private var leftBorder: JPanel? = null
    private var rightBorder: JPanel? = null
    private var menuPanel: MenuLoadingPanel? = null

    // private LoadingAnimation completeLoadingProgress;
    private var nextLoadingProgress: LoadingAnimation? = null

    private fun initComponents() {
        layout = BorderLayout()
        titlePanel = LevelTitleLoadingPanel()
        mainPanel = JPanel()
        leftBorder = JPanel()
        rightBorder = JPanel()
        menuPanel = MenuLoadingPanel()
        // completeLoadingProgress = new LoadingAnimation();
        nextLoadingProgress = LoadingAnimation()
        val layout = BoxLayout(mainPanel, BoxLayout.LINE_AXIS)
        mainPanel!!.layout = layout
        mainPanel!!.background = Color.BLACK
        mainPanel!!.border = BorderFactory.createBevelBorder(
            BevelBorder.LOWERED,
            Color.white,
            Color(204, 204, 204),
            Color.gray,
            Color(0, 0, 0),
        )
        leftBorder!!.isOpaque = false
        rightBorder!!.isOpaque = false
        menuPanel!!.isOpaque = false
        leftBorder!!.preferredSize = Dimension(100, 0)
        rightBorder!!.preferredSize = Dimension(100, 0)

        // mainPanel.add(completeLoadingProgress);
        mainPanel!!.add(nextLoadingProgress)

        add(titlePanel, BorderLayout.PAGE_START)
        add(mainPanel, BorderLayout.CENTER)
        add(menuPanel, BorderLayout.PAGE_END)
        add(rightBorder, BorderLayout.EAST)
        add(leftBorder, BorderLayout.WEST)
    }

    fun updateDynamicComponents() {
        titlePanel!!.updateDynamicComponents()
        background = gameManager.level!!.color
        nextLoadingProgress!!.isIndeterminate = false
    }

    private var nextContext: String? = null
    private var maxProgress = 100
    private var counter = 0

    init {
        initComponents()
    }

    fun setLoadingStateChange(context: String?, stepCount: Int) {
        nextLoadingProgress!!.setText(context)
        nextContext = context
        maxProgress = stepCount
        counter = 0
        nextLoadingProgress!!.process = 0
    }

    fun setLoadingStep(counter: Int) {
        if (counter == -1) {
            this.counter++
        } else {
            this.counter = counter
        }
        nextLoadingProgress!!.setText(nextContext + " " + this.counter)
        logger.info("SetProgressTo:" + (this.counter * 100 / maxProgress))
        nextLoadingProgress!!.process = this.counter * 100 / maxProgress
    }
}
