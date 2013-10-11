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
public interface AgentInterface {

	void cancelSupport();

	/**
	 * Der Agent &uuml;bergibt die Resource dem Mutterschiff.
	 * Aktionspunkte: 10
	 * Treibstoff: 1
	 */
	void deliverResourceToMothership();

	void deployMarker();

	/**
	 * Der Agent legt seine Mine an seiner aktuellen Position ab.
	 * Aktionspunkte: 1
	 * Treibstoff: 1
	 */
	void deployMine();

	void fightWithAdversaryAgent();

	void fightWithAdversaryMothership();

	/**
	 * Gibt die verf&uuml;gbaren Aktionspunkte wieder.
	 * @return Die Aktionspunkte als ganze Zahl. (z.B. 2051)
	 */
	int getActionPoints();

	/**
	 * Gibt die Richtung des Agenten in Grad wieder.
	 * Rechts = 0°
	 * Runter = 90°
	 * Links = 180°
	 * Hoch = 270°
	 * @return Den Winkel von 0 - 360° als ganze Zahl. (z.B. 128)
	 */
	int getAngle();

	/**
	 * Zeigt den verbliebenen Treibstoff des Agenten an.
	 * @return Den verbliebenen Treibstoff als ganz Zahl.
	 */
	int getFuel();

	/**
	 * Gibt den verbliebenen Treibstoff in Prozent an.
	 * @return Treibstoffwert in Prozent als ganze Zahl. (z.B. 47 bei 47% verbliebenen Treibstoff)
	 */
	int getFuelInPercent();

	int getFuelVolume();

	/**
	 * Gibt den Typ der Resource wieder, welche der Agent ber&uuml;hrt.
	 * @return Den Ressourcenwert
	 */
	Resource.ResourceType getResourceType();

	/**
	 * Gibt die Punkte des Teams wieder.
	 * @return Den Punktestand als ganze Zahl (z.B. 47)
	 */
	int getTeamPoints();

	/**
	 * Der Agent bewegt sich geradeaus.
	 * Aktionspunkte: 4
	 * Treibstoff: 1
	 * Treibstoff wenn der Agent eine Resource tr&auml;gt: 2
	 */
	void go();

	void goLeft(int beta);

	void goRight(int beta);

	void goToMarker();

	/**
	 * Der Agent bewegt sich auf das Mutterschiff zu.
	 * Hierbei wird ein Weg berechnet, welcher auf k&uuml;rzester Distanz zum Mutterschiff f&uuml;hrt.
	 * Um Hindernisse bewegt sich der Agent hierbei automatisch herum.
	 * Aktionspunkte: 2
	 * Treibstoff: 1
	 * Tr&auml;gt der Agent eine Resource verlangsamt er sich ebenfalls und der Treibstoffverbrauch ist 2.
	 */
	void goToMothership();

	/**
	 * Der Agent bewegt sich zur Resource.
	 * Aktionspunkte: 1
	 * Treibstoff: 1
	 */
	void goToResource();

	void goToSupportAgent();

	/**
	 * Gibt zur&uuml;ck, ob der Agent Treibstoff hat.
	 * @return true oder false.
	 */
	boolean hasFuel();

	/**
	 * Gibt zur&uuml;ck, ob der Agent seine Mine noch tr&auml;gt.
	 * @return true oder false.
	 */
	boolean hasMine();

	/**
	 * Gibt an, ob der Agent noch lebt.
	 * @return true oder false.
	 */
	boolean isAlive();

	/**
	 * Gibt zur&uuml;ck, ob der Agent beim Mutterschiff ist.
	 * @return true oder false.
	 */
	boolean isAtMothership();

	/**
	 * Zeigt an, ob der Agent eine Resource Tr&auml;gt.
	 * @return true oder false
	 */
	boolean isCarringResource();

	boolean isCarringResource(final Resource.ResourceType type);

	/**
	 * Gibt an, ob ein Zusammensto&szlig; mit einer Wand bevorsteht.
	 * @return true oder false.
	 */
	boolean isCollisionDetected();

	boolean isCommander();

	/**
	 * Gibt zur&uuml;ck, ob der Agent Bewegungsunf&auml;hig ist. (Zerst&ouml;rt oder ohne Treibstoff)
	 * @return true oder false.
	 */
	boolean isDisabled();

	boolean isFighting();

	boolean isSupportOrdered();

	boolean isGameOverSoon();

	/**
	 * Gibt zur&uuml;ck, ob der Agent eine Resource ber&uuml;hrt und diese somit aufgeben kann.
	 * @return true oder false.
	 */
	boolean isTouchingResource();

	boolean isTouchingResource(final Resource.ResourceType type);

	/**
	 * Gibt an, ob der Agent unter Beschuss steht.
	 * @return true oder false.
	 */
	boolean isUnderAttack();

	/**
	 * Der Agent fordert @param percent Prozent Treibstoff vom Mutterschiff an.
	 * @param percent Der anzufordernde Treibstoffwert in Prozent als ganze Zahl von 0 - 100.
	 * Aktionspunkte: 20 + 2 für jeden getankten Treibstoff.
	 */
	void orderFuel(int percent);

	void orderSupport();

	/**
	 * Der Befehl zum aufnehmen einer Resource wenn der Agent sie anfassen kann.
	 * Aktionspunkte: 10
	 * Treibstoff: 1
	 */
	void pickupResource();

	void releaseResource();

	void repairMothership();

	void searchResources();

	boolean seeAdversaryAgent();

	boolean seeAdversaryMothership();

	boolean seeLostTeamAgent();

	boolean seeMarker();

	/**
	 * Zeigt an ob der Agent eine Resource sehen kann.
	 * @return true oder false.
	 */
	boolean seeResource();

	void spendTeamAgentFuel(int value);

	/**
	 * Der Agent dreht sich um 180 Grad in die entgegengesetzte Richtung.
	 * Aktionspunkte: 1
	 * Treibstoff: 1
	 */
	void turnAround();

	/**
	 * Der Agent dreht sich um Winkel beta nach links.
	 * @param beta Gew&uuml;nschter Winkel in Grad (0 - 360)
	 * Aktionspunkte: 1
	 * Treibstoff: 1
	 */
	void turnLeft(int beta);

	/**
	 * Der Agent dreht sich in eine zuf&auml;llige Richtung.
	 * Aktionspunkte: 1
	 * Treibstoff: 1
	 */
	void turnRandom();

	void turnRandom(final int opening);

	/**
	 * Der Agent dreht sich um Winkel beta nach rechts.
	 * @param beta Gew&uuml;nschter Winkel in Grad (0 - 360)
	 * Aktionspunkte: 1
	 * Treibstoff: 1
	 */
	void turnRight(int beta);

}
