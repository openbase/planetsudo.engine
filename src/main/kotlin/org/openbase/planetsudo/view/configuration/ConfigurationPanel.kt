/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * ConfigurationPanel.java
 *
 * Created on Jun 17, 2010, 1:02:56 AM
 */
package org.openbase.planetsudo.view.configuration

import org.apache.commons.io.FileUtils
import org.openbase.jul.exception.CouldNotPerformException
import org.openbase.jul.exception.printer.ExceptionPrinter
import org.openbase.jul.visual.swing.image.ImageLoader
import org.openbase.planetsudo.game.GameManager
import org.openbase.planetsudo.game.GameManager.Companion.gameManager
import org.openbase.planetsudo.game.Team.Companion.loadAll
import org.openbase.planetsudo.game.Team.Companion.loadDefaultTeam
import org.openbase.planetsudo.game.Team.Companion.saveDefaultTeam
import org.openbase.planetsudo.game.TeamData
import org.openbase.planetsudo.level.LevelLoader.Companion.getInstance
import org.openbase.planetsudo.net.PlanetSudoClient
import org.openbase.planetsudo.net.PlanetSudoClient.Companion.instance
import org.openbase.planetsudo.view.MainGUI
import org.openbase.planetsudo.view.level.LevelDisplayPanel
import org.openbase.planetsudo.view.util.getItems
import org.openbase.planetsudo.view.util.getSelection
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Color
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*
import javax.swing.*
import javax.swing.border.BevelBorder
import javax.swing.border.SoftBevelBorder
import kotlin.random.Random

