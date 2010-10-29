/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.level;

import java.util.Set;
import java.util.TreeMap;
import logging.Logger;

/**
 *
 * @author divine
 */
public class LevelLoader {
	private static LevelLoader instance;
	private TreeMap<String, Class<? extends AbstractLevel>> levelMap;
	private final static String[] levelClasses = {	"SimpleWorld",
													"AgentK",
													"CircleOfLife",
													"LuckyLoop",
													"MarioWorld",
													"QuadratischPraktischBoese",
													"QuadratischPraktischGut",
													"SimpleFight", 
													"SimpleWorld",
													"UTurn",
													"WakaWaka"};

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
//			Logger.error(this, "Could not find level folder! "+CommandParameterParser.getAttribute(SetLevelPathCommand.class).getValue()+" does not exist!");
//			return;
//		}
//		String[] levelClassNameList = levelFolder.list();
//		if(levelClassNameList == null) {
//			Logger.error(this, "Level folder is empty!!");
//			return;
//		}
		
		for(String levelClassName : levelClasses) {
			Logger.info(this, "Load Level: "+levelClassName);
			try {
				levelMap.put(levelClassName, (Class<? extends AbstractLevel>) getClass().getClassLoader().loadClass(getClass().getPackage().getName()+".save."+levelClassName));
			} catch (ClassNotFoundException ex) {
				Logger.error(this, "Could not find level "+levelClassName+"!");
			}
			Logger.debug(this, levelMap.size()+" Level loaded.");
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
