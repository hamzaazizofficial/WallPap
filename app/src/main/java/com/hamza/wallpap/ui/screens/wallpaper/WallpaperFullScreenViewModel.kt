package com.hamza.wallpap.ui.screens.wallpaper

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WallpaperFullScreenViewModel : ViewModel() {
    var id by mutableStateOf(0)
    var setOriginalWallpaperDialog = mutableStateOf(false)
    var setWallpaperAs by mutableStateOf(1)
    var interstitialState = mutableStateOf(false)
    var saturationSliderValue = mutableStateOf(1f)
    var saturationSliderPosition = mutableStateOf(1f)
}