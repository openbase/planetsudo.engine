 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.level;

import org.openbase.util.data.Point2D;
import java.util.ArrayList;
import org.openbase.planetsudo.level.levelobjects.Resource;

/**
 *
 * @author divine
 */
public interface ResourcePlacement {
	public ArrayList<Resource> getResources(AbstractLevel level);
	public int getResourceCount();
	public Point2D calcRandomLevelPosition(AbstractLevel level);
    public void translateIntoBase(final Point2D base);
}
