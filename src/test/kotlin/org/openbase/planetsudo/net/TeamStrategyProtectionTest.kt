package org.openbase.planetsudo.net

import org.junit.jupiter.api.io.TempDir
import org.openbase.planetsudo.game.TeamData
import java.awt.Color
import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TeamStrategyProtectionTest {
    @TempDir
    lateinit var strategyDirectory: Path

    @Test
    fun `keeps level strategies`() {
        assertTrue(shouldPreserveTeam(team("LevelOne"), strategyDirectory.toFile()))
        assertTrue(shouldPreserveTeam(team("LevelTwo"), strategyDirectory.toFile()))
    }

    @Test
    fun `keeps a strategy defined in the local strategy directory`() {
        Files.createFile(strategyDirectory.resolve("LocalStrategy.kt"))

        assertTrue(shouldPreserveTeam(team("LocalStrategy"), strategyDirectory.toFile()))
    }

    @Test
    fun `does not keep a strategy that is only provided by a dependency`() {
        assertFalse(shouldPreserveTeam(team("DependencyStrategy"), strategyDirectory.toFile()))
    }

    private fun team(strategy: String) = TeamData("test", Color.BLACK, strategy)
}
