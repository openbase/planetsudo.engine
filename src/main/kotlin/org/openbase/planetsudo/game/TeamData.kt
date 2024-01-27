/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game

import java.awt.Color
import java.io.Serializable

/**
 *
 * @author Divine Threepwood
 */
data class TeamData(
    @JvmField val name: String = "",
    val teamColor: Color = Color.BLACK,
    @JvmField val strategy: String = "",
    val members: List<String> = ArrayList()
) : Serializable {
    override fun toString(): String {
        return name
    }

    companion object {
        const val serialVersionUID: Long = 100L
    }
}
