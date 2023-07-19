/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game.strategy;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2023 openbase.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

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
