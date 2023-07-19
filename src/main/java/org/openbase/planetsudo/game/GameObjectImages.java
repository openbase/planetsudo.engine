/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2023 openbase.org
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

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public enum GameObjectImages {

	Default("mothership.png"),
	Mothership("mothership2.png"),
	Agent("agent-HD.png"),
	AgentCommander("commander-HD.png"),
	ResourceNormal("resource1.png"),
	ResourceDoublePoints("resource3.png"),
	ResourceExtremPoint("resource6.png"),
	ResourceExtraAgentFuel("resource5.png"),
	ResourceExtraMothershipFuel("resource7.png"),
	ResourceMine("mine.png"),
	Tower("tower.png"),
	DefenceTowerTop("observation-tower-top.png"),
	ObservationTowerTop("defence-tower-top.png");

	public static final String IMAGE_DIRECTORY = "img";
	public final String imagesURL;

	private GameObjectImages(final String imagesURL) {
		this.imagesURL = IMAGE_DIRECTORY + "/" + imagesURL;
	}
}
