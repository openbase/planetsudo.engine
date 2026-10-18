package org.openbase.planetsudo.game.strategy

import io.mockk.mockk
import org.openbase.jps.core.JPService
import org.openbase.planetsudo.level.levelobjects.Agent
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ManualPlayerStrategyTest {
    init {
        JPService.setupJUnitTestMode()
    }

    @Test
    fun `manual gamepad actions are isolated by player`() {
        val strategy = object : StrategyLevelLegacy(mockk<Agent>(relaxed = true)) {
            override fun loadAgentCount() = 1

            override fun loadRules() = Unit

            fun isPressed(player: ManualPlayer, action: ManualAction) = manualControl(player, action)
        }

        ManualControlInput.clear()
        try {
            ManualControlInput.setGamepadActions(ManualPlayer.PLAYER_1, setOf(ManualAction.GO_FORWARD))

            assertTrue(strategy.isPressed(ManualPlayer.PLAYER_1, ManualAction.GO_FORWARD))
            assertFalse(strategy.isPressed(ManualPlayer.PLAYER_2, ManualAction.GO_FORWARD))
        } finally {
            ManualControlInput.clear()
        }
    }
}
