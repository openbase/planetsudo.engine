package org.openbase.planetsudo.jp

import org.openbase.jps.preset.AbstractJPString

/**
 *
 * @author Divine Threepwood
 */
class JPServerHostname : AbstractJPString(COMMAND_IDENTIFIERS) {
    override fun generateArgumentIdentifiers(): Array<String> {
        return ARGUMENT_IDENTIFIER
    }

    override fun getPropertyDefaultValue(): String {
//		return "cpwe";
        return "bone.no-ip.biz"
    }

    override fun getDescription(): String {
        return "Setup the default server."
    }

    companion object {
        val COMMAND_IDENTIFIERS: Array<String> = arrayOf("--server")
        val ARGUMENT_IDENTIFIER: Array<String> = arrayOf("HOSTNAME")
    }
}
