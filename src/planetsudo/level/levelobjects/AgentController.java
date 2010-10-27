/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.level.levelobjects;

import data.Direction2D;

/**
 *
 * @author divine
 */
public class AgentController {

	private final Agent agent;
	private final MothershipController mothershipController;

	public AgentController(Agent agent) {
		this.agent = agent;
		this.mothershipController = new MothershipController(agent.getMothership());
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
	 * @return
	 */
	public int getDirection() {
		return agent.getDirection().getAngle();
	}

	/**
	 * Gibt die verfügbaren Aktionspunkte wieder.
	 * @return
	 */
	public int getActionPoints() {
		return agent.getActionPoints().getActionPoints();
	}

	/**
	 * Zeigt den verbliebenen Treibstoff des Agenten an.
	 * @return
	 */
	public int getFuel() {
		return agent.getFuel();
	}

	/**
	* Gibt den verbliebenen Treibstoff in Prozent an.
	* @return
	*/
	public int getFuelInPercent() {
		return agent.getFuelInPercent();
	}

	/**
	 * Gibt zurück, ob der Agent Treibstoff hat.
	 * @return
	 */
	public boolean hasFuel() {
		return agent.hasFuel();
	}

	/**
	 * Gibt zurück, ob der Agent seine Mine noch trägt.
	 * @return
	 */
	public boolean hasMine() {
		return agent.hasMine();
	}

	/**
	 * Der Agent legt seine Mine an seiner aktuellen Position ab.
	 */
	public void placeMine() {
		agent.placeMine();
	}

	/**
	 * Gibt zurück, ob der Agent Bewegungsunfähig ist. (Zerstört oder ohne Treibstoff)
	 * @return
	 */
	public boolean isDisabled() {
		return agent.isDisabled();
	}

	/**
	 * Zeigt an, ob der Agent eine Resource Trägt.
	 */
	public boolean isCarringResource() {
		return agent.isCarringResource();
	}

	/**
	 * Gibt an, ob der Agent noch lebt.
	 * @return
	 */
	public boolean isAlive() {
		return agent.isAlive();
	}

	/**
	 * Gibt an, ob ein Zusammenstoß mit einer Wand bevorsteht.
	 * @return
	 */
	public boolean collisionDetected() {
		return agent.collisionDetected();
	}

	/**
	 * Der Agent bewegt sich geradeaus.
	 */
	public void goStraightAhead() {
		agent.goStraightAhead();
	}

	/**
	 * Der Agent dreht sich um 180° in die entgegengesetzte Richtung.
	 */
	public void turnAround() {
		agent.turnAround();
	}

	/**
	 * Der Agent dreht sich um Winkel @param beta nach links.
	 * @param beta Gewünschter Winkel in Grad (0 - 360)
	 */
	public void turnLeft(int beta) {
		agent.turnLeft(beta);
	}

	/**
	 * Der Agent dreht sich um Winkel @param beta nach rechts.
	 * @param beta Gewünschter Winkel in Grad (0 - 360)
	 */
	public void turnRight(int beta) {
		agent.turnRight(beta);
	}

	/**
	 * Der Agent dreht sich um eine zufällige Gradzahl in eine zufällige Richtung.
	 */
	public void turnRandom() {
		agent.turnRandom();
	}

	/**
	 * Der Agent bewegt sich auf das Mutterschiff zu.
	 * Hierbei wird ein Weg berechnet, welcher auf kürzester Distanz zum Mutterschiff führt.
	 * Um Hindernisse bewegt sich der Agent hierbei automatisch herum.
	 */
	public void moveOneStepInTheMothershipDirection() {
		agent.moveOneStepInTheMothershipDirection();
	}

	/**
	 * Gibt zurück, ob der Agent beim Mutterschiff ist.
	 * @return
	 */
	public boolean isAtMothership() {
		return agent.isAtMothership();
	}

	/**
	 * Der Agent fordert Treibstoff vom Mutterschiff an.
	 * @param percent
	 */
	public void orderFuel(int percent) {
		agent.orderFuel(percent);
	}

	/**
	 * Zeigt an ob der Agent eine Resource aufgespürt hat.
	 * @return
	 */
	public boolean seeResource() {
		return agent.seeResource();
	}

	/**
	 * Der Agent bewegt sich zur Resource.
	 */
	public void goToResource() {
		agent.goToResource();
	}

	/**
	 * Der Agent trägt die Resource zum Mutterschiff zurück.
	 * Hierbei folgt er automatisch einem vorher Berechneten Weg zurück zum Mutterschiff, welcher um Hindernisse herum führt.
	 */
	public void deliverResourceToMothership() {
		agent.deliverResourceToMothership();
	}

	/**
	 * Gibt zurück, ob der Agent eine Resource berührt.
	 * @return
	 */
	public boolean touchResource() {
		return agent.touchResource();
	}

	/**
	 * Gibt die Punkte des Teams wieder.
	 * @return
	 */
	public int getTeamPoints() {
		return agent.getTeam().getPoints();
	}

	/**
	 * Gibt den Typ der Resource wieder, welche der Agent berührt.
	 * @return
	 */
	public Resource.ResourceType touchResourceType() {
		return agent.touchResourceType();
	}

	/**
	 * Gibt an, ob der Agent unter Beschuss steht.
	 * @return
	 */
	public boolean isUnderAttack() {
		return agent.isUnderAttack();
	}

	/**
	 * Der Befehl zum aufnehmen einer Resource.
	 */
	public void pickupResource() {
		agent.pickupResource();
	}

	/**
	 * Zeigt an, ob der Agent einen Feindlichen Agenten sieht.
	 * @return
	 */
	public boolean seeAdversaryAgent() {
		return agent.seeAdversaryAgent();
	}

	/**
	 * Zeigt an, ob der Agent das feindliche Mutterschiff sieht.
	 * @return
	 */
	public boolean seeAdversaryMothership() {
		return agent.seeAdversaryMothership();
	}

	/**
	 * Der Befehl zum bekämpfen eines feindlichen Agenten.
	 */
	public void fightWithAdversaryAgent() {
		agent.fightWithAdversaryAgent();
	}

	/**
	 * Der Befehl zum Angreifen des feindlichen Mutterschiffs.
	 */
	public void fightWithAdversaryMothership() {
		agent.fightWithAdversaryMothership();
	}

	/**
	 * Abfrage, ob der Agent sich im Kampf befindet oder nicht.
	 * @return
	 */
	public boolean isFighting() {
		return agent.isFighting();
	}

	/**
	 * Zeigt an, ob sich in der Nähe des Agenten ein Teammitglied ohne Treibstoff befindet.
	 * @return
	 */
	public boolean seeLostTeamAgent() {
		return agent.seeLostTeamAgent();
	}

	/**
	 * Der Agent spendet einem Teammitglied @param value Treibstoff.
	 * Ein Agent ist Bewegungsunfähig, solange er Treibstoff spendet!
	 * @param value Der zu spendende Treibstoff.
	 */
	public void spendFuelTeamAgent(int value) {
		agent.spendFuelTeamAgent(value);
	}

	/**
	 * Der Befehl zum reparieren des eigenen Mutterschiffs.
	 */
	public void repaireMothership() {
		agent.repaireMothership();
	}
}
