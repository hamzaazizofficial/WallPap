package com.hamza.wallpap.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val items = listOf(
        "Home",
        "Settings",
        "Email Us",
        "Rate the App",
        "Remove Ads",
        "More Apps",
        "About Us"
    )

    val selectedItem = mutableStateOf(items[0])
}