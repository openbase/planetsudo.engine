package org.openbase.planetsudo.level.levelobjects

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
class MothershipInterfaceGermanWrapper(private val mothership: MothershipInterface) {
    /**
     * Löscht den Marker sofern dieser gesetzt wurde.
     */
    fun loescheMarkierung() = mothership.clearMarker()

    /**
     * Gibt zurück, wie viele Agenten das Team hat.
     * @return Anzahl der Agenten
     */
    val agentenZahl get() = mothership.agentCount

    /**
     * Gibt die Schildstaerke des Mutterschiffs wieder.
     * @return Schildstaerke von 0 - 100 als ganze Zahl.
     */
    val schildstaerke get() = mothership.shieldForce

    /**
     * Gibt zurück, ob das Mutterschiff Treibstoff hat.
     * @return true oder false.
     */
    fun hatTreibstoff() = mothership.hasFuel()

    /**
     * Gibt an wie viel Treibstoff das Mutterschiff noch besitzt.
     * @return
     */
    val treibstoff get() = mothership.fuel

    /**
     * Gibt an wie viel Treibstoff das Mutterschiff maximal besitzen kann.
     * @return
     */
    val treibstoffVolumen get() = mothership.fuelVolume

    /**
     * Gibt prozentual an wie viel Treibstoff das Mutterschiff aktuell besitzt.
     * @return
     */
    val treibstoffInProzent get() = mothership.fuelInPercent

    /**
     * Gibt zurück, ob das Mutterschiff brennt und somit Treibstoff verliert.
     * @return true oder false.
     */
    val istAmBrennen get() = mothership.isBurning

    /**
     * Gibt zurück, ob das Mutterschiff und somit der Schutzschild beschdigt ist.
     * @return true oder false.
     */
    val istBeschaedigt get() = mothership.isDamaged

    /**
     * Gibt zurück ob der Marker gesetzt wurde.
     * @return true oder false.
     */
    val istMarkierungGesetzt get() = mothership.isMarkerDeployed

    /**
     * Gibt zurück, ob das Mutterschiff maximal beschdigt wurde. (Das Schutzschild is komplett zerstrt)
     * @return true oder false.
     */
    val istMaximalBeschaedigt get() = mothership.isMaxDamaged

    /**
     * Gibt zurück ob ein Agent des eigenen Teams Hilfe benötigt.
     * @return true oder false.
     */
    fun braucheUnterstuetzung() = mothership.needSomeoneSupport()
}
