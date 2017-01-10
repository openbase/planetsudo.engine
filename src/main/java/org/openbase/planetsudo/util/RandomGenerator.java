package org.openbase.planetsudo.util;

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

import java.util.Random;
import org.openbase.jul.exception.InvalidStateException;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class RandomGenerator {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public static int getRandom(int min, int max) throws InvalidStateException {
        if (min > max) {
            throw new InvalidStateException("Could not generate random value, because min value is greater than max value!");
        }
        //TODO 
        // http://stackoverflow.com/questions/738629/math-random-versus-random-nextintint
        return (int) (Math.random() * (max - min)) + min;
        // return RANDOM.nextInt(min)ints(min, max+1).;
    }
}
