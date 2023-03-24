package com.hamza.wallpap.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.hamza.wallpap.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalPagingApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: Repository,
) : ViewModel() {

    val wallpaperItems = repository.wallpaperItems
    val selectedIndex = repository.selectedIndex

    val query = MutableStateFlow<String?>("4k wallpapers")

    var showUserDetails by mutableStateOf(false)

    val itemsFlow =
        query.flatMapLatest {
            if (it.isNullOrEmpty()) {
                repository.getAllImages().cachedIn(viewModelScope)
            } else {
                repository.searchImages(it).cachedIn(viewModelScope)
            }
        }
}
