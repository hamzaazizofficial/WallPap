package com.hamza.wallpap.ui.screens.wallpaper

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.ViewModel

class WallpaperFullScreenViewModel : ViewModel() {
    var scale by mutableStateOf(ContentScale.Crop)
    var showFitScreenBtn by mutableStateOf(true)
    var showCropScreenBtn by mutableStateOf(false)
    var id by mutableStateOf(0)
    var setOriginalWallpaperDialog = mutableStateOf(false)
    var setWallpaperAs by mutableStateOf(1)
    var interstitialState = mutableStateOf(0)
    var saturationSliderValue = mutableStateOf(1f)
    var saturationSliderPosition = mutableStateOf(1f)
    var scaleFitState by mutableStateOf(true)
    var scaleCropState by  mutableStateOf(false)
    var scaleStretchState by  mutableStateOf(false)
    var finalScaleState by  mutableStateOf(scaleFitState)
}