/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.level;

import java.io.File;
import logging.Logger;

/**
 *
 * @author divine
 */
public class LevelLoader {
	public final static String LEVEL_PATH= "planetmesserlost.level";
	private static LevelLoader instance;


	public LevelLoader() {
		instance = this;
		update();
	}

	private void update() {
		// Read Levelfiles
		String[] levelFilenameList = new File("build/classes/planetmesserlost/level/save/").list();
		Class[] levelclasses = new Class[levelFilenameList.length];
		for(int i=0; i<levelFilenameList.length;i++) {
			Logger.info(this, "Found Level: "+levelFilenameList[i]);
//			levelclasses[this.getClass().getPackage().get
		}
//		this.getClass().getResourceAsStream( "kullin_fun.txt" )
	}

	public static synchronized LevelLoader getInstance() {
		if(instance == null) {
			new LevelLoader();
		}
		return instance;
	}




}
