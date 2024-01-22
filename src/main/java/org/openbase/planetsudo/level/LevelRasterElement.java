/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.level;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2024 openbase.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import org.openbase.planetsudo.level.levelobjects.Agent;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
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
		this.xLevelPosition = (int) levelView.getLevelObject().level.x +(x*size+size/2);
		this.yLevelPosition = (int) levelView.getLevelObject().level.y +(y*size+size/2);
		this.rasterLevelRectangle = new Rectangle2D.Double(xLevelPosition-size/2, yLevelPosition-size/2, size, size);
		Rectangle2D agentBoundsRectangle = new Rectangle2D.Double(xLevelPosition-Agent.AGENT_SIZE, yLevelPosition-Agent.AGENT_SIZE, Agent.AGENT_SIZE*2, Agent.AGENT_SIZE*2);
		this.partOfWall = levelView.getLevelObject().level.containsWall(rasterLevelRectangle);
		this.nextToWall = levelView.getLevelObject().level.containsWall(agentBoundsRectangle);
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
