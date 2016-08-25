/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game.strategy;

import org.openbase.planetsudo.jp.JPExternalStrategyJar;
import java.io.File;
import java.util.Vector;
import org.apache.tomcat.loader.AdaptiveClassLoader;
import org.apache.tomcat.loader.ClassRepository;
import org.openbase.jps.core.JPService;
import org.openbase.jps.exception.JPNotAvailableException;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Divine Threepwood
 */
public class StrategyClassLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(StrategyClassLoader.class);
    private static final AdaptiveClassLoader ADAPTIVE_CLASS_LOADER;

    static {
        ADAPTIVE_CLASS_LOADER = new AdaptiveClassLoader();
        revalidate();
    }

    public static void revalidate() {
        try {
            File strategyJar = JPService.getProperty(JPExternalStrategyJar.class).getValue();

            if (!strategyJar.exists()) {
                LOGGER.warn("Could not revalidate! No jar package found!");
                return;
            }
            ClassRepository cr = new ClassRepository(strategyJar, null);
            Vector<ClassRepository> classRepositoryVector = new Vector<ClassRepository>(1);
            classRepositoryVector.add(cr);
            ADAPTIVE_CLASS_LOADER.setParent(AbstractStrategy.class.getClassLoader());
            ADAPTIVE_CLASS_LOADER.setRepository(classRepositoryVector);
        } catch (JPNotAvailableException ex) {
            ExceptionPrinter.printHistory(new CouldNotPerformException("Could not revalidate strategy classes!", ex), LOGGER);
        }
    }

    public static Class<? extends AbstractStrategy> loadStrategy(final String strategyName) throws CouldNotPerformException {
        try {
            Class clazz;
            clazz = ADAPTIVE_CLASS_LOADER.loadClass(AbstractStrategy.class.getPackage().getName() + "." + strategyName);
            LOGGER.info("Load successful " + AbstractStrategy.class.getPackage().getName() + "." + strategyName);
            return (Class<AbstractStrategy>) clazz;
        } catch (Exception ex) {
            throw new CouldNotPerformException("Could not load Class[" + strategyName + "]external strategy!", ex);
        }
    }
}
