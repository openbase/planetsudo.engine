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
import org.openbase.planetsudo.game.Team.Companion.loadDefaultTeam
import org.openbase.planetsudo.game.Team.Companion.save
import org.openbase.planetsudo.game.TeamData
import org.openbase.planetsudo.game.strategy.StrategyClassLoader.revalidate
import org.openbase.planetsudo.jp.JPExternalStrategyJar
import org.openbase.planetsudo.jp.JPServerHostname
import org.openbase.planetsudo.jp.JPServerPort
import org.openbase.planetsudo.jp.JPStrategySourceDirectory
import org.openbase.planetsudo.view.MainGUI
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import java.io.*
import java.net.Socket
import javax.swing.SwingWorker

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
        ConnectionError("Verbindungsfehler!")
    }

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val change = PropertyChangeSupport(this)
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

    @Synchronized
    private fun sync() {
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
                    JPServerHostname::class.java
                ).value,
                JPService.getProperty(JPServerPort::class.java).value
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
        downloadTeams()
        downloadStrategies()
        setConnectionState(ConnectionState.SyncSuccessful)
    }

    @Throws(CouldNotPerformException::class, IOException::class)
    private fun uploadDefaultTeam() {
        setConnectionState(ConnectionState.UploadDefaultTeam)
        out!!.writeObject(loadDefaultTeam())
    }

    @Throws(IOException::class, ClassNotFoundException::class, CouldNotPerformException::class)
    private fun downloadTeams() {
        setConnectionState(ConnectionState.DownloadTeams)
        val teamCounter = `in`!!.readInt()
        var teamData: TeamData
        for (i in 0 until teamCounter) {
            teamData = `in`!!.readObject() as TeamData
            save(teamData)
        }
    }

    @Throws(CouldNotPerformException::class, FileNotFoundException::class, IOException::class)
    private fun uploadDefaultStrategy() {
        try {
            setConnectionState(ConnectionState.UploadDefaultStrategy)
            val defaultTeamData = loadDefaultTeam()
            val sourceFile = File(
                JPService.getProperty(
                    JPStrategySourceDirectory::class.java
                ).value,
                defaultTeamData!!.strategy + ".kt"
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
    private fun downloadStrategies() {
        try {
            setConnectionState(ConnectionState.DownloadStrategies)
            val jarFile = JPService.getProperty(JPExternalStrategyJar::class.java).value
            val fileByteLenght = `in`!!.readInt()
            val fileBytes = ByteArray(fileByteLenght)
            IOUtils.readFully(`in`, fileBytes)
            FileUtils.writeByteArrayToFile(jarFile, fileBytes)
            revalidate()
        } catch (ex: JPNotAvailableException) {
            throw CouldNotPerformException("Could not download stradegies!", ex)
        } catch (ex: IOException) {
            throw CouldNotPerformException("Could not download stradegies!", ex)
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

        @JvmStatic
        @get:Synchronized
        var instance: PlanetSudoClient? = null
            get() {
                if (field == null) {
                    field = PlanetSudoClient()
                }
                return field
            }
            private set
    }
}
