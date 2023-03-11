package com.hamza.wallpap.ui.screens.editor

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class CustomWallpaperViewModel : ViewModel() {
    var boxColor = mutableStateOf(Color(0xF1FFFFFF))
}