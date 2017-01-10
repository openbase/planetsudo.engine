/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.openbase.planetsudo.game.SwatTeam;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2017 openbase.org
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
public abstract class Rule {

    private final int priority;
    private final String name;
    private final Set<SwatTeam> swatTeamSet;

    /**
     * Anlegen einer neuen Regel.
     * Die priorität der Regel wird automatisch generiert und ist abhängig von der Reihenfolge der Registrierung.
     * Eine Regel die registriert wird, hat eine höhere Priorität als zurvor registrierte Regeln und eine geringere Priorität als nachfolgend regisrierte Regeln.
     *
     * @param name
     * @param swatTeams
     */
    public Rule(String name, SwatTeam... swatTeams) {
        this(-1, name, swatTeams);
    }

    public Rule(int priority, String name, SwatTeam... swatTeams) {
        this.priority = priority;
        this.name = name;
        final TreeSet<SwatTeam> swats = new TreeSet<>(Arrays.asList(swatTeams));
        if (checkAutoWildcard(swatTeams)) {
            swats.add(SwatTeam.ALL);
        }
        this.swatTeamSet = Collections.unmodifiableSet(swats);
    }

    private boolean checkAutoWildcard(final SwatTeam[] swatTeams) {
        // No swats means rule is valid for all agents,
        if (swatTeams == null || swatTeams.length == 0) {
            return true;
        }

        // Only negation means rule is valid for all non excluded agents.
        final List<SwatTeam> swatTeamList = new ArrayList(Arrays.asList(swatTeams));
        swatTeamList.removeAll(SwatTeam.NEGATED_TEAM_LIST);
        return swatTeamList.isEmpty();
    }

    public int getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + name + "]";
    }

    public Collection<SwatTeam> getSwatTeams() {
        return swatTeamSet;
    }

    protected abstract boolean constraint();

    protected abstract void action();
}
