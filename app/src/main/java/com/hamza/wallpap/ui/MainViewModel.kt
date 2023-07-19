package com.hamza.wallpap.ui

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applovin.sdk.AppLovinSdk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
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
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(300)
            _isLoading.value = false
        }
    }
}