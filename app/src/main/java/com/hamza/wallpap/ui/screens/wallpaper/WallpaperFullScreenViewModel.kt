package com.hamza.wallpap.ui.screens.wallpaper

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WallpaperFullScreenViewModel: ViewModel() {
    var id by mutableStateOf(0)
    var dialogState = mutableStateOf(false)
    var setWallpaperAs by mutableStateOf(1)
    var interstitalState = mutableStateOf(false)
    var downloadWallpaperChecked = mutableStateOf(false)
    var zoomOutWallpaperChecked = mutableStateOf(false)
    var turnHdWallpaperChecked = mutableStateOf(false)

    var redColorSliderPosition = mutableStateOf(0f)
    var blueColorSliderPosition =  mutableStateOf(0f)
    var greenColorSliderPosition =   mutableStateOf(0f)
    var alphaColorSliderPosition =  mutableStateOf(255f)
    var redColorValue =  mutableStateOf(0)
    var blueColorValue = mutableStateOf(0)
    var greenColorValue = mutableStateOf(0)
    var alphaColorValue = mutableStateOf(255)
    var saturationSliderValue = mutableStateOf(1f)
    var saturationSliderPosition = mutableStateOf(1f)
    var blurSliderValue = mutableStateOf(0f)
    var blurSliderPosition = mutableStateOf(0f)

//    var image = mutableStateOf<Bitmap?>(null)
}