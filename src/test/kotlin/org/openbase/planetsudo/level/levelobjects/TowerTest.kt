package org.openbase.planetsudo.level.levelobjects

import org.openbase.jps.core.JPService
import org.openbase.planetsudo.game.Team
import org.openbase.planetsudo.game.TeamData
import org.openbase.planetsudo.geometry.Point2D
import org.openbase.planetsudo.level.LevelSize
import org.openbase.planetsudo.level.save.SimpleWorld
import java.awt.Color
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TowerTest {
    init {
        JPService.setupJUnitTestMode()
    }

    @Test
    fun `destroyed tower hides its scanned level size until scanned again`() {
        val level = SimpleWorld()
        val mothership = Mothership(0, createTeam(), level)
        val commander = Agent("Commander", true, Mothership.AGENT_FUEL_VOLUME, mothership)
        val tower = mothership.tower

        try {
            tower.construct(Tower.TowerType.ObservationTower, commander)
            assertEquals(level.size, tower.levelSize)

            repeat(100) { tower.attack() }

            assertTrue(tower.isMaxDamaged)
            assertFalse(tower.isOperational)
            assertEquals(LevelSize.UNKNOWN, tower.levelSize)

            tower.repair()

            assertTrue(tower.isOperational)
            assertEquals(LevelSize.UNKNOWN, tower.levelSize)
            tower.scanLevelSize()
            assertEquals(level.size, tower.levelSize)

            tower.deconstruct(commander)
            assertEquals(LevelSize.UNKNOWN, tower.levelSize)
        } finally {
            tower.reset()
        }
    }

    @Test
    fun `observation tower reveals distant resources to its team`() {
        val level = SimpleWorld()
        val mothership = Mothership(0, createTeam(), level)
        val commander = Agent("Commander", true, Mothership.AGENT_FUEL_VOLUME, mothership)
        val agent = Agent("Scout", false, Mothership.AGENT_FUEL_VOLUME, mothership)
        val tower = mothership.tower
        level.addResource(
            Resource(
                level.generateNewResourceID(),
                Resource.ResourceType.Normal,
                level,
                Point2D(900.0, 900.0),
            ),
        )

        try {
            tower.construct(Tower.TowerType.DefenceTower, commander)
            assertFalse(agent.seeResource)
            tower.deconstruct(commander)

            tower.construct(Tower.TowerType.ObservationTower, commander)
            assertTrue(agent.seeResource)
        } finally {
            tower.reset()
        }
    }

    private fun createTeam() = Team(
        TeamData(
            name = "TowerTest",
            teamColor = Color.RED,
            strategy = "SimpleStrategy",
            members = listOf("Test"),
        ),
    )
}
