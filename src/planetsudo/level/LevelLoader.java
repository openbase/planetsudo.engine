/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.level;

import java.io.File;
import java.util.Set;
import java.util.TreeMap;
import logging.Logger;

/**
 *
 * @author divine
 */
public class LevelLoader {
	public final static String LEVEL_PATH= "planetsudo.level";
	private static LevelLoader instance;
	private TreeMap<String, Class<? extends AbstractLevel>> levelMap;

	public LevelLoader() {
		instance = this;
		update();
	}

	private void update() {
		// Read Levelfiles
		String[] levelClassNameList = new File("build/classes/planetsudo/level/save/").list();
		levelMap = new TreeMap<String, Class<? extends AbstractLevel>>();
		for(String levelClassName : levelClassNameList) {
			levelClassName = levelClassName.replace(".class", "");
			Logger.info(this, "Found Level: "+levelClassName);
			try {
				levelMap.put(levelClassName, (Class<? extends AbstractLevel>) getClass().getClassLoader().loadClass(getClass().getPackage().getName()+".save."+levelClassName));
			} catch (ClassNotFoundException ex) {
				Logger.error(this, "Could not load level binary file!", ex);
			}
			Logger.info(this, levelMap.size()+" Level loaded.");
		}
	}

	public Set<String> getLevelNameSet() {
		return levelMap.keySet();
	}

	public AbstractLevel loadLevel(String name) {
		try {
			return levelMap.get(name).newInstance();
		} catch (Exception ex) {
			Logger.error(this, "Could not load Level!", ex);
		}
		return null;
	}

	public static synchronized LevelLoader getInstance() {
		if(instance == null) {
			new LevelLoader();
		}
		return instance;
	}




}
