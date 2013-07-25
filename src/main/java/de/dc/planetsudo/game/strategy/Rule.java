/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.game.strategy;

/**
 *
 * @author divine
 */
public abstract class Rule {
	private int priority;
	private String name;

	public Rule(int priority, String name) {
		this.priority = priority;
		this.name = name;
	}

	public int getPriority() {
		return priority;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return getClass().getSimpleName()+"["+name+"]";
	}

	protected abstract boolean constraint();
	protected abstract void action();
}
