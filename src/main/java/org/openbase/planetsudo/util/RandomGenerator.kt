package org.openbase.planetsudo.util

import org.openbase.jul.exception.InvalidStateException /*-
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
import java.util.*

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
object RandomGenerator {
    private val RANDOM = Random(System.currentTimeMillis())

    @JvmStatic
    @Throws(InvalidStateException::class)
    fun getRandom(min: Int, max: Int): Int {
        if (min > max) {
            throw InvalidStateException("Could not generate random value, because min value is greater than max value!")
        }
        //TODO 
        // http://stackoverflow.com/questions/738629/math-random-versus-random-nextintint
        return (Math.random() * (max - min)).toInt() + min
        // return RANDOM.nextInt(min)ints(min, max+1).;
    }
}
