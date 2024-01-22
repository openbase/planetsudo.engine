/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game

import org.openbase.jps.core.JPService
import org.openbase.jps.exception.JPNotAvailableException
import org.openbase.jul.exception.CouldNotPerformException
import org.openbase.jul.exception.printer.ExceptionPrinter
import org.openbase.jul.processing.json.JSonObjectFileProcessor
import org.openbase.planetsudo.deprication.ObjectFileController
import org.openbase.planetsudo.game.strategy.AbstractStrategy
import org.openbase.planetsudo.game.strategy.StrategyClassLoader.loadStrategy
import org.openbase.planetsudo.jp.JPTeamPath
import org.openbase.planetsudo.level.levelobjects.Agent
import org.openbase.planetsudo.level.levelobjects.AgentInterface
import org.openbase.planetsudo.level.levelobjects.AgentMock
import org.openbase.planetsudo.level.levelobjects.Mothership
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Color
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import java.io.File
import java.io.IOException

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class Team(data: TeamData) {
    @JvmField
    var name: String? = null

    @JvmField
    var teamColor: Color? = null

    @JvmField
    var mothership: Mothership? = null

    @JvmField
    var strategy: Class<out AbstractStrategy>
    var points: Int = 0
        private set
    private var members: List<String>
    protected var changes: PropertyChangeSupport? = null

    init {
        try {
            this.name = data.name
            this.teamColor = data.teamColor
            this.strategy = loadStrategy(data.strategy)
            this.points = 0
            this.changes = PropertyChangeSupport(this)
            this.members = data.members
        } catch (ex: CouldNotPerformException) {
            throw org.openbase.jul.exception.InstantiationException(this, ex)
        }
    }

    private fun loadAgentCount(): Int {
        var counter = 0
        try {
            counter = strategy.getConstructor(AgentInterface::class.java).newInstance(AgentMock()).agentCount
        } catch (ex: InstantiationException) {
            logger.error("Could not create strategy instance!", ex)
        } catch (ex: IllegalAccessException) {
            logger.error("Could not access strategy file!", ex)
        }
        if (counter > Mothership.MAX_AGENT_COUNT || counter <= 0) {
            logger.info("Bad agent count! Corrected to " + Mothership.MAX_AGENT_COUNT)
            counter = Mothership.MAX_AGENT_COUNT
        }
        return counter
    }

    fun reset() {
        points = 0
    }

    val agentCount: Int
        get() = loadAgentCount()

    fun addPoint() {
        addPoints(1)
    }

    fun addPoints(points: Int) {
        this.points += points
        changes!!.firePropertyChange(POINT_STATE_CHANGE, null, this.points)
    }

    override fun toString(): String {
        return name!!
    }

    fun getMembers(): List<String?>? {
        return members
    }

    val finalPoints: Int
        get() = mothership!!.agentsAtHomePoints + mothership!!.shieldPoints + points

    fun addPropertyChangeListener(l: PropertyChangeListener) {
        changes!!.addPropertyChangeListener(l)
        logger.debug("Add " + l.javaClass + " as new PropertyChangeListener.")
    }

    fun removePropertyChangeListener(l: PropertyChangeListener) {
        changes!!.removePropertyChangeListener(l)
        logger.debug("Remove PropertyChangeListener " + l.javaClass + ".")
    }

    companion object {
        const val DEFAULT_ID: String = "default"

        const val POINT_STATE_CHANGE: String = "PointStateChange"

        private val logger: Logger = LoggerFactory.getLogger(Team::class.java)

        private val TEAM_DATA_PROCESSOR = JSonObjectFileProcessor(TeamData::class.java)

        @JvmOverloads
        @Throws(CouldNotPerformException::class)
        fun saveDefaultTeam(data: TeamData, force: Boolean = false) {
            save(data, DEFAULT_ID, force)
        }

        @JvmOverloads
        @Throws(CouldNotPerformException::class)
        fun save(data: TeamData, teamName: String? = data.name, force: Boolean = true) {
            try {
                val fileURL =
                    JPService.getProperty(JPTeamPath::class.java).value.absolutePath + "/" + teamName + ".team"

                if (!force && File(fileURL).exists()) {
                    throw CouldNotPerformException("File already exist!")
                }
                TEAM_DATA_PROCESSOR.serialize(data, File(fileURL))
                //            final ObjectFileController<TeamData> fileWriter = new ObjectFileController<TeamData>(fileURL);
//            fileWriter.writeObject(data);
            } catch (ex: JPNotAvailableException) {
                throw CouldNotPerformException("Could not save TeamData[" + data.name + "] as default team!", ex)
            } catch (ex: CouldNotPerformException) {
                throw CouldNotPerformException("Could not save TeamData[" + data.name + "] as default team!", ex)
            }
        }

        @JvmStatic
        fun resetDefaultTeam() {
            try {
                File(JPService.getProperty(JPTeamPath::class.java).value.absolutePath + "/" + DEFAULT_ID + ".team").delete()
            } catch (ex: JPNotAvailableException) {
                ExceptionPrinter.printHistory(CouldNotPerformException("Could not reset default team!", ex), logger)
            }
        }

        @JvmStatic
        @Throws(CouldNotPerformException::class)
        fun loadDefaultTeam(): TeamData? {
            return load(DEFAULT_ID)
        }

        @Throws(CouldNotPerformException::class)
        fun load(teamName: String): TeamData? {
            try {
                return TEAM_DATA_PROCESSOR.deserialize(
                    File(
                        JPService.getProperty(
                            JPTeamPath::class.java
                        ).value.absolutePath + "/" + teamName + ".team"
                    )
                )
            } catch (ex: CouldNotPerformException) {
                ExceptionPrinter.printHistory(
                    CouldNotPerformException(
                        "Could not load team $teamName! Try again with outdated deserializer.",
                        ex
                    ), logger
                )
                try {
                    val fileController = ObjectFileController<TeamData>(
                        JPService.getProperty(
                            JPTeamPath::class.java
                        ).value.absolutePath + "/" + teamName + ".team"
                    )
                    return fileController.readObject()
                } catch (exx: IOException) {
                    throw CouldNotPerformException("Could not load TeamData[$teamName]!", exx)
                } catch (exx: ClassNotFoundException) {
                    throw CouldNotPerformException("Could not load TeamData[$teamName]!", exx)
                } catch (exx: JPNotAvailableException) {
                    throw CouldNotPerformException("Could not load TeamData[$teamName]!", exx)
                }
            } catch (ex: JPNotAvailableException) {
                ExceptionPrinter.printHistory(
                    CouldNotPerformException(
                        "Could not load team $teamName! Try again with outdated deserializer.",
                        ex
                    ), logger
                )
                try {
                    val fileController = ObjectFileController<TeamData>(
                        JPService.getProperty(
                            JPTeamPath::class.java
                        ).value.absolutePath + "/" + teamName + ".team"
                    )
                    return fileController.readObject()
                } catch (exx: IOException) {
                    throw CouldNotPerformException("Could not load TeamData[$teamName]!", exx)
                } catch (exx: ClassNotFoundException) {
                    throw CouldNotPerformException("Could not load TeamData[$teamName]!", exx)
                } catch (exx: JPNotAvailableException) {
                    throw CouldNotPerformException("Could not load TeamData[$teamName]!", exx)
                }
            }
        }

        @JvmStatic
        @Throws(CouldNotPerformException::class)
        fun loadAll(): List<TeamData?> {
            try {
                val teamList: MutableList<TeamData?> = ArrayList()
                val teamFolder = JPService.getProperty(JPTeamPath::class.java).value
                if (!teamFolder.exists()) {
                    throw CouldNotPerformException("Could not find team folder!")
                }

                val teamClassNameList = teamFolder.list() ?: throw CouldNotPerformException("Team folder is empty!!")

                for (teamClassName in teamClassNameList) {
                    if (teamClassName.startsWith(DEFAULT_ID)) {
                        continue
                    }
                    if (teamClassName.endsWith(".team")) {
                        val teamStringID = teamClassName.replace(".team", "")
                        try {
                            teamList.add(load(teamStringID))
                        } catch (ex: Exception) {
                            ExceptionPrinter.printHistory(
                                CouldNotPerformException(
                                    "Could not load team $teamClassName!",
                                    ex
                                ), logger
                            )
                        }
                    }
                }
                return teamList
            } catch (ex: JPNotAvailableException) {
                throw CouldNotPerformException("Could not load all teams.", ex)
            } catch (ex: CouldNotPerformException) {
                throw CouldNotPerformException("Could not load all teams.", ex)
            }
        }
    }
}
