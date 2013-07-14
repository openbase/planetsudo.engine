 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.level;

import data.Point2D;
import java.util.ArrayList;
import planetsudo.level.levelobjects.Resource;

/**
 *
 * @author divine
 */
public interface ResourcePlacement {
	public ArrayList<Resource> getResources(AbstractLevel level);
	public int getResourceCount();
	public Point2D calcRandomLevelPosition(AbstractLevel level);
}
