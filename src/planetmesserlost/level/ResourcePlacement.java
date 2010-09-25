 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.level;

import java.util.ArrayList;
import planetmesserlost.level.levelobjects.Resource;

/**
 *
 * @author divine
 */
public interface ResourcePlacement {
	public ArrayList<Resource> getResources(AbstractLevel level);
}
