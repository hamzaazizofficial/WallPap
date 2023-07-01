package com.hamza.wallpap.ui.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.hamza.wallpap.data.repository.Repository
import com.hamza.wallpap.model.UnsplashImage
import com.hamza.wallpap.ui.UnsplashImageUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

data class SearchChip(val title: String, val query: String)

@ExperimentalPagingApi
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchedImages = MutableStateFlow<PagingData<UnsplashImage>>(PagingData.empty())
    val searchedImages = _searchedImages.map { pagingData ->
        pagingData.map { image: UnsplashImage ->
            UnsplashImageUI(image = image, height = Random.nextInt(140, 380))
        }
    }

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