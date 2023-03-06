package com.hamza.wallpap.ui.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    val themes = listOf(
        "Original",
        "Dracula"
    )
    var value = mutableStateOf(themes[0])

    var dialogState = mutableStateOf(false)
    var dialogStateAbout = mutableStateOf(false)
}