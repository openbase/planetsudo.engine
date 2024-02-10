package org.openbase.planetsudo.game.strategy

import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.`should be equal to`
import org.openbase.planetsudo.game.SwatTeam
import org.openbase.planetsudo.level.levelobjects.AgentMock
import kotlin.test.Test

class AbstractStrategyTest {

    @Test
    fun `all rule dsl should assigned to all`() {
        var rule: Rule? = null
        val agent = mockk<AgentMock>(relaxed = true)
        var condition = false
        object : AbstractStrategy(agent) {
            init {
                loadRules()
            }

            override fun loadAgentCount() = 1
            override fun loadRules() {
                rule = "Rule for All" all inCase { condition } then { agent.turnRandom() }
            }
        }

        rule!!.swatTeams.size `should be equal to` 1
        rule!!.swatTeams.first() `should be equal to` SwatTeam.ALL
        condition = true
        rule!!.constraint() `should be equal to` true
        condition = false
        rule!!.constraint() `should be equal to` false
        rule!!.action()
        verify { agent.turnRandom() }
    }

    @Test
    fun `commander rule dsl should assigned to commander`() {
        var rule: Rule? = null
        val agent = mockk<AgentMock>(relaxed = true)
        var condition = false
        object : AbstractStrategy(agent) {
            init {
                loadRules()
            }

            override fun loadAgentCount() = 1
            override fun loadRules() {
                rule = "Rule for commander" commander inCase { condition } then { agent.cancelSupport() }
            }
        }

        rule!!.swatTeams.size `should be equal to` 1
        rule!!.swatTeams.first() `should be equal to` SwatTeam.COMMANDER
        condition = true
        rule!!.constraint() `should be equal to` true
        condition = false
        rule!!.constraint() `should be equal to` false
        rule!!.action()
        verify { agent.cancelSupport() }
    }

    @Test
    fun `swat rule dsl should assigned to commander`() {
        var rule: Rule? = null
        val agent = mockk<AgentMock>(relaxed = true)
        var condition = false
        object : AbstractStrategy(agent) {
            init {
                loadRules()
            }

            override fun loadAgentCount() = 1
            override fun loadRules() {
                rule = "Rule for alpha" swat SwatTeam.ALPHA inCase { condition } then { agent.turnAround() }
            }
        }

        rule!!.swatTeams.size `should be equal to` 1
        rule!!.swatTeams.first() `should be equal to` SwatTeam.ALPHA
        condition = true
        rule!!.constraint() `should be equal to` true
        condition = false
        rule!!.constraint() `should be equal to` false
        rule!!.action()
        verify { agent.turnAround() }
    }

    @Test
    fun `non commander rule dsl should assigned to all except of commander`() {
        var rule: Rule? = null
        val agent = mockk<AgentMock>(relaxed = true)
        var condition = false
        object : AbstractStrategy(agent) {
            init {
                loadRules()
            }

            override fun loadAgentCount() = 1
            override fun loadRules() {
                rule = "Rule for alpha" swat SwatTeam.NOT_COMMANDER inCase { condition } then { agent.orderSupport() }
            }
        }

        rule!!.swatTeams.size `should be equal to` 2
        rule!!.swatTeams.sorted() `should be equal to` setOf(SwatTeam.NOT_COMMANDER, SwatTeam.ALL).sorted()
        condition = true
        rule!!.constraint() `should be equal to` true
        condition = false
        rule!!.constraint() `should be equal to` false
        rule!!.action()
        verify { agent.orderSupport() }
    }
}
