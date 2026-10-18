package org.openbase.planetsudo.game.strategy

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.`should be equal to`
import org.openbase.jps.core.JPService
import org.openbase.planetsudo.game.SwatTeam
import org.openbase.planetsudo.level.levelobjects.Agent
import kotlin.test.Test
import kotlin.test.assertEquals

class AbstractStrategyTest {

    init {
        JPService.setupJUnitTestMode()
    }

    @Test
    fun `rule dsl should assign rule to all`() {
        var rule: Rule? = null
        val agent = mockk<Agent>(relaxed = true)
        var condition = false
        object : StrategyLevelLegacy(agent) {
            init {
                loadRules()
            }

            override fun loadAgentCount() = 1
            override fun loadRules() {
                rule = "Rule for all" all inCase { condition } then { agent.turnRandom() }
            }
        }

        rule!!.run {
            swatTeams.size `should be equal to` 1
            swatTeams.first() `should be equal to` SwatTeam.ALL
            condition = true
            constraint() `should be equal to` true
            condition = false
            constraint() `should be equal to` false
            action()
        }
        verify { agent.turnRandom() }
    }

    @Test
    fun `rule dsl should assign rule to commander`() {
        var rule: Rule? = null
        val agent = mockk<Agent>(relaxed = true)
        var condition = false
        object : StrategyLevelLegacy(agent) {
            init {
                loadRules()
            }

            override fun loadAgentCount() = 1
            override fun loadRules() {
                rule = "Rule for commander" commander inCase { condition } then { agent.cancelSupport() }
            }
        }

        rule!!.run {
            swatTeams.size `should be equal to` 1
            swatTeams.first() `should be equal to` SwatTeam.COMMANDER
            condition = true
            constraint() `should be equal to` true
            condition = false
            constraint() `should be equal to` false
            action()
        }
        verify { agent.cancelSupport() }
    }

    @Test
    fun `rule dsl should assign rule to swat alpha`() {
        var rule: Rule? = null
        val agent = mockk<Agent>(relaxed = true)
        var condition = false
        object : StrategyLevelLegacy(agent) {
            init {
                loadRules()
            }

            override fun loadAgentCount() = 1
            override fun loadRules() {
                rule = "Rule for alpha" swat SwatTeam.ALPHA inCase { condition } then { agent.turnAround() }
            }
        }

        rule!!.run {
            swatTeams.size `should be equal to` 1
            swatTeams.first() `should be equal to` SwatTeam.ALPHA
            condition = true
            constraint() `should be equal to` true
            condition = false
            constraint() `should be equal to` false
            action()
        }
        verify { agent.turnAround() }
    }

    @Test
    fun `rule dsl should assign rule not to commander`() {
        var rule: Rule? = null
        val agent = mockk<Agent>(relaxed = true)
        var condition = false
        object : StrategyLevelLegacy(agent) {
            init {
                loadRules()
            }

            override fun loadAgentCount() = 1
            override fun loadRules() {
                rule = "Rule for non commander" nonCommander inCase { condition } then { agent.requestSupport() }
            }
        }

        rule!!.run {
            swatTeams.size `should be equal to` 2
            swatTeams.sorted() `should be equal to` setOf(SwatTeam.NOT_COMMANDER, SwatTeam.ALL).sorted()
            condition = true
            constraint() `should be equal to` true
            condition = false
            constraint() `should be equal to` false
            action()
        }
        verify { agent.requestSupport() }
    }

    @Test
    fun `selected gamepad overrides commander rules without changing other agents`() {
        ManualControlInput.selectGamepad(ManualPlayer.PLAYER_1, "0")
        ManualControlInput.clear()

        try {
            val commander = mockk<Agent>(relaxed = true)
            every { commander.isCommander } returns true
            every { commander.mothership.id } returns 0
            every { commander.isMemberOfSwatTeam(any()) } returns true
            var aiCommanderActions = 0
            val commanderStrategy =
                object : StrategyLevelLegacy(commander) {
                    init {
                        loadRules()
                        configureManualCommanderControl()
                    }

                    override fun loadAgentCount() = 1

                    override fun loadRules() {
                        "AI commander rule" commander inCase { true } then { aiCommanderActions++ }
                    }
                }

            commanderStrategy.executeRule()
            assertEquals(0, aiCommanderActions)

            ManualControlInput.setGamepadActions(ManualPlayer.PLAYER_1, setOf(ManualAction.GO_FORWARD))
            commanderStrategy.executeRule()
            verify(exactly = 1) { commander.go() }
            assertEquals(0, aiCommanderActions)

            ManualControlInput.setGamepadActions(ManualPlayer.PLAYER_1, setOf(ManualAction.ORDER_SUPPORT))
            commanderStrategy.executeRule()
            verify(exactly = 1) { commander.requestSupport() }

            every { commander.hasRequestedSupport } returns true
            commanderStrategy.executeRule()
            verify(exactly = 1) { commander.cancelSupport() }

            val teammate = mockk<Agent>(relaxed = true)
            every { teammate.isCommander } returns false
            every { teammate.isMemberOfSwatTeam(any()) } returns true
            var aiTeammateActions = 0
            val teammateStrategy =
                object : StrategyLevelLegacy(teammate) {
                    init {
                        loadRules()
                        configureManualCommanderControl()
                    }

                    override fun loadAgentCount() = 1

                    override fun loadRules() {
                        "AI teammate rule" all inCase { true } then { aiTeammateActions++ }
                    }
                }

            teammateStrategy.executeRule()
            assertEquals(1, aiTeammateActions)
        } finally {
            ManualControlInput.clear()
            ManualControlInput.selectGamepad(ManualPlayer.PLAYER_1, null)
        }
    }
}
