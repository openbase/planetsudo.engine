/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game;

import java.awt.Color;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Divine Threepwood
 */
public class TeamData implements Serializable {

	public final static long serialVersionUID = 100l;

	private final String name;
	private final Color teamColor;
	private final String strategy;
	private final List<String> members;

	public TeamData(final String name, final Color teamColor, final String strategy, final List<String> members) {
		this.name = name;
		this.teamColor = teamColor;
		this.strategy = strategy;
		this.members = members;
	}

	public String getName() {
		return name;
	}

	public Color getTeamColor() {
		return teamColor;
	}

	public String getStrategy() {
		return strategy;
	}

	public List<String> getMembers() {
		return Collections.unmodifiableList(members);
	}

	@Override
	public String toString() {
		return name;
	}
}
