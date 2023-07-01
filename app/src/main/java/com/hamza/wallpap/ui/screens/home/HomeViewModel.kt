package com.hamza.wallpap.ui.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import androidx.paging.map
import com.hamza.wallpap.data.repository.Repository
import com.hamza.wallpap.model.UnsplashImage
import com.hamza.wallpap.ui.UnsplashImageUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalPagingApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: Repository,
) : ViewModel() {

    val wallpaperItems = repository.wallpaperItems
    val selectedIndex = repository.selectedIndex
    val query = MutableStateFlow<String?>("digital art")
    var showUserDetails by mutableStateOf(false)

    val itemsFlow =
        query.flatMapLatest {
            if (it.isNullOrEmpty()) {
                repository.getAllImages().cachedIn(viewModelScope)
            } else {
                repository.searchImages(it).cachedIn(viewModelScope)
            }
        }.map { pagingData ->
            Log.d("check", "amshar")
            pagingData.map { image: UnsplashImage ->
                UnsplashImageUI(image = image, height = Random.nextInt(140, 380))
            }
        }
}
