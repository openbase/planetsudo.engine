/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.game;

import planetmesserlost.game.strategy.AbstractStrategy;
import concepts.Manageable;

/**
 *
 * @author divine
 */
public class Team implements Manageable {

	private final int id;
	private final String name;
	private Class<? extends AbstractStrategy> strategy;

	public Team(int id, String name, Class<? extends AbstractStrategy> strategy) {
		this.id = id;
		this.name = name;
		this.strategy = strategy;
	}

	@Override
	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Class<? extends AbstractStrategy> getStrategy() {
		return strategy;
	}

	@ Override
	public String toString() {
		return name;
	}


}
