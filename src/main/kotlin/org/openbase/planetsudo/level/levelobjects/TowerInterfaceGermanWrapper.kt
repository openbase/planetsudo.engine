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
class TowerInterfaceGermanWrapper(private val tower: TowerInterface) {

    /**
     * Gibt zurück, ob der Turm Treibstoff hat.
     */
    fun hatTreibstoff() = tower.hasFuel()

    /**
     * Aktueller Treibstoff des Turms.
     */
    val treibstoff get() = tower.fuel

    /**
     * Maximales Treibstoffvolumen des Turms.
     */
    val treibstoffVolumen get() = tower.fuelVolume

    /**
     * Treibstoff in Prozent.
     */
    val treibstoffInProzent get() = tower.fuelInPercent

    /**
     * Lässt den Turm angreifen.
     */
    fun greifeAn() = tower.attack()

    /**
     * Repariert den Turm.
     */
    fun repariere() = tower.repair()

    /**
     * Gibt zurück, ob der Turm brennt.
     */
    val istAmBrennen get() = tower.isBurning

    /**
     * Gibt die Schildpunkte des Turms zurück.
     */
    val schildPunkte get() = tower.shieldPoints

    /**
     * Gibt zurück, ob der Turm maximal beschädigt ist.
     */
    val istMaximalBeschaedigt get() = tower.isMaxDamaged

    /**
     * Gibt zurück, ob der Turm beschädigt ist.
     */
    val istBeschaedigt get() = tower.isDamaged

    /**
     * Scannt die Level-Größe und aktualisiert die interne Größe.
     */
    fun scanneLevelGroesse() = tower.scanLevelSize()

    /**
     * Zuletzt gescannte Level-Größe.
     */
    val levelGroesse get() = tower.levelSize

    /**
     * Typ des Turms (DefenceTower oder ObservationTower). Null wenn nicht konstruiert.
     */
    val turmTyp get() = tower.type
}
