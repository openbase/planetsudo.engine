package org.openbase.planetsudo.game.strategy

import io.mockk.mockk
import org.openbase.jps.core.JPService
import org.openbase.planetsudo.level.levelobjects.Agent
import java.awt.event.KeyEvent
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ManualControlTest {
    init {
        JPService.setupJUnitTestMode()
    }

    @Test
    fun `manual control trigger tracks held keys`() {
        val strategy = object : StrategyLevelLegacy(mockk<Agent>(relaxed = true)) {
            override fun loadAgentCount() = 1

            override fun loadRules() = Unit

            fun isKeyPressed(keyCode: Int) = manualControl(keyCode)
        }

        ManualControlInput.clear()
        try {
            assertFalse(strategy.isKeyPressed(KeyEvent.VK_UP))
            ManualControlInput.keyPressed(KeyEvent.VK_UP)
            assertTrue(strategy.isKeyPressed(KeyEvent.VK_UP))
            ManualControlInput.keyReleased(KeyEvent.VK_UP)
            assertFalse(strategy.isKeyPressed(KeyEvent.VK_UP))
        } finally {
            ManualControlInput.clear()
        }
    }
}
