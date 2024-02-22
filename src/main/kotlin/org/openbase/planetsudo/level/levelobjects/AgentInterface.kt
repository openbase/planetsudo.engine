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
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType
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
interface AgentInterface : GlobalAgentInterface {

    /**
     * Gibt an, ob ein Zusammensto mit einer Wand bevorsteht.
     *
     * @return true oder false.
     */
    val isCollisionDetected: Boolean

    /**
     * Gibt zurück, ob der Agent Bewegungsunfähig ist. (Zerstört
     * oder ohne Treibstoff)
     *
     * @return true oder false.
     */
    val isDisabled: Boolean

    /**
     * Abfrage, ob der Agent aktuell unsichtbar ist.
     */
    val isInvisible: Boolean

    /**
     * Abfrage, ob der Agent aktuell sichtbar ist.
     */
    val isVisible: Boolean get() = !isInvisible

    /**
     * Diese Abfrage checkt das bevorstehende Ende des Spiels. Wenn es bald
     * vorbei ist, gibt sie true zurück, dann sollte man schleunigst zum
     * Mutterschiff.
     *
     * @return true oder false
     */
    val isGameOverSoon: Boolean

    /**
     * Gibt zurück, ob der Agent eine Resource berührt und diese somit
     * aufgeben kann.
     *
     * @return true oder false.
     */
    val isTouchingResource: Boolean

    /**
     * Gibt die Punkte des Teams wieder.
     *
     * @return Den Punktestand als ganze Zahl (z.B. 47)
     */
    val teamPoints: Int

    /**
     * Gibt an, ob der Agent noch lebt.
     *
     * @return true oder false.
     */
    val isAlive: Boolean

    /**
     * Gibt zurück, ob der Agent beim Mutterschiff ist.
     *
     * @return true oder false.
     */
    val isAtMothership: Boolean

    /**
     * Liefert Informationen über einen feindlichen Agenten in der Nähe.
     */
    val adversaryAgent: GlobalAgentInterface

    /**
     * Der angeforderte Support des Agenten wird beendet.
     * Treibstoff: 1
     * Aktionspunkte: 5
     */
    fun cancelSupport()

    /**
     * Der Agent übergibt die Resource dem Mutterschiff.
     * Treibstoff: 1
     * Aktionspunkte: 10
     */
    fun deliverResourceToMothership()

    /**
     * Eine Markierung, sichtbar für das ganze Team wird gesetzt. Alle Agenten
     * des Teams können sich auf diesen zubewegen.
     * Treibstoff: 1
     * Aktionspunkte: 5
     */
    fun deployMarker()

    /**
     * Der Agent legt seine Mine an seiner aktuellen Position ab.
     * Aktionspunkte: 50
     * Treibstoff: 5
     */
    fun deployMine()

    /**
     * Der Befehl zum Bekämpfen eines feindlichen Agenten.
     * Wenn ein Agent einen anderen erfolgreich angreift, bekommt er eine Treibstoffbelohnung.
     * Aktionspunkte: 21
     * Treibstoff: 1
     */
    fun fightWithAdversaryAgent()

    /**
     * Der Befehl zum Angreifen des feindlichen Mutterschiffs.
     * Aktionspunkte: 30
     * Treibstoff: 1
     */
    fun fightWithAdversaryMothership()

    /**
     * Der Agent bewegt sich geradeaus.
     * Aktionspunkte: 3 (+ 3 wenn resource geladen)
     * Treibstoff: 1
     */
    fun go()

    /**
     * Der Agent bewegt sich geradeaus und dreht sich anschließend um `beta`° nach links.
     * Aktionspunkte: 3 (+ 3 wenn resource geladen)
     * Treibstoff: 1
     *
     *
     * @param beta Drehwinkel
     */
    fun goLeft(beta: Int)

    /**
     * Der Agent bewegt sich geradeaus und dreht sich anschließend um `beta`° nach rechts.
     * Aktionspunkte: 3 (+ 3 wenn resource geladen)
     * Treibstoff: 1
     *
     * @param beta Drehwinkel
     */
    fun goRight(beta: Int)

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
     * Der Agent bewegt sich auf das Mutterschiff zu. Hierbei wird ein Weg
     * berechnet, welcher auf kürzester Distanz zum Mutterschiff
     * fährt. Um Hindernisse bewegt sich der Agent hierbei automatisch
     * herum.
     *
     * Achtung: Der Agent bewegt sich erst geradeaus und dreht sich danach.
     *
     * Aktionspunkte: 4 (+ 4 wenn resource geladen)
     * Treibstoff: 1
     */
    fun goToMothership()

    /**
     * Der Agent bewegt sich zur Resource.
     *
     * Achtung: Der Agent bewegt sich erst geradeaus und dreht sich danach.
     *
     * Aktionspunkte: 4 (+ 4 wenn resource geladen)
     * Treibstoff: 1
     */
    fun goToResource()

    /**
     * Der Agent bewegt sich zur Resource vom angegebenen Typen.
     *
     * Achtung: Der Agent bewegt sich erst geradeaus und dreht sich danach.
     *
     * Aktionspunkte: 4 + (+ 4 wenn resource geladen)
     * Treibstoff: 1
     * @param resourceType
     */
    fun goToResource(resourceType: ResourceType)

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
     * Gehe zu einem feindlichen Agenten in der Nähe.
     *
     * Achtung: Der Agent bewegt sich erst geradeaus und dreht sich danach.
     *
     * Aktionspunkte: 4 + (+ 4 wenn resource geladen)
     * Treibstoff: 1
     */
    fun goToAdversaryAgent()

