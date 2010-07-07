/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.level;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author divine
 */
public class LevelRasterElement implements Comparable<LevelRasterElement> {
	private final int index, x, y;
	private final int xLevelPosition;
	private final int yLevelPosition;
	Rectangle2D rasterLevelRectangle;
	//private final Point2D levelRasterCenterPoint;
	private final int size;
	private final boolean partOfWall;
	private boolean visited;
	private Integer distance;
	private LevelView levelView;
	private ArrayList<LevelRasterElementNeigbour> neigbours;

	public LevelRasterElement(int index, LevelView levelView) {
		this.index = index;
		this.levelView = levelView;
		this.x = index % levelView.getWidth();
		this.y = index / levelView.getWidth();
		this.size = levelView.getRasterSize();
		this.xLevelPosition = x*size+size/2;
		this.yLevelPosition = y*size+size/2;
		this.rasterLevelRectangle = new Rectangle2D.Double(xLevelPosition-size/2, yLevelPosition-size/2, size, size);
		this.partOfWall = !levelView.getLevel().getLevelBorderPolygon().intersects(rasterLevelRectangle);
		this.visited = false;
		this.distance = Integer.MAX_VALUE;
		this.neigbours = new ArrayList<LevelRasterElementNeigbour>() {
			@Override
			public boolean add(LevelRasterElementNeigbour neigbour) {
				if(neigbour.getElement().isPartOfWall()) {
					return false;
				}
				return super.add(neigbour);
			}
		};
	}

	protected void reset(LevelRasterElement source) {
		visited = false;
		if(source.index == index) {	
			distance = 0;
			return;
		}
		distance = Integer.MAX_VALUE;
	}

	public int getDistance() {
		return distance;
	}

	public int getIndex() {
		return index;
	}

	public boolean isPartOfWall() {
		return partOfWall;
	}

	public Rectangle2D getRasterLevelRectangle() {
		return rasterLevelRectangle;
	}

	public int getSize() {
		return size;
	}

	public boolean isVisited() {
		return visited;
	}

	public int getX() {
		return x;
	}

	public int getxLevelPosition() {
		return xLevelPosition;
	}

	public int getY() {
		return y;
	}

	public int getyLevelPosition() {
		return yLevelPosition;
	}

	public void setDistance(int distance) {
		this.distance = Math.min(this.distance, distance);
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	@Override
	public int compareTo(LevelRasterElement element) {
		return distance.compareTo(element.distance);
	}

	protected void calculateNeigbours() {
		// NORTH
		if(y!=0) {
			neigbours.add(new LevelRasterElementNeigbour(levelView.get(x, y-1), 2));
		}
		// EAST
		if(x!=levelView.getWidth()-1) {
			neigbours.add(new LevelRasterElementNeigbour(levelView.get(x+1, y), 2));
		}
		// SOUTH
		if(y!=levelView.getHeight()-1) {
			neigbours.add(new LevelRasterElementNeigbour(levelView.get(x, y+1), 2));
		}
		// WEST
		if(x!=0) {
			neigbours.add(new LevelRasterElementNeigbour(levelView.get(x-1, y), 2));
		}
	}

	public Collection<LevelRasterElementNeigbour> getNeigbours() {
		return neigbours;
	}
}
