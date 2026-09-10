package org.openbase.planetsudo.level.levelobjects

import org.openbase.planetsudo.level.levelobjects.Agent.Companion.AGENT_SIZE

enum class WallDistance(val pixel: Int) {
    VERY_CLOSE(pixel = OFFSET + agentFactor(0.1)),
    CLOSE(pixel = OFFSET + agentFactor(0.5)),
    FAR(pixel = OFFSET + agentFactor(1.0)),
    FAR_AWAY(pixel = OFFSET + agentFactor(1.5)),
    VERY_FAR_AWAY(pixel = OFFSET + agentFactor(2.0)),
}

private val OFFSET = (AGENT_SIZE / 2)
private fun agentFactor(factor: Double) = (AGENT_SIZE * factor).toInt()
