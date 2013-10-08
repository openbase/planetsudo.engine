/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.game.strategy;

import de.dc.planetsudo.main.command.SetExternalStrategyJar;
import de.dc.util.exceptions.CouldNotPerformException;
import de.dc.util.logging.Logger;
import de.unibi.agai.clparser.CLParser;
import java.io.File;
import java.util.Vector;
import org.apache.tomcat.loader.AdaptiveClassLoader;
import org.apache.tomcat.loader.ClassRepository;

/**
 *
 * @author Divine <DivineThreepwood@gmail.com>
 */
public class StrategyClassLoader {

	private static File strategyJar = CLParser.getAttribute(SetExternalStrategyJar.class).getValue();

	private static AdaptiveClassLoader adaptiveClassLoader;

	static {
		adaptiveClassLoader = new AdaptiveClassLoader();
		revalidate();
	}

	public static void revalidate() {
		if (!strategyJar.exists()) {
			Logger.warn(StrategyClassLoader.class, "Could not revalidate! No jar package found!");
			return;
		}
		ClassRepository cr = new ClassRepository(strategyJar, null);
		Vector<ClassRepository> classRepositoryVector = new Vector<ClassRepository>(1);
		classRepositoryVector.add(cr);
		adaptiveClassLoader.setParent(AbstractStrategy.class.getClassLoader());
		adaptiveClassLoader.setRepository(classRepositoryVector);
	}

	public static Class<? extends AbstractStrategy> loadStrategy(final String strategyName) throws CouldNotPerformException {
		try {
			Class clazz = null;
			try {
				clazz = adaptiveClassLoader.loadClass(AbstractStrategy.class.getPackage().getName() + "." + strategyName);
				Logger.info(StrategyClassLoader.class, "Load successful " + AbstractStrategy.class.getPackage().getName() + "." + strategyName);
			} catch (Exception ex) {
				Logger.error(StrategyClassLoader.class, "Could not load " + AbstractStrategy.class.getPackage().getName() + "." + strategyName, ex);
			}
			return (Class<AbstractStrategy>) clazz;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new CouldNotPerformException("Could not load Class[" + strategyName + "]external strategy!", ex);
		}
	}
}
