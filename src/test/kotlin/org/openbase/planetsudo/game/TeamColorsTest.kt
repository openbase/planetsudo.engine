package org.openbase.planetsudo.game

import java.awt.Color
import kotlin.test.Test
import kotlin.test.assertEquals

class TeamColorsTest {
    @Test
    fun `invert color when team colors are the same`() {
        assertEquals(Color(0, 255, 255), TeamColors.contrastingColor(Color.RED, Color.RED))
    }

    @Test
    fun `invert color when team colors are similar`() {
        assertEquals(Color(35, 235, 235), TeamColors.contrastingColor(Color(220, 20, 20), Color.RED))
    }

    @Test
    fun `preserve color when team colors are distinct`() {
        assertEquals(Color.RED, TeamColors.contrastingColor(Color.RED, Color.BLUE))
    }

    @Test
    fun `preserve color when there is no opponent color`() {
        assertEquals(Color.RED, TeamColors.contrastingColor(Color.RED, null))
    }
}
