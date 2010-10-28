/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.level;

import configuration.parameter.CommandParameterParser;
import java.io.File;
import java.util.Set;
import java.util.TreeMap;
import logging.Logger;
import planetsudo.main.clc.SetLevelPathCommand;
import planetsudo.view.level.LevelPanel;

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
		levelMap = new TreeMap<String, Class<? extends AbstractLevel>>();
		update();
	}

	private void update() {
		// Read Levelfiles
		//File levelFolder = new File("build/classes/planetsudo/level/save/");
		File levelFolder = new File(CommandParameterParser.getAttribute(SetLevelPathCommand.class).getValue());
		if(!levelFolder.exists()) {
			Logger.error(this, "Could not find level folder! "+CommandParameterParser.getAttribute(SetLevelPathCommand.class).getValue()+" does not exist!");
			return;
		}
		String[] levelClassNameList = levelFolder.list();
		if(levelClassNameList == null) {
			Logger.error(this, "Level folder is empty!!");
			return;
		}




		for(String levelClassName : levelClassNameList) {
			if(levelClassName.endsWith(".class")) {
				levelClassName = levelClassName.replace(".class", "");
				Logger.info(this, "Found Level: "+levelClassName);
				try {
					levelMap.put(levelClassName, (Class<? extends AbstractLevel>) getClass().getClassLoader().loadClass(getClass().getPackage().getName()+".save."+levelClassName));
				} catch (ClassNotFoundException ex) {
					Logger.error(this, "Could not load level binary file!", ex);
				}
				Logger.info(this, levelMap.size()+" Level loaded.");
			} else {
				Logger.info(this, "Ignore file "+levelClassName+" in level folder.");
			}
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
