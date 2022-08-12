package com.hamza.wallpap.data.screens.wallpaper

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WallpaperFullScreenViewModel: ViewModel() {
    var id by mutableStateOf(0)
    var dialogState = mutableStateOf(false)
    var setWallpaperAs by mutableStateOf(1)
    var interstitalState = mutableStateOf(false)
}