/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.net

import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.openbase.jps.core.JPService
import org.openbase.jps.exception.JPNotAvailableException
import org.openbase.jul.exception.CouldNotPerformException
import org.openbase.jul.exception.printer.ExceptionPrinter
import org.openbase.planetsudo.game.Team
import org.openbase.planetsudo.game.Team.Companion.loadDefaultTeam
import org.openbase.planetsudo.game.Team.Companion.save
import org.openbase.planetsudo.game.TeamData
import org.openbase.planetsudo.game.strategy.StrategyClassLoader.loadStrategy
import org.openbase.planetsudo.game.strategy.StrategyClassLoader.revalidate
import org.openbase.planetsudo.jp.JPExternalStrategyJar
import org.openbase.planetsudo.jp.JPServerHostname
import org.openbase.planetsudo.jp.JPServerPort
import org.openbase.planetsudo.jp.JPStrategySourceDirectory
import org.openbase.planetsudo.jp.JPTeamPath
import org.openbase.planetsudo.view.MainGUI
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import java.io.*
import java.net.Socket
import java.util.concurrent.locks.ReentrantLock
import javax.swing.SwingWorker
import kotlin.concurrent.withLock

/**
 * @author Divine Threepwood
 */
class PlanetSudoClient private constructor() {
    enum class ConnectionState(@JvmField val description: String) {
        UploadDefaultTeam("Sende Team..."),
        DownloadTeams("Empfange Teams..."),
        UploadDefaultStrategy("Sende Strategie..."),
        DownloadStrategies("Empfange Strategien..."),
        Connecting("Verbindung wird hergestellt..."),
        SyncSuccessful("Erfolgreich Synchronisiert!"),
        ConnectionError("Verbindungsfehler!"),
    }

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val change = PropertyChangeSupport(this)
    private val syncLock = ReentrantLock()
    private var state: ConnectionState? = null
    private var out: ObjectOutputStream? = null
    private var `in`: ObjectInputStream? = null

    fun runSync() {
        val worker: SwingWorker<*, *> = object : SwingWorker<Any?, Any?>() {
            @Throws(Exception::class)
            override fun doInBackground(): Any? {
                sync()
                return null
            }
        }
        worker.execute()
    }

    private fun sync() = syncLock.withLock {
        try {
            connecting()
            transferData()
        } catch (ex: CouldNotPerformException) {
            setConnectionState(ConnectionState.ConnectionError)
            logger.error("Could not sync!", ex)
        } finally {
            try {
                if (`in` != null) {
                    `in`!!.close()
                }
                if (out != null) {
                    out!!.close()
                }
            } catch (ex: IOException) {
                logger.error("Connection Lost!")
            }
        }

        MainGUI.instance?.configurationPanel?.updateTeamList()
    }

    private fun connecting() {
        setConnectionState(ConnectionState.Connecting)
        try {
            val clientSocket = Socket(
                JPService.getProperty(
                    JPServerHostname::class.java,
                ).value,
                JPService.getProperty(JPServerPort::class.java).value,
            )
            out = ObjectOutputStream(clientSocket.getOutputStream())
            `in` = ObjectInputStream(clientSocket.getInputStream())
        } catch (ex: CouldNotPerformException) {
            ExceptionPrinter.printHistory(CouldNotPerformException("Error during transfer occured!", ex), logger)
        }
    }

    @Throws(CouldNotPerformException::class, IOException::class, ClassNotFoundException::class)
    private fun transferData() {
        uploadDefaultTeam()
        uploadDefaultStrategy()
        downloadTeams().also { teams ->
            downloadStrategies(teams)
        }
        setConnectionState(ConnectionState.SyncSuccessful)
    }

    @Throws(CouldNotPerformException::class, IOException::class)
    private fun uploadDefaultTeam() {
        setConnectionState(ConnectionState.UploadDefaultTeam)
        out!!.writeObject(loadDefaultTeam())
    }

    @Throws(IOException::class, ClassNotFoundException::class, CouldNotPerformException::class)
    private fun downloadTeams(): List<TeamData> {
        setConnectionState(ConnectionState.DownloadTeams)
        val teamCounter = `in`!!.readInt()
        val downloadedTeams = ArrayList<TeamData>(teamCounter)
        for (i in 0 until teamCounter) {
            val teamData = `in`!!.readObject() as TeamData
            if (teamData.name == Team.DEFAULT_ID) {
                continue
            }
            save(teamData)
            downloadedTeams.add(teamData)
        }
        removeTeamsNotFromServer(downloadedTeams.mapTo(mutableSetOf()) { "${it.name}.team" })
        return downloadedTeams
    }

    @Throws(CouldNotPerformException::class)
    private fun removeTeamsNotFromServer(serverTeamFiles: Set<String>) {
        try {
            val teamFolder = JPService.getProperty(JPTeamPath::class.java).value
            val strategySourceDirectory = JPService.getProperty(JPStrategySourceDirectory::class.java).value
            val teamFiles = teamFolder.listFiles()
                ?: throw CouldNotPerformException("Could not list team folder[${teamFolder.absolutePath}]!")

            for (teamFile in teamFiles) {
                if (!isTeamFileEligibleForDeletion(teamFile, teamFolder)) {
                    continue
                }
                if (teamFile.name !in serverTeamFiles) {
                    val teamData = try {
                        Team.load(teamFile.nameWithoutExtension)
                    } catch (ex: CouldNotPerformException) {
                        logger.warn("Keeping team[${teamFile.name}] because its strategy could not be checked.", ex)
                        continue
                    }
                    if (teamData == null) {
                        logger.warn("Keeping team[${teamFile.name}] because its strategy could not be checked.")
                        continue
                    }
                    if (shouldPreserveTeam(teamData, strategySourceDirectory)) {
                        logger.info("Keeping team[${teamData.name}] because it uses a local strategy[${teamData.strategy}].")
                        continue
                    }
                    FileUtils.delete(teamFile)
                }
            }
        } catch (ex: JPNotAvailableException) {
            throw CouldNotPerformException("Could not synchronize teams!", ex)
        } catch (ex: IOException) {
            throw CouldNotPerformException("Could not delete teams not provided by the server!", ex)
        }
    }

