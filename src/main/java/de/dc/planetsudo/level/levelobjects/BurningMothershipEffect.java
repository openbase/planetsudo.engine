/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.level.levelobjects;

import java.util.TimerTask;

/**
 *
 * @author divine
 */
public class BurningMothershipEffect extends TimerTask {

	public final Mothership mothership;

	public BurningMothershipEffect(Mothership mothership) {
		this.mothership = mothership;
	}

	@Override
	public void run() {
		mothership.orderFuel(33-mothership.getShieldForce(), null);
	}

}
