package org.openbase.planetsudo.level

import kotlin.math.max

enum class LevelSize(val label: String) {
    SMALL("Klein"),
    MEDIUM("Mittel"),
    LARGE("Groß"),
    UNKNOWN("?"),
    ;

    companion object {
        fun fromLevel(level: AbstractLevel): LevelSize {
            val size = max(level.levelBorderPolygon.bounds2D.width, level.levelBorderPolygon.bounds2D.height)
            return when {
                size <= 1000 -> SMALL
                size <= 1500 -> MEDIUM
                else -> LARGE
            }
        }
    }
}
