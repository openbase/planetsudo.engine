/*
 * Copyright (C) 2013 DivineCorporation
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openbase.planetsudo.level.levelobjects;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2016 openbase.org
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
 * @author Divine Threepwood
 */
public interface MothershipInterface {

	/**
	 * Löscht den Marker sofern dieser gesetzt wurde.
	 */
	void clearMarker();

	/**
	 * Gibt zur&uuml;ck, wie viele Agenten das Team hat.
	 * @return Anzahl der Agenten
	 */
	int getAgentCount();

	/**
	 * Gibt die Schildst&auml;rke des Mutterschiffs wieder.
	 * @return Schildst&auml;rke von 0 - 100 als ganze Zahl.
	 */
	int getShieldForce();

	/**
	 * Gibt zur&uuml;ck, ob das Mutterschiff Treibstoff hat.
	 * @return true oder false.
	 */
	boolean hasFuel();

	/**
	 * Gibt zur&uuml;ck, ob das Mutterschiff brennt und somit Treibstoff verliert.
	 * @return true oder false.
	 */
	boolean isBurning();

	/**
	 * Gibt zur&uuml;ck, ob das Mutterschiff und somit der Schutzschild besch&auml;digt ist.
	 * @return true oder false.
	 */
	boolean isDamaged();

	/**
	 * Gibt zurück ob der Marker gesetzt wurde.
	 * @return true oder false.
	 */
	boolean isMarkerDeployed();

	/**
	 * Gibt zur&uuml;ck, ob das Mutterschiff maximal besch&auml;digt wurde. (Das Schutzschild is komplett zerst&ouml;rt)
	 * @return true oder false.
	 */
	boolean isMaxDamaged();

	/**
	 * Gibt zurück ob ein Agent des eigenen Teams Hilfe benötigt.
	 * @return true oder false.
	 */
	boolean needSomeoneSupport();

}
