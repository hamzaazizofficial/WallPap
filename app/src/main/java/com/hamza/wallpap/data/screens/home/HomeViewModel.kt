package com.hamza.wallpap.data.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.hamza.wallpap.data.repository.Repository
import com.hamza.wallpap.data.screens.search.SearchChip
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalPagingApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {

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

    val query = MutableStateFlow<String?>("wallpaper")

    var showUserDetails by mutableStateOf(false)

    val itemsFlow = query.flatMapLatest {
        if (it.isNullOrEmpty()) {
            repository.getAllImages()
        } else {
            repository.searchImages(it)
        }
    }
}
