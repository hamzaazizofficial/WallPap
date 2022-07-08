package com.hamza.wallpap.data.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hamza.wallpap.data.screens.search.SearchChip

class SearchChipsViewModel : ViewModel() {

    val wallpaperItems =
        arrayListOf(
            SearchChip("Popular", "wallpaper"),
            SearchChip("Night", "Night"),
            SearchChip("Mobile", "Android Wallpapers"),
            SearchChip("Anime", "Anime"),
            SearchChip("Dark", "Dark"),
            SearchChip("Nature", "Nature"),
        )

    var selectedIndex = mutableStateOf(0)

}