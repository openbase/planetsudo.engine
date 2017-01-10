/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2017 openbase.org
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

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
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

    public TeamData() {
        this("", Color.BLACK, "", new ArrayList<>());
    }
    
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
