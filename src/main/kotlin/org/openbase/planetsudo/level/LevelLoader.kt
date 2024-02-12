/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.level

import org.openbase.jul.exception.CouldNotPerformException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class LevelLoader {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val levelMap: TreeMap<String, Class<out AbstractLevel>>

    init {
        instance = this
        levelMap = TreeMap()
        update()
    }

    private fun update() {
        // Read Levelfiles
        // File levelFolder = new File("build/classes/planetsudo/level/save/");
// 		File levelFolder = new File(CommandParameterParser.getAttribute(SetLevelPathCommand.class).getValue());
// 		if(!levelFolder.exists()) {
// 			logger.error("Could not find level folder! "+CommandParameterParser.getAttribute(SetLevelPathCommand.class).getValue()+" does not exist!");
// 			return;
// 		}
// 		String[] levelClassNameList = levelFolder.list();
// 		if(levelClassNameList == null) {
// 			logger.error("Level folder is empty!!");
// 			return;
// 		}

        for (levelClassName in levelClasses) {
            logger.info("Load Level: $levelClassName")
            try {
                levelMap[levelClassName] =
                    javaClass.classLoader.loadClass(javaClass.getPackage().name + ".save." + levelClassName) as Class<out AbstractLevel>
            } catch (ex: ClassNotFoundException) {
                logger.error("Could not find level $levelClassName!")
            }
            logger.debug(levelMap.size.toString() + " Level loaded.")
        }
    }

    val levelNameSet: Set<String>
        get() = levelMap.keys

    fun loadLevel(name: String): AbstractLevel? {
        try {
            return levelMap[name]!!.newInstance()
        } catch (ex: CouldNotPerformException) {
            logger.error("Could not load Level!", ex)
        }
        return null
    }

    companion object {
        private var instance: LevelLoader? = null

        private val levelClasses = arrayOf(
            "SimpleWorld",
            "Atomum",
            "AtomumLight",
            "BackToBack",
            "TwoKings",
            "AgentK",
            "CircleOfLife",
            "LuckyLoop",
            "MarioWorld",
            "QuadratischPraktischBoese",
            "QuadratischPraktischGut",
            "SimpleFight",
            "SimpleWorld",
            "UTurn",
            "WakaWaka",
            "Revolution",
            "FrauenWG",
            "Engpass",
            "JD",
            "Arena",
            "Kreuzung",
            "Entropie",
            "Offensive",
            "OffensiveBackdoor",
            "ChemStation",
            "HeliLandeplatz",
            "DasBoot",
            "Broken",
            "Pentagon",
            "UnbekannterWeg",
            "Maze",
            "LordHelmchen",
            "Hexagon"
        )

        @JvmStatic
        @Synchronized
        fun getInstance(): LevelLoader? {
            if (instance == null) {
                LevelLoader()
            }
            return instance
        }
    }
}
