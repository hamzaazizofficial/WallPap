package com.hamza.wallpap.util

import kotlinx.coroutines.flow.StateFlow

enum class WallPapTheme {
//    MODE_AUTO,
    MODE_DAY,
    MODE_NIGHT;

    companion object {
        fun fromOrdinal(ordinal: Int) = values()[ordinal]
    }
}

interface ThemeSetting {
    val themeStream: StateFlow<WallPapTheme>
    var theme: WallPapTheme
}