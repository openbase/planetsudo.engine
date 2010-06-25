/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.levelobjects;

import data.Point2D;
import planetmesserlost.level.Level;

/**
 *
 * @author divine
 */
public class Resource extends AbstractLevelObject {

	public Resource(int id, Level level, Point2D position) {
		super(id, Resource.class.getSimpleName()+"["+id+"]", level, position, 5, 5, ObjectShape.Oval);
	}

	@Override
	public void reset() {
	}
}
