package org.openbase.planetsudo.game

import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class ManualPlayerTeamTest {
    @Test
    fun `manual player teams reference their matching strategies`() {
        val player1 = Team.readTeamData(File("teams/ManualPlayer1.team"))
        val player2 = Team.readTeamData(File("teams/ManualPlayer2.team"))

        assertEquals("ManualPlayer1", player1.strategy)
        assertEquals("ManualPlayer2", player2.strategy)
        assertEquals("Manual Player 1", player1.name)
        assertEquals("Manual Player 2", player2.name)
    }
}
