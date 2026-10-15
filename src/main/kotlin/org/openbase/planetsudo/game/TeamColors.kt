package org.openbase.planetsudo.game

import java.awt.Color

internal object TeamColors {
    private const val MAX_SIMILARITY_DISTANCE_SQUARED = 10_000
    private const val MIN_VISIBLE_CHANNEL_VALUE = 64

    fun contrastingColor(teamColor: Color, opponentColor: Color?): Color {
        val opaqueTeamColor = teamColor.withoutAlpha()
        if (opponentColor == null) {
            return opaqueTeamColor
        }

        if (distanceSquared(opaqueTeamColor, opponentColor) > MAX_SIMILARITY_DISTANCE_SQUARED) {
            return opaqueTeamColor
        }

        val invertedColor = Color(255 - teamColor.red, 255 - teamColor.green, 255 - teamColor.blue)
        val isTooDark = maxOf(invertedColor.red, invertedColor.green, invertedColor.blue) < MIN_VISIBLE_CHANNEL_VALUE
        val isStillSimilarToOpponent = distanceSquared(invertedColor, opponentColor) <= MAX_SIMILARITY_DISTANCE_SQUARED

        return if (isTooDark || isStillSimilarToOpponent) Color.RED else invertedColor
    }

    fun Color.withoutAlpha() = Color(red, green, blue)

    private fun distanceSquared(first: Color, second: Color): Int {
        val redDifference = first.red - second.red
        val greenDifference = first.green - second.green
        val blueDifference = first.blue - second.blue
        return redDifference * redDifference +
                greenDifference * greenDifference +
                blueDifference * blueDifference
    }
}
