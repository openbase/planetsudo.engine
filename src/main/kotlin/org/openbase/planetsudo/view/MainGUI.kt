package org.openbase.planetsudo.view

import org.openbase.jul.exception.CouldNotPerformException
import org.openbase.planetsudo.game.GameManager.Companion.gameManager
import org.openbase.planetsudo.game.GameManager.GameState
import org.openbase.planetsudo.game.GameSound
import org.openbase.planetsudo.game.Team.Companion.resetDefaultTeam
import org.openbase.planetsudo.level.LevelView
import org.openbase.planetsudo.main.GUIController
import org.openbase.planetsudo.view.configuration.ConfigurationPanel
import org.openbase.planetsudo.view.configuration.CreateTeamFrame.Companion.display
import org.openbase.planetsudo.view.game.GamePanel
import org.openbase.planetsudo.view.level.LevelDisplayPanel.VideoThreadCommand
import org.openbase.planetsudo.view.level.levelobjects.AgentPanel
import org.openbase.planetsudo.view.loading.LevelLoadingPanel
import org.openbase.planetsudo.view.menu.GameContext
import org.openbase.planetsudo.view.menu.HelpFrame
import org.openbase.planetsudo.view.menu.SpeedControlFrame
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.CardLayout
import java.awt.Dimension
import java.awt.EventQueue
import java.awt.GraphicsEnvironment
import java.awt.event.ActionEvent
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import javax.swing.*
import kotlin.system.exitProcess

