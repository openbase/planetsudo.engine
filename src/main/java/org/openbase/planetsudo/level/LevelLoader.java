/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.level;

import java.util.Set;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author divine
 */
public class LevelLoader {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
	private static LevelLoader instance;
	private TreeMap<String, Class<? extends AbstractLevel>> levelMap;

	static {
		//LevelLoader.class.
	}
	private final static String[] levelClasses = {"SimpleWorld",
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
                "OffensiveBackdoor"
	};

	public LevelLoader() {
		instance = this;
		levelMap = new TreeMap<String, Class<? extends AbstractLevel>>();
		update();
	}

	private void update() {
		// Read Levelfiles
		//File levelFolder = new File("build/classes/planetsudo/level/save/");
//		File levelFolder = new File(CommandParameterParser.getAttribute(SetLevelPathCommand.class).getValue());
//		if(!levelFolder.exists()) {
//			logger.error("Could not find level folder! "+CommandParameterParser.getAttribute(SetLevelPathCommand.class).getValue()+" does not exist!");
//			return;
//		}
//		String[] levelClassNameList = levelFolder.list();
//		if(levelClassNameList == null) {
//			logger.error("Level folder is empty!!");
//			return;
//		}

		for (String levelClassName : levelClasses) {
			logger.info("Load Level: " + levelClassName);
			try {
				levelMap.put(levelClassName, (Class<? extends AbstractLevel>) getClass().getClassLoader().loadClass(getClass().getPackage().getName() + ".save." + levelClassName));
			} catch (ClassNotFoundException ex) {
				logger.error("Could not find level " + levelClassName + "!");
			}
			logger.debug(levelMap.size() + " Level loaded.");
		}
	}

	public Set<String> getLevelNameSet() {
		return levelMap.keySet();
	}

	public AbstractLevel loadLevel(String name) {
		try {
			return levelMap.get(name).newInstance();
		} catch (Exception ex) {
			logger.error("Could not load Level!", ex);
		}
		return null;
	}

	public static synchronized LevelLoader getInstance() {
		if (instance == null) {
			new LevelLoader();
		}
		return instance;
	}
}
