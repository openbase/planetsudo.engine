/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.level.levelobjects;
/**
 *
 * @author divine
 */
public class MothershipController {

		private Mothership mothership;

	public MothershipController(Mothership mothership) {
		this.mothership = mothership;
	}

	/**
	 * Gibt zur&uuml;ck, ob das Mutterschiff Treibstoff hat.
	 * @return Einen der Resourcentypen: Unknown, Normal, DoublePoints, ExtremPoint, ExtraAgentFuel, ExtraMothershipFuel, Bomb
	 */
	public boolean hasFuel() {
		return mothership.hasFuel();
	}

	/**
	 * Gibt zur&uuml;ck, wie viele Agenten das Team hat.
	 */
	public void getAgentCount() {
		mothership.getAgentCount();
	}

	/**
	 * Gibt zur&uuml;ck, ob das Mutterschiff brennt und somit Treibstoff verliert.
	 * @return true oder false.
	 */
	public boolean isBurning() {
		return mothership.isBurning();
	}

	/**
	 * Gibt die Schildst&auml;rke des Mutterschiffs wieder.
	 * @return Schildst&auml;rke von 0 - 100 als ganze Zahl.
	 */
	public int getShieldForce() {
		return mothership.getShieldForce();
	}

	/**
	 * Gibt zur&uuml;ck, ob das Mutterschiff maximal besch&auml;digt wurde. (Der Schutzschild is komplett zerst&ouml;rt)
	 * @return true oder false.
	 */
	public boolean isMaxDamaged() {
		return mothership.isMaxDamaged();
	}

	/**
	 * Gibt zur&uuml;ck, ob das Mutterschiff und somit der Schutzschild besch&auml;digt ist.
	 * @return true oder false.
	 */
	public boolean isDamaged() {
		return mothership.isDamaged();
	}

}
