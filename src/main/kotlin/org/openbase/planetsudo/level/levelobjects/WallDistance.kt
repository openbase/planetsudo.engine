package org.openbase.planetsudo.level.levelobjects

import org.openbase.planetsudo.level.levelobjects.Agent.Companion.AGENT_SIZE

/**
 * Maximum distance used by [AgentSpecialInterface.seeWallAtLeft] and
 * [AgentSpecialInterface.seeWallAtRight] when checking for a wall.
 *
 * The distance is measured in pixels from the agent's center in the requested direction.
 * It is a search limit, not the measured distance to a wall. Each step represents a multiple
 * of [AGENT_SIZE].
 *
 * @property pixel Maximum search distance in pixels.
 */
enum class WallDistance(val pixel: Int) {
    /** Search up to one agent size away. */
    VERY_CLOSE(pixel = agentFactor(1.0)),

    /** Search up to one and a half agent sizes away. */
    CLOSE(pixel = agentFactor(1.5)),

    /** Search up to two agent sizes away. */
    FAR(pixel = agentFactor(2.0)),

    /** Search up to two and a half agent sizes away. */
    VERY_FAR(pixel = agentFactor(2.5)),
}

private fun agentFactor(factor: Double) = (AGENT_SIZE * factor).toInt()
