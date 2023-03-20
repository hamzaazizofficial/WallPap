package com.hamza.wallpap.ui.screens.wallpaper

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
}