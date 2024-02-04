/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game.strategy

import org.openbase.planetsudo.game.SwatTeam

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2024 openbase.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
abstract class Rule(val priority: Int, val name: String, var swatTeam: Set<SwatTeam>) {

    /**
     * Anlegen einer neuen Regel.
     * Die priorität der Regel wird automatisch generiert und ist abhängig von der Reihenfolge der Registrierung.
     * Eine Regel die registriert wird, hat eine höhere Priorität als zurvor registrierte Regeln und eine geringere Priorität als nachfolgend regisrierte Regeln.
     *
     * @param name
     * @param swatTeams
     */
    constructor(priority: Int, name: String, vararg swatTeams: SwatTeam) : this(
        priority = priority,
        name = name,
        swatTeam = swatTeams.toSet(),
    )

    constructor(name: String, vararg swatTeams: SwatTeam) : this(-1, name, *swatTeams)

    init {
        swatTeam = swatTeam.applyWildcard()
    }

    private fun Set<SwatTeam>.applyWildcard(): Set<SwatTeam> {
        // No swats means rule is valid for all agents,
        if (isEmpty()) {
            return setOf(SwatTeam.ALL)
        }

        // Only negation means rule is valid for all non excluded agents.
        return minus(SwatTeam.NEGATED_SWATS).takeIf { it.isNotEmpty() }
            ?: setOf(SwatTeam.ALL)
    }

    override fun toString(): String {
        return javaClass.simpleName + "[" + name + "]"
    }

    abstract fun constraint(): Boolean

    abstract fun action()
}