    @Throws(CouldNotPerformException::class, FileNotFoundException::class, IOException::class)
    private fun uploadDefaultStrategy() {
        try {
            setConnectionState(ConnectionState.UploadDefaultStrategy)
            val defaultTeamData = loadDefaultTeam()
            val sourceFile = File(
                JPService.getProperty(
                    JPStrategySourceDirectory::class.java,
                ).value,
                defaultTeamData!!.strategy + ".kt",
            )
            if (!sourceFile.exists()) {
                throw CouldNotPerformException("File[" + sourceFile.absolutePath + "] does not exist!")
            }
            out!!.writeUTF(sourceFile.name)
            val fileBytes = FileUtils.readFileToByteArray(sourceFile)
            out!!.writeInt(fileBytes.size)
            IOUtils.write(fileBytes, out)
            out!!.flush()
        } catch (ex: CouldNotPerformException) {
            throw CouldNotPerformException("Could not upload default strategy!", ex)
        } catch (ex: JPNotAvailableException) {
            throw CouldNotPerformException("Could not upload default strategy!", ex)
        }
    }

    @Throws(CouldNotPerformException::class)
    private fun downloadStrategies(downloadedTeams: List<TeamData>) {
        try {
            setConnectionState(ConnectionState.DownloadStrategies)
            val jarFile = JPService.getProperty(JPExternalStrategyJar::class.java).value
            val strategySourceDirectory = JPService.getProperty(JPStrategySourceDirectory::class.java).value
            val fileByteLenght = `in`!!.readInt()
            val fileBytes = ByteArray(fileByteLenght)
            IOUtils.readFully(`in`, fileBytes)
            FileUtils.writeByteArrayToFile(jarFile, fileBytes)
            revalidate()
            removeTeamsWithUnavailableStrategies(downloadedTeams, strategySourceDirectory)
        } catch (ex: JPNotAvailableException) {
            throw CouldNotPerformException("Could not download stradegies!", ex)
        } catch (ex: IOException) {
            throw CouldNotPerformException("Could not download stradegies!", ex)
        }
    }

    private fun removeTeamsWithUnavailableStrategies(teams: List<TeamData>, strategySourceDirectory: File) {
        for (team in teams) {
            if (shouldPreserveTeam(team, strategySourceDirectory)) {
                continue
            }
            try {
                loadStrategy(team.strategy)
            } catch (ex: CouldNotPerformException) {
                logger.warn("Removing team[${team.name}] because its strategy[${team.strategy}] cannot be loaded.", ex)
                deleteTeamFile(team.name)
            } catch (ex: LinkageError) {
                logger.warn("Removing team[${team.name}] because its strategy[${team.strategy}] cannot be loaded.", ex)
                deleteTeamFile(team.name)
            }
        }
    }

    @Throws(CouldNotPerformException::class)
    private fun deleteTeamFile(teamName: String) {
        try {
            val teamFolder = JPService.getProperty(JPTeamPath::class.java).value.canonicalFile
            val teamFile = File(teamFolder, "$teamName.team").canonicalFile
            if (!isTeamFileEligibleForDeletion(teamFile, teamFolder)) {
                throw CouldNotPerformException("Refusing to delete an ineligible team file.")
            }
            FileUtils.delete(teamFile)
        } catch (ex: JPNotAvailableException) {
            throw CouldNotPerformException("Could not access team folder while removing team[$teamName]!", ex)
        } catch (ex: IOException) {
            throw CouldNotPerformException("Could not remove team[$teamName]!", ex)
        }
    }

    fun setConnectionState(state: ConnectionState) {
        if (this.state == state) {
            return
        }
        this.state = state
        logger.info("ConnectionState: " + state.name)
        change.firePropertyChange(CONNECTION_STATE_UPDATE, null, state)
    }

    fun addPropertyChangeListener(listener: PropertyChangeListener?) {
        change.addPropertyChangeListener(listener)
    }

    fun removePropertyChangeListener(listener: PropertyChangeListener?) {
        change.removePropertyChangeListener(listener)
    }

    companion object {
        const val CONNECTION_STATE_UPDATE: String = "ConnectionStateUpdate"

        private val instanceLock = ReentrantLock()

        @JvmStatic
        var instance: PlanetSudoClient? = null
            get() = instanceLock.withLock { field ?: PlanetSudoClient().also { field = it } }
            private set
    }
}

private fun isTeamFileEligibleForDeletion(teamFile: File, teamFolder: File): Boolean {
    if (!teamFile.isFile || !teamFile.name.endsWith(".team") || teamFile.name == "default.team") {
        return false
    }

    val canonicalTeamFolder = teamFolder.canonicalFile
    val canonicalTeamFile = teamFile.canonicalFile
    return canonicalTeamFile.parentFile == canonicalTeamFolder && canonicalTeamFile.name != "default.team"
}

internal fun shouldPreserveTeam(teamData: TeamData, strategySourceDirectory: File): Boolean {
    return teamData.strategy in PROTECTED_STRATEGIES ||
        File(strategySourceDirectory, "${teamData.strategy}.kt").isFile
}

private val PROTECTED_STRATEGIES = setOf("LevelOne", "LevelTwo")
