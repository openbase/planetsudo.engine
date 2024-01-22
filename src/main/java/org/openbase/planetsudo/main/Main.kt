/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.main

import org.openbase.jps.core.JPService
import org.openbase.jps.preset.JPDebugMode
import org.openbase.jps.preset.JPPrefix
import org.openbase.planetsudo.jp.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

/**
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class Main {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)
    private val guiController: GUIController

    init {
        logger.info("Starting Game...")
        this.guiController = GUIController()
        guiController.startGUI()
    }

    companion object {
        /**
         * @param args the command line arguments
         */
        @JvmStatic
        fun main(args: Array<String>) {
            JPService.setApplicationName("PlanetSudo")
            JPService.registerProperty(JPPrefix::class.java, File("."))
            JPService.registerProperty(JPTeamPath::class.java)
            JPService.registerProperty(JPStrategyPath::class.java)
            JPService.registerProperty(JPDebugMode::class.java)
            JPService.registerProperty(JPLevelPath::class.java)
            JPService.registerProperty(JPServerHostname::class.java)
            JPService.registerProperty(JPServerPort::class.java)
            JPService.registerProperty(JPStrategySourceDirectory::class.java)
            JPService.registerProperty(JPExternalStrategyJar::class.java)
            JPService.parseAndExitOnError(args)

            //        System.setProperty("-Dsun.java2d.opengl", "true");
            Main()
        }
    }
}
