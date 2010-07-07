/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.level;

/**
 *
 * @author divine
 */
public class LevelRasterElementNeigbour {
	private LevelRasterElement element;
	private int weight;

	public LevelRasterElementNeigbour(LevelRasterElement element, int weight) {
		this.element = element;
		this.weight = weight;
	}

	public LevelRasterElement getElement() {
		return element;
	}

	public int getWeight() {
		return weight;
	}
}
