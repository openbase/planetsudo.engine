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
interface AgentInterface {
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
     * Gibt die verfügbaren Aktionspunkte wieder.
     *
     * @return Die Aktionspunkte als ganze Zahl. (z.B. 2051)
     */
    val actionPoints: Int

    /**
     * Gibt die Richtung des Agenten in Grad wieder. Rechts = 0° Runter = 90°
     * Links = 180° Hoch = 270°
     *
     * @return Den Winkel von 0 - 360° als ganze Zahl. (z.B. 128)
     */
    val angle: Int

    /**
     * Zeigt den verbliebenen Treibstoff des Agenten an.
     *
     * @return Den verbliebenen Treibstoff als ganz Zahl.
     */
    val fuel: Int

    /**
     * Gibt den verbliebenen Treibstoff in Prozent an.
     *
     * @return Treibstoffwert in Prozent als ganze Zahl. (z.B. 47 bei 47%
     * verbliebenen Treibstoff)
     */
    val fuelInPercent: Int

    /**
     * Gibt das Maximalvolumen vom Treibstoff eines Agenten an.
     *
     * @return
     */
    val fuelVolume: Int

    /**
     * Gibt den Typ der Resource wieder, welche der Agent berührt.
     *
     * @return Den Ressourcenwert
     */
    val resourceType: ResourceType?

    /**
     * Gibt die Punkte des Teams wieder.
     *
     * @return Den Punktestand als ganze Zahl (z.B. 47)
     */
    val teamPoints: Int

    /**
     * Der Agent bewegt sich geradeaus.
     * Aktionspunkte: 3 (+ 3 wenn resource geladen)
     * Treibstoff: 1
     */
    fun go()

    /**
     * Der Agent dreht sich um `beta`° nach links und bewegt sich anschließend geradeaus.
     * Aktionspunkte: 3 (+ 3 wenn resource geladen)
     * Treibstoff: 1
     *
     *
     * @param beta Drehwinkel
     */
    fun goLeft(beta: Int)

    /**
     * Der Agent dreht sich um `beta`° nach rechts und bewegt sich anschließend geradeaus.
     * Aktionspunkte: 3 (+ 3 wenn resource geladen)
     * Treibstoff: 1
     *
     * @param beta Drehwinkel
     */
    fun goRight(beta: Int)

    /**
     * Der Agent bewegt sich in Richtung des Markers, falls dieser gesetzt
     * wurde. Hierbei wird ein Weg berechnet, welcher auf krzester Distanz
     * zum Marker fhrt. Um Hindernisse bewegt sich der Agent hierbei
     * automatisch herum.
     * Aktionspunkte: 4 (+ 4 wenn resource geladen)
     * Treibstoff: 1
     */
    fun goToMarker()

    /**
     * Der Agent bewegt sich auf das Mutterschiff zu. Hierbei wird ein Weg
     * berechnet, welcher auf krzester Distanz zum Mutterschiff
     * fhrt. Um Hindernisse bewegt sich der Agent hierbei automatisch
     * herum.
     * Aktionspunkte: 4 (+ 4 wenn resource geladen)
     * Treibstoff: 1
     */
    fun goToMothership()

    /**
     * Der Agent bewegt sich zur Resource.
     * Aktionspunkte: 4 (+ 4 wenn resource geladen)
     * Treibstoff: 1
     */
    fun goToResource()

    /**
     * Der Agent bewegt sich zur Resource vom angegebenen Typen.
     * Aktionspunkte: 4 + (+ 4 wenn resource geladen)
     * Treibstoff: 1
     * @param resourceType
     */
    fun goToResource(resourceType: ResourceType)

    /**
     * Gehe zu einem Agenten der Hilfe benötigt.
     * Aktionspunkte: 4 + (+ 4 wenn resource geladen)
     * Treibstoff: 1
     */
    fun goToSupportAgent()

