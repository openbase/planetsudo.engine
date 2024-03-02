/*
 * Copyright (C) 2013 DivineCorporation
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openbase.planetsudo.level.levelobjects

import org.openbase.planetsudo.game.SwatTeam
import org.openbase.planetsudo.level.levelobjects.Tower.TowerType

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
 * @author Divine Threepwood
 */
interface AgentInterface : AgentBattleInterface {

    /**
     * Abfrage, ob der Agent aktuell unsichtbar ist.
     */
    val isInvisible: Boolean

    /**
     * Abfrage, ob der Agent aktuell sichtbar ist.
     */
    val isVisible: Boolean get() = !isInvisible

    /**
     * Gibt zurück, ob der Agent den Turm sieht.
     *
     * @return true oder false
     */
    @Deprecated("NOT YET SUPPORTED")
    fun seeTower(): Boolean

    /**
     * Überprüft, ob dieser Agent in mindestens einer der übergebenen SwatTeams enthalten ist.
     *
     * @param swatTeams
     * @return
     */
    fun isMemberOfSwatTeam(swatTeams: Set<SwatTeam>): Boolean

    /**
     * Errichtet den Turm an der Position des Commander.
     * Diese Aktion kann nur durch den Commander durchgeführt werden!
     *
     * Es gibt hierbei zwei Arten von Türmen:
     * - Einen Verteidigungsturm (DefenceTower) der feindliche Agenten für alle Agenten des Teams sichtbar macht und diese angreift.
     * - Einen Beobachtungsturm (ObservationTower) der Ressourcen in Reichweite für alle Agenten des Teams sichtbar macht und zudem Agenten in Reichweite mit Energie beliefert.
     *
     * @param type Hier rüber kannst du den Turmtypen auswählen welcher errichtet werden soll.
     */
    @Deprecated("NOT YET SUPPORTED")
    fun constructTower(type: TowerType)

    /**
     * Baut einen Turm wieder ab der zuvor aufgestellt wurde.
     * Diese Aktion kann nur vom Commander durchgeführt werden, und zwar nur dann, wenn er in unmittelbarer Nähe des Turms ist.
     */
    @Deprecated("NOT YET SUPPORTED")
    fun deconstructTower()

    /**
     * Überprüft, ob dieser Agent einen Turm aufbauen könnte.
     * Bedenke das nur Commander einen Turm trägt!
     * @return
     */
    @Deprecated("NOT YET SUPPORTED")
    val hasTower: Boolean

    /**
     * Diese Abfrage checkt das bevorstehende Ende des Spiels. Wenn es bald
     * vorbei ist, gibt sie true zurück, dann sollte man schleunigst zum
     * Mutterschiff.
     *
     * @return true oder false
     */
    val isGameOverSoon: Boolean

    /**
     * Gibt die Punkte des Teams wieder.
     *
     * @return Den Punktestand als ganze Zahl (z.B. 47)
     */
    val teamPoints: Int

    /**
     * Der angeforderte Support des Agenten wird beendet.
     * Treibstoff: 1
     * Aktionspunkte: 5
     */
    fun cancelSupport()

    /**
     * Eine Markierung, sichtbar für das ganze Team wird gesetzt. Alle Agenten
     * des Teams können sich auf diesen zubewegen.
     * Treibstoff: 1
     * Aktionspunkte: 5
     */
    fun deployMarker()

    /**
     * Der Agent bewegt sich in Richtung des Markers, falls dieser gesetzt
     * wurde. Hierbei wird ein Weg berechnet, welcher auf kürzester Distanz
     * zum Marker fährt. Um Hindernisse bewegt sich der Agent hierbei
     * automatisch herum.
     *
     * Achtung: Der Agent bewegt sich erst geradeaus und dreht sich danach.
     *
     * Aktionspunkte: 4 (+ 4 wenn resource geladen)
     * Treibstoff: 1
     */
    fun goToMarker()

    /**
     * Gehe zu einem Agenten der Hilfe benötigt.
     *
     * Achtung: Der Agent bewegt sich erst geradeaus und dreht sich danach.
     *
     * Aktionspunkte: 4 + (+ 4 wenn resource geladen)
     * Treibstoff: 1
     */
    fun goToSupportAgent()

    /**
     * Befehl zum Aussenden des Hilferufs.
     * Treibstoff: 0
     * Aktionspunkte: 5
     */
    fun orderSupport()

    /**
     * Gibt zurück, ob der Agent den Marker sieht.
     *
     * @return true oder false
     */
    fun seeMarker(): Boolean

    /**
     * Sofern der Agent 3 Tonic besitzt, kann er sich hiermit unsichtbar machen.
     * Unsichtbar heißt, dass kein feindlicher Agent (außer der feindliche Commander) diesen Agenten sehen oder angreifen kann.
     * Ein Agent ist so lange unsichtbar, bis er eine feindliche Einheit angreift oder eine Mine setzt.
     */
    fun makeInvisible()

    /**
     * Mit einem Shift bewegt sich der Agent für die nächsten Schritte (agent.go...) besonders schnell, sofern er genug Tonic besitzt.
     * Dies kann z. B. strategisch genutzt werden, um feindlichen Angriffen zu entkommen, schneller Hilfe leisten zu können oder
     * Ressourcen schneller zu transportieren.
     *
     * Aktionspunkte: 1
     * Tonic: 1
     */
    fun shift()

    /**
     * Der Agent läuft amok.
     */
    fun kill()
}
