package org.openbase.planetsudo.game

import java.util.*

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
enum class SwatTeam(@JvmField val negative: Boolean = false, @JvmField val opposition: SwatTeam? = null) {
    ALL,
    COMMANDER,
    ALPHA,
    BRAVO,
    CHARLIE,
    DELTA,
    ECHO,
    FOXTROT,
    GOLF,
    HOTEL,
    INDIA,
    JULIET,
    KILO,
    LIMA,
    MIKE,
    NOVEMBER,
    OSCAR,
    PAPA,
    QUEBEC,
    ROMEO,
    SIERRA,
    TANGO,
    UNIFORM,
    VIKTOR,
    WHISKEY,
    X_RAY,
    NOT_COMMANDER(true, COMMANDER),
    NOT_ALPHA(true, ALPHA),
    NOT_BRAVO(true, BRAVO),
    NOT_CHARLIE(true, CHARLIE),
    NOT_DELTA(true, DELTA),
    NOT_ECHO(true, ECHO),
    NOT_FOXTROT(true, FOXTROT),
    NOT_GOLF(true, GOLF),
    NOT_HOTEL(true, HOTEL),
    NOT_INDIA(true, INDIA),
    NOT_JULIET(true, JULIET),
    NOT_KILO(true, KILO),
    NOT_LIMA(true, LIMA),
    NOT_MIKE(true, MIKE),
    NOT_NOVEMBER(true, NOT_ALPHA),
    NOT_OSCAR(true, OSCAR),
    NOT_PAPA(true, PAPA),
    NOT_QUEBEC(true, QUEBEC),
    NOT_ROMEO(true, ROMEO),
    NOT_SIERRA(true, SIERRA),
    NOT_TANGO(true, TANGO),
    NOT_UNIFORM(true, UNIFORM),
    NOT_VIKTOR(true, VIKTOR),
    NOT_WHISKEY(true, WHISKEY),
    NOT_X_RAY(true, X_RAY);

    operator fun plus(swat: SwatTeam): Set<SwatTeam> {
        return setOf(this, swat)
    }

    operator fun Collection<SwatTeam>.plus(swat: SwatTeam): Set<SwatTeam> {
        return this.plus(swat)
    }

    companion object {
        var SWATS: Set<SwatTeam> = setOf(
            COMMANDER,
            ALPHA,
            BRAVO,
            CHARLIE,
            DELTA,
            ECHO,
            FOXTROT,
            GOLF,
            HOTEL,
            INDIA,
            JULIET,
            KILO,
            LIMA,
            MIKE,
            NOVEMBER,
            OSCAR,
            PAPA,
            QUEBEC,
            ROMEO,
            SIERRA,
            TANGO,
            UNIFORM,
            VIKTOR,
            WHISKEY,
            X_RAY
        )

        var NEGATED_SWATS: Set<SwatTeam> = setOf(
            NOT_COMMANDER,
            NOT_ALPHA,
            NOT_BRAVO,
            NOT_CHARLIE,
            NOT_DELTA,
            NOT_ECHO,
            NOT_FOXTROT,
            NOT_GOLF,
            NOT_HOTEL,
            NOT_INDIA,
            NOT_JULIET,
            NOT_KILO,
            NOT_LIMA,
            NOT_MIKE,
            NOT_NOVEMBER,
            NOT_OSCAR,
            NOT_PAPA,
            NOT_QUEBEC,
            NOT_ROMEO,
            NOT_SIERRA,
            NOT_TANGO,
            NOT_UNIFORM,
            NOT_VIKTOR,
            NOT_WHISKEY,
            NOT_X_RAY
        )

        fun toString(swatTeams: Collection<SwatTeam>): String {
            val swatCount = swatTeams.size
            var swatIndex = 0
            if (swatCount <= 0) {
                // skip if empty
                return ""
            }

            val stringBuilder = StringBuilder()
            stringBuilder.append('[')
            for (swat in swatTeams) {
                swatIndex++
                if (swat == ALL) {
                    continue
                }
                stringBuilder.append(swat.toString())
                if (swatIndex != swatCount) {
                    stringBuilder.append(", ")
                }
            }
            return stringBuilder.append(']').toString()
        }
    }
}
