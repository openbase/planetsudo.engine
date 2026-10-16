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

    @Test
    fun `ignore alpha channel on returned color`() {
        assertEquals(Color(20, 40, 60), TeamColors.contrastingColor(Color(20, 40, 60, 32), null))
    }

    @Test
    fun `ignore alpha channel when comparing colors`() {
        assertEquals(
            Color(0, 255, 255),
            TeamColors.contrastingColor(Color(255, 0, 0, 32), Color(255, 0, 0, 128)),
        )
    }

    @Test
    fun `use red instead of black for white team colors`() {
        assertEquals(Color.RED, TeamColors.contrastingColor(Color.WHITE, Color.WHITE))
    }

    @Test
    fun `use red instead of black for nearly white team colors`() {
        assertEquals(Color.RED, TeamColors.contrastingColor(Color(250, 250, 250), Color(250, 250, 250)))
    }

    @Test
    fun `use red when inversion remains similar for middle gray`() {
        assertEquals(Color.RED, TeamColors.contrastingColor(Color(128, 128, 128), Color(128, 128, 128)))
    }

    @Test
    fun `use red when inversion remains similar for nearby gray colors`() {
        assertEquals(Color.RED, TeamColors.contrastingColor(Color(130, 130, 130), Color(128, 128, 128)))
    }
}
