/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.level.levelobjects;

import de.dc.util.exceptions.CouldNotPerformException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author divine
 */
public class AgentController {

	private final Agent agent;
	private Mothership mothership;
	public final MothershipController mothershipController;

	public AgentController(Agent agent) {
		this.agent = agent;
		this.mothership = agent.getMothership();
		this.mothershipController = new MothershipController(mothership, agent);
	}

	public MothershipController getMothership() {
		return mothershipController;
	}

	/**
	 * Gibt die Richtung des Agenten in Grad wieder.
	 * Rechts = 0°
	 * Runter = 90°
	 * Links = 180°
	 * Hoch = 270°
	 * @return Den Winkel von 0 - 360° als ganze Zahl. (z.B. 128)
	 */
	public int getDirection() {
		return agent.getDirection().getAngle();
	}

	/**
	 * Gibt die verf&uuml;gbaren Aktionspunkte wieder.
	 * @return Die Aktionspunkte als ganze Zahl. (z.B. 2051)
	 */
	public int getActionPoints() {
		return agent.getActionPoints().getActionPoints();
	}

	/**
	 * Zeigt den verbliebenen Treibstoff des Agenten an.
	 * @return Den verbliebenen Treibstoff als ganz Zahl.
	 */
	public int getFuel() {
		return agent.getFuel();
	}

	/**
	* Gibt den verbliebenen Treibstoff in Prozent an.
	* @return Treibstoffwert in Prozent als ganze Zahl. (z.B. 47 bei 47% verbliebenen Treibstoff)
	*/
	public int getFuelInPercent() {
		return agent.getFuelInPercent();
	}

	/**
	 * Gibt zur&uuml;ck, ob der Agent Treibstoff hat.
	 * @return true oder false.
	 */
	public boolean hasFuel() {
		return agent.hasFuel();
	}

	/**
	 * Gibt zur&uuml;ck, ob der Agent seine Mine noch tr&auml;gt.
	 * @return true oder false.
	 */
	public boolean hasMine() {
		return agent.hasMine();
	}

	/**
	 * Der Agent legt seine Mine an seiner aktuellen Position ab.
	 * Aktionspunkte: 1
	 * Treibstoff: 1
	 */
	public void placeMine() {
		agent.placeMine();
	}

	/**
	 * Gibt zur&uuml;ck, ob der Agent Bewegungsunf&auml;hig ist. (Zerst&ouml;rt oder ohne Treibstoff)
	 * @return true oder false.
	 */
	public boolean isDisabled() {
		return agent.isDisabled();
	}

	/**
	 * Zeigt an, ob der Agent eine Resource Tr&auml;gt.
	 * @return true oder false
	 */
	public boolean isCarringResource() {
		return agent.isCarringResource();
	}

	/**
	 * Gibt an, ob der Agent noch lebt.
	 * @return true oder false.
	 */
	public boolean isAlive() {
		return agent.isAlive();
	}

	/**
	 * Gibt an, ob ein Zusammensto&szlig; mit einer Wand bevorsteht.
	 * @return true oder false.
	 */
	public boolean collisionDetected() {
		return agent.collisionDetected();
	}

	/**
	 * Der Agent bewegt sich geradeaus.
	 * Aktionspunkte: 4
	 * Treibstoff: 1
	 * Treibstoff wenn der Agent eine Resource tr&auml;gt: 2
	 */
	public void goStraightAhead() {
		agent.goStraightAhead();
	}

	/**
	 * Der Agent dreht sich um 180 Grad in die entgegengesetzte Richtung.
	 * Aktionspunkte: 1
	 * Treibstoff: 1
	 */
	public void turnAround() {
		agent.turnAround();
	}

	/**
	 * Der Agent dreht sich um Winkel beta nach links.
	 * @param beta Gew&uuml;nschter Winkel in Grad (0 - 360)
	 * Aktionspunkte: 1
	 * Treibstoff: 1
	 */
	public void turnLeft(int beta) {
		agent.turnLeft(beta);
	}

	/**
	 * Der Agent dreht sich um Winkel beta nach rechts.
	 * @param beta Gew&uuml;nschter Winkel in Grad (0 - 360)
	 * Aktionspunkte: 1
	 * Treibstoff: 1
	 */
	public void turnRight(int beta) {
		agent.turnRight(beta);
	}

	/**
	 * Der Agent dreht sich in eine zuf&auml;llige Richtung.
	 * Aktionspunkte: 1
	 * Treibstoff: 1
	 */
	public void turnRandom() {
		agent.turnRandom();
	}

	/**
	 * Der Agent bewegt sich auf das Mutterschiff zu.
	 * Hierbei wird ein Weg berechnet, welcher auf k&uuml;rzester Distanz zum Mutterschiff f&uuml;hrt.
	 * Um Hindernisse bewegt sich der Agent hierbei automatisch herum.
	 * Aktionspunkte: 2
	 * Treibstoff: 1
	 * Tr&auml;gt der Agent eine Resource verlangsamt er sich ebenfalls und der Treibstoffverbrauch ist 2.
	 */
	public void moveOneStepInTheMothershipDirection() {
		agent.moveOneStepInTheMothershipDirection();
	}

	/**
	 * Gibt zur&uuml;ck, ob der Agent beim Mutterschiff ist.
	 * @return true oder false.
	 */
	public boolean isAtMothership() {
		return agent.isAtMothership();
	}

