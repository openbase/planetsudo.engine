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
package de.dc.planetsudo.level.levelobjects;

/**
 *
 * @author Divine <DivineThreepwood@gmail.com>
 */
public interface MothershipInterface {

	void clearMarker();

	/**
	 * Gibt zur&uuml;ck, wie viele Agenten das Team hat.
	 */
	void getAgentCount();

	/**
	 * Gibt die Schildst&auml;rke des Mutterschiffs wieder.
	 * @return Schildst&auml;rke von 0 - 100 als ganze Zahl.
	 */
	int getShieldForce();

	/**
	 * Gibt zur&uuml;ck, ob das Mutterschiff Treibstoff hat.
	 * @return Einen der Resourcentypen: Unknown, Normal, DoublePoints, ExtremPoint, ExtraAgentFuel, ExtraMothershipFuel, Bomb
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

	boolean isMarkerDeployed();

	/**
	 * Gibt zur&uuml;ck, ob das Mutterschiff maximal besch&auml;digt wurde. (Der Schutzschild is komplett zerst&ouml;rt)
	 * @return true oder false.
	 */
	boolean isMaxDamaged();

	boolean needSomeoneSupport();

}
