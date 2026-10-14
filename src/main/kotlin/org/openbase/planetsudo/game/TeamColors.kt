package org.openbase.planetsudo.game

import java.awt.Color

internal object TeamColors {
    private const val MAX_SIMILARITY_DISTANCE_SQUARED = 10_000

    fun contrastingColor(teamColor: Color, opponentColor: Color?): Color {
        if (opponentColor == null) {
            return teamColor
        }

        val redDifference = teamColor.red - opponentColor.red
        val greenDifference = teamColor.green - opponentColor.green
        val blueDifference = teamColor.blue - opponentColor.blue
        val distanceSquared =
            redDifference * redDifference +
                greenDifference * greenDifference +
                blueDifference * blueDifference

        return if (distanceSquared <= MAX_SIMILARITY_DISTANCE_SQUARED) {
            Color(255 - teamColor.red, 255 - teamColor.green, 255 - teamColor.blue, teamColor.alpha)
        } else {
            teamColor
        }
    }
}
