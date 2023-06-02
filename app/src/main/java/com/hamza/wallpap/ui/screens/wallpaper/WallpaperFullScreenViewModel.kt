package com.hamza.wallpap.ui.screens.wallpaper

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class WallpaperFullScreenViewModel : ViewModel() {

//    var smallSizeImage =mutableStateOf<Bitmap?>(null)
//    var originalImage = mutableStateOf<Bitmap?>(null)
//    val fullUrl = mutableStateOf<String>("")
//    init {
//        viewModelScope.launch {
//            originalImage.value = getBitmapFromUrl(fullUrl.value)
//            smallSizeImage.value = loadReducedSizeBitmapFromUrl(fullUrl.value)
//        }
//    }

    var id by mutableStateOf(0)
    var setOriginalWallpaperDialog = mutableStateOf(false)
    var setModifiedWallpaperDialog = mutableStateOf(false)
    var setWallpaperAs by mutableStateOf(1)
    var interstitalState = mutableStateOf(false)
    var downloadWallpaperChecked = mutableStateOf(false)
    var zoomOutWallpaperChecked = mutableStateOf(false)
    var turnHdWallpaperChecked = mutableStateOf(false)

    var redColorSliderPosition = mutableStateOf(0f)
    var blueColorSliderPosition = mutableStateOf(0f)
    var greenColorSliderPosition = mutableStateOf(0f)
    var alphaColorSliderPosition = mutableStateOf(255f)
    var redColorValue = mutableStateOf(0)
    var blueColorValue = mutableStateOf(0)
    var greenColorValue = mutableStateOf(0)
    var alphaColorValue = mutableStateOf(255)
    var saturationSliderValue = mutableStateOf(1f)
    var saturationSliderPosition = mutableStateOf(1f)
    var doneEditing = mutableStateOf(false)
    var blurSliderValue = mutableStateOf(0f)
    var blurSliderPosition = mutableStateOf(0f)

//    var image = mutableStateOf<Bitmap?>(null)
}