/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.jp

import org.openbase.jps.preset.AbstractJPInteger

/**
 *
 * @author Divine Threepwood
 */
class JPServerPort : AbstractJPInteger(COMMAND_IDENTIFIERS) {
    override fun getPropertyDefaultValue(): Int {
        return 8253
    }

    override fun getDescription(): String {
        return "Setup the default server port."
    }

    companion object {
        val COMMAND_IDENTIFIERS: Array<String> = arrayOf("--port")
    }
}
