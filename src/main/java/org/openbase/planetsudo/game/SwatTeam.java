package org.openbase.planetsudo.game;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public enum SwatTeam {
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

    public static SwatTeam[] TEAMS = {
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
        X_RAY};

    public static SwatTeam[] NEGATED_TEAMS = {
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
        NOT_X_RAY};

    public static List<SwatTeam> NEGATED_TEAM_LIST = Arrays.asList(NEGATED_TEAMS);
    public static List<SwatTeam> TEAM_LIST = Arrays.asList(TEAMS);

    public final boolean negative;
    public final SwatTeam opposition;

    private SwatTeam() {
        this(false, null);
    }

    private SwatTeam(final boolean negative, final SwatTeam opposition) {
        this.negative = negative;
        this.opposition = opposition;
    }

    public static String toString(final Collection<SwatTeam> swatTeams) {
        final int swatCount = swatTeams.size();
        int swatIndex = 0;
        if (swatCount <= 0) {
            // skip if empty
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        for (final SwatTeam swat : swatTeams) {
            swatIndex++;
            if (swat.equals(ALL)) {
                continue;
            }
            stringBuilder.append(String.valueOf(swat));
            if (swatIndex != swatCount) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.append(']').toString();
    }
}
