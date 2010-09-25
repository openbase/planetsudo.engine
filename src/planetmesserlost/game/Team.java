/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.game;

import java.awt.Color;
import planetmesserlost.game.strategy.AbstractStrategy;
import concepts.Manageable;
import planetmesserlost.level.levelobjects.Mothership;

/**
 *
 * @author divine
 */
public class Team implements Manageable {

	private final int id;
	private final String name;
	private final Color teamColor;
	private Mothership mothership;
	private Class<? extends AbstractStrategy> strategy;


	public Team(int id, String name, Color teamColor, Class<? extends AbstractStrategy> strategy) {
		this.id = id;
		this.name = name;
		this.teamColor = teamColor;
		this.strategy = strategy;
	}

	@Override
	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Mothership getMothership() {
		return mothership;
	}

	public void setMothership(Mothership mothership) {
		this.mothership = mothership;
	}

	public Class<? extends AbstractStrategy> getStrategy() {
		return strategy;
	}

	@ Override
	public String toString() {
		return name;
	}

	public Color getTeamColor() {
		return teamColor;
	}
}
