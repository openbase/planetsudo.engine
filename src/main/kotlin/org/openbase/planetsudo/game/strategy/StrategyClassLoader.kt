/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game.strategy

import org.apache.tomcat.loader.AdaptiveClassLoader
import org.apache.tomcat.loader.ClassRepository
import org.openbase.jps.core.JPService
import org.openbase.jps.exception.JPNotAvailableException
import org.openbase.jul.exception.CouldNotPerformException
import org.openbase.jul.exception.printer.ExceptionPrinter
import org.openbase.planetsudo.jp.JPExternalStrategyJar
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

/**
 *
 * @author Divine Threepwood
 */
object StrategyClassLoader {
    private val LOGGER: Logger = LoggerFactory.getLogger(StrategyClassLoader::class.java)
    private val ADAPTIVE_CLASS_LOADER = AdaptiveClassLoader()

    init {
        revalidate()
    }

    @JvmStatic
    fun revalidate() {
        try {
            val strategyJar = JPService.getProperty(JPExternalStrategyJar::class.java).value

            if (!strategyJar.exists()) {
                LOGGER.warn("Could not revalidate! No jar package found!")
                return
            }
            val cr = ClassRepository(strategyJar, null)
            val classRepositoryVector = Vector<ClassRepository>(1)
            classRepositoryVector.add(cr)
            ADAPTIVE_CLASS_LOADER.parent = AbstractStrategy::class.java.classLoader
            ADAPTIVE_CLASS_LOADER.setRepository(classRepositoryVector)
        } catch (ex: JPNotAvailableException) {
            ExceptionPrinter.printHistory(
                CouldNotPerformException("Could not revalidate strategy classes!", ex),
                LOGGER
            )
        }
    }

    @JvmStatic
    @Throws(CouldNotPerformException::class)
    fun loadStrategy(strategyName: String): Class<out AbstractStrategy> {
        try {
            val clazz =
                ADAPTIVE_CLASS_LOADER.loadClass(AbstractStrategy::class.java.getPackage().name + "." + strategyName)
            LOGGER.info("Load successful " + AbstractStrategy::class.java.getPackage().name + "." + strategyName)
            return clazz as Class<AbstractStrategy>
        } catch (ex: ClassNotFoundException) {
            throw CouldNotPerformException("Could not load Class[$strategyName]external strategy!", ex)
        }
    }
}