    /**
     * Gibt zurück, ob der Agent seine Mine noch trägt.
     *
     * @return true oder false.
     */
    val hasMine: Boolean

    /**
     * Der Agent fordert `percent` Prozent Treibstoff vom Mutterschiff an.
     *
     * @param percent Der anzufordernde Treibstoffwert in Prozent als ganze Zahl
     * von 0 - 100.
     * Aktionspunkte: 20 + 2 für jeden getankten Treibstoff.
     */
    fun orderFuel(percent: Int)

    /**
     * Befehl zum Aussenden des Hilferufs.
     * Treibstoff: 0
     * Aktionspunkte: 5
     */
    fun orderSupport()

    /**
     * Der Befehl zum Aufnehmen einer Resource, wenn der Agent sie anfassen kann.
     * Treibstoff: 1
     * Aktionspunkte: 10 + Normal(200), DoublePoints(400), ExtremPoint(700), ExtraAgentFuel(400), ExtraMothershipFuel(400), Mine(200)
     */
    fun pickupResource()

    /**
     * Wenn der Agent eine Resource trägt, lässt er sie wieder fallen.
     * Treibstoff: 0
     * Aktionspunkte: 0
     */
    fun releaseResource()

    /**
     * Der Befehl zum Reparieren des eigenen Mutterschiffs. Hierzu muss sich der
     * Agent am Mutterschiff befinden!
     * Aktionspunkte: 30
     * Treibstoff: 1
     */
    fun repairMothership()

    /**
     * Der Agent dreht sich einmal im Kreis. Sieht er während der Drehung eine
     * Resource, unterbricht er und schaut in ihre Richtung.
     * Aktionspunkte: 10 pro 20° Drehung
     * Treibstoff: 1 pro 20° Drehung
     */
    fun searchResources()

    /**
     * Zeigt an, ob der Agent einen feindlichen Agenten sieht.
     *
     * @return true oder false.
     */
    val seeAdversaryAgent: Boolean

    /**
     * Zeigt an, ob der Agent einen Agenten des eigenen Teams sieht.
     *
     * @return true oder false.
     */
    val seeTeamAgent: Boolean

    /**
     * Zeigt an, ob der Agent das feindliche Mutterschiff sieht.
     *
     * @return true oder false.
     */
    val seeAdversaryMothership: Boolean

    /**
     * Zeigt an, ob sich in Sicht des Agenten ein Teammitglied ohne Treibstoff
     * befindet.
     *
     * @return true oder false.
     */
    fun seeLostTeamAgent(): Boolean

    /**
     * Gibt zurück, ob der Agent den Marker sieht.
     *
     * @return true oder false
     */
    fun seeMarker(): Boolean

    /**
     * Gibt zurück, ob der Agent den Turm sieht.
     *
     * @return true oder false
     */
    fun seeTower(): Boolean

    /**
     * Zeigt an, ob der Agent eine Resource sehen kann.
     *
     * @return true oder false.
     */
    val seeResource: Boolean

    /**
     * Zeigt an, ob der Agent eine Resource vom angegebenen Typen sehen kann.
     *
     * @return true oder false.
     */
    fun seeResource(resourceType: ResourceType): Boolean

    /**
     * Der Agent spendet einem Teammitglied Treibstoff
     *
     * @param value Treibstoff. Ein Agent ist Bewegungsunfhig, solange er
     * Treibstoff spendet!
     * Aktionspunkte: value * 2
     * Treibstoff: 1 + value
     * @param value Der zu spendende Treibstoff.
     */
    fun spendTeamAgentFuel(value: Int)

    /**
     * Der Agent dreht sich um 180 Grad in die entgegengesetzte Richtung.
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    fun turnAround()

    /**
     * Der Agent dreht sich um Winkel beta nach links.
     *
     * @param beta Gewünschter Winkel in Grad (0 - 360)
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    fun turnLeft(beta: Int)

    /**
     * Der Agent dreht sich in eine zufällige Richtung.
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    fun turnRandom()

    /**
     * Der Agent dreht sich in eine beliebige, zufällige Richtung mit dem
     * Öffnungswinkel.
     *
     * `opening`°. Aktionspunkte: 1
     * Treibstoff: 1
     *
     * @param opening
     */
    fun turnRandom(opening: Int)

    /**
     * Der Agent dreht sich um Winkel `beta`° nach rechts.
     *
     * @param beta Gewünschter Winkel in Grad (0 - 360)
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    fun turnRight(beta: Int)

    /**
     * Der Agent dreht sich zu einer nahen Resource.
     * Falls er keine findet, dreht er sich nicht
     *
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    fun turnToResource()

    /**
     * Der Agent dreht sich zu einem feindlichen Agenten in Sichtweite.
     * Falls kein feindlicher Agent in der Nähe ist, kostet diese Aktion nur APs.
     *
     * Optional kann ein Winkel `beta`° angegeben werden, um den Agenten zusätzlich weiter rechts rum zu drehen.
     *
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    fun turnToAdversaryAgent(beta: Int = 0)

    /**
     * Der Agent dreht sich zu einer nahen Resource eines bestimmten Typs.
     * Falls er keine findet, dreht er sich nicht
     *
     * Aktionspunkte: 1
     * Treibstoff: 1
     * @param resourceType Der Typ der Resource
     */
    fun turnToResource(resourceType: ResourceType)

    /**
     * Der Agent läuft amok.
     */
    fun kill()

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
}
