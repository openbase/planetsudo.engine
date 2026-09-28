package org.openbase.planetsudo.level.levelobjects

import org.openbase.planetsudo.level.LevelSize

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
interface TowerInterface {

    val de get() = TowerInterfaceGermanWrapper(this)

    /**
     * Returns whether the tower has any fuel left.
     */
    fun hasFuel(): Boolean

    /**
     * Current fuel amount of the tower.
     */
    val fuel: Int

    /**
     * Maximum fuel capacity of the tower.
     */
    val fuelVolume: Int

    /**
     * Current fuel level as percentage.
     */
    val fuelInPercent: Int

    /**
     * Makes the tower perform an attack.
     */
    fun attack()

    /**
     * Repairs the tower (increases shield strength).
     */
    fun repair()

    /**
     * Returns whether the tower is burning (losing fuel).
     */
    val isBurning: Boolean

    /**
     * Shield points of the tower.
     */
    val shieldPoints: Int

    /**
     * Returns whether the tower is completely destroyed (no shield left).
     */
    val isMaxDamaged: Boolean

    /**
     * Returns whether the tower is damaged (shield not full).
     */
    val isDamaged: Boolean

    /**
     * Current tower type (DefenceTower or ObservationTower). Unknown if no tower is constructed.
     */
    val type: Tower.TowerType

    /**
     * Scans and updates the currently detected level size.
     */
    fun scanLevelSize()

    /**
     * The last scanned level size.
     */
    val levelSize: LevelSize
}
