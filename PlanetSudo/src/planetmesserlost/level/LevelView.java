/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.level;

/**
 *
 * @author divine
 */
public class LevelView {
	private int[][] levelRepresentation;

	public LevelView(Level level) {
		level.getLevelBorderPolygon().getBounds2D().getWidth();
		levelRepresentation = new int [(int)level.getLevelBorderPolygon().getBounds2D().getWidth()]
									  [(int)level.getLevelBorderPolygon().getBounds2D().getHeight()];
	///	initLevelRepresentation();
	}

//	public initLevelRepresentation() {
//		for()
//	}
}
