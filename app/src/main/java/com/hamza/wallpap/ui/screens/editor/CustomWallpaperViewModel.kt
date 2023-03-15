package com.hamza.wallpap.ui.screens.editor

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel

class CustomWallpaperViewModel : ViewModel() {
    var boxColor = mutableStateOf(Color(0xF1FFFFFF))

    var wallpaperText = mutableStateOf("")
    var wallpaperTextSize = mutableStateOf(16.sp)
    var wallpaperTextColor = mutableStateOf(Color(0xFF000000))
    var wallpaperTextAlign = mutableStateOf(TextAlign.Center)
    var wallpaperDialogState = mutableStateOf(false)
    var wallpaperTextFontWeight = mutableStateOf(FontWeight.Normal)
    var wallpaperTextDecoration = mutableStateOf(TextDecoration.None)
    var wallpaperTextFontStyle = mutableStateOf(FontStyle.Normal)
    var textSliderPosition = mutableStateOf(0f)
    var transparencySliderPosition = mutableStateOf(0.4f)

    /* Text Format Icons Checked */
    var textAlignCenterChecked = mutableStateOf(false)
    var textAlignRightChecked = mutableStateOf(false)
    var textAlignLeftChecked = mutableStateOf(false)
    var textAlignJustifyChecked = mutableStateOf(false)
    var textFontBoldChecked = mutableStateOf(false)
    var textFontItalicChecked = mutableStateOf(false)
    var textFontUnderlinedChecked = mutableStateOf(false)
    var textFontStrikethroughChecked = mutableStateOf(false)

    var bgColorBottomSheet = mutableStateOf(false)
    var textBottomSheet = mutableStateOf(false)
    var bgImageBottomSheet = mutableStateOf(false)
    var bgImageUrl = mutableStateOf<String?>(null)
    var bgImageTransparency = mutableStateOf(0.4f)
}