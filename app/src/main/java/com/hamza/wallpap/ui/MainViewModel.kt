package com.hamza.wallpap.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(300)
            _isLoading.value = false
        }
    }
}