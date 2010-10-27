/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.level.levelobjects;
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
	 * Gibt zurück, ob Mutterschiff Treibstoff hat.
	 * @return
	 */
	public boolean hasFuel() {
		return mothership.hasFuel();
	}

	/**
	 * Gibt zurück, wie viele Agenten das Team hat.
	 */
	public void getAgentCount() {
		mothership.getAgentCount();
	}

	/**
	 * Gibt zurück, ob das Mutterschiff brennt und somit Treibstoff verliert.
	 * @return
	 */
	public boolean isBurning() {
		return mothership.isBurning();
	}

	/**
	 * Gibt die Schildstärke des Mutterschiffs wieder. (0-100)
	 * @return
	 */
	public int getShieldForce() {
		return mothership.getShieldForce();
	}

	/**
	 * Gibt zurück, ob das Mutterschiff maximal beschädigt wurde. (Der Schutzschild is komplett zerstört)
	 * @return
	 */
	public boolean isMaxDamaged() {
		return mothership.isMaxDamaged();
	}

	/**
	 * Gibt zurück, ob das Mutterschiff und somit der Schutzschild beschädigt ist.
	 * @return
	 */
	public boolean isDamaged() {
		return mothership.isDamaged();
	}

}