/**
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class ConfigurationPanel : JPanel() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)
    private val stateProperties: Properties

    private fun initDynamicComponents() {
        // load level
        levelChooserComboBox!!.isEnabled = false
        randomLevelButton!!.isEnabled = false
        levelChooserComboBox!!.removeAllItems()
        for (levelName in getInstance()!!.levelNameSet) {
            levelChooserComboBox!!.addItem(levelName)
        }
        if (levelChooserComboBox!!.itemCount >= 0) {
            val index = stateProperties.getProperty(PROPERTY_SELECTED_LEVEL, "0").toInt()
            levelChooserComboBox!!.selectedIndex = if (index < levelChooserComboBox!!.itemCount) index else 0
        }
        levelChooserComboBox!!.isEnabled = true
        randomLevelButton!!.isEnabled = true

        // load teams
        updateTeamList()

        // setup default team
        enableDefaultTeamSelection()

        try {
            loadDefaultTeam()?.let {
                setDefaultTeam(it)
            }
        } catch (ex: CouldNotPerformException) {
            ExceptionPrinter.printHistory(CouldNotPerformException("Could not load default team!", ex), logger)
        }
    }

    fun enableDefaultTeamSelection() {
        setDefaultTeamButton!!.foreground = Color.BLACK
        setDefaultTeamButton!!.isEnabled = true
        defaultTeamComboBox!!.isEnabled = true
        syncButton!!.isEnabled = false
    }

    fun updateTeamList(selectTeam: TeamData? = null) {
        try {
            teamAComboBox!!.isEnabled = false
            teamBComboBox!!.isEnabled = false
            teamAComboBox!!.removeAllItems()
            teamBComboBox!!.removeAllItems()

            if (defaultTeamComboBox!!.isEnabled) {
                defaultTeamComboBox!!.removeAllItems()
            }

            val teams = loadAll()
            for (teamData in teams) {
                teamAComboBox!!.addItem(teamData)
                teamBComboBox!!.addItem(teamData)

                if (defaultTeamComboBox!!.isEnabled) {
                    defaultTeamComboBox!!.addItem(teamData)
                }
            }

            // restore selection
            if (teamAComboBox!!.itemCount > 0) {
                (
                    selectTeam
                        ?: teamAComboBox!!.getItems()
                            .filterNotNull()
                            .find { it.name == stateProperties.getProperty(PROPERTY_SELECTED_TEAM_A, "") }
                    )
                    ?.also { teamAComboBox!!.selectedItem = it }
                    ?: run { teamAComboBox!!.selectedIndex = 0 }
            }

            if (teamBComboBox!!.itemCount > 0) {
                teamBComboBox!!.getItems()
                    .filterNotNull()
                    .find { it.name == stateProperties.getProperty(PROPERTY_SELECTED_TEAM_B, "") }
                    ?.also { teamBComboBox!!.selectedItem = it }
                    ?: run { teamBComboBox!!.selectedIndex = 0 }
            }

            teamAComboBox!!.isEnabled = true
            teamBComboBox!!.isEnabled = true
        } catch (ex: CouldNotPerformException) {
            logger.warn("Could not load teams!", ex)
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private fun initComponents() {
        levelPreviewPanel = JPanel()
        levelPreviewDisplayPanel = LevelDisplayPanel()
        logoLabel = JLabel()
        gameSettingsPanel = JPanel()
        teamsPanel = JPanel()
        versusLabel = JLabel()
        teamAPanel = JPanel()
        teamAComboBox = JComboBox<TeamData?>()
        teamALabel = JLabel()
        teamBPanel = JPanel()
        teamBComboBox = JComboBox<TeamData?>()
        teamBLabel = JLabel()
        levelChooserPanel = JPanel()
        levelChooserComboBox = JComboBox<String>()
        startGameButton = JButton()
        networkPanel = JPanel()
        networkTeamPanel = JPanel()
        defaultTeamComboBox = JComboBox()
        setDefaultTeamButton = JButton()
        serverPanel = JPanel()
        connectionStateLabel = JLabel()
        syncButton = JButton()
        randomLevelButton = JButton()

        levelPreviewPanel!!.border = BorderFactory.createTitledBorder("Vorschau")

        val levelPreviewDisplayPanelLayout = GroupLayout(levelPreviewDisplayPanel)
        levelPreviewDisplayPanel!!.layout = levelPreviewDisplayPanelLayout
        levelPreviewDisplayPanelLayout.setHorizontalGroup(
            levelPreviewDisplayPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 801, Short.MAX_VALUE.toInt()),
        )
        levelPreviewDisplayPanelLayout.setVerticalGroup(
            levelPreviewDisplayPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE.toInt()),
        )

        val levelPreviewPanelLayout = GroupLayout(levelPreviewPanel)
        levelPreviewPanel!!.layout = levelPreviewPanelLayout
        levelPreviewPanelLayout.setHorizontalGroup(
            levelPreviewPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    GroupLayout.Alignment.TRAILING,
                    levelPreviewPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(
                            levelPreviewDisplayPanel,
                            GroupLayout.DEFAULT_SIZE,
                            GroupLayout.DEFAULT_SIZE,
                            Short.MAX_VALUE.toInt(),
                        )
                        .addContainerGap(),
                ),
        )
        levelPreviewPanelLayout.setVerticalGroup(
            levelPreviewPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    levelPreviewPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(
                            levelPreviewDisplayPanel,
                            GroupLayout.DEFAULT_SIZE,
                            GroupLayout.DEFAULT_SIZE,
                            Short.MAX_VALUE.toInt(),
                        )
                        .addContainerGap(),
                ),
        )

        logoLabel!!.horizontalAlignment = SwingConstants.CENTER
        logoLabel!!.verticalAlignment = SwingConstants.BOTTOM
        logoLabel!!.horizontalTextPosition = SwingConstants.CENTER
        logoLabel!!.verticalTextPosition = SwingConstants.BOTTOM

        gameSettingsPanel!!.border = BorderFactory.createTitledBorder("Spiel Einstellungen")

        teamsPanel!!.border = BorderFactory.createTitledBorder("Teams")

        versusLabel!!.horizontalAlignment = SwingConstants.CENTER
        versusLabel!!.text = "VS"

        teamAComboBox!!.setModel(DefaultComboBoxModel(emptyArray()))
        teamAComboBox!!.addActionListener { _ -> teamAComboBoxActionPerformed() }

        teamALabel!!.horizontalAlignment = SwingConstants.CENTER
        teamALabel!!.text = "Team A"

        val teamAPanelLayout = GroupLayout(teamAPanel)
        teamAPanel!!.layout = teamAPanelLayout
        teamAPanelLayout.setHorizontalGroup(
            teamAPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(teamAComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE.toInt())
                .addComponent(teamALabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE.toInt()),
        )
        teamAPanelLayout.setVerticalGroup(
            teamAPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    GroupLayout.Alignment.TRAILING,
                    teamAPanelLayout.createSequentialGroup()
                        .addComponent(teamALabel)
                        .addPreferredGap(
                            LayoutStyle.ComponentPlacement.RELATED,
                            GroupLayout.DEFAULT_SIZE,
                            Int.MAX_VALUE,
                        )
                        .addComponent(
                            teamAComboBox,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE,
                            GroupLayout.PREFERRED_SIZE,
                        ),
                ),
        )

        teamBComboBox!!.setModel(DefaultComboBoxModel())
        teamBComboBox!!.addActionListener { _ -> teamBComboBoxActionPerformed() }

        teamBLabel!!.horizontalAlignment = SwingConstants.CENTER
        teamBLabel!!.text = "Team B"

        val teamBPanelLayout = GroupLayout(teamBPanel)
        teamBPanel!!.layout = teamBPanelLayout
        teamBPanelLayout.setHorizontalGroup(
            teamBPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(teamBComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE.toInt())
                .addComponent(
                    teamBLabel,
                    GroupLayout.Alignment.TRAILING,
                    GroupLayout.DEFAULT_SIZE,
                    GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE.toInt(),
                ),
        )
        teamBPanelLayout.setVerticalGroup(
            teamBPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    teamBPanelLayout.createSequentialGroup()
                        .addComponent(teamBLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(
                            teamBComboBox,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE,
                            GroupLayout.PREFERRED_SIZE,
                        ),
                ),
        )

        val teamsPanelLayout = GroupLayout(teamsPanel)
        teamsPanel!!.layout = teamsPanelLayout
        teamsPanelLayout.setHorizontalGroup(
            teamsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    teamsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(
                            teamAPanel,
                            GroupLayout.DEFAULT_SIZE,
                            GroupLayout.DEFAULT_SIZE,
                            Short.MAX_VALUE.toInt(),
                        )
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(versusLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(
                            teamBPanel,
                            GroupLayout.DEFAULT_SIZE,
                            GroupLayout.DEFAULT_SIZE,
                            Short.MAX_VALUE.toInt(),
                        )
                        .addContainerGap(),
                ),
        )
        teamsPanelLayout.setVerticalGroup(
            teamsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    teamsPanelLayout.createSequentialGroup()
                        .addGroup(
                            teamsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(
                                    teamsPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(
                                            versusLabel,
                                            GroupLayout.DEFAULT_SIZE,
                                            GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE.toInt(),
                                        )
                                        .addComponent(
                                            teamAPanel,
                                            GroupLayout.DEFAULT_SIZE,
                                            GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE.toInt(),
                                        ),
                                )
                                .addComponent(
                                    teamBPanel,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE,
                                ),
                        )
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE.toInt()),
                ),
        )

        levelChooserPanel!!.border = BorderFactory.createTitledBorder("Level")

        levelChooserComboBox!!.setMaximumRowCount(20)
        levelChooserComboBox!!.setModel(DefaultComboBoxModel())
        levelChooserComboBox!!.addActionListener { _ -> levelChooserComboBoxActionPerformed() }

        randomLevelButton!!.text = "Zufällig"
        randomLevelButton!!.addActionListener { _ -> randomLevelButtonActionPerformed() }

        val levelChooserPanelLayout = GroupLayout(levelChooserPanel)
        levelChooserPanel!!.layout = levelChooserPanelLayout
        levelChooserPanelLayout.setHorizontalGroup(
            levelChooserPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    levelChooserPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(levelChooserComboBox, 0, 324, Short.MAX_VALUE.toInt())
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(randomLevelButton)
                        .addContainerGap(),
                ),
        )

        levelChooserPanelLayout.setVerticalGroup(
            levelChooserPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    levelChooserPanelLayout.createSequentialGroup()
                        .addGroup(
                            levelChooserPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(
                                    levelChooserComboBox,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE,
                                )
                                .addComponent(randomLevelButton),
                        )
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE.toInt()),
                ),
        )

        startGameButton!!.text = "Spiel Starten"
        startGameButton!!.addActionListener { _ -> startGameButtonActionPerformed() }

        val gameSettingsPanelLayout = GroupLayout(gameSettingsPanel)
        gameSettingsPanel!!.layout = gameSettingsPanelLayout
        gameSettingsPanelLayout.setHorizontalGroup(
            gameSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    gameSettingsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            gameSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(
                                    teamsPanel,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE.toInt(),
                                )
                                .addComponent(
                                    levelChooserPanel,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE.toInt(),
                                )
                                .addComponent(
                                    startGameButton,
                                    GroupLayout.Alignment.TRAILING,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE.toInt(),
                                ),
                        )
                        .addContainerGap(),
                ),
        )
        gameSettingsPanelLayout.setVerticalGroup(
            gameSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    gameSettingsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(teamsPanel, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(levelChooserPanel, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(startGameButton)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE.toInt()),
                ),
        )

        networkPanel!!.border = BorderFactory.createTitledBorder("Netzwerk")

        networkTeamPanel!!.border = BorderFactory.createTitledBorder("Dein Team")

        defaultTeamComboBox!!.setModel(
            DefaultComboBoxModel<TeamData>(),
        )
        defaultTeamComboBox!!.addActionListener { _ -> defaultTeamComboBoxActionPerformed() }

        setDefaultTeamButton!!.text = "Setzen"
        setDefaultTeamButton!!.addActionListener { _ -> setDefaultTeamButtonActionPerformed() }

        val networkTeamPanelLayout = GroupLayout(networkTeamPanel)
        networkTeamPanel!!.layout = networkTeamPanelLayout
        networkTeamPanelLayout.setHorizontalGroup(
            networkTeamPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    GroupLayout.Alignment.TRAILING,
                    networkTeamPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(defaultTeamComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE.toInt())
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(setDefaultTeamButton)
                        .addContainerGap(),
                ),
        )
        networkTeamPanelLayout.setVerticalGroup(
            networkTeamPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    networkTeamPanelLayout.createSequentialGroup()
                        .addGroup(
                            networkTeamPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    defaultTeamComboBox,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE,
                                )
                                .addComponent(setDefaultTeamButton),
                        )
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE.toInt()),
                ),
        )

        serverPanel!!.border = BorderFactory.createTitledBorder("Server")

        connectionStateLabel!!.horizontalAlignment = SwingConstants.CENTER
        connectionStateLabel!!.text = "Verbindungsstatus"
        connectionStateLabel!!.border = SoftBevelBorder(BevelBorder.LOWERED)
        connectionStateLabel!!.isOpaque = true

        syncButton!!.text = "Synchronisation"
        syncButton!!.addActionListener { _ -> syncButtonActionPerformed() }

        val serverPanelLayout = GroupLayout(serverPanel)
        serverPanel!!.layout = serverPanelLayout
        serverPanelLayout.setHorizontalGroup(
            serverPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    serverPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            serverPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(
                                    connectionStateLabel,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE.toInt(),
                                )
                                .addComponent(
                                    syncButton,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE.toInt(),
                                ),
                        )
                        .addContainerGap(),
                ),
        )
        serverPanelLayout.setVerticalGroup(
            serverPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    serverPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(connectionStateLabel, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(syncButton)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE.toInt()),
                ),
        )

        val networkPanelLayout = GroupLayout(networkPanel)
        networkPanel!!.layout = networkPanelLayout
        networkPanelLayout.setHorizontalGroup(
            networkPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    networkPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            networkPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(
                                    networkTeamPanel,
                                    GroupLayout.Alignment.TRAILING,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE.toInt(),
                                )
                                .addComponent(
                                    serverPanel,
                                    GroupLayout.Alignment.TRAILING,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE.toInt(),
                                ),
                        )
                        .addContainerGap(),
                ),
        )
        networkPanelLayout.setVerticalGroup(
            networkPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    networkPanelLayout.createSequentialGroup()
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE.toInt())
                        .addComponent(networkTeamPanel, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(serverPanel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE),
                ),
        )

        val layout = GroupLayout(this)
        this.layout = layout
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(
                                    gameSettingsPanel,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE.toInt(),
                                )
                                .addComponent(logoLabel, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
                                .addComponent(
                                    networkPanel,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE.toInt(),
                                ),
                        )
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(
                            levelPreviewPanel,
                            GroupLayout.DEFAULT_SIZE,
                            GroupLayout.DEFAULT_SIZE,
                            Short.MAX_VALUE.toInt(),
                        )
                        .addContainerGap(),
                ),
        )
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(
                                    levelPreviewPanel,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE.toInt(),
                                )
                                .addGroup(
                                    layout.createSequentialGroup()
                                        .addComponent(
                                            gameSettingsPanel,
                                            GroupLayout.PREFERRED_SIZE,
                                            GroupLayout.DEFAULT_SIZE,
                                            GroupLayout.PREFERRED_SIZE,
                                        )
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(
                                            networkPanel,
                                            GroupLayout.PREFERRED_SIZE,
                                            GroupLayout.DEFAULT_SIZE,
                                            GroupLayout.PREFERRED_SIZE,
                                        )
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 219, Int.MAX_VALUE)
                                        .addComponent(
                                            logoLabel,
                                            GroupLayout.PREFERRED_SIZE,
                                            192,
                                            GroupLayout.PREFERRED_SIZE,
                                        ),
                                ),
                        )
                        .addContainerGap(),
                ),
        )
    } // </editor-fold>//GEN-END:initComponents

    private fun levelChooserComboBoxActionPerformed() { // GEN-FIRST:event_levelChooserComboBoxActionPerformed
        object : SwingWorker<Any?, Any?>() {
            @Throws(Exception::class)
            override fun doInBackground(): Any? {
                synchronized(levelChooserComboBox!!) {
                    if (levelChooserComboBox!!.selectedItem != null) {
                        try {
                            if (levelChooserComboBox!!.isEnabled) {
                                stateProperties.setProperty(
                                    PROPERTY_SELECTED_LEVEL,
                                    levelChooserComboBox!!.selectedIndex.toString(),
                                )
                            }
                            val level = getInstance()!!
                                .loadLevel(levelChooserComboBox!!.selectedItem!!.toString())
                            gameManager.setLevel(level!!)
                            levelPreviewDisplayPanel!!.setLevel(level)
                            levelPreviewDisplayPanel!!.isOpaque = true
                            levelPreviewDisplayPanel!!.background = level.color
                        } catch (ex: CouldNotPerformException) {
                            logger.error("Could not update level preview!", ex)
                        }
                    }
                }
                return null
            }
        }.execute()
    } // GEN-LAST:event_levelChooserComboBoxActionPerformed

    private fun randomLevelButtonActionPerformed() =
        levelChooserComboBox
            ?.takeIf { it.itemCount > 1 }
            ?.selectedIndex?.also { selectedIndex ->
                do {
                    levelChooserComboBox!!.selectedIndex = Random.nextInt(levelChooserComboBox!!.itemCount)
                } while (selectedIndex == levelChooserComboBox!!.selectedIndex)
            }

    private fun startGameButtonActionPerformed() { // GEN-FIRST:event_startGameButtonActionPerformed
        MainGUI.instance!!.showLoadingPanel()
        gameManager.startGame()
    } // GEN-LAST:event_startGameButtonActionPerformed

    private fun teamAComboBoxActionPerformed() { // GEN-FIRST:event_teamAComboBoxActionPerformed
        teamAComboBox?.apply {
            selectedItem?.let { selectedItem ->
                gameManager.addTeam(selectedItem as TeamData, GameManager.TeamType.A)
                if (isEnabled) {
                    getSelection()?.name?.let {
                        stateProperties.setProperty(PROPERTY_SELECTED_TEAM_A, it)
                    }
                }
            }
        }
    } // GEN-LAST:event_teamAComboBoxActionPerformed

    private fun teamBComboBoxActionPerformed() { // GEN-FIRST:event_teamBComboBoxActionPerformed
        teamBComboBox?.apply {
            selectedItem?.let { selectedItem ->
                gameManager.addTeam(selectedItem as TeamData, GameManager.TeamType.B)
                if (isEnabled) {
                    getSelection()?.name?.let {
                        stateProperties.setProperty(PROPERTY_SELECTED_TEAM_B, it)
                    }
                }
            }
        }
    } // GEN-LAST:event_teamBComboBoxActionPerformed

    private fun defaultTeamComboBoxActionPerformed() { // GEN-FIRST:event_defaultTeamComboBoxActionPerformed
    } // GEN-LAST:event_defaultTeamComboBoxActionPerformed

    fun setDefaultTeamCandidate(teamData: TeamData) = teamData
        .takeIf { defaultTeamComboBox!!.isEnabled }
        ?.also {
            saveDefaultTeam(it)
            setDefaultTeam(it)
        }

    private fun setDefaultTeam(defaultTeamData: TeamData) {
        setDefaultTeamButton!!.foreground = Color.BLACK
        setDefaultTeamButton!!.isEnabled = false
        defaultTeamComboBox!!.isEnabled = false
        syncButton!!.isEnabled = true
        try {
            for (i in 0 until defaultTeamComboBox!!.model.size) {
                if ((defaultTeamComboBox!!.model.getElementAt(i) as TeamData).name == defaultTeamData.name) {
                    defaultTeamComboBox!!.selectedItem = defaultTeamComboBox!!.model.getElementAt(i)
                    break
                }
            }
        } catch (ex: CouldNotPerformException) {
            ExceptionPrinter.printHistory(CouldNotPerformException("Could not resolve default Team!", ex), logger)
        }
    }

    private fun setDefaultTeamButtonActionPerformed() { // GEN-FIRST:event_setDefaultTeamButtonActionPerformed
        try {
            val defaultTeamData = defaultTeamComboBox!!.selectedItem as TeamData
            setDefaultTeamButton!!.foreground = Color.BLACK
            saveDefaultTeam(defaultTeamData)
            setDefaultTeam(defaultTeamData)
        } catch (exx: CouldNotPerformException) {
            logger.error("Could not define default team!", exx)
            setDefaultTeamButton!!.foreground = Color.RED
            return
        }
    } // GEN-LAST:event_setDefaultTeamButtonActionPerformed

    private fun syncButtonActionPerformed() { // GEN-FIRST:event_syncButtonActionPerformed
        instance!!.runSync()
    } // GEN-LAST:event_syncButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private var levelChooserComboBox: JComboBox<String>? = null
    private var connectionStateLabel: JLabel? = null
    private var defaultTeamComboBox: JComboBox<TeamData>? = null
    private var startGameButton: JButton? = null
    private var teamALabel: JLabel? = null
    private var teamBLabel: JLabel? = null
    private var versusLabel: JLabel? = null
    private var teamsPanel: JPanel? = null
    private var levelChooserPanel: JPanel? = null
    private var levelPreviewPanel: JPanel? = null
    private var gameSettingsPanel: JPanel? = null
    private var networkTeamPanel: JPanel? = null
    private var networkPanel: JPanel? = null
    private var serverPanel: JPanel? = null
    private var teamAPanel: JPanel? = null
    private var teamBPanel: JPanel? = null
    private var levelPreviewDisplayPanel: LevelDisplayPanel? = null
    private var logoLabel: JLabel? = null
    private var setDefaultTeamButton: JButton? = null
    private var syncButton: JButton? = null
    private var teamAComboBox: JComboBox<TeamData?>? = null
    private var teamBComboBox: JComboBox<TeamData?>? = null
    private var randomLevelButton: JButton? = null // End of variables declaration//GEN-END:variables

    /**
     * Creates new form ConfigurationPanel
     */
    init {
        initComponents()
        try {
            logoLabel!!.icon = ImageIcon(ImageLoader.getInstance().loadImage("img/PlanetSudoLogoMedium.png"))
        } catch (ex: CouldNotPerformException) {
            ExceptionPrinter.printHistory(CouldNotPerformException("Could not display image", ex), logger)
        }

        instance!!.addPropertyChangeListener { evt ->
            if (evt.propertyName == PlanetSudoClient.CONNECTION_STATE_UPDATE) {
                val state = evt.newValue as PlanetSudoClient.ConnectionState

                connectionStateLabel!!.text = state.description

                when (state) {
                    PlanetSudoClient.ConnectionState.Connecting, PlanetSudoClient.ConnectionState.DownloadStrategies, PlanetSudoClient.ConnectionState.DownloadTeams, PlanetSudoClient.ConnectionState.UploadDefaultStrategy, PlanetSudoClient.ConnectionState.UploadDefaultTeam -> {
                        connectionStateLabel!!.background = Color(100, 200, 100)
                        syncButton!!.isEnabled = false
                    }

                    PlanetSudoClient.ConnectionState.SyncSuccessful -> {
                        connectionStateLabel!!.background = Color(100, 100, 200)
                        syncButton!!.isEnabled = true
                    }

                    PlanetSudoClient.ConnectionState.ConnectionError -> {
                        connectionStateLabel!!.background = Color(200, 100, 100)
                        syncButton!!.isEnabled = true
                    }
                }
            }
        }

        stateProperties = Properties()

        try {
            val propertiesFile = File(FileUtils.getTempDirectory(), "planetsudo.properties")
            if (propertiesFile.exists()) {
                stateProperties.load(FileInputStream(propertiesFile))
                logger.info("Load GUI Properties from " + propertiesFile.absolutePath)
            }
        } catch (ex: CouldNotPerformException) {
            ExceptionPrinter.printHistory("Could not load gui properties!", ex, logger)
        }

        initDynamicComponents()

        Runtime.getRuntime().addShutdownHook(
            object : Thread() {
                override fun run() {
                    try {
                        val propertiesFile = File(FileUtils.getTempDirectory(), "planetsudo.properties")
                        logger.info("Store GUI Properties to " + propertiesFile.absolutePath)
                        if (!propertiesFile.exists()) {
                            logger.info("Create: " + propertiesFile.createNewFile())
                        }
                        stateProperties.store(FileOutputStream(propertiesFile), "PlanetSudo GUI Properties")
                    } catch (ex: CouldNotPerformException) {
                        ExceptionPrinter.printHistory("Could not store gui properties!", ex, logger)
                    }
                }
            },
        )
    }

    companion object {
        const val PROPERTY_SELECTED_TEAM_A: String = "TEAM_A"
        const val PROPERTY_SELECTED_TEAM_B: String = "TEAM_B"
        const val PROPERTY_SELECTED_LEVEL: String = "org.openbase.planetsudo.level"
    }
}
