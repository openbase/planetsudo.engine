/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.level.levelobjects;


/**
 *
 * @author divine
 */
public class AgentController {

//	private final Agent agent;
//	private Mothership mothership;
//	public final MothershipController mothershipController;
//
//	public AgentController(Agent agent) {
//		this.agent = agent;
//		this.mothership = agent.getMothership();
//		this.mothershipController = new MothershipController(mothership, agent);
//	}
//
//	public MothershipController getMothership() {
//		return mothershipController;
//	}
//
//	/**
//	 * Gibt die Richtung des Agenten in Grad wieder.
//	 * Rechts = 0°
//	 * Runter = 90°
//	 * Links = 180°
//	 * Hoch = 270°
//	 * @return Den Winkel von 0 - 360° als ganze Zahl. (z.B. 128)
//	 */
//	@Override
//	public int getAngle() {
//		return agent.getAngle();
//	}
//
//	/**
//	 * Gibt die verf&uuml;gbaren Aktionspunkte wieder.
//	 * @return Die Aktionspunkte als ganze Zahl. (z.B. 2051)
//	 */
//	@Override
//	public int getActionPoints() {
//		return agent.getActionPoints();
//	}
//
//	/**
//	 * Zeigt den verbliebenen Treibstoff des Agenten an.
//	 * @return Den verbliebenen Treibstoff als ganz Zahl.
//	 */
//	@Override
//	public int getFuel() {
//		return agent.getFuel();
//	}
//
//	/**
//	* Gibt den verbliebenen Treibstoff in Prozent an.
//	* @return Treibstoffwert in Prozent als ganze Zahl. (z.B. 47 bei 47% verbliebenen Treibstoff)
//	*/
//	@Override
//	public int getFuelInPercent() {
//		return agent.getFuelInPercent();
//	}
//
//	/**
//	 * Gibt zur&uuml;ck, ob der Agent Treibstoff hat.
//	 * @return true oder false.
//	 */
//	@Override
//	public boolean hasFuel() {
//		return agent.hasFuel();
//	}
//
//	/**
//	 * Gibt zur&uuml;ck, ob der Agent seine Mine noch tr&auml;gt.
//	 * @return true oder false.
//	 */
//	@Override
//	public boolean hasMine() {
//		return agent.hasMine();
//	}
//
//	/**
//	 * Der Agent legt seine Mine an seiner aktuellen Position ab.
//	 * Aktionspunkte: 1
//	 * Treibstoff: 1
//	 */
//	@Override
//	public void deployMine() {
//		agent.deployMine();
//	}
//
//	/**
//	 * Gibt zur&uuml;ck, ob der Agent Bewegungsunf&auml;hig ist. (Zerst&ouml;rt oder ohne Treibstoff)
//	 * @return true oder false.
//	 */
//	@Override
//	public boolean isDisabled() {
//		return agent.isDisabled();
//	}
//
//	/**
//	 * Zeigt an, ob der Agent eine Resource Tr&auml;gt.
//	 * @return true oder false
//	 */
//	@Override
//	public boolean isCarringResource() {
//		return agent.isCarringResource();
//	}
//
//	/**
//	 * Gibt an, ob der Agent noch lebt.
//	 * @return true oder false.
//	 */
//	@Override
//	public boolean isAlive() {
//		return agent.isAlive();
//	}
//
//	/**
//	 * Gibt an, ob ein Zusammensto&szlig; mit einer Wand bevorsteht.
//	 * @return true oder false.
//	 */
//	@Override
//	public boolean isCollisionDetected() {
//		return agent.isCollisionDetected();
//	}
//
//	/**
//	 * Der Agent bewegt sich geradeaus.
//	 * Aktionspunkte: 4
//	 * Treibstoff: 1
//	 * Treibstoff wenn der Agent eine Resource tr&auml;gt: 2
//	 */
//	@Override
//	public void go() {
//		agent.go();
//	}
//
//	/**
//	 * Der Agent dreht sich um 180 Grad in die entgegengesetzte Richtung.
//	 * Aktionspunkte: 1
//	 * Treibstoff: 1
//	 */
//	@Override
//	public void turnAround() {
//		agent.turnAround();
//	}
//
//	@Override
//	public void goLeft(int beta) {
//		agent.goLeft(beta);
//	}
//
//	@Override
//	public void goRight(int beta) {
//		agent.goRight(beta);
//	}
//
//	/**
//	 * Der Agent dreht sich um Winkel beta nach links.
//	 * @param beta Gew&uuml;nschter Winkel in Grad (0 - 360)
//	 * Aktionspunkte: 1
//	 * Treibstoff: 1
//	 */
//	@Override
//	public void turnLeft(int beta) {
//		agent.turnLeft(beta);
//	}
//
//	/**
//	 * Der Agent dreht sich um Winkel beta nach rechts.
//	 * @param beta Gew&uuml;nschter Winkel in Grad (0 - 360)
//	 * Aktionspunkte: 1
//	 * Treibstoff: 1
//	 */
//	@Override
//	public void turnRight(int beta) {
//		agent.turnRight(beta);
//	}
//
//	/**
//	 * Der Agent dreht sich in eine zuf&auml;llige Richtung.
//	 * Aktionspunkte: 1
//	 * Treibstoff: 1
//	 */
//	@Override
//	public void turnRandom() {
//		agent.turnRandom();
//	}
//
//	@Override
//	public void turnRandom(final int opening) {
//		agent.turnRandom(opening);
//	}
//
//	/**
//	 * Der Agent bewegt sich auf das Mutterschiff zu.
//	 * Hierbei wird ein Weg berechnet, welcher auf k&uuml;rzester Distanz zum Mutterschiff f&uuml;hrt.
//	 * Um Hindernisse bewegt sich der Agent hierbei automatisch herum.
//	 * Aktionspunkte: 2
//	 * Treibstoff: 1
//	 * Tr&auml;gt der Agent eine Resource verlangsamt er sich ebenfalls und der Treibstoffverbrauch ist 2.
//	 */
//	@Override
//	public void goToMothership() {
//		agent.goToMothership();
//	}
//
//	/**
//	 * Gibt zur&uuml;ck, ob der Agent beim Mutterschiff ist.
//	 * @return true oder false.
//	 */
//	@Override
//	public boolean isAtMothership() {
//		return agent.isAtMothership();
//	}
//
//	/**
//	 * Der Agent fordert @param percent Prozent Treibstoff vom Mutterschiff an.
//	 * @param percent Der anzufordernde Treibstoffwert in Prozent als ganze Zahl von 0 - 100.
//	 * Aktionspunkte: 20 + 2 für jeden getankten Treibstoff.
//	 */
//	@Override
//	public void orderFuel(int percent) {
//		agent.orderFuel(percent);
//	}
//
//	/**
//	 * Zeigt an ob der Agent eine Resource sehen kann.
//	 * @return true oder false.
//	 */
//	@Override
//	public boolean seeResource() {
//		return agent.seeResource();
//	}
//
//	/**
//	 * Der Agent bewegt sich zur Resource.
//	 * Aktionspunkte: 1
//	 * Treibstoff: 1
//	 */
//	@Override
//	public void goToResource() {
//		agent.goToResource();
//	}
//
//	/**
//	 * Der Agent &uuml;bergibt die Resource dem Mutterschiff.
//	 * Aktionspunkte: 10
//	 * Treibstoff: 1
//	 */
//	@Override
//	public void deliverResourceToMothership() {
//		agent.deliverResourceToMothership();
//	}
//
//	/**
//	 * Gibt zur&uuml;ck, ob der Agent eine Resource ber&uuml;hrt und diese somit aufgeben kann.
//	 * @return true oder false.
//	 */
//	@Override
//	public boolean isTouchingResource() {
//		return agent.isTouchingResource();
//	}
//
//	/**
//	 * Gibt die Punkte des Teams wieder.
//	 * @return Den Punktestand als ganze Zahl (z.B. 47)
//	 */
//	@Override
//	public int getTeamPoints() {
//		return agent.getTeam().getPoints();
//	}
//
//	/**
//	 * Gibt den Typ der Resource wieder, welche der Agent ber&uuml;hrt.
//	 * @return Den Ressourcenwert
//	 */
//	@Override
//	public Resource.ResourceType getResourceType() {
//		return agent.getResourceType();
//	}
//
//	@Override
//	public boolean isTouchingResource(final Resource.ResourceType type) {
//		return agent.isTouchingResource(type);
//	}
//
//	/**
//	 * Gibt an, ob der Agent unter Beschuss steht.
//	 * @return true oder false.
//	 */
//	@Override
//	public boolean isUnderAttack() {
//		return agent.isUnderAttack();
//	}
//
//	/**
//	 * Der Befehl zum aufnehmen einer Resource wenn der Agent sie anfassen kann.
//	 * Aktionspunkte: 10
//	 * Treibstoff: 1
//	 */
//	@Override
//	public void pickupResource() {
//		agent.pickupResource();
//	}
//
//
//	@Override
//	public boolean seeAdversaryAgent() {
//		return agent.seeAdversaryAgent();
//	}
//
//
//	@Override
//	public boolean seeAdversaryMothership() {
//		return agent.seeAdversaryMothership();
//	}
//
//
//	@Override
//	public void fightWithAdversaryAgent() {
//		agent.fightWithAdversaryAgent();
//	}
//
//
//	@Override
//	public void fightWithAdversaryMothership() {
//		agent.fightWithAdversaryMothership();
//	}
//
//
//	@Override
//	public boolean isFighting() {
//		return agent.isFighting();
//	}
//
//
//	@Override
//	public boolean seeLostTeamAgent() {
//		return agent.seeLostTeamAgent();
//	}
//
//
//	@Override
//	public void spendTeamAgentFuel(int value) {
//		agent.spendTeamAgentFuel(value);
//	}
//
//
//	@Override
//	public void repairMothership() {
//		agent.repairMothership();
//	}
//
//
//	@Override
//	public void releaseResource() {
//		agent.releaseResource();
//	}
//
//	@Override
//	public void cancelSupport() {
//		agent.cancelSupport();
//	}
//
//	@Override
//	public void orderSupport() {
//		agent.orderSupport();
//	}
//
//	@Override
//	public boolean isSupportOrdered() {
//		return agent.isSupportOrdered();
//	}
//
//	@Override
//	public void goToSupportAgent() {
//		agent.goToSupportAgent();
//	}
//
//	@Override
//	public void deployMarker() {
//		agent.deployMarker();
//	}
//
//	@Override
//	public void goToMarker() {
//		agent.goToMarker();
//	}
//
//	@Override
//	public boolean isCommander() {
//		return agent.isCommander();
//	}
//
//	@Override
//	public boolean seeMarker() {
//		return agent.seeMarker();
//	}
//
//	@Override
//	public int getFuelVolume() {
//		return agent.getFuelVolume();
//	}
//
//	@Override
//	public void searchResources() {
//		agent.searchResources();
//	}
//
//	@Override
//	public boolean isCarringResource(final ResourceType type) {
//		return isCarringResource(type);
//	}
//
//	@Override
//	public boolean isGameOverSoon() {
//		return agent.isGameOverSoon();
//	}
}
