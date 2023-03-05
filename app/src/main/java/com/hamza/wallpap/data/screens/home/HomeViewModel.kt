package com.hamza.wallpap.data.screens.home

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
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalPagingApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {


//    val clipItems =
//        arrayListOf(
//            SearchChip("Popular", "hd amoled wallpapers"),
//            SearchChip("Night", "Night"),
//            SearchChip("Mobile", "Android Wallpapers"),
//            SearchChip("Anime", "Anime"),
//            SearchChip("Dark", "Dark"),
//            SearchChip("Nature", "Nature"),
//        )
//
//    var selectedIndex = mutableStateOf(0)

    val wallpaperItems = repository.wallpaperItems
    val selectedIndex = repository.selectedIndex

    val query = MutableStateFlow<String?>("hd amoled wallpapers")


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