	/**
	 * Der Agent fordert @param percent Prozent Treibstoff vom Mutterschiff an.
	 * @param percent Der anzufordernde Treibstoffwert in Prozent als ganze Zahl von 0 - 100.
	 * Aktionspunkte: 20 + 2 für jeden getankten Treibstoff.
	 */
	public void orderFuel(int percent) {
		agent.orderFuel(percent);
	}

	/**
	 * Zeigt an ob der Agent eine Resource sehen kann.
	 * @return true oder false.
	 */
	public boolean seeResource() {
		return agent.seeResource();
	}

	/**
	 * Der Agent bewegt sich zur Resource.
	 * Aktionspunkte: 1
	 * Treibstoff: 1
	 */
	public void goToResource() {
		agent.goToResource();
	}

	/**
	 * Der Agent &uuml;bergibt die Resource dem Mutterschiff.
	 * Aktionspunkte: 10
	 * Treibstoff: 1
	 */
	public void deliverResourceToMothership() {
		agent.deliverResourceToMothership();
	}

	/**
	 * Gibt zur&uuml;ck, ob der Agent eine Resource ber&uuml;hrt und diese somit aufgeben kann.
	 * @return true oder false.
	 */
	public boolean touchResource() {
		return agent.touchResource();
	}

	/**
	 * Gibt die Punkte des Teams wieder.
	 * @return Den Punktestand als ganze Zahl (z.B. 47)
	 */
	public int getTeamPoints() {
		return agent.getTeam().getPoints();
	}

	/**
	 * Gibt den Typ der Resource wieder, welche der Agent ber&uuml;hrt.
	 * @return Den Ressourcenwert
	 */
	public Resource.ResourceType touchResourceType() {
		return agent.touchResourceType();
	}

	public boolean touchResourceType(final Resource.ResourceType type) {
		return agent.touchResourceType(type);
	}

	/**
	 * Gibt an, ob der Agent unter Beschuss steht.
	 * @return true oder false.
	 */
	public boolean isUnderAttack() {
		return agent.isUnderAttack();
	}

	/**
	 * Der Befehl zum aufnehmen einer Resource wenn der Agent sie anfassen kann.
	 * Aktionspunkte: 10
	 * Treibstoff: 1
	 */
	public void pickupResource() {
		agent.pickupResource();
	}

	/**
	 * Zeigt an, ob der Agent einen feindlichen Agenten sieht.
	 * @return true oder false.
	 */
	public boolean seeAdversaryAgent() {
		return agent.seeAdversaryAgent();
	}

	/**
	 * Zeigt an, ob der Agent das feindliche Mutterschiff sieht.
	 * @return true oder false.
	 */
	public boolean seeAdversaryMothership() {
		return agent.seeAdversaryMothership();
	}

	/**
	 * Der Befehl zum bek&auml;mpfen eines feindlichen Agenten.
	 * Aktionspunkte: 20
	 * Treibstoff: 1
	 */
	public void fightWithAdversaryAgent() {
		agent.fightWithAdversaryAgent();
	}

	/**
	 * Der Befehl zum Angreifen des feindlichen Mutterschiffs.
	 * Aktionspunkte: 10
	 * Treibstoff: 1
	 */
	public void fightWithAdversaryMothership() {
		agent.fightWithAdversaryMothership();
	}

	/**
	 * Abfrage, ob der Agent sich im Kampf befindet oder nicht.
	 * @return true oder false.
	 */
	public boolean isFighting() {
		return agent.isFighting();
	}

	/**
	 * Zeigt an, ob sich in der N&auml;he des Agenten ein Teammitglied ohne Treibstoff befindet.
	 * @return true oder false.
	 */
	public boolean seeLostTeamAgent() {
		return agent.seeLostTeamAgent();
	}

	/**
	 * Der Agent spendet einem Teammitglied @param value Treibstoff.
	 * Ein Agent ist Bewegungsunf&auml;hig, solange er Treibstoff spendet!
	 * Aktionspunkte: value*2
	 * Treibstoff: 1 + value
	 * @param value Der zu spendende Treibstoff.
	 */
	public void spendFuelTeamAgent(int value) {
		agent.spendFuelTeamAgent(value);
	}

	/**
	 * Der Befehl zum reparieren des eigenen Mutterschiffs. Hierzu muss sich der Agent am Mutterschiff befinden!
	 * Aktionspunkte: 50
	 * Treibstoff: 1
	 */
	public void repaireMothership() {
		agent.repaireMothership();
	}

	/**
	 * Wenn der Agent eine Resource trägt, lässt er sie wieder fallen.
	 */
	public void releaseResource() {
		agent.releaseResource();
	}

	public void cancelSupport() {
		agent.cancelSupport();
	}

	public void orderSupport() {
		agent.orderSupport();
	}
	
	public boolean supportOrdered() {
		return agent.needSupport();
	}//TODO

	public void goToSuppordAgent() {
		agent.goToSuppordAgent();
	}

	public void placeMarker() {
		agent.placeMarker();
	}

	public void goToMarker() {
		agent.goToMarker();
	}

	public boolean isCommander() {
		return agent.isCommander();
	}

	public boolean seeMarker() {
		return agent.seeMarker();
	}

	public int getFuelVolume() {
		return agent.getFuelVolume();
	}
}
