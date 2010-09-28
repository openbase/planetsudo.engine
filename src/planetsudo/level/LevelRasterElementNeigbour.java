/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.level;

import logging.Logger;

/**
 *
 * @author divine
 */
public class LevelRasterElementNeigbour {

	public enum NeigbourType {North, West, East, South, NorthEast, SouthEast, SouthWest, NorthWest};

	private final LevelRasterElement element;
	private final NeigbourType type;
	private final int angle;
	private final int weight;

	public LevelRasterElementNeigbour(LevelRasterElement element, int weight, NeigbourType type) {
		this.type = type;
		this.element = element;
		this.weight = weight;
		this.angle = calcAngle();
	}

	public LevelRasterElement getElement() {
		return element;
	}

	public int getWeight() {
		return weight;
	}

	public NeigbourType getType() {
		return type;
	}

	public int getAngle() {
		return angle;
	}

	private int calcAngle() {
		switch(type) {
			case North:
				return 270;
			case NorthEast:
				return 315;
			case East:
				return 0;
			case SouthEast:
				return 45;
			case South:
				return 90;
			case SouthWest:
				return 135;
			case West:
				return 180;
			case NorthWest:
				return 225;
			default:
				Logger.error(this, "Unknown NeigbourType: "+type);
				return 0;
		}
	}
}
