/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.level.levelobjects

import java.util.*

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class BurningMothershipEffect(val mothership: Mothership) : TimerTask() {
    override fun run() {
        mothership.orderFuel(33 - mothership.shieldForce, null)
    }
}
