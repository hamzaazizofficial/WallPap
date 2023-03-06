package com.hamza.wallpap.ui.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hamza.wallpap.data.repository.Repository
import com.hamza.wallpap.model.UnsplashImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchChip(val title: String, val query: String)

@ExperimentalPagingApi
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

//    val wallpaperItems =
//        arrayListOf(
//            SearchChip("Popular", "wallpaper"),
//            SearchChip("Night", "Night"),
//            SearchChip("Mobile", "Android Wallpapers"),
//            SearchChip("Anime", "Anime"),
//            SearchChip("Dark", "Dark"),
//            SearchChip("Nature", "Nature"),
//        )
//
//    var selectedIndex = mutableStateOf(0)

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchedImages = MutableStateFlow<PagingData<UnsplashImage>>(PagingData.empty())
    val searchedImages = _searchedImages

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchHeroes(query: String) {
        viewModelScope.launch {
            repository.searchImages(query = query).cachedIn(viewModelScope).collect {
                _searchedImages.value = it
            }
        }
    }
}