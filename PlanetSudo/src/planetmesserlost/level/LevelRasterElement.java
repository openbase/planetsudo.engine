/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.level;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import planetmesserlost.levelobjects.Agent;

/**
 *
 * @author divine
 */
public class LevelRasterElement implements Comparable<LevelRasterElement> {
	private final int index, x, y;
	private final int xLevelPosition;
	private final int yLevelPosition;
	private final Rectangle2D rasterLevelRectangle;
	private final int size;
	private final boolean partOfWall;
	private final boolean nextToWall;
	private final LevelView levelView;
	private final ArrayList<LevelRasterElementNeigbour> neigbours;
	
	private boolean visited;
	private Integer distance;

	public LevelRasterElement(int index, LevelView levelView) {
		this.index = index;
		this.levelView = levelView;
		this.x = index % levelView.getWidth();
		this.y = index / levelView.getWidth();
		this.size = levelView.getRasterSize();
		this.xLevelPosition = (int) levelView.getAgent().getMothership().getLevel().getX()+(x*size+size/2);
		this.yLevelPosition = (int) levelView.getAgent().getMothership().getLevel().getY()+(y*size+size/2);
		this.rasterLevelRectangle = new Rectangle2D.Double(xLevelPosition-size/2, yLevelPosition-size/2, size, size);
		Rectangle2D agentBoundsRectangle = new Rectangle2D.Double(xLevelPosition-Agent.AGENT_SIZE, yLevelPosition-Agent.AGENT_SIZE, Agent.AGENT_SIZE*2, Agent.AGENT_SIZE*2);
		this.partOfWall = !levelView.getAgent().getMothership().getLevel().getLevelBorderPolygon().contains(rasterLevelRectangle);
		this.nextToWall = !levelView.getAgent().getMothership().getLevel().getLevelBorderPolygon().contains(agentBoundsRectangle);
		this.visited = false;
		this.distance = Integer.MAX_VALUE;
		this.neigbours = new ArrayList<LevelRasterElementNeigbour>();
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

	public boolean isNextToWall() {
		return nextToWall;
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
		int weight = 0;
		// North
		if(y!=0) {
			weight = levelView.get(x, y-1).isNextToWall() ? 50 : 2;
			//weight += levelView.get(x, y-1).isPartOfWall() ? Integer.MAX_VALUE : 0;
			addNeigbour(new LevelRasterElementNeigbour(levelView.get(x, y-1), weight, LevelRasterElementNeigbour.NeigbourType.North));
		}

		// NorthEast
		if(x!=levelView.getWidth()-1 && y!=0) {
			weight = levelView.get(x+1, y-1).isNextToWall() ? 75 : 3;
			//weight += levelView.get(x, y-1).isPartOfWall() ? Integer.MAX_VALUE : 0;
			addNeigbour(new LevelRasterElementNeigbour(levelView.get(x+1, y-1), weight, LevelRasterElementNeigbour.NeigbourType.NorthEast));
		}

		// East
		if(x!=levelView.getWidth()-1) {
			weight = levelView.get(x+1, y).isNextToWall() ? 50 : 2;
			//weight += levelView.get(x+1, y).isPartOfWall() ? Integer.MAX_VALUE : 0;
			addNeigbour(new LevelRasterElementNeigbour(levelView.get(x+1, y), weight, LevelRasterElementNeigbour.NeigbourType.East));
		}

		// SouthEast
		if(x!=levelView.getWidth()-1 && y!=levelView.getHeight()-1) {
			weight = levelView.get(x+1, y+1).isNextToWall() ? 75 : 3;
			//weight += levelView.get(x+1, y).isPartOfWall() ? Integer.MAX_VALUE : 0;
			addNeigbour(new LevelRasterElementNeigbour(levelView.get(x+1, y+1), weight, LevelRasterElementNeigbour.NeigbourType.SouthEast));
		}
		// South
		if(y!=levelView.getHeight()-1) {
			weight = levelView.get(x, y+1).isNextToWall() ? 50 : 2;
			//weight += levelView.get(x, y+1).isPartOfWall() ? Integer.MAX_VALUE : 0;
			addNeigbour(new LevelRasterElementNeigbour(levelView.get(x, y+1), weight, LevelRasterElementNeigbour.NeigbourType.South));
		}
		// SouthWest
		if(x!=0 && y!=levelView.getHeight()-1) {
			weight = levelView.get(x-1, y+1).isNextToWall() ? 75 : 3;
			//weight += levelView.get(x, y+1).isPartOfWall() ? Integer.MAX_VALUE : 0;
			addNeigbour(new LevelRasterElementNeigbour(levelView.get(x-1, y+1), weight, LevelRasterElementNeigbour.NeigbourType.SouthWest));
		}
		// West
		if(x!=0) {
			weight = levelView.get(x-1, y).isNextToWall() ? 50 : 2;
			//weight += levelView.get(x-1, y).isPartOfWall() ? Integer.MAX_VALUE : 0;
			addNeigbour(new LevelRasterElementNeigbour(levelView.get(x-1, y), weight, LevelRasterElementNeigbour.NeigbourType.West));
		}
		// NorthWest
		if(x!=0 && y!=0) {
			weight = levelView.get(x-1, y-1).isNextToWall() ? 75 : 3;
			//weight += levelView.get(x-1, y).isPartOfWall() ? Integer.MAX_VALUE : 0;
			addNeigbour(new LevelRasterElementNeigbour(levelView.get(x-1, y-1), weight, LevelRasterElementNeigbour.NeigbourType.NorthWest));
		}
	}

	private boolean addNeigbour(LevelRasterElementNeigbour neigbour) {
		if(neigbour.getElement().isPartOfWall()) {
			return false;
		}
		return neigbours.add(neigbour);
	}

	public Collection<LevelRasterElementNeigbour> getNeigbours() {
		return neigbours;
	}

	@Override
	public String toString() {
		return LevelRasterElement.class.getSimpleName()+"[Distance:"+distance+"|visited:"+visited+"|neigbours:"+neigbours.size()+"]";
	}
}