    /**
     * Gibt zurück, ob der Agent Treibstoff hat.
     *
     * @return true oder false.
     */
    fun hasFuel(): Boolean

    /**
     * Gibt zurück, ob der Agent seine Mine noch trägt.
     *
     * @return true oder false.
     */
    fun hasMine(): Boolean

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
     * Zeigt an, ob der Agent eine Resource trägt.
     *
     * @return true oder false
     */
    val isCarryingResource: Boolean

    /**
     * Gibt an, ob der Agent eine Resource vom `type` trägt.
     *
     * @param type
     * @return true oder false.
     */
    fun isCarryingResource(type: ResourceType): Boolean

    /**
     * Zeigt an, ob der Agent eine Resource trägt.
     *
     * @return true oder false
     */
    @Deprecated("Typo fixed", replaceWith = ReplaceWith("isCarryingResource"))
    val isCarringResource: Boolean get() = isCarryingResource

    /**
     * Gibt an, ob der Agent eine Resource vom `type` trägt.
     *
     * @param type
     * @return true oder false.
     */
    @Deprecated("Typo fixed", replaceWith = ReplaceWith("isCarryingResource"))
    fun isCarringResource(type: ResourceType): Boolean = isCarryingResource(type)

    /**
     * Gibt an, ob ein Zusammensto mit einer Wand bevorsteht.
     *
     * @return true oder false.
     */
    val isCollisionDetected: Boolean

    /**
     * Gibt zurück, ob der Agent ein Commander ist oder nicht.
     *
     * @return true oder false
     */
    val isCommander: Boolean

    /**
     * Gibt zurück, ob der Agent Bewegungsunfähig ist. (Zerstört
     * oder ohne Treibstoff)
     *
     * @return true oder false.
     */
    val isDisabled: Boolean

    /**
     * Abfrage, ob der Agent sich im Kampf befindet oder nicht.
     *
     * @return true oder false.
     */
    val isFighting: Boolean

    /**
     * Abfrage, ob dieser agent Support angefordert hat!
     * !!! Nicht ob irgend einer Support angefordert hat!!!
     * Hierfür mothership.needSomeoneSupport(); benutzen.
     *
     * @return true oder false
     */
    val isSupportOrdered: Boolean

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
     * Gibt zurück, ob der Agent eine Resource vom Typ `type` berührt und diese somit aufgeben kann.
     *
     * @param type
     * @return
     */
    fun isTouchingResource(type: ResourceType): Boolean

    /**
     * Gibt an, ob der Agent unter Beschuss steht.
     *
     * @return true oder false.
     */
    val isUnderAttack: Boolean

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
    fun seeAdversaryAgent(): Boolean

    /**
     * Zeigt an, ob der Agent einen Agenten des eigenen Teams sieht.
     *
     * @return true oder false.
     */
    fun seeTeamAgent(): Boolean

    /**
     * Zeigt an, ob der Agent das feindliche Mutterschiff sieht.
     *
     * @return true oder false.
     */
    fun seeAdversaryMothership(): Boolean

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
    fun seeResource(): Boolean

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
     * Der Agent dreht sich um Winkel {
     *
     * `beta`° nach rechts.
     *
     * @param beta Gewünschter Winkel in Grad (0 - 360)
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    fun turnRight(beta: Int)

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
    fun erectTower(type: TowerType)

    /**
     * Baut einen Turm wieder ab der zuvor aufgestellt wurde.
     * Diese Aktion kann nur vom Commander durchgeführt werden, und zwar nur dann, wenn er in unmittelbarer Nähe des Turms ist.
     */
    @Deprecated("NOT YET SUPPORTED")
    fun dismantleTower()

    /**
     * Überprüft, ob dieser Agent einen Turm aufbauen könnte.
     * Bedenke das nur Commander einen Turm trägt!
     * @return
     */
    @Deprecated("NOT YET SUPPORTED")
    fun hasTower(): Boolean
}