/**
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class MainGUI : JFrame, PropertyChangeListener {
    var configurationPanel: ConfigurationPanel? = null
        private set
    private var levelLoadingPanel: LevelLoadingPanel? = null
    private var gamePanel: GamePanel? = null

    /**
     * Creates new form MainGui
     */
    constructor() {
        initComponents()
    }

    /**
     * Create the frame
     */
    constructor(guiController: GUIController?) : super("PlanetSudo") {
        instance = this
        this.screenDim = Dimension(X_DIM, Y_DIM)
        this.guiController = guiController
        this.iconImage = ImageIcon("img/PlanetSudoLogoIcon.png").image
    }

    private var screenDim: Dimension? = null

    /**
     * @return the iPadController
     */
    var guiController: GUIController? = null
        private set
    private val cardLayout: CardLayout? = null
    private var fullscreenMode = false

    fun initialize() {
        SwingUtilities.invokeLater {
            initComponents()
            configurationPanel = ConfigurationPanel()
            levelLoadingPanel = LevelLoadingPanel()
            gamePanel = GamePanel()
            mainPanel!!.add(configurationPanel, CONFIGURATION_PANEL)
            mainPanel!!.add(levelLoadingPanel, LOADING_PANEL)
            mainPanel!!.add(gamePanel, GAME_PANEL)
            (mainPanel!!.layout as CardLayout).show(mainPanel, CONFIGURATION_PANEL)
            setFullScreenMode(DEFAULT_FULLSCREENMODE)
            guiController!!.addPropertyChangeListener(instance!!)
            displayTeamPanelCheckBoxMenuItem!!.isSelected = gamePanel!!.isTeamPanelDisplayed
            updateButtons(gameManager.gameState)
        }
    }

    fun setFullScreenMode(enabled: Boolean) {
        fullscreenMode = enabled
        LOGGER.info("setFullscreenMode $fullscreenMode")
        isVisible = false
        if (fullscreenMode) {
            // setSize(guiController.getVisualFeedbackConfig().getFrameDimension());
            // setSize(screenDim);
            setLocation(0, 0)
            if (isDisplayable) dispose()
            isUndecorated = true

            val env = GraphicsEnvironment.getLocalGraphicsEnvironment()
            val device = env.defaultScreenDevice

            try {
                device.fullScreenWindow = this // Setzen des FullScreenmodus.
                this.validate()
                // fullscreenModeMenuItem.setText("Leave FullScreen Mode");
            } catch (ex: CouldNotPerformException) {
                LOGGER.error("no Fullscreen.", ex)
                device.fullScreenWindow = null
            }
        } else {
            // pack();
            setLocation(X_LOCATION, Y_LOCATION)
            size = screenDim
            extendedState = this.extendedState or MAXIMIZED_BOTH

            if (isDisplayable) dispose()
            isUndecorated = false

            val env = GraphicsEnvironment.getLocalGraphicsEnvironment()
            val device = env.defaultScreenDevice

            try {
                device.fullScreenWindow = null // Setzen des FullScreenmodus.
                // fullscreenModeMenuItem.setText("Enter FullScreen Mode");
            } catch (ex: CouldNotPerformException) {
                LOGGER.error("no Fullscreen.", ex)
                device.fullScreenWindow = null
            }
        }
        validate()
        isVisible = true
    }

    fun fullscreenModeEnabled(): Boolean {
        return fullscreenMode
    }

    /**
     * Analyse des propertyChangeEvents und setzen der neuen Werte.
     */
    override fun propertyChange(changeEvent: PropertyChangeEvent) {
        LOGGER.info("PropertyChange input: " + changeEvent.propertyName)
        try {
            if (changeEvent.propertyName == GUIController.GAME_STATE_CHANGE) {
                updateButtons(changeEvent.newValue as GameState)
                if (changeEvent.newValue === GameState.Configuration) {
                    gamePanel!!.setVideoThreadCommand(VideoThreadCommand.Stop)
                    (mainPanel!!.layout as CardLayout).show(mainPanel, CONFIGURATION_PANEL)
                } else if (changeEvent.newValue === GameState.Initialisation) {
                    levelLoadingPanel!!.updateDynamicComponents()
                    gamePanel!!.setVideoThreadCommand(VideoThreadCommand.Stop)
                    (mainPanel!!.layout as CardLayout).show(mainPanel, LOADING_PANEL)
                } else if (changeEvent.newValue === GameState.Running) {
                    if (changeEvent.oldValue !== GameState.Break) {
                        gamePanel!!.updateDynamicComponents()
                        (mainPanel!!.layout as CardLayout).show(mainPanel, GAME_PANEL)
                    }
                    gamePanel!!.setVideoThreadCommand(VideoThreadCommand.Start)
                } else if (changeEvent.newValue === GameState.Break) {
                    (mainPanel!!.layout as CardLayout).show(mainPanel, GAME_PANEL)
                    gamePanel!!.setVideoThreadCommand(VideoThreadCommand.Pause)
                }
            } else if (changeEvent.propertyName == GUIController.LOADING_STATE_CHANGE) {
                levelLoadingPanel!!.setLoadingStateChange(
                    changeEvent.newValue as String,
                    (changeEvent.oldValue as Int),
                )
            } else if (changeEvent.propertyName == GUIController.LOADING_STEP) {
                levelLoadingPanel!!.setLoadingStep((changeEvent.newValue as Int))
            } else {
                LOGGER.warn("Event [" + changeEvent.propertyName + "] is an bad property change event!")
            }
        } catch (ex: CouldNotPerformException) {
            LOGGER.debug("Exception from PropertyChange")
        }
    }

    fun updateButtons(state: GameState?) {
        when (state) {
            GameState.Configuration -> {
                startPauseMenuItem!!.text = "Starte Spiel"
                startPauseMenuItem!!.isEnabled = true
                stopMenuItem!!.isEnabled = false
                finalCalculationMenuItem!!.isEnabled = false
            }

            GameState.Initialisation -> {
                startPauseMenuItem!!.text = "Starte Spiel"
                startPauseMenuItem!!.isEnabled = false
                stopMenuItem!!.isEnabled = false
                finalCalculationMenuItem!!.isEnabled = false
            }

            GameState.Running -> {
                startPauseMenuItem!!.text = "Pause"
                startPauseMenuItem!!.isEnabled = true
                stopMenuItem!!.isEnabled = true
                finalCalculationMenuItem!!.isEnabled = true
            }

            GameState.Break -> {
                startPauseMenuItem!!.text = "Weiter"
                startPauseMenuItem!!.isEnabled = true
                stopMenuItem!!.isEnabled = true
                finalCalculationMenuItem!!.isEnabled = false
            }

            else -> {}
        }
        if (finished) {
            startPauseMenuItem!!.isEnabled = false
            stopMenuItem!!.isEnabled = true
            finalCalculationMenuItem!!.isEnabled = false
            stopMenuItem!!.text = "Zurück zum Hauptmenü"
        } else {
            stopMenuItem!!.text = "Stop"
        }
        finished = false
    }

    private var finished = false

    private fun finalizeGame() {
        finished = true

        gameManager.switchGameState(GameState.Break)
        GameSound.End.play()
        gamePanel!!.displayEndCalculation()
    }

    fun showLoadingPanel() {
        LOGGER.info("ShowLoadingPanel")
        (mainPanel!!.layout as CardLayout).show(mainPanel, LOADING_PANEL)
    }

    fun exitApp() {
        isVisible = false
        exitProcess(0)
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private fun initComponents() {
        addWindowListener(
            object : java.awt.event.WindowAdapter() {
                override fun windowClosing(evt: java.awt.event.WindowEvent) {
                    exitApp()
                }
            },
        )

        jFrame1 = JFrame()
        jMenuItem1 = JMenuItem()
        mainPanel = JPanel()
        menuBar = JMenuBar()
        fileMenu = JMenu()
        finalCalculationMenuItem = JMenuItem()
        jSeparator3 = JPopupMenu.Separator()
        jMenuItem3 = JMenuItem()
        startPauseMenuItem = JMenuItem()
        stopMenuItem = JMenuItem()
        jSeparator2 = JPopupMenu.Separator()
        exitMenuItem = JMenuItem()
        editMenu = JMenu()
        jMenu2 = JMenu()
        displayTeamPanelCheckBoxMenuItem = JCheckBoxMenuItem()
        jCheckBoxMenuItem3 = JCheckBoxMenuItem()
        jSeparator1 = JPopupMenu.Separator()
        jCheckBoxMenuItem1 = JCheckBoxMenuItem()
        toolMenu = JMenu()
        jMenuItem2 = JMenuItem()
        createTeamMenuItem = JMenuItem()
        jMenuItem4 = JMenuItem()
        helpMenu = JMenu()
        contentsMenuItem = JMenuItem()
        aboutMenuItem = JMenuItem()

        val jFrame1Layout = GroupLayout(jFrame1!!.contentPane)
        jFrame1!!.contentPane.layout = jFrame1Layout
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 400, Short.MAX_VALUE.toInt()),
        )
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 300, Short.MAX_VALUE.toInt()),
        )

        jMenuItem1!!.text = "jMenuItem1"

        defaultCloseOperation = EXIT_ON_CLOSE
        title = "PlanetSudo - The Force Of Life"

        mainPanel!!.layout = CardLayout()

        fileMenu!!.text = "Spiel"

        finalCalculationMenuItem!!.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0)
        finalCalculationMenuItem!!.text = "Endabrechnung"
        finalCalculationMenuItem!!.addActionListener { evt -> finalCalculationMenuItemActionPerformed(evt) }
        fileMenu!!.add(finalCalculationMenuItem)
        fileMenu!!.add(jSeparator3)

        jMenuItem3!!.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0)
        jMenuItem3!!.text = "Leite Spielende ein"
        jMenuItem3!!.addActionListener { evt -> jMenuItem3ActionPerformed(evt) }
        fileMenu!!.add(jMenuItem3)

        startPauseMenuItem!!.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0)
        startPauseMenuItem!!.text = "Start"
        startPauseMenuItem!!.isEnabled = false
        startPauseMenuItem!!.addActionListener { evt -> startPauseMenuItemActionPerformed(evt) }
        fileMenu!!.add(startPauseMenuItem)

        stopMenuItem!!.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0)
        stopMenuItem!!.text = "Stop"
        stopMenuItem!!.isEnabled = false
        stopMenuItem!!.addActionListener { evt -> stopMenuItemActionPerformed(evt) }
        fileMenu!!.add(stopMenuItem)
        fileMenu!!.add(jSeparator2)

        exitMenuItem!!.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK)
        exitMenuItem!!.text = "Beenden"
        exitMenuItem!!.addActionListener { evt -> exitMenuItemActionPerformed(evt) }
        fileMenu!!.add(exitMenuItem)

        menuBar!!.add(fileMenu)

        editMenu!!.text = "Ansicht"

        jMenu2!!.text = "Spiel"

        displayTeamPanelCheckBoxMenuItem!!.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_M, 0)
        displayTeamPanelCheckBoxMenuItem!!.isSelected = true
        displayTeamPanelCheckBoxMenuItem!!.text = "Teamanzeige"
        displayTeamPanelCheckBoxMenuItem!!.addActionListener { evt ->
            displayTeamPanelCheckBoxMenuItemActionPerformed(
                evt,
            )
        }
        jMenu2!!.add(displayTeamPanelCheckBoxMenuItem)

        jCheckBoxMenuItem3!!.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_S, 0)
        jCheckBoxMenuItem3!!.text = "AgentenStatus"
        jCheckBoxMenuItem3!!.addActionListener { evt -> jCheckBoxMenuItem3ActionPerformed(evt) }
        jMenu2!!.add(jCheckBoxMenuItem3)

        editMenu!!.add(jMenu2)
        editMenu!!.add(jSeparator1)

        jCheckBoxMenuItem1!!.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0)
        jCheckBoxMenuItem1!!.text = "Vollbild"
        jCheckBoxMenuItem1!!.addActionListener { evt -> jCheckBoxMenuItem1ActionPerformed(evt) }
        editMenu!!.add(jCheckBoxMenuItem1)

        menuBar!!.add(editMenu)

        toolMenu!!.text = "Einstellungen"

        jMenuItem2!!.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK)
        jMenuItem2!!.text = "Spielgeschwindigkeit"
        jMenuItem2!!.addActionListener { evt -> jMenuItem2ActionPerformed(evt) }
        toolMenu!!.add(jMenuItem2)

        createTeamMenuItem!!.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK)
        createTeamMenuItem!!.text = "Team Erstellen"
        createTeamMenuItem!!.addActionListener { evt -> createTeamMenuItemActionPerformed(evt) }
        toolMenu!!.add(createTeamMenuItem)

        jMenuItem4!!.text = "Reset Standard Team"
        jMenuItem4!!.addActionListener { evt -> jMenuItem4ActionPerformed(evt) }
        toolMenu!!.add(jMenuItem4)

        menuBar!!.add(toolMenu)

        helpMenu!!.text = "Info"

        contentsMenuItem!!.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0)
        contentsMenuItem!!.text = "Spielablauf"
        contentsMenuItem!!.addActionListener { evt -> contentsMenuItemActionPerformed(evt) }
        helpMenu!!.add(contentsMenuItem)

        aboutMenuItem!!.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0)
        aboutMenuItem!!.text = "Über PlanetSudo"
        aboutMenuItem!!.addActionListener { evt -> aboutMenuItemActionPerformed(evt) }
        helpMenu!!.add(aboutMenuItem)

        menuBar!!.add(helpMenu)

        jMenuBar = menuBar

        val layout = GroupLayout(contentPane)
        contentPane.layout = layout
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE.toInt()),
        )
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE.toInt()),
        )

        pack()
    } // </editor-fold>//GEN-END:initComponents

    private fun exitMenuItemActionPerformed(evt: ActionEvent) { // GEN-FIRST:event_exitMenuItemActionPerformed
        exitApp()
    } // GEN-LAST:event_exitMenuItemActionPerformed

    private fun jCheckBoxMenuItem1ActionPerformed(evt: ActionEvent) { // GEN-FIRST:event_jCheckBoxMenuItem1ActionPerformed
        setFullScreenMode(jCheckBoxMenuItem1!!.isSelected)
    } // GEN-LAST:event_jCheckBoxMenuItem1ActionPerformed

    private fun startPauseMenuItemActionPerformed(evt: ActionEvent) { // GEN-FIRST:event_startPauseMenuItemActionPerformed
        when (gameManager.gameState) {
            GameState.Configuration -> gameManager.startGame()
            GameState.Running -> gameManager.switchGameState(GameState.Break)
            GameState.Break -> gameManager.switchGameState(GameState.Running)
            else -> {}
        }
    } // GEN-LAST:event_startPauseMenuItemActionPerformed

    private fun displayTeamPanelCheckBoxMenuItemActionPerformed(evt: ActionEvent) { // GEN-FIRST:event_displayTeamPanelCheckBoxMenuItemActionPerformed
        gamePanel!!.displayTeamPanel(displayTeamPanelCheckBoxMenuItem!!.isSelected)
    } // GEN-LAST:event_displayTeamPanelCheckBoxMenuItemActionPerformed

    private fun jCheckBoxMenuItem3ActionPerformed(evt: ActionEvent) { // GEN-FIRST:event_jCheckBoxMenuItem3ActionPerformed
        AgentPanel.showStateLabel = jCheckBoxMenuItem3!!.isSelected
    } // GEN-LAST:event_jCheckBoxMenuItem3ActionPerformed

    private fun stopMenuItemActionPerformed(evt: ActionEvent) { // GEN-FIRST:event_stopMenuItemActionPerformed
        gameManager.switchGameState(GameState.Configuration)
    } // GEN-LAST:event_stopMenuItemActionPerformed

    private fun aboutMenuItemActionPerformed(evt: ActionEvent) { // GEN-FIRST:event_aboutMenuItemActionPerformed
        HelpFrame.Companion.display()
    } // GEN-LAST:event_aboutMenuItemActionPerformed

    private fun contentsMenuItemActionPerformed(evt: ActionEvent) { // GEN-FIRST:event_contentsMenuItemActionPerformed
        GameContext.Companion.display()
    } // GEN-LAST:event_contentsMenuItemActionPerformed

    private fun finalCalculationMenuItemActionPerformed(evt: ActionEvent) { // GEN-FIRST:event_finalCalculationMenuItemActionPerformed
        finalizeGame()
    } // GEN-LAST:event_finalCalculationMenuItemActionPerformed

    private fun createTeamMenuItemActionPerformed(evt: ActionEvent) { // GEN-FIRST:event_createTeamMenuItemActionPerformed
        display()
    } // GEN-LAST:event_createTeamMenuItemActionPerformed

    private fun jMenuItem2ActionPerformed(evt: ActionEvent) { // GEN-FIRST:event_jMenuItem2ActionPerformed
        SpeedControlFrame.Companion.display()
    } // GEN-LAST:event_jMenuItem2ActionPerformed

    private fun jMenuItem3ActionPerformed(evt: ActionEvent) { // GEN-FIRST:event_jMenuItem3ActionPerformed
        gameManager.setGameOverSoon()
    } // GEN-LAST:event_jMenuItem3ActionPerformed

    private fun jMenuItem4ActionPerformed(evt: ActionEvent) { // GEN-FIRST:event_jMenuItem4ActionPerformed
        try {
            resetDefaultTeam()
        } catch (ex: CouldNotPerformException) {
            LOGGER.warn("Could not reset default team!")
        }
    } // GEN-LAST:event_jMenuItem4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private var aboutMenuItem: JMenuItem? = null
    private var contentsMenuItem: JMenuItem? = null
    private var createTeamMenuItem: JMenuItem? = null
    private var displayTeamPanelCheckBoxMenuItem: JCheckBoxMenuItem? = null
    private var editMenu: JMenu? = null
    private var exitMenuItem: JMenuItem? = null
    private var fileMenu: JMenu? = null
    private var finalCalculationMenuItem: JMenuItem? = null
    private var helpMenu: JMenu? = null
    private var jCheckBoxMenuItem1: JCheckBoxMenuItem? = null
    private var jCheckBoxMenuItem3: JCheckBoxMenuItem? = null
    private var jFrame1: JFrame? = null
    private var jMenu2: JMenu? = null
    private var jMenuItem1: JMenuItem? = null
    private var jMenuItem2: JMenuItem? = null
    private var jMenuItem3: JMenuItem? = null
    private var jMenuItem4: JMenuItem? = null
    private var jSeparator1: JPopupMenu.Separator? = null
    private var jSeparator2: JPopupMenu.Separator? = null
    private var jSeparator3: JPopupMenu.Separator? = null
    private var mainPanel: JPanel? = null
    private var menuBar: JMenuBar? = null
    private var startPauseMenuItem: JMenuItem? = null
    private var stopMenuItem: JMenuItem? = null
    private var toolMenu: JMenu? = null // End of variables declaration//GEN-END:variables

    companion object {
        const val GAME_PANEL: String = "GamePanel"
        const val LOADING_PANEL: String = "LoadingPanel"
        const val CONFIGURATION_PANEL: String = "ConfigurationPanel"

        private val LOGGER: Logger = LoggerFactory.getLogger(MainGUI::class.java)

        var instance: MainGUI? = null
        const val X_LOCATION: Int = 0
        const val Y_LOCATION: Int = 0
        const val X_DIM: Int = 800
        const val Y_DIM: Int = 600
        const val DEFAULT_FULLSCREENMODE: Boolean = false

        var levelView: LevelView? = null

        /**
         * @param args the command line arguments
         */
        @JvmStatic
        fun main(args: Array<String>) {
            EventQueue.invokeLater { MainGUI().isVisible = true }
        }
    }
}
