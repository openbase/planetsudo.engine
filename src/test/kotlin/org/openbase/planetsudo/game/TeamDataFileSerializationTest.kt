package org.openbase.planetsudo.game

import org.junit.jupiter.api.io.TempDir
import java.awt.Color
import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class TeamDataFileSerializationTest {
    @TempDir
    lateinit var temporaryDirectory: Path

    @Test
    fun `writes a readable team file that can immediately be deleted`() {
        val teamFile = temporaryDirectory.resolve("team.team")
        val teamData = TeamData("team", Color.BLUE, "LocalStrategy", listOf("member"))

        Team.writeTeamData(teamData, teamFile.toFile())

        val loadedTeamData = Team.readTeamData(teamFile.toFile())

        assertEquals(teamData, loadedTeamData)
        Files.delete(teamFile)
    }
}
