package com.hamza.wallpap.data.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SettingsViewmodel : ViewModel() {
    val themes = listOf(
        "Original",
        "Dracula"
    )
    var value = mutableStateOf(themes[0])
}