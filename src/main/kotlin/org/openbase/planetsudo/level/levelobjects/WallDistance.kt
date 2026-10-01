package org.openbase.planetsudo.level.levelobjects

import org.openbase.planetsudo.level.levelobjects.Agent.Companion.AGENT_SIZE

/**
 * Maximum wall-search distance in pixels, measured from the agent's center.
 * The values represent 1, 1.5, 2, and 2.5 times [AGENT_SIZE], respectively.
 */
enum class WallDistance(val pixel: Int) {
    VERY_CLOSE(pixel = agentFactor(1.0)),
    CLOSE(pixel = agentFactor(1.5)),
    FAR(pixel = agentFactor(2.0)),
    VERY_FAR(pixel = agentFactor(2.5)),
}

private fun agentFactor(factor: Double) = (AGENT_SIZE * factor).toInt()
