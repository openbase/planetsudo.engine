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
interface MothershipInterface {
    /**
     * Löscht den Marker sofern dieser gesetzt wurde.
     */
    fun clearMarker()

    /**
     * Gibt zurück, wie viele Agenten das Team hat.
     * @return Anzahl der Agenten
     */
    val agentCount: Int

    /**
     * Gibt die Schildstrke des Mutterschiffs wieder.
     * @return Schildstrke von 0 - 100 als ganze Zahl.
     */
    val shieldForce: Int

    /**
     * Gibt zurück, ob das Mutterschiff Treibstoff hat.
     * @return true oder false.
     */
    fun hasFuel(): Boolean

    /**
     * Gibt an wie viel Treibstoff das Mutterschiff noch besitzt.
     * @return
     */
    val fuel: Int

    /**
     * Gibt an wie viel Treibstoff das Mutterschiff maximal besitzen kann.
     * @return
     */
    val fuelVolume: Int

    /**
     * Gibt prozentual an wie viel Treibstoff das Mutterschiff aktuell besitzt.
     * @return
     */
    val fuelInPercent: Int

    /**
     * Gibt zurück, ob das Mutterschiff brennt und somit Treibstoff verliert.
     * @return true oder false.
     */
    val isBurning: Boolean

    /**
     * Gibt zurück, ob das Mutterschiff und somit der Schutzschild beschdigt ist.
     * @return true oder false.
     */
    val isDamaged: Boolean

    /**
     * Gibt zurück ob der Marker gesetzt wurde.
     * @return true oder false.
     */
    val isMarkerDeployed: Boolean

    /**
     * Gibt zurück, ob das Mutterschiff maximal beschdigt wurde. (Das Schutzschild is komplett zerstrt)
     * @return true oder false.
     */
    val isMaxDamaged: Boolean

    /**
     * Gibt zurück ob ein Agent des eigenen Teams Hilfe benötigt.
     * @return true oder false.
     */
    fun needSomeoneSupport(): Boolean
}